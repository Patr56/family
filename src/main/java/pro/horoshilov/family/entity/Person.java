package pro.horoshilov.family.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

/**
 * Человек.
 */
public class Person {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /** Идентификатор. */
    private Long id;

    /** ФИО. */
    private Name name;

    /** Дата рождения. */
    private Calendar birthday;

    /** Дата смерти. */
    private Calendar death;

    /** Пол. */
    private Sex sex;

    /** Контактная информация. */
    private Map<String, String> contactInformation;

    /** Аватарка. */
    private Photo avatar;

    /** Описание. */
    private String description;

    public Person() {
    }

    public Person(
            final Long id,
            final Name name,
            final Calendar birthday,
            final Calendar death,
            final Sex sex,
            final Map<String, String> contactInformation,
            final Photo avatar,
            final String description
    ) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.death = death;
        this.sex = sex;
        this.contactInformation = contactInformation;
        this.avatar = avatar;
        this.description = description;
    }

    public Name getName() {
        return name;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public Calendar getDeath() {
        return death;
    }

    public Sex getSex() {
        return sex;
    }

    public Map<String, String> getContactInformation() {
        return contactInformation;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public Photo getAvatar() {
        return avatar;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public void setDeath(Calendar death) {
        this.death = death;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setContactInformation(Map<String, String> contactInformation) {
        this.contactInformation = contactInformation;
    }

    public void setAvatar(Photo avatar) {
        this.avatar = avatar;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) &&
                Objects.equals(formatDate(birthday), formatDate(person.birthday)) &&
                Objects.equals(formatDate(death), formatDate(person.death)) &&
                sex == person.sex &&
                Objects.equals(contactInformation, person.contactInformation) &&
                Objects.equals(avatar, person.avatar) &&
                Objects.equals(description, person.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, formatDate(birthday), formatDate(death), sex, contactInformation, avatar, description);
    }

    /**
     * ФИО.
     */
    public static class Name {
        /** Имя. */
        private String first;

        /** Отчество. */
        private String middle;

        /** Фамилия. */
        private String last;

        public Name(final String first, final String middle, final String last) {
            this.first = first;
            this.middle = middle;
            this.last = last;
        }

        public String getFirst() {
            return first;
        }

        public String getMiddle() {
            return middle;
        }

        public String getLast() {
            return last;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "first='" + first + '\'' +
                    ", middle='" + middle + '\'' +
                    ", last='" + last + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Name name = (Name) o;
            return Objects.equals(first, name.first) &&
                    Objects.equals(middle, name.middle) &&
                    Objects.equals(last, name.last);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, middle, last);
        }
    }

    /**
     * Пол.
     */
    public enum Sex {
        /** Мужской. */
        MAN,
        /** Женский. */
        WOMAN
    }

    private String formatDate(Calendar date) {
        return date != null ? sdf.format(date.getTime()) : null;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name=" + name +
                ", birthday=" + formatDate(birthday) +
                ", death=" + formatDate(death) +
                ", sex=" + sex +
                ", contactInformation=" + contactInformation +
                ", avatar=" + avatar +
                ", description='" + description + '\'' +
                '}';
    }
}
