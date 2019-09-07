package idealab.api.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class PrintModelUpdateRequest {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
