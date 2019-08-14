package idealab.api.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DropboxController {
  @Value("${dropbox.ACCESS_TOKEN}")
  private String ACCESS_TOKEN;

  @GetMapping("/api/dropbox")
  ResponseEntity<?> getDropboxFileList() {
    // Create Dropbox client
    DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

    System.out.println("---- List Files and Folders ----");
    ListFolderResult result;
    try {
      // This current implementation lists both folders and files together
      result = client.files().listFolder("");
    } catch (DbxException e) {
      return new ResponseEntity<>("Unable to return dropbox files", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    HashMap<Number, String> dropboxFiles = new HashMap<>();
    while (true) {
      for (Metadata metadata : result.getEntries()) {
        System.out.println(metadata.getPathLower());
        dropboxFiles.put(metadata.hashCode(), metadata.getPathLower());
      }

      if (!result.getHasMore()) {
        break;
      }

      try {
        result = client.files().listFolderContinue(result.getCursor());
      } catch (DbxException e) {
        return new ResponseEntity<>("Unable to return dropbox files", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    System.out.println(dropboxFiles);
    return new ResponseEntity<>(dropboxFiles, HttpStatus.OK);
  }

  @PostMapping("/api/dropbox")
  ResponseEntity<?> uploadDropboxFile() {
    // Create Dropbox client
    DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

    InputStream in;
    try {
      in = new FileInputStream("/Users/josh/Projects/ideaLab/Backend/src/main/java/idealab/api/controller/test2.txt");
    } catch (FileNotFoundException e) {
      return new ResponseEntity<>("Could not upload file <p/>" + e.toString(), HttpStatus.CONFLICT);
    }

    // TODO See if file already exists.  
    // If file has same content (hash checked) it will be successful, but different content with same name will fail
    FileMetadata metadata;
    try {
      metadata = client.files().uploadBuilder("/test2-title.txt").uploadAndFinish(in);
      return new ResponseEntity<>("Uploaded file <p/>" + metadata.toStringMultiline(), HttpStatus.OK);
    } catch (DbxException | IOException e) {
      return new ResponseEntity<>("Could not upload file <p/>" + e.toString(), HttpStatus.CONFLICT);
    }
  }
}