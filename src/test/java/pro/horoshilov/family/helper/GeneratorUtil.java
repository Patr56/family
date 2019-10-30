package pro.horoshilov.family.helper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import pro.horoshilov.family.entity.ContactInformation;
import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.entity.Photo;
import pro.horoshilov.family.entity.Relationship;

public class GeneratorUtil {

    private static Random random = new Random();

    public static List<Person> generatePersons(final int count) {
        final List<Person> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            result.add(generatePerson());
        }

        return result;
    }

    public static Person generatePerson() {
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

    public static ContactInformation generateContactInformation(final Long personId, final Integer position) {
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

    public static Photo generatePhoto() {
        final Faker faker = new Faker(new Locale("ru"));
        final Photo photo = new Photo();

        final int valueRand = random.nextInt(Photo.Type.values().length);
        final Photo.Type type = Photo.Type.values()[valueRand];

        photo.setUrl(faker.file().fileName());
        photo.setType(type);

        return photo;
    }

    public static Relationship generateRelationship(final Long personId, final Long relatedId) {
        final Relationship relationship = new Relationship();

        final int valueRand = random.nextInt(Photo.Type.values().length);
        final Relationship.Type type = Relationship.Type.values()[valueRand];

        relationship.setRelatedId(relatedId);
        relationship.setPersonId(personId);
        relationship.setType(type);

        return relationship;
    }
}
