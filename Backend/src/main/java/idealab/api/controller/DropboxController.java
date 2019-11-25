package idealab.api.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idealab.api.dto.request.UpdateFilePathRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.model.PrintJob;
import idealab.api.service.FileService;

@RestController
@RequestMapping("/api/dropbox")
public class DropboxController {

    private final FileService fileService;

    public DropboxController(FileService fileService) {
        this.fileService = fileService;
    }

    @PutMapping("/file-path")
    public DataResponse<PrintJob> updateFilePath(@RequestBody UpdateFilePathRequest request) {
        return fileService.updateFilePath(request);
    }

}
