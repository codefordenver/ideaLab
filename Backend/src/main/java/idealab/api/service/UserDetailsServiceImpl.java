package idealab.api.service;

import idealab.api.model.EmployeeLogins;
import idealab.api.repositories.EmployeeLoginsRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private EmployeeLoginsRepo employeeLoginsRepo;

    public UserDetailsServiceImpl(EmployeeLoginsRepo employeeLoginsRepo) {
        this.employeeLoginsRepo = employeeLoginsRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeLogins login = employeeLoginsRepo.getEmployeeLoginsByLogin(username);
        if (login == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(login.getLogin(), login.getPasswordHash(), emptyList());
    }
}