package idealab.api.controller;

import idealab.api.dto.response.GenericResponse;
import idealab.api.model.Employee;
import idealab.api.operations.UserOperations;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserOperations userOperations;

    public UserController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid Employee user) {
        GenericResponse response = userOperations.userSignUp(user);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        GenericResponse response = userOperations.deleteUser(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    /*
    * the login endpoint is going to be handled by the in the background by spring.
    * */
}