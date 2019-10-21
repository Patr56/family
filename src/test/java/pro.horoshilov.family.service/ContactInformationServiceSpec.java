package pro.horoshilov.family.service;

import java.util.LinkedList;
import java.util.List;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.horoshilov.family.entity.ContactInformation;
import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.NotFoundEntityException;

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

    @Test
    public void testRemove() throws EmptyInsertIdException, NotFoundEntityException {
        final Long personId = personService.add(GeneratorUtil.generatePerson());
        final List<ContactInformation> ciInit = contactInformationService.findAll(personId);
        assertThat(ciInit.size()).isEqualTo(0);

        final int COUNT = 3;
        final List<Long> items = new LinkedList<>();
        for (int i = 0; i < COUNT; i++) {
            final Long id = contactInformationService.add(GeneratorUtil.generateContactInformation(personId, i));
            items.add(id);
        }

        final List<ContactInformation> ciFull = contactInformationService.findAll(personId);

        assertThat(ciFull.size()).isEqualTo(COUNT);

        for (int i = 0; i < COUNT; i++) {
            final ContactInformation ci = new ContactInformation();
            ci.setId(items.get(i));
            contactInformationService.remove(ci);

            final List<ContactInformation> ciLast = contactInformationService.findAll(personId);
            assertThat(ciLast.size()).isEqualTo(COUNT - i - 1);
        }

        final List<ContactInformation> ciEmpty = contactInformationService.findAll(personId);

        assertThat(ciEmpty.size()).isEqualTo(0);
    }

    @Test
    public void testRemoveAll() throws EmptyInsertIdException {
        final Person person = GeneratorUtil.generatePerson();
        final Long personId = personService.add(person);
        final List<ContactInformation> ciInit = contactInformationService.findAll(personId);
        assertThat(ciInit.size()).isEqualTo(0);

        final int COUNT = 3;
        for (int i = 0; i < COUNT; i++) {
            contactInformationService.add(GeneratorUtil.generateContactInformation(personId, i));
        }

        final List<ContactInformation> ciFull = contactInformationService.findAll(personId);

        assertThat(ciFull.size()).isEqualTo(COUNT);

        person.setId(personId);
        contactInformationService.remove(person);

        final List<ContactInformation> ciEmpty = contactInformationService.findAll(personId);

        assertThat(ciEmpty.size()).isEqualTo(0);
    }

}
