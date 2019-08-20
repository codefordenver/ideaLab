package idealab.api.dto;

import static idealab.api.models.Status.*;

import org.springframework.web.multipart.MultipartFile;

public class PrintJobNewRequest {

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
