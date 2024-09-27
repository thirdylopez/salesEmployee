package management.employee.salesEmployee.controllers;

import management.employee.salesEmployee.model.SalesAmountDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import management.employee.salesEmployee.model.SalesEmployeeDTO;
import management.employee.salesEmployee.services.SalesEmployeeService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SalesEmployeeController {

    public static final String SALES_EMPLOYEE_PATH = "/api/v1/salesEmployee";
    public static final String SALES_EMPLOYEE_PATH_ID = SALES_EMPLOYEE_PATH + "/{salesEmployeeId}";

    public static final String SALES_EMPLOYEE_PATH_ID_COMMISSION = SALES_EMPLOYEE_PATH_ID + "/commission";

    private final SalesEmployeeService salesEmployeeService;

    @GetMapping(value = SALES_EMPLOYEE_PATH_ID)
    public SalesEmployeeDTO getSalesEmployee(@PathVariable("salesEmployeeId") Long salesEmployeeId) {
        return salesEmployeeService.getSalesEmployeeById(salesEmployeeId).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = SALES_EMPLOYEE_PATH)
    public List<SalesEmployeeDTO> getAllSalesEmployees() {
        return salesEmployeeService.listSalesEmployees();
    }

    @PostMapping(value = SALES_EMPLOYEE_PATH)
    public ResponseEntity saveNewSalesEmployee(@RequestBody SalesEmployeeDTO salesEmployeeDTO) {
        SalesEmployeeDTO savedSalesEmployeeDTO = salesEmployeeService.saveNewSalesEmployee(salesEmployeeDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", SALES_EMPLOYEE_PATH + "/"
                        + savedSalesEmployeeDTO.getId())
                .body("Sales Employee has been added with Id = " + savedSalesEmployeeDTO.getId());
    }

    @PutMapping(value = SALES_EMPLOYEE_PATH_ID)
    public ResponseEntity updateSalesEmployeeById(@PathVariable("salesEmployeeId") Long salesEmployeeId, @RequestBody SalesEmployeeDTO salesEmployeeDTO) {
        SalesEmployeeDTO savedSalesEmployee = salesEmployeeService.updateSalesEmployeeById(salesEmployeeId, salesEmployeeDTO).
                orElseThrow(NotFoundException::new);

        return ResponseEntity.status(HttpStatus.OK).header("Location", SALES_EMPLOYEE_PATH + "/"
                        + savedSalesEmployee.getId())
                .body("Sales Employee has been updated with Id = " + savedSalesEmployee.getId());
    }

    @PostMapping(value = SALES_EMPLOYEE_PATH_ID_COMMISSION)
    public ResponseEntity computeCommission(@PathVariable("salesEmployeeId") Long salesEmployeeId, @RequestBody SalesAmountDTO salesAmount){
        String output = salesEmployeeService.computeComission(salesEmployeeId, salesAmount.getSalesAmount());
        if(output.isEmpty()){
            throw new NotFoundException("Sales Employee is not Found.");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("Location", SALES_EMPLOYEE_PATH + "/"
                        + salesEmployeeId + "/commission")
                .body(output);

    }
}
