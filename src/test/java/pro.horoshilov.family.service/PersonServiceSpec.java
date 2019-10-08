package pro.horoshilov.family.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.horoshilov.family.entity.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@TestPropertySource(locations = "classpath:test.properties")
public class PersonServiceSpec {

    private Random random = new Random();

    @Autowired
    private PersonService personService;

    @BeforeClass
    @FlywayTest
    public static void before() {

    }

    private Person generatePerson() {
        final Faker faker = new Faker(new Locale("ru"));
        final Person person = new Person();
        final Name nameItem = faker.name();
        final String[] name = nameItem.nameWithMiddle().split(" ");
        person.setName(new Person.Name(name[0], name[1], name[2]));
        person.setSex(random.nextInt(100) > 50 ? Person.Sex.MAN : Person.Sex.WOMAN);

        final Map<String, String> contactInformation = new HashMap<>();
        contactInformation.put("e-mail", nameItem.username() + "@mail.ru");
        contactInformation.put("телефон", faker.phoneNumber().phoneNumber());
        contactInformation.put("индекс", faker.address().zipCode());

        person.setContactInformation(contactInformation);

        final Calendar birthday = new GregorianCalendar();

        birthday.setTime(faker.date().birthday());
        birthday.set(Calendar.HOUR_OF_DAY, 0);
        birthday.set(Calendar.MINUTE, 0);
        birthday.set(Calendar.SECOND, 0);
        birthday.set(Calendar.MILLISECOND, 0);
        System.out.println("--------------" + birthday.getTime());
        person.setBirthday(birthday);

        return person;
    }

    @Test
    public void testInsert() throws Exception {
        Person person = new Person();
        person.setName(new Person.Name("Павел", "Сергеевич", "Хорошилов"));
        person.setSex(Person.Sex.MAN);

        Map<String, String> contactInformation = new HashMap<>();
        contactInformation.put("e-mail", "patr56@yande.ru");
        contactInformation.put("телефон", "8(916)588-98-92");
        contactInformation.put("индекс", "41-24-08");

        person.setContactInformation(contactInformation);

        Calendar birthday = new GregorianCalendar();
        birthday.set(1988, Calendar.MARCH, 6);
        person.setBirthday(birthday);

        Long personId = personService.insert(person);
        assertThat(personId).isGreaterThan(0);

        List<Person> personList = personService.findAll();

        assertThat(personList.size()).isEqualTo(1);
        Person personFromDb = personList.get(0);
        assertThat(personFromDb.getBirthday().get(Calendar.YEAR)).isEqualTo(1988);
        assertThat(personFromDb.getBirthday().get(Calendar.MONTH)).isEqualTo(Calendar.MARCH);
        assertThat(personFromDb.getBirthday().get(Calendar.DAY_OF_MONTH)).isEqualTo(6);

        final Map<String, String> contactInformationFromDb = personFromDb.getContactInformation();

        assertThat(contactInformationFromDb.size()).isEqualTo(3);
        assertThat(contactInformationFromDb.get("телефон")).isEqualTo("8(916)588-98-92");
    }

    @Test
    public void testFindAll() throws Exception {
        List<Person> personListOld = personService.findAll();
        assertThat(personListOld.size()).isEqualTo(0);

        personService.insert(generatePerson());
        personService.insert(generatePerson());
        personService.insert(generatePerson());

        List<Person> personListNew = personService.findAll();

        assertThat(personListNew.size()).isEqualTo(3);
    }

    @Test
    public void testFindById() throws Exception {
        List<Person> personListOld = personService.findAll();
        assertThat(personListOld.size()).isEqualTo(0);

        final Person person1 = generatePerson();
        final Person person2 = generatePerson();
        final Person person3 = generatePerson();

        Long personId1 = personService.insert(person1);
        Long personId2 = personService.insert(person2);
        Long personId3 = personService.insert(person3);

        Person personFromDb1 = personService.findById(personId1);
        Person personFromDb2 = personService.findById(personId2);
        Person personFromDb3 = personService.findById(personId3);

        List<Person> personListNew = personService.findAll();
        assertThat(personListNew.size()).isEqualTo(3);

        person1.setId(personId1);
        person2.setId(personId2);
        person3.setId(personId3);

        assertThat(person1).isEqualTo(personFromDb1);
        assertThat(person2).isEqualTo(personFromDb2);
        assertThat(person3).isEqualTo(personFromDb3);
    }
}
