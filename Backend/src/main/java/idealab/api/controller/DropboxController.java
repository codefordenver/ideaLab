package idealab.api.controller;

import idealab.api.dto.request.UpdateFilePathRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
import idealab.api.model.PrintJob;
import idealab.api.service.FileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/token")
    @PreAuthorize("hasRole('ADMIN')")
    public GenericResponse updateToken(@RequestBody Map<String, String> mapParam) {
        String token = mapParam.get("token");
        return fileService.updateToken(token);
    }

}
