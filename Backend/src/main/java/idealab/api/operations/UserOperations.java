package idealab.api.operations;

import idealab.api.dto.response.GenericResponse;
import idealab.api.model.Employee;
import idealab.api.repositories.EmployeeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserOperations {

    private EmployeeRepo employeeRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserOperations(EmployeeRepo employeeRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeRepo = employeeRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public GenericResponse userSignUp(Employee login) {
        GenericResponse response = new GenericResponse();

        try {
            login.setPassword(bCryptPasswordEncoder.encode(login.getPassword()));
            employeeRepo.save(login);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("User Sign Up Failed");
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            return response;
        }

        response.setSuccess(true);
        response.setMessage("User Sign Up Successful");
        response.setHttpStatus(HttpStatus.CREATED);
        return response;
    }

    public GenericResponse deleteUser(Integer id) {
        GenericResponse response = new GenericResponse();
        Employee e = employeeRepo.findEmployeeById(id);

        if(e != null) {
            try {
                employeeRepo.deleteById(id);
                response.setSuccess(true);
                response.setMessage("Employee Deleted Successfully");
                response.setHttpStatus(HttpStatus.ACCEPTED);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setSuccess(false);
                response.setMessage("Employee Could Not Be Deleted");
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
            }
        } else {
            response.setSuccess(false);
            response.setMessage("Employee ID is not valid");
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
