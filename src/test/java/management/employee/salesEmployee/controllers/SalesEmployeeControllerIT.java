package management.employee.salesEmployee.controllers;

import management.employee.salesEmployee.entities.SalesEmployee;
import management.employee.salesEmployee.mapper.SalesEmployeeMapper;
import management.employee.salesEmployee.model.Position;
import management.employee.salesEmployee.model.SalesAmountDTO;
import management.employee.salesEmployee.model.SalesEmployeeDTO;
import management.employee.salesEmployee.repositories.SalesEmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SalesEmployeeControllerIT {

    @Autowired
    SalesEmployeeController salesEmployeeController;

    @Autowired
    SalesEmployeeRepository salesEmployeeRepository;

    @Autowired
    SalesEmployeeMapper salesEmployeeMapper;

    @Rollback
    @Transactional
    @Test
    void testSaveNewSalesEmployee(){
        SalesEmployeeDTO salesEmployeeDTO = SalesEmployeeDTO.builder().fullName("Test Employee").birthDate(LocalDate.of(2020, 1,2)).position(Position.SALES_ASSOCIATE).build();

        ResponseEntity responseEntity = salesEmployeeController.saveNewSalesEmployee(salesEmployeeDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        Long savedID = Long.parseLong(locationUUID[4]);

        SalesEmployee salesEmployee = salesEmployeeRepository.findById(savedID).get();
        assertThat(salesEmployee).isNotNull();

    }

    @Rollback
    @Transactional
    @Test
    void testUpdateSalesEmployee(){
        SalesEmployee salesEmployee = salesEmployeeRepository.findAll().get(0);
        SalesEmployeeDTO salesEmployeeDTO = salesEmployeeMapper.salesEmployeeToSalesEmployeeDTO(salesEmployee);
        salesEmployeeDTO.setId(null);
        final String newName = "Gina Gee";
        salesEmployeeDTO.setFullName(newName);

        ResponseEntity responseEntity = salesEmployeeController.updateSalesEmployeeById(salesEmployee.getId(),salesEmployeeDTO );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        SalesEmployee updatedSalesEmployee = salesEmployeeRepository.findById(salesEmployee.getId()).get();
        assertThat(updatedSalesEmployee.getFullName()).isEqualTo(newName);

    }

    @Test
    void testUpdateNotFound(){
        assertThrows(NotFoundException.class, () -> salesEmployeeController
                .updateSalesEmployeeById(100L, SalesEmployeeDTO.builder().build()));
    }

    @Test
    void testSalesEmployeeIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            salesEmployeeController.getSalesEmployee(100L);
        });
    }

    @Test
    void computeComissionNotFound() {

        assertThrows(NotFoundException.class, () -> {
            salesEmployeeController.computeCommission(100L, SalesAmountDTO.builder().salesAmount(BigDecimal.ZERO).build());
        });
    }

    @Test
    void testComputeComissionSalesAssociate(){
        SalesEmployee salesEmployee = salesEmployeeRepository.findById(1L).get();
        ResponseEntity responseEntity = salesEmployeeController
                .computeCommission(1L, SalesAmountDTO.builder().salesAmount(BigDecimal.valueOf(100)).build());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        String output = responseEntity.getBody().toString().replaceAll("Employee's commission: ", "");
        BigDecimal bdOutput = BigDecimal.valueOf(Double.valueOf(output)).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertThat(bdOutput).isEqualTo(BigDecimal.valueOf(5).setScale(2, BigDecimal.ROUND_HALF_UP));

    }

    @Test
    void testComputeComissionSeniorSalesAssociate(){
        ResponseEntity responseEntity = salesEmployeeController
                .computeCommission(2L, SalesAmountDTO.builder().salesAmount(BigDecimal.valueOf(100)).build());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        String output = responseEntity.getBody().toString().replaceAll("Employee's commission: ", "");
        BigDecimal bdOutput = BigDecimal.valueOf(Double.valueOf(output)).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertThat(bdOutput).isEqualTo(BigDecimal.valueOf(7).setScale(2, BigDecimal.ROUND_HALF_UP));

    }

    @Test
    void testComputeComissionSeniorSalesAssociateOverTreshold(){
        ResponseEntity responseEntity = salesEmployeeController
                .computeCommission(2L, SalesAmountDTO.builder().salesAmount(BigDecimal.valueOf(60000)).build());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        String output = responseEntity.getBody().toString().replaceAll("Employee's commission: ", "");
        BigDecimal bdOutput = BigDecimal.valueOf(Double.valueOf(output)).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertThat(bdOutput).isEqualTo(BigDecimal.valueOf(4400).setScale(2, BigDecimal.ROUND_HALF_UP));

    }

    @Test
    void testComputeComissionSalesManager(){
        ResponseEntity responseEntity = salesEmployeeController
                .computeCommission(3L, SalesAmountDTO.builder().salesAmount(BigDecimal.valueOf(100)).build());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        String output = responseEntity.getBody().toString().replaceAll("Employee's commission: ", "");
        BigDecimal bdOutput = BigDecimal.valueOf(Double.valueOf(output)).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertThat(bdOutput).isEqualTo(BigDecimal.valueOf(6).setScale(2, BigDecimal.ROUND_HALF_UP));

    }

    @Test
    void testComputeComissionSalesManagerOverTreshold(){
        ResponseEntity responseEntity = salesEmployeeController
                .computeCommission(3L, SalesAmountDTO.builder().salesAmount(BigDecimal.valueOf(210000)).build());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        String output = responseEntity.getBody().toString().replaceAll("Employee's commission: ", "");
        BigDecimal bdOutput = BigDecimal.valueOf(Double.valueOf(output)).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertThat(bdOutput).isEqualTo(BigDecimal.valueOf(13600).setScale(2, BigDecimal.ROUND_HALF_UP));

    }

    @Test
    void testGetSalesEmployeeById() {
        SalesEmployee salesEmployee = salesEmployeeRepository.findAll().get(0);

        SalesEmployeeDTO dto = salesEmployeeController.getSalesEmployee(salesEmployee.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testListSalesEmployee(){
        List<SalesEmployeeDTO> salesEmployeeDTOs = salesEmployeeController.getAllSalesEmployees();

        assertThat(salesEmployeeDTOs.size()).isEqualTo(3);
    }

}