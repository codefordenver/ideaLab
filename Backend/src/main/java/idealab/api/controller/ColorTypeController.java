package idealab.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idealab.api.dto.request.ColorTypeUpdateRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
import idealab.api.model.ColorType;
import idealab.api.operations.ColorTypeOperations;

@RestController
@RequestMapping("/colors")
public class ColorTypeController {

    private ColorTypeOperations colorOperations;

    public ColorTypeController(ColorTypeOperations colorOperations) {
        this.colorOperations = colorOperations;
    }

    @GetMapping
    public ResponseEntity<?> getActiveColors() {
        DataResponse<ColorType> response = colorOperations.getActiveColors();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    /**
     * This allows the user to change the availability status of the color.
     * Requires input of the color that wants the availability changed + whether it should
     * be available or not.
     * @param printId
     * @param model
     * @return
     */
    @PutMapping("/{color-id}/availability")
    public ResponseEntity<?> colorTypeUpdateAvailability(@PathVariable("color-id") Integer colorId,
                                                  @RequestBody ColorTypeUpdateRequest dto) {

        GenericResponse response = colorOperations.updateColorAvailability(colorId, dto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/inactive")
    public ResponseEntity<?> getInactiveColors() {
        DataResponse<ColorType> response = colorOperations.getInactiveColors();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}