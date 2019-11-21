package idealab.api.repositories;

import idealab.api.dto.response.BasicEmployee;
import idealab.api.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepo extends CrudRepository<Employee, Integer> {
    Employee findEmployeeById(Integer id);
    Employee findEmployeeByUsernameEquals(String username);
    Employee findEmployeeByUsername(String username);

    /**
     * Gets a list of all employees with first name, last name, username, and role.
     */
    @Query(value = "SELECT new idealab.api.dto.response.BasicEmployee(e.firstName, e.lastName, e.username, e.role) " +
            "from Employee e")
    List<BasicEmployee> findAllEmployeeBasics();

    void deleteById(Integer id);
}
