package idealab.api.operations;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.ListSharedLinksErrorException;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;

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

  public String getSharableLink(String filePath) throws ListSharedLinksErrorException, DbxException {
    ListSharedLinksResult listSharedLinksResult;
      // Gets current shared links
      listSharedLinksResult = client.sharing().listSharedLinksBuilder().withPath(filePath).withDirectOnly(true).start();
      if (listSharedLinksResult.getLinks().isEmpty()) {
        // If no shared link exists create one
        @SuppressWarnings("unused")
        SharedLinkMetadata sharedLinkMetadata = client.sharing().createSharedLinkWithSettings(filePath);
        listSharedLinksResult = client.sharing().listSharedLinksBuilder().withPath(filePath).withDirectOnly(true)
            .start();
      }
      return listSharedLinksResult.getLinks().get(0).getUrl();
  }

  public String uploadDropboxFile(int id, MultipartFile file) {
    String dropboxFilePath = "/" + id + "-" + file.getOriginalFilename();
    InputStream in;
    FileMetadata metadata;

    try {
      in = new BufferedInputStream(file.getInputStream());
    } catch (IOException e) {
      return "Upload Error";
    }

    // TODO See if file already exists to delete and replace it.
    try {
      metadata = client.files().uploadBuilder(dropboxFilePath).uploadAndFinish(in);
      String filePath = metadata.getPathLower();
      String sharableLink = getSharableLink(filePath);

      return sharableLink;
    } catch (DbxException | IOException e) {
      return "Link Error";
    }
  }

  public String deleteDropboxFile(int id, MultipartFile file) {
    //TODO:  Delete a dropbox file based on print job id (or filepath?)
    return null;
  }

  public String updateDropboxFile(int id, MultipartFile file) {
    //TODO: Update a dropbox file, should return a new sharable link.

    // 1. Delete existing dropbox file using deleteDropboxFile() method

    // 2. Create new dropbox file using uploadDropboxFile() method

    // 3. Create a new sharable link using the getSharableLink method
    return null;
  }

  // TODO: Determine if we even need to return every file on dropbox. All the data
  // should be stored in the database now.

  // public HashMap<Number, String> getDropboxFileList() {
  // // Create Dropbox client
  // System.out.println("---- List Files and Folders ----");
  // ListFolderResult result;
  // try {
  // // This current implementation lists both folders and files together
  // result = client.files().listFolder("");
  // } catch (DbxException e) {
  // return null;
  // }

  // HashMap<Number, String> dropboxFiles = new HashMap<>();
  // while (true) {
  // for (Metadata metadata : result.getEntries()) {
  // System.out.println(metadata.getPathLower());
  // dropboxFiles.put(metadata.hashCode(), metadata.getPathLower());
  // }

  // if (!result.getHasMore()) {
  // break;
  // }

  // try {
  // result = client.files().listFolderContinue(result.getCursor());
  // } catch (DbxException e) {
  // return null;
  // }
  // }
  // return dropboxFiles;
  // }

  // TODO: Figure out if we want to download the files. Currently the downloader
  // will save to a temp folder, however the stream to fontend is not working.

  // public File downloadFile(String filename) {
  // try {
  // File file = new File("temp_files/" + filename);
  // FileOutputStream out = new FileOutputStream(file);
  // DbxDownloader<FileMetadata> downloader = client.files().download("/" +
  // filename);
  // downloader.download(out);

  // FileInputStream fis = new FileInputStream(file);

  // byte[] b = new byte[256];
  // int i=0;
  // String result = "";
  // while ((i = fis.read(b)) != -1) {
  // result += new String(b);
  // System.out.print(new String(b));
  // }
  // fis.close();
  // file.delete();

  // return result;
  // } catch (DbxException | IOException e) {
  // return null;
  // }
  // }
}