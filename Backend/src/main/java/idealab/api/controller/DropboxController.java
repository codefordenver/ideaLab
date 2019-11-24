package idealab.api.controller;

import idealab.api.dto.request.UpdateFilePathRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
import idealab.api.model.PrintJob;
import idealab.api.operations.PropertyOperations;
import idealab.api.service.FileService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dropbox")
public class DropboxController {

    private final FileService fileService;
    private final PropertyOperations propertyOperations;

    public DropboxController(FileService fileService, PropertyOperations propertyOperations) {
        this.fileService = fileService;
        this.propertyOperations = propertyOperations;
    }

    @PutMapping("/file-path")
    public DataResponse<PrintJob> updateFilePath(@RequestBody UpdateFilePathRequest request) {
        return fileService.updateFilePath(request);
    }

    @PostMapping("/token")
    public GenericResponse updateToken(@RequestBody Map<String, String> mapParam) {
        String token = mapParam.get("token");
        return propertyOperations.updateDropboxToken(token);
    }

}
