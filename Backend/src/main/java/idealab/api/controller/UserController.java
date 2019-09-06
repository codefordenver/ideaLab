package idealab.api.controller;

import idealab.api.dto.response.GenericResponse;
import idealab.api.model.Employee;
import idealab.api.operations.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserOperations userOperations;

    public UserController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody Employee user) {
        GenericResponse response = userOperations.userSignUp(user);
        if(response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    * the login endpoint is going to be handled by the in the background by spring.
    * */
}