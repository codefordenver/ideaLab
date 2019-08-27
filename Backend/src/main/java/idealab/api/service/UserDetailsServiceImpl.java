package idealab.api.service;

import idealab.api.model.Employee;
import idealab.api.repositories.EmployeeRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private EmployeeRepo employeeRepo;

    public UserDetailsServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee login = employeeRepo.findEmployeeByLoginEquals(username);
        if (login == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(login.getLogin(), login.getPassword(), emptyList());
    }
}