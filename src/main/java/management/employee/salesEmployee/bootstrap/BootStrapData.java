package management.employee.salesEmployee.bootstrap;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import management.employee.salesEmployee.entities.SalesEmployee;
import management.employee.salesEmployee.model.Position;
import management.employee.salesEmployee.repositories.SalesEmployeeRepository;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final SalesEmployeeRepository salesEmployeeRepository;

    @Override
    public void run(String... args) throws Exception {
        loadSalesEmployeeData();

    }

    private void loadSalesEmployeeData() {
        if (salesEmployeeRepository.count() == 0) {
            SalesEmployee salesEmployee1 = SalesEmployee.builder().fullName("Tina Mae")
                    .birthDate(LocalDate.of(1990, 12, 12)).position(Position.SALES_ASSOCIATE).build();

            SalesEmployee salesEmployee2 = SalesEmployee.builder().fullName("Don Kim")
                    .birthDate(LocalDate.of(1990, 10, 5)).position(Position.SENIOR_SALES_ASSOCIATE).build();

            SalesEmployee salesEmployee3 = SalesEmployee.builder().fullName("Priyanka Lakshmi")
                    .birthDate(LocalDate.of(1988, 2, 6)).position(Position.SALES_MANAGER).build();

            salesEmployeeRepository.saveAll(Arrays.asList(salesEmployee1, salesEmployee2, salesEmployee3));
        }
    }
}
