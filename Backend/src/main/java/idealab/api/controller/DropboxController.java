package idealab.api.controller;

import idealab.api.dto.request.DropBoxFilePathRequest;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.service.FileService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dropbox")
public class DropboxController {

    private final FileService googleFileServiceImpl;

    public DropboxController(FileService googleFileServiceImpl) {
        this.googleFileServiceImpl = googleFileServiceImpl;
    }

    @PutMapping("/file-path")
    public PrintJobResponse updateFilePath(@RequestBody DropBoxFilePathRequest request) {
        return googleFileServiceImpl.updateFilePath(request);
    }

}
