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

@Component
public class ColorTypesOperations {

    public ColorTypesOperations () {
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

        // Create new record based off of the printJobNewRequest
        String color = color.getColor();
        Boolean available = color.getAvailable();


        // Check if Color exists otherwise make a new record
//        ColorType databaseColor = colorTypeRepo.findByColor(color);
//        if (databaseColor == null) {
//            databaseColor = new ColorType(color);
//            colorTypeRepo.save(databaseColor);
//        }

        // Create a new print model first with temp dropbox link
        ColorType colorType = new ColorType(color);
        colorTypeRepo.save(colorType);

        response.setSuccess(true);
        response.setMessage("Successfully saved new color to database!");
        response.setData(color);
        response.setHttpStatus(HttpStatus.ACCEPTED);
        return response;
}