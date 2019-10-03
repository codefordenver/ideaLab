package idealab.api.controller;

import idealab.api.dto.request.DropBoxFilePathRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.operations.DropboxOperations;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dropbox")
public class DropBoxController {

    private final DropboxOperations dropboxOperations;

    public DropBoxController(DropboxOperations dropboxOperations) {
        this.dropboxOperations = dropboxOperations;
    }

    @PutMapping("/file-path")
    public GenericResponse updateFilePath(@RequestBody DropBoxFilePathRequest request) {
        return dropboxOperations.updateDropboxPath(request);
    }

}
