package idealab.api.controller;

import idealab.api.dto.response.GetPrintJobDataResponse;
import idealab.api.dto.request.PrintJobNewRequest;
import idealab.api.dto.request.PrintModelUpdateRequest;
import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.operations.PrintJobOperations;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/colortypes")
public class ColorTypesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColorTypesController.class);

    private final ColorTypesController colorTypesController;

    public ColorTypesController(ColorTypesOperations colorTypesOperations) {
        this.colorTypesOperations = colorTypesOperations;
    }

    //get all colors
    @GetMapping
    public ResponseEntity<AllColorsResponse> getAllColors(){
        AllColorsResponse response = colorTypesOperations.getAllColors();

        if(response == null || response.getPrintJobs() == null || response.getPrintJobs().size() == 0){
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    //getAllAvailableColors
    @GetMapping
    public ResponseEntity<AllColorsResponse> getAllAvailableColors(){
        AllColorsResponse response = colorTypesOperations.getAllAvailableColors();

        if(response == null || response.getPrintJobs() == null || response.getPrintJobs().size() == 0){
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    //put ColorTypesAdd new color
    @PostMapping
    public ResponseEntity<?> addColor(AddColorRequest model) {
        LOGGER.info("AddColor request is:" + model.toString());
        ColorResponse response = colorTypesOperations.addColor(model);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }


    //update color availability

}