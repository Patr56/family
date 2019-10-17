package pro.horoshilov.family.service;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.horoshilov.family.exception.EmptyInsertIdException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@TestPropertySource(locations = "classpath:test.properties")
public class ContactInformationServiceSpec {

    @Autowired
    private ContactInformationService contactInformationService;

    @Autowired
    private PersonService personService;

    @BeforeClass
    @FlywayTest
    public static void before() {

    }

    @Test(expected = Exception.class)
    public void testAddThrow() throws EmptyInsertIdException {
        contactInformationService.add(0L, GeneratorUtil.generateContactInformation(1));
    }

    @Test
    public void testAdd() throws EmptyInsertIdException {
        final Long personId = personService.add(GeneratorUtil.generatePerson());
        final Long id = contactInformationService.add(personId, GeneratorUtil.generateContactInformation(1));

        assertThat(id).isGreaterThan(0);
    }
}
