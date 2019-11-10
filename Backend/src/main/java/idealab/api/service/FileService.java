package idealab.api.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import idealab.api.dto.request.UpdateFilePathRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.model.PrintJob;

public interface FileService {

    Map<String, String> uploadFile(Long id, MultipartFile file);
    void deleteFile(String path);
    Map<String, String> updateFile(PrintJob printJob, MultipartFile file);
    DataResponse<PrintJob> updateFilePath(UpdateFilePathRequest request);

}
