package pro.horoshilov.family.entity;

import java.util.Objects;

/**
 * Контактная информация.
 */
public class ContactInformation {
    private Long id;

    private String value;

    private String code;

    private Type type;

    private Integer position;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactInformation that = (ContactInformation) o;
        return Objects.equals(position, that.position) &&
                Objects.equals(id, that.id) &&
                Objects.equals(value, that.value) &&
                Objects.equals(code, that.code) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, code, type, position);
    }

    public ContactInformation(
            final Long id,
            final String value,
            final String code,
            final Type type,
            final Integer position
    ) {
        this.id = id;
        this.value = value;
        this.code = code;
        this.type = type;
        this.position = position;
    }

    @Override
    public String toString() {
        return "ContactInformation{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", position=" + position +
                '}';
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

    public enum Type {
        PHONE,
        EMAIL,
        ADDRESS,
        DATE,
        GEO_POSITION
    }
}
