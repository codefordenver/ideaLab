package idealab.api.service;

import idealab.api.dto.request.DropBoxFilePathRequest;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.model.PrintJob;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class GoogleFileServiceImpl implements FileService {

    @Override
    public Map<String, String> uploadFile(Long id, MultipartFile file) {
        return null;
    }

    @Override
    public void deleteFile(String path) {

    }

    @Override
    public Map<String, String> updateFile(PrintJob printJob, MultipartFile file) {
        return null;
    }

    @Override
    public PrintJobResponse updateFilePath(DropBoxFilePathRequest request) {
        return null;
    }
}
