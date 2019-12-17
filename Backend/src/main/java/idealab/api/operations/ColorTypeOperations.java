package idealab.api.operations;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import idealab.api.dto.request.ColorTypeUpdateRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
import idealab.api.exception.ErrorType;
import idealab.api.model.ColorType;
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

    /**
     * Takes in the color id and whether or not it should be considered available. It
     * then updates the color availability.
     * @param colorId
     * @param availability
     * @return
     */
    public GenericResponse updateColorAvailability(Integer colorId, ColorTypeUpdateRequest request) {

        ColorType color = colorTypeRepo.findColorTypeById(colorId);

        if(color == null){
            ErrorType.COLOR_CANT_FIND_BY_TYPE.throwException();
        }

        GenericResponse response = new GenericResponse();

        color.setAvailable(request.getAvailability());
        colorTypeRepo.save(color);

        response.setSuccess(true);
        response.setMessage("Color availability updated");

        return response;
    }
        
    public DataResponse<ColorType> getInactiveColors() {
        DataResponse<ColorType> response = new DataResponse<ColorType>("Could not get inactive color list");

        List<ColorType> colors = colorTypeRepo.findByAvailable(false);

        if (colors == null || colors.isEmpty()){
            ErrorType.COLOR_CANT_FIND_BY_TYPE.throwException();
        }

        response.setSuccess(true);
        response.setMessage("Succesfully returned all unavailable colors");
        response.setData(colors);
        response.setHttpStatus(HttpStatus.ACCEPTED);

        return response;
    }
}
