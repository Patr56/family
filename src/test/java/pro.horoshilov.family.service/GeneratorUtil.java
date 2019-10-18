package pro.horoshilov.family.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import pro.horoshilov.family.entity.ContactInformation;
import pro.horoshilov.family.entity.Person;

class GeneratorUtil {

    private static Random random = new Random();

    static Person generatePerson() {
        final Faker faker = new Faker(new Locale("ru"));
        final Person person = new Person();
        final Name nameItem = faker.name();
        final String[] name = nameItem.nameWithMiddle().split(" ");
        person.setName(new Person.Name(name[0], name[1], name[2]));
        person.setSex(random.nextInt(100) > 50 ? Person.Sex.MAN : Person.Sex.WOMAN);

        final Calendar birthday = new GregorianCalendar();

        birthday.setTime(faker.date().birthday());
        birthday.set(Calendar.HOUR_OF_DAY, 0);
        birthday.set(Calendar.MINUTE, 0);
        birthday.set(Calendar.SECOND, 0);
        birthday.set(Calendar.MILLISECOND, 0);

        person.setBirthday(birthday);

        return person;
    }

    static ContactInformation generateContactInformation(final Long personId, final Integer position) {
        final Faker faker = new Faker(new Locale("ru"));

        final int valueRand = random.nextInt(ContactInformation.Type.values().length);
        final ContactInformation.Type type = ContactInformation.Type.values()[valueRand];

        String code = null;
        String value = null;

        switch (type) {
            case ADDRESS:
                code = "Адрес " + position;
                value = faker.address().fullAddress();
                break;
            case DATE:
                code = "Дата " + position;
                value = faker.date().birthday().toString();
                break;
            case GEO_POSITION:
                code = "geo позиция " + position;
                value = faker.address().longitude() + " " + faker.address().latitude();
                break;
            case EMAIL:
                code = "email " + position;
                value = faker.name().username() + "@mail.ru";
                break;
            case PHONE:
                code = "Телефон " + position;
                value = faker.phoneNumber().phoneNumber();
                break;
        }

        final ContactInformation contactInformation = new ContactInformation();
        contactInformation.setPersonId(personId);
        contactInformation.setCode(code);
        contactInformation.setValue(value);
        contactInformation.setType(type);
        contactInformation.setPosition(position);

        return contactInformation;
    }
}
