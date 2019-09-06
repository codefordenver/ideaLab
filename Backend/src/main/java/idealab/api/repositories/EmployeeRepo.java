package idealab.api.repositories;

import idealab.api.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepo extends CrudRepository<Employee, Integer> {
    Employee findEmployeeById(Integer id);
    Employee findEmployeeByUsernameEquals(String username);
    Employee findEmployeeByUsername(String username);

}
