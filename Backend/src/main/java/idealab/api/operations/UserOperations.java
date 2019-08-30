package idealab.api.operations;

import idealab.api.dto.response.GenericResponse;
import idealab.api.model.Employee;
import idealab.api.repositories.EmployeeRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import idealab.api.dto.response.GenericResponse;
import idealab.api.model.Employee;
import idealab.api.repositories.EmployeeRepo;

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
            return response;
        }

        response.setSuccess(true);
        response.setMessage("User Sign Up Successful");
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
            } catch (Exception ex) {
                response.setSuccess(false);
                response.setMessage("Employee Could Not Be Deleted");
            }
        } else {
            response.setSuccess(false);
            response.setMessage("Employee ID is not valid");
        }

        return response;
    }
}
