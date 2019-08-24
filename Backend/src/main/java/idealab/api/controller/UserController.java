package idealab.api.controller;

import idealab.api.model.EmployeeLogins;
import idealab.api.repositories.EmployeeLoginsRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private EmployeeLoginsRepo employeeLoginsRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(EmployeeLoginsRepo employeeLoginsRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeLoginsRepo = employeeLoginsRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody EmployeeLogins user) {
        user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPasswordHash()));
        employeeLoginsRepo.save(user);
    }
}