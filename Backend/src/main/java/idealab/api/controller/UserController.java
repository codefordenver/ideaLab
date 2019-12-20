package idealab.api.controller;

import idealab.api.dto.request.EmployeeUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import idealab.api.dto.request.EmployeeSignUpRequest;
import idealab.api.dto.request.UserChangePasswordRequest;
import idealab.api.dto.response.BasicEmployee;
import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
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

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody EmployeeUpdateRequest request) {
        GenericResponse response = userOperations.updateUser(request);
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
        DataResponse<BasicEmployee> response = userOperations.getAllUsers();
        
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    /*
    * the login endpoint is going to be handled by the in the background by spring.
    * */
}