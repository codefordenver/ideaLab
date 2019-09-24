package idealab.api.operations;

import com.dropbox.core.DbxException;
import idealab.api.dto.ColorResponse;
import idealab.api.dto.AllColorsResponse;
import idealab.api.model.*;
import idealab.api.repositories.ColorTypeRepo;
//import idealab.api.repositories.CustomerInfoRepo;
//import idealab.api.repositories.EmailHashRepo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.dto.response.GetAllPrintJobResponse;
import idealab.api.model.Employee;
import idealab.api.model.PrintJob;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintJobRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Service
public class ColorTypesOperations {
    private final ColorTypeRepo colorTypeRepo;

    public final ColorTypesOperations(ColorTypeRepo colorTypeRepo) {
        this.colorTypeRepo = colorTypeRepo;
    }

    public ColorTypesOperations() {
    }

    AllColorsResponse getAllColors() {
        //TODO: Temporary
        return new AllColorsResponse();
    }

    AllColorsResponse getAllAvailableColors() {
        //TODO: Temporary
        return new AllColorsResponse();
    }

    public ColorResponse addColor(AddColorRequest color) {
        ColorResponse response = new ColorResponse();
        response.setSuccess(false);
        response.setMessage("Color could not be uploaded");
        response.setHttpStatus(HttpStatus.BAD_REQUEST);

        //need to check for existence of color first
        String color = color.getColor();
        Boolean available = color.getAvailable();

        ColorType colorType = new ColorType(color, available);
        colorTypeRepo.save(colorType);

        response.setSuccess(true);
        response.setMessage("Successfully saved new color to database!");
        response.setData(color);
        response.setHttpStatus(HttpStatus.ACCEPTED);
        return response;
    }

    public ColorResponse updateColor(Integer colorId, UpdateColorRequest model) {
        ColorResponse response = new ColorResponse();

        ColorType color = colorTypeRepo.findbyColorId(colorId);

        color.setColor(model.GetColor());
        color.setAvailable(model.GetAvailable());

        color = colorTypeRepo.save(color);

        response.setSuccess(true);
        response.setMessage("Successfully updated color");
        response.setData(color);

        response.setHttpStatus(HttpStatus.ACCEPTED);
        return response;
    }
}