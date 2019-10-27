package idealab.api.controller;

import idealab.api.dto.request.DropBoxFilePathRequest;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.service.DropboxService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dropbox")
public class DropboxController {

    private final DropboxService dropboxService;

    public DropboxController(DropboxService dropboxService) {
        this.dropboxService = dropboxService;
    }

    @PutMapping("/file-path")
    public PrintJobResponse updateFilePath(@RequestBody DropBoxFilePathRequest request) {
        return dropboxService.updateDropboxPath(request);
    }

}
