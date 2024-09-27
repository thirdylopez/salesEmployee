package management.employee.salesEmployee.bootstrap;

import management.employee.salesEmployee.repositories.SalesEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BootStrapDataTest {

    @Autowired
    SalesEmployeeRepository salesEmployeeRepository;

    BootStrapData bootStrapData;

    @BeforeEach
    void setUp(){
        bootStrapData = new BootStrapData(salesEmployeeRepository);
    }

    @Test
    void testRun() throws Exception{
        bootStrapData.run();

        assertThat(salesEmployeeRepository.count()).isEqualTo(3);
    }

}