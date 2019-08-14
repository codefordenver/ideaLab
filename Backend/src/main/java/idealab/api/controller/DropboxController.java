package idealab.api.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DropboxController {
  @Value("${dropbox.ACCESS_TOKEN}")
  private String ACCESS_TOKEN;


  @GetMapping("/api/dropbox")
  ResponseEntity<?> uploadDropbox() throws DbxApiException, DbxException, IOException {
    // Create Dropbox client
    DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

    FullAccount account = client.users().getCurrentAccount();
    System.out.println(account.getName().getDisplayName());

    System.out.println("---- List Files and Folders ----");
    ListFolderResult result = client.files().listFolder("");
    while (true) {
      for (Metadata metadata : result.getEntries()) {
        System.out.println(metadata.getPathLower());
      }

      if (!result.getHasMore()) {
        break;
      }

      result = client.files().listFolderContinue(result.getCursor());
    }

    System.out.println("---- Upload test.txt to dropbox ----");
    try (InputStream in = new FileInputStream(
        "/Users/josh/Projects/ideaLab/Backend/src/main/java/idealab/api/controller/test2.txt")) {
      FileMetadata metadata = client.files().uploadBuilder("/test2-title.txt").uploadAndFinish(in);
      return new ResponseEntity<>("Uploaded file file <p/>" + metadata.toStringMultiline(), HttpStatus.OK);
    } catch (UploadErrorException e) {
      return new ResponseEntity<>("Could not upload file <p/>" + e.toString(), HttpStatus.CONFLICT);
    }
  }
}