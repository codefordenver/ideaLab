package idealab.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import idealab.api.dto.response.BasicEmployeeResponse.EmployeeBasic;
import idealab.api.model.Employee;

public interface EmployeeRepo extends CrudRepository<Employee, Integer> {
    Employee findEmployeeById(Integer id);
    Employee findEmployeeByUsernameEquals(String username);
    Employee findEmployeeByUsername(String username);

    /**
     * Gets a list of all employees with first name, last name, username, and role.
     */
    @Query(value = "SELECT first_name as firstName, last_name as lastName, username, role from Employee", nativeQuery = true)
    List<EmployeeBasic> findAllEmployeeBasics();
    void deleteById(Integer id);
}
