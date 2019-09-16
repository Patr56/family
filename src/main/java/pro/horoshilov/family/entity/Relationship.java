package pro.horoshilov.family.entity;

public class Relationship {
	/** Человек. */
	private Person person;

	/** Связанный человек. */
	private Person related;

	/** Тип связи. */
	private Type type;

	public Relationship(final Person person, final Person related, final Type type) {
		this.person = person;
		this.related = related;
		this.type = type;
	}

	public Person getPerson() {
		return person;
	}

	public Person getRelated() {
		return related;
	}

	public Type getType() {
		return type;
	}

	public enum Type {
		/** Брак. */
		MARRIAGE,
		/** Потомок. */
		DESCENDANT
	}
}
