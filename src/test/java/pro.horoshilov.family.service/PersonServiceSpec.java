package pro.horoshilov.family.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private PersonService personService;

    @BeforeClass
    @FlywayTest
    public static void before() {

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
        assertThat(personId).isEqualTo(1);

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
}
