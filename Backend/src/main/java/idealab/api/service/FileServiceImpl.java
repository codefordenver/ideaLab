package idealab.api.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.RelocationResult;
import com.dropbox.core.v2.sharing.ListSharedLinksErrorException;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import idealab.api.dto.request.UpdateFilePathRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.PrintJob;
import idealab.api.model.Property;
import idealab.api.repositories.PrintJobRepo;
import idealab.api.repositories.PropertyRepo;
import idealab.api.security.RegexUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static idealab.api.exception.ErrorType.*;

@Service
public class FileServiceImpl implements FileService {

  private final PropertyRepo propertyRepo;
  private DbxClientV2 client;
  private PrintJobRepo printJobRepo;

  public FileServiceImpl(PrintJobRepo printJobRepo, PropertyRepo propertyRepo) {
    this.printJobRepo = printJobRepo;
    this.propertyRepo = propertyRepo;
  }

  @PostConstruct
  public void postConstruct() {
    updateConfig();
  }

  public GenericResponse updateToken(String token) {
    if(token == null || token.isEmpty())
      throw new IdeaLabApiException(VALIDATION_ERROR, "Missing token paramater");

    if(!token.matches(RegexUtil.alphaNumericHyphenUnderscore))
      throw new IdeaLabApiException(VALIDATION_ERROR, "Token failed regex validation");

    GenericResponse response = new GenericResponse();
    try {
      Property property = propertyRepo.findByName("dropbox_access_token");
      property.setValue(token);
      propertyRepo.save(property);
      updateConfig();
    } catch (Exception e) {
      response.setSuccess(false);
      response.setMessage("Token failed to update due to server error: " + e.getMessage());
      response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      return response;
    }

    response.setSuccess(true);
    response.setMessage("Token updated successfully");
    response.setHttpStatus(HttpStatus.ACCEPTED);
    return response;
  }

  private void updateConfig() {
    DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
    client = new DbxClientV2(config, propertyRepo.findByName("dropbox_access_token").getValue());
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

  public Map<String, String> uploadFile(Long id, MultipartFile file){
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

  public void deleteFile(String dropboxPath){
    try{
      client.files().deleteV2(dropboxPath);
    }
    catch (DbxException e) {
      throw new IdeaLabApiException(DROPBOX_DELETE_FILE_ERROR);
    }
  }

  public Map<String, String> updateFile(PrintJob printJob, MultipartFile file){
    // 1. Delete existing dropbox file using deleteDropboxFile() method
    // Note:  if the file path does not start with a "/" then there is either an error or it was deleted already.
    if(printJob.getFilePath().startsWith("/")){
      deleteFile(printJob.getFilePath());
    }
    // 2. Create new sharable data link using uploadDropboxFile() method
    LocalDateTime currentTime = LocalDateTime.now();
    return uploadFile(currentTime.toLocalTime().toNanoOfDay(), file);
  }

  public DataResponse<PrintJob> updateFilePath(UpdateFilePathRequest request) {
    request.validate();

    PrintJob printJob = printJobRepo.findPrintJobById(request.getPrintJobId());

    if(printJob == null)
      throw new IdeaLabApiException(PRINT_JOB_CANT_FIND_BY_ID);

    LocalDateTime currentTime = LocalDateTime.now();
    String newPath = "/" + currentTime.toLocalTime().toNanoOfDay() + "-" + request.getNewPath();
    String oldPath = printJob.getFilePath();

    try {
      //Move file to new location in dropbox, update database
      RelocationResult result = client.files().moveV2(oldPath, newPath);
      Metadata metaData = result.getMetadata();
      if(metaData.getPathDisplay().equalsIgnoreCase(newPath)) {
        printJob.setFilePath(metaData.getPathDisplay());
        String sharableLink = getSharableLink(metaData.getPathLower());
        printJob.setFileSharableLink(sharableLink);
        printJob = printJobRepo.save(printJob);
      }
    } catch (DbxException e) {
      e.printStackTrace();
      throw new IdeaLabApiException(DROPBOX_UPDATE_FILE_ERROR);
    }

    DataResponse<PrintJob> response = new DataResponse<PrintJob>();
    response.setMessage("Dropbox file path updated successfully");
    response.setSuccess(true);
    response.setHttpStatus(HttpStatus.ACCEPTED);

    List<PrintJob> printJobs = new ArrayList<>();
    printJobs.add(printJob);
    response.setData(printJobs);
    return response;
  }

}
