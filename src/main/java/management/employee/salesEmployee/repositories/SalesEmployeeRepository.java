package management.employee.salesEmployee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import management.employee.salesEmployee.entities.SalesEmployee;

public interface SalesEmployeeRepository extends JpaRepository<SalesEmployee, Long> {

}
