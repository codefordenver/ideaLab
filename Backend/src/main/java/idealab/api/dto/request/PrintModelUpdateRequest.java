package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;
import org.springframework.web.multipart.MultipartFile;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class PrintModelUpdateRequest implements GenericRequest {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "PrintModelUpdateRequest{" +
                "file=" + file.getOriginalFilename() +
                '}';
    }

    @Override
    public void validate() {
        if(this.file == null || this.file.isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "File is invalid");
        if(!this.file.getOriginalFilename().toLowerCase().endsWith(".stl"))
            throw new IdeaLabApiException(VALIDATION_ERROR, "File must be .stl");
    }
}
