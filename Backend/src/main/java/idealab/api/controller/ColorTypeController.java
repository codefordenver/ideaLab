package idealab.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idealab.api.dto.response.DataResponse;
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

    @GetMapping("/inactive")
    public ResponseEntity<?> getInactiveColors() {
        DataResponse<ColorType> response = colorOperations.getInactiveColors();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}