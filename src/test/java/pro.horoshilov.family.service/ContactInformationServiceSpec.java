package pro.horoshilov.family.service;

import java.util.List;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.horoshilov.family.entity.ContactInformation;
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
        contactInformationService.add(GeneratorUtil.generateContactInformation(0L, 1));
    }

    @Test
    public void testAdd() throws EmptyInsertIdException {
        final Long personId = personService.add(GeneratorUtil.generatePerson());
        final Long id = contactInformationService.add(GeneratorUtil.generateContactInformation(personId, 1));

        assertThat(id).isGreaterThan(0);
    }

    @Test
    public void testFindAll() throws EmptyInsertIdException {
        final int COUNT = 3;

        final Long personId = personService.add(GeneratorUtil.generatePerson());
        final List<ContactInformation> ciInit = contactInformationService.findAll(personId);
        assertThat(ciInit.size()).isEqualTo(0);

        for (int i = 0; i < COUNT; i++) {
            contactInformationService.add(GeneratorUtil.generateContactInformation(personId, i));
        }

        final List<ContactInformation> ci = contactInformationService.findAll(personId);

        assertThat(ci.size()).isEqualTo(COUNT);
    }
}
