package idealab.api.repositories;

import idealab.api.model.EmployeeLogins;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeLoginsRepo extends CrudRepository<EmployeeLogins, Integer> {
    EmployeeLogins getEmployeeLoginsById(Integer id);
    EmployeeLogins getEmployeeLoginsByLogin(String login);
    EmployeeLogins getEmployeeLoginsByLoginEqualsAndPasswordHashEquals(String login, String passwordHash);
}
