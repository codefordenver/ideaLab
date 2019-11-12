package idealab.api.operations;

import static idealab.api.exception.ErrorType.PRINT_JOB_CANT_FIND_BY_ID;
import static idealab.api.exception.ErrorType.PRINT_JOB_UPDATE_FAILED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import idealab.api.dto.request.UpdatePrintJobPropertiesRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.exception.ErrorType;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.ColorType;
import idealab.api.model.PrintJob;
import idealab.api.model.Status;
import idealab.api.repositories.ColorTypeRepo;

@Service
public class ColorTypeOperations {
    private final ColorTypeRepo colorTypeRepo;

    public ColorTypeOperations(ColorTypeRepo colorTypeRepo) {
        this.colorTypeRepo = colorTypeRepo;
    }

    public DataResponse<ColorType> getActiveColors() {
        DataResponse<ColorType> response = new DataResponse<ColorType>("Could not get color list");

        List<ColorType> colors = colorTypeRepo.findByAvailable(true);

        if (colors == null || colors.isEmpty()){
            ErrorType.COLOR_CANT_FIND_BY_TYPE.throwException();
        }

        response.setSuccess(true);
        response.setMessage("Succesfully returned all available colors");
        response.setData(colors);
        response.setHttpStatus(HttpStatus.ACCEPTED);

        return response;
    }
}
