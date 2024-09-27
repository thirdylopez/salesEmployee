package management.employee.salesEmployee.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import management.employee.salesEmployee.entities.SalesEmployee;
import management.employee.salesEmployee.model.SalesEmployeeDTO;

public interface SalesEmployeeService {

	List<SalesEmployeeDTO> listSalesEmployees();

	Optional<SalesEmployeeDTO> getSalesEmployeeById(Long id);

	SalesEmployeeDTO saveNewSalesEmployee(SalesEmployeeDTO salesEmployeeDTO);

	Optional<SalesEmployeeDTO> updateSalesEmployeeById(Long id, SalesEmployeeDTO salesEmployeeDTO);


	String computeComission(Long id, BigDecimal salesAmount);

}
