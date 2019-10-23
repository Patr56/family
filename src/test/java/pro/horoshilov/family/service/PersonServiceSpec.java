package pro.horoshilov.family.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.FoundTooManyEntityException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.helper.GeneratorUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@TestPropertySource(locations = "classpath:test.properties")
public class PersonServiceSpec {

    @Autowired
    private PersonService personService;

    @BeforeClass
    @FlywayTest
    public static void before() {

    }

    @Test
    public void testAdd() throws EmptyInsertIdException {
        Person person = new Person();
        person.setName(new Person.Name("Павел", "Сергеевич", "Хорошилов"));
        person.setSex(Person.Sex.MAN);

        Calendar birthday = new GregorianCalendar();
        birthday.set(1988, Calendar.MARCH, 6);
        person.setBirthday(birthday);

        Long personId = personService.add(person);
        assertThat(personId).isGreaterThan(0);

        List<Person> personList = personService.findAll();

        assertThat(personList.size()).isEqualTo(1);
        Person personFromDb = personList.get(0);
        assertThat(personFromDb.getBirthday().get(Calendar.YEAR)).isEqualTo(1988);
        assertThat(personFromDb.getBirthday().get(Calendar.MONTH)).isEqualTo(Calendar.MARCH);
        assertThat(personFromDb.getBirthday().get(Calendar.DAY_OF_MONTH)).isEqualTo(6);
    }

    @Test
    public void testFindAll() throws EmptyInsertIdException {

        List<Person> personListOld = personService.findAll();
        assertThat(personListOld.size()).isEqualTo(0);

        personService.add(GeneratorUtil.generatePerson());
        personService.add(GeneratorUtil.generatePerson());
        personService.add(GeneratorUtil.generatePerson());

        List<Person> personListNew = personService.findAll();

        assertThat(personListNew.size()).isEqualTo(3);
    }

    @Test
    public void testRemove() throws EmptyInsertIdException, FoundTooManyEntityException, NotFoundEntityException {
        List<Person> personListOld = personService.findAll();
        assertThat(personListOld.size()).isEqualTo(0);

        final Person person = GeneratorUtil.generatePerson();

        Long personId = personService.add(person);
        personService.add(GeneratorUtil.generatePerson());
        personService.add(GeneratorUtil.generatePerson());
        personService.add(GeneratorUtil.generatePerson());

        Person personFromDb = personService.findById(personId);

        List<Person> personListNew = personService.findAll();
        assertThat(personListNew.size()).isEqualTo(4);

        person.setId(personId);

        assertThat(personFromDb).isEqualTo(person);

        personService.remove(person);

        List<Person> personListAfterRemove = personService.findAll();
        assertThat(personListAfterRemove.size()).isEqualTo(3);
    }

    @Test
    public void testFindById() throws EmptyInsertIdException, FoundTooManyEntityException, NotFoundEntityException {
        List<Person> personListOld = personService.findAll();
        assertThat(personListOld.size()).isEqualTo(0);

        final Person person1 = GeneratorUtil.generatePerson();
        final Person person2 = GeneratorUtil.generatePerson();
        final Person person3 = GeneratorUtil.generatePerson();

        Long personId1 = personService.add(person1);
        Long personId2 = personService.add(person2);
        Long personId3 = personService.add(person3);

        Person personFromDb1 = personService.findById(personId1);
        Person personFromDb2 = personService.findById(personId2);
        Person personFromDb3 = personService.findById(personId3);

        List<Person> personListNew = personService.findAll();
        assertThat(personListNew.size()).isEqualTo(3);

        person1.setId(personId1);
        person2.setId(personId2);
        person3.setId(personId3);

        assertThat(personFromDb1).isEqualTo(person1);
        assertThat(personFromDb2).isEqualTo(person2);
        assertThat(personFromDb3).isEqualTo(person3);
    }

    @Test
    public void testUpdate() throws EmptyInsertIdException, FoundTooManyEntityException, NotFoundEntityException {
        List<Person> personListOld = personService.findAll();
        assertThat(personListOld.size()).isEqualTo(0);

        final Person person = GeneratorUtil.generatePerson();
        final Person.Name name = new Person.Name("name", "middle", "lastName");
        person.setName(name);
        person.setSex(Person.Sex.MAN);

        Long personId = personService.add(person);

        Person personFromDb = personService.findById(personId);

        List<Person> personListNew = personService.findAll();
        assertThat(personListNew.size()).isEqualTo(1);

        person.setId(personId);

        assertThat(personFromDb).isEqualTo(person);

        final Person modPerson = new Person(person);
        final Person.Name newName = new Person.Name("Name", "Middle", "Lastname");
        modPerson.setName(newName);
        modPerson.setSex(Person.Sex.WOMAN);

        personService.update(modPerson);

        Person personFromDbMod = personService.findById(personId);

        assertThat(personFromDbMod).isEqualTo(modPerson);
    }
}
