package management.employee.salesEmployee.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Builder
@Data
public class SalesEmployeeDTO {

	private Long id;
	private String fullName;
	private LocalDate birthDate;
	private Position position;

}
