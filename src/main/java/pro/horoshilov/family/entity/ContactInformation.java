package pro.horoshilov.family.entity;

import java.util.Objects;

/**
 * Контактная информация.
 */
public class ContactInformation {
    private Long id;

    /** Значение. */
    private String value;

    /** Идентификатор человека. */
    private Long personId;

    /** Название. */
    private String code;

    /** Тип записи. */
    private Type type;

    /** Позиция в списке. */
    private Integer position;

    public ContactInformation() {

    }

    public ContactInformation(
            final Long id,
            final Long personId,
            final String value,
            final String code,
            final Type type,
            final Integer position
    ) {
        this.id = id;
        this.personId = personId;
        this.value = value;
        this.code = code;
        this.type = type;
        this.position = position;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public Type getType() {
        return type;
    }

    public Integer getPosition() {
        return position;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public enum Type {
        PHONE,
        EMAIL,
        ADDRESS,
        DATE,
        GEO_POSITION
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactInformation that = (ContactInformation) o;
        return Objects.equals(position, that.position) &&
                Objects.equals(id, that.id) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(value, that.value) &&
                Objects.equals(code, that.code) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, value, code, type, position);
    }

    @Override
    public String toString() {
        return "ContactInformation{" +
                "id=" + id +
                ", personId=" + personId +
                ", value='" + value + '\'' +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", position=" + position +
                '}';
    }
}
