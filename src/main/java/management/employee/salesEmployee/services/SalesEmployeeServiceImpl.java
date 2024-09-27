package management.employee.salesEmployee.services;

import lombok.RequiredArgsConstructor;
import management.employee.salesEmployee.controllers.NotFoundException;
import management.employee.salesEmployee.entities.SalesEmployee;
import management.employee.salesEmployee.mapper.SalesEmployeeMapper;
import management.employee.salesEmployee.model.SalesEmployeeDTO;
import management.employee.salesEmployee.repositories.SalesEmployeeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesEmployeeServiceImpl implements SalesEmployeeService {

    private final SalesEmployeeRepository salesEmployeeRepository;
    private final SalesEmployeeMapper salesEmployeeMapper;


    @Override
    public List<SalesEmployeeDTO> listSalesEmployees() {
        return salesEmployeeRepository.findAll()
                .stream()
                .map(salesEmployeeMapper::salesEmployeeToSalesEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SalesEmployeeDTO> getSalesEmployeeById(Long id) {
        return Optional.ofNullable(salesEmployeeMapper
                .salesEmployeeToSalesEmployeeDTO(
                        salesEmployeeRepository.findById(id).orElse(null)));
    }

    @Override
    public SalesEmployeeDTO saveNewSalesEmployee(SalesEmployeeDTO salesEmployeeDTO) {
        return salesEmployeeMapper.salesEmployeeToSalesEmployeeDTO(salesEmployeeRepository
                .save(salesEmployeeMapper
                        .salesEmployeeDTOToSalesEmployee(salesEmployeeDTO)));
    }

    @Override
    public Optional<SalesEmployeeDTO> updateSalesEmployeeById(Long id, SalesEmployeeDTO salesEmployeeDTO) {

        AtomicReference<Optional<SalesEmployeeDTO>> atomicReference = new AtomicReference<>();

        salesEmployeeRepository.findById(id).ifPresentOrElse(foundEmployee -> {
                    foundEmployee.setFullName(salesEmployeeDTO.getFullName());
                    foundEmployee.setBirthDate(salesEmployeeDTO.getBirthDate());
                    foundEmployee.setPosition(salesEmployeeDTO.getPosition());
                    atomicReference.set(Optional.of(salesEmployeeMapper
                            .salesEmployeeToSalesEmployeeDTO(salesEmployeeRepository.save(foundEmployee))));
                }, () -> {
                    atomicReference.set(Optional.empty());
                }
        );

        return atomicReference.get();
    }

    @Override
    public String computeComission(Long id, BigDecimal salesAmount) {

        AtomicReference<String> stringAtomicReference = new AtomicReference<>();

        salesEmployeeRepository.findById(id).ifPresentOrElse(foundEmployee -> {
            BigDecimal commission = foundEmployee.getPosition().computeCommission(salesAmount);
            stringAtomicReference.set("Employee's commission: " +commission.toString() );

        }, () -> stringAtomicReference.set(String.valueOf("")));
        return stringAtomicReference.get();
    }
}
