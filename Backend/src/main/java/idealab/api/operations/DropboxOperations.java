package idealab.api.operations;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.ListSharedLinksErrorException;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;

import idealab.api.model.PrintJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@PropertySource("classpath:dropbox.properties")
public class DropboxOperations {

  @Value("${dropbox.ACCESS_TOKEN}")
  private String ACCESS_TOKEN;
  DbxRequestConfig config;
  DbxClientV2 client;

  @PostConstruct
  public void postConstruct() {
    config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
    client = new DbxClientV2(config, ACCESS_TOKEN);
  }

  public ListSharedLinksResult getListSharedLinkResult(String filePath)
      throws ListSharedLinksErrorException, DbxException {
    return client.sharing().listSharedLinksBuilder().withPath(filePath).withDirectOnly(true).start();
  }

  public String getSharableLink(String filePath) throws ListSharedLinksErrorException, DbxException {
    ListSharedLinksResult listSharedLinksResult = getListSharedLinkResult(filePath);

    if (listSharedLinksResult.getLinks().isEmpty()) {
      // If no shared link exists create one
      @SuppressWarnings("unused")
      SharedLinkMetadata sharedLinkMetadata = client.sharing().createSharedLinkWithSettings(filePath);
      listSharedLinksResult = getListSharedLinkResult(filePath);
    }
    return listSharedLinksResult.getLinks().get(0).getUrl();
  }

  public Map<String, String> uploadDropboxFile(int id, MultipartFile file) throws IOException, DbxException {
    String dropboxFilePath = "/" + id + "-" + file.getOriginalFilename();

    InputStream in = new BufferedInputStream(file.getInputStream());
    FileMetadata metadata = client.files().uploadBuilder(dropboxFilePath).uploadAndFinish(in);
    String filePath = metadata.getPathLower();
    String sharableLink = getSharableLink(filePath);

    Map<String, String> data = new HashMap<>();

    data.put("filePath", filePath);
    data.put("sharableLink", sharableLink);
    return data;
  }

  public void deleteDropboxFile(PrintJob printJob) throws DbxException {
    DeleteResult deleteResult = client.files().deleteV2(printJob.getDropboxPath());
  }

  public Map<String, String> updateDropboxFile(PrintJob printJob, MultipartFile file) throws DbxException, IOException {
    // 1. Delete existing dropbox file using deleteDropboxFile() method
    // Note:  if the file path does not start with a "/" then there is either an error or it was deleted already.
    if(printJob.getDropboxPath().startsWith("/")){
      deleteDropboxFile(printJob);
    }
    // 2. Create new sharable data link using uploadDropboxFile() method
    Map<String, String> data = uploadDropboxFile(printJob.getId(), file);

    return data;
  }
}