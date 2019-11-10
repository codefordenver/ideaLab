package idealab.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import idealab.api.model.Employee;

public interface EmployeeRepo extends CrudRepository<Employee, Integer> {
    Employee findEmployeeById(Integer id);
    Employee findEmployeeByUsernameEquals(String username);
    Employee findEmployeeByUsername(String username);

    /**
     * Gets a list of all users but only returns name, username, and role.
     */
    @Query("select firstName, lastName, username, role from Employee")
    List<Object[]> findAllSimple();
    void deleteById(Integer id);
}
