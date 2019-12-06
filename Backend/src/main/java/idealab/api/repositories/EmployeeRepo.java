package idealab.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import idealab.api.dto.response.BasicEmployee;
import idealab.api.model.Employee;

public interface EmployeeRepo extends CrudRepository<Employee, Integer> {
    Employee findEmployeeById(Integer id);
    Employee findEmployeeByUsernameEquals(String username);
    Employee findEmployeeByUsername(String username);

    /**
     * Gets a list of all employees with first name, last name, username, and role.
     */
    @Query("SELECT new idealab.api.dto.response.BasicEmployee(e.username, e.role, e.firstName, e.lastName) from Employee e")
    List<BasicEmployee> findAllEmployeeBasics();
    void deleteById(Integer id);
}
