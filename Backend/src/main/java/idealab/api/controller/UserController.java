package idealab.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idealab.api.dto.request.EmployeeSignUpRequest;
import idealab.api.dto.request.UserChangePasswordRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.UserResponse;
import idealab.api.operations.UserOperations;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserOperations userOperations;

    public UserController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody EmployeeSignUpRequest user) {
        GenericResponse response = userOperations.userSignUp(user);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        GenericResponse response = userOperations.deleteUser(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody UserChangePasswordRequest request) {
        GenericResponse response = userOperations.changePassword(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping()
    public ResponseEntity<?> getUsers() {
        UserResponse response = userOperations.getAllUsers();
        
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    /*
    * the login endpoint is going to be handled by the in the background by spring.
    * */
}