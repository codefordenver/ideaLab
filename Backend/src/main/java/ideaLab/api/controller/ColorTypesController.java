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
    public ResponseEntity<?> getAllColors(){
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

    //post updateColorAvailability
    //put ColorTypesAdd new color

    @PostMapping
    public ResponseEntity<?> printJobNew(@ModelAttribute PrintJobNewRequest model) {
        LOGGER.info("PrintJobNew request is:" + model.toString());
        GetPrintJobDataResponse response = printJobOperations.newPrintJob(model);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/{printId}/model")
    public ResponseEntity<?> printJobUpdateModel(@PathVariable("printId") Integer printId,
                                                 @ModelAttribute PrintModelUpdateRequest model) {

        LOGGER.info("PrintJobUpdateModel request is job:" + printId.toString() + "| model: " + model.toString());
        GetPrintJobDataResponse response = printJobOperations.updateModel(printId, model);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{printId}/model")
    public ResponseEntity<?> printJobDeleteModel(@PathVariable("printId") Integer printId) {
        LOGGER.info("PrintJobDeleteModel request is " + printId.toString());
        GenericResponse response = printJobOperations.deleteModel(printId);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/{printId}/status")
    public ResponseEntity<?> printJobUpdateStatus(@PathVariable ("printId") Integer printId, @RequestBody PrintJobUpdateRequest dto)
    {
        LOGGER.info("PrintJobUpdateStatus request is " + dto.toString());

        GenericResponse response = printJobOperations.updatePrintJobStatus(printId, dto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping
    public ResponseEntity<?> printJobDelete(@RequestBody PrintJobDeleteRequest dto)
    {
        LOGGER.info("PrintJobDelete request is " + dto.toString());

        GenericResponse response = printJobOperations.deletePrintJobStatus(dto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}