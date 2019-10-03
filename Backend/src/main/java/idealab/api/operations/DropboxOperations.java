package idealab.api.operations;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.RelocationResult;
import com.dropbox.core.v2.sharing.ListSharedLinksErrorException;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import idealab.api.dto.request.DropBoxFilePathRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.PrintJob;
import idealab.api.repositories.PrintJobRepo;
import idealab.configurations.DropboxConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static idealab.api.exception.ErrorType.*;

@Component
public class DropboxOperations {
  private final DropboxConfiguration dropboxConfig;
  private DbxClientV2 client;
  private PrintJobRepo printJobRepo;

  public DropboxOperations(DropboxConfiguration dropboxConfig, PrintJobRepo printJobRepo) {
    this.dropboxConfig = dropboxConfig;
    this.printJobRepo = printJobRepo;
  }

  @PostConstruct
  public void postConstruct() {
    DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
    client = new DbxClientV2(config, dropboxConfig.getAccessToken());
  }

  private ListSharedLinksResult getListSharedLinkResult(String filePath) throws ListSharedLinksErrorException, DbxException {
    return client.sharing()
            .listSharedLinksBuilder()
            .withPath(filePath)
            .withDirectOnly(true)
            .start();
  }

  private String getSharableLink(String filePath){
    ListSharedLinksResult listSharedLinksResult = null;

    try{
      listSharedLinksResult = getListSharedLinkResult(filePath);

      if (listSharedLinksResult.getLinks().isEmpty()) {
        // If no shared link exists create one
        client.sharing().createSharedLinkWithSettings(filePath);
        listSharedLinksResult = getListSharedLinkResult(filePath);
      }
    }catch (Exception ex){
      throw new IdeaLabApiException(DROPBOX_UPLOAD_FILE_ERROR);
    }

    return listSharedLinksResult.getLinks().get(0).getUrl();
  }

  public Map<String, String> uploadDropboxFile(Long id, MultipartFile file){
    String dropboxFilePath = "/" + id + "-" + file.getOriginalFilename();
    Map<String, String> data = new HashMap<>();

    try{
      InputStream in = new BufferedInputStream(file.getInputStream());
      FileMetadata metadata = client.files().uploadBuilder(dropboxFilePath).uploadAndFinish(in);

      String filePath = metadata.getPathLower();
      String sharableLink = getSharableLink(filePath);

      data.put("filePath", filePath);
      data.put("sharableLink", sharableLink);

      in.close();
    }
    catch(Exception ex){
      throw new IdeaLabApiException(DROPBOX_UPLOAD_FILE_ERROR);
    }

    return data;
  }

  public void deleteDropboxFile(PrintJob printJob){
    try{
      client.files().deleteV2(printJob.getDropboxPath());
    }
    catch (DbxException e) {
      throw new IdeaLabApiException(DROPBOX_DELETE_FILE_ERROR);
    }
  }

  public Map<String, String> updateDropboxFile(PrintJob printJob, MultipartFile file){
    // 1. Delete existing dropbox file using deleteDropboxFile() method
    // Note:  if the file path does not start with a "/" then there is either an error or it was deleted already.
    if(printJob.getDropboxPath().startsWith("/")){
      deleteDropboxFile(printJob);
    }
    // 2. Create new sharable data link using uploadDropboxFile() method
    LocalDateTime currentTime = LocalDateTime.now();
    return uploadDropboxFile(currentTime.toLocalTime().toNanoOfDay(), file);
  }

  public GenericResponse updateDropboxPath(DropBoxFilePathRequest request) {
    PrintJob printJob = printJobRepo.findPrintJobById(request.getPrintJobId());

    if(printJob == null)
      throw new IdeaLabApiException(PRINT_JOB_CANT_FIND_BY_ID);

    String oldPath = printJob.getDropboxPath();

    try {
      RelocationResult result = client.files().copyV2(oldPath, request.getNewPath());
      Metadata metaData = result.getMetadata();
      if(metaData.getPathDisplay().equalsIgnoreCase(request.getNewPath())) {
        printJob.setDropboxPath(metaData.getPathDisplay());
        printJob = printJobRepo.save(printJob);
      }
    } catch (DbxException e) {
      e.printStackTrace();
      throw new IdeaLabApiException(DROPBOX_UPDATE_FILE_ERROR);
    }

    GenericResponse response = new GenericResponse();
    response.setMessage("Dropbox file path updated successfully");
    response.setSuccess(true);
    response.setHttpStatus(HttpStatus.ACCEPTED);
    return response;
  }

}