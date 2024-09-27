package management.employee.salesEmployee.mapper;

import org.mapstruct.Mapper;

import management.employee.salesEmployee.entities.SalesEmployee;
import management.employee.salesEmployee.model.SalesEmployeeDTO;

@Mapper
public interface SalesEmployeeMapper {

	SalesEmployee salesEmployeeDTOToSalesEmployee(SalesEmployeeDTO salesEmployeeDTO);

	SalesEmployeeDTO salesEmployeeToSalesEmployeeDTO(SalesEmployee salesEmployee);

}
