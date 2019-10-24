package pro.horoshilov.family.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
            final Photo avatar,
            final String description
    ) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.death = death;
        this.sex = sex;
        this.avatar = avatar;
        this.description = description;
    }

    public Person(final Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.birthday = person.getBirthday();
        this.death = person.getDeath();
        this.sex = person.getSex();
        this.avatar = person.getAvatar();
        this.description = person.getDescription();
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

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public Photo getAvatar() {
        return avatar;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final Name name) {
        this.name = name;
    }

    public void setBirthday(final Calendar birthday) {
        this.birthday = birthday;
    }

    public void setDeath(final Calendar death) {
        this.death = death;
    }

    public void setSex(final Sex sex) {
        this.sex = sex;
    }

    public void setAvatar(final Photo avatar) {
        this.avatar = avatar;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) &&
                Objects.equals(formatDate(birthday), formatDate(person.birthday)) &&
                Objects.equals(formatDate(death), formatDate(person.death)) &&
                sex == person.sex &&
                Objects.equals(avatar, person.avatar) &&
                Objects.equals(description, person.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, formatDate(birthday), formatDate(death), sex, avatar, description);
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

        public Name() {
        }

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
        public boolean equals(final Object o) {
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

    private String formatDate(final Calendar date) {
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
                ", avatar=" + avatar +
                ", description='" + description + '\'' +
                '}';
    }
}
