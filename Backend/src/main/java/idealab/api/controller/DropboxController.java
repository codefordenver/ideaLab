package idealab.api.controller;

import idealab.api.dto.request.UpdateFilePathRequest;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.service.FileService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dropbox")
public class DropboxController {

    private final FileService fileService;

    public DropboxController(FileService fileService) {
        this.fileService = fileService;
    }

    @PutMapping("/file-path")
    public PrintJobResponse updateFilePath(@RequestBody UpdateFilePathRequest request) {
        return fileService.updateFilePath(request);
    }

}
