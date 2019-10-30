package pro.horoshilov.family.entity;

import java.util.Objects;

public class Relationship {

    private Long id;

    /** Человек. */
    private Long personId;

    /** Связанный человек. */
    private Long relatedId;

    /** Тип связи. */
    private Type type;

    public Relationship() {
    }

    public Relationship(final Long id, final Long personId, final Long relatedId, final Type type) {
        this.id = id;
        this.personId = personId;
        this.relatedId = relatedId;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(relatedId, that.relatedId) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, relatedId, type);
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "id=" + id +
                ", personId=" + personId +
                ", relatedId=" + relatedId +
                ", type=" + type +
                '}';
    }

    public enum Type {
        /** Брак. */
        MARRIAGE,
        /** Развод. */
        DIVORCE,
        /** Потомок. */
        CHILD
    }
}
