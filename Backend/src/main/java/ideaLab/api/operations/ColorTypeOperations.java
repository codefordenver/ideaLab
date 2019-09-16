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
        return new AllColorsResponse();
    }


}