package idealab.api.dto;

import org.springframework.web.multipart.MultipartFile;

public class PrintModel {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
