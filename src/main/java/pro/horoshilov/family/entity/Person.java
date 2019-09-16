package pro.horoshilov.family.entity;

import java.util.Calendar;

/**
 * Человек.
 */
public class Person {

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
	private ContactInformation contactInformation;

	/** Аватарка. */
	private Photo avatar;

	/** Описание. */
	private String description;

	public Person(
			final Long id,
			final Name name,
			final Calendar birthday,
			final Calendar death,
			final Sex sex,
			final ContactInformation contactInformation,
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

	public ContactInformation getContactInformation() {
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

	public static class ContactInformation {
		private String phone;

		private String email;

		private String address;

		public ContactInformation(final String phone, final String email, final String address) {
			this.phone = phone;
			this.email = email;
			this.address = address;
		}

		public String getPhone() {
			return phone;
		}

		public String getEmail() {
			return email;
		}

		public String getAddress() {
			return address;
		}
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

		Name(String first, String middle, String last) {
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
}
