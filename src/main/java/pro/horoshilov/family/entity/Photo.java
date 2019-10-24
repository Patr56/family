package pro.horoshilov.family.entity;

import java.util.Objects;

/**
 * Фотография.
 */
public class Photo {

    /** Идентификатор. */
    private Long id;

    /** Ссылка на фото. */
    private String url;

    private Type type;

    public Photo(final Long id, final String url, final Type type) {
        this.id = id;
        this.url = url;
        this.type = type;
    }

    public Photo() {
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        FRONT,
        BACK
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(id, photo.id) &&
                Objects.equals(url, photo.url) &&
                type == photo.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, type);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", type=" + type +
                '}';
    }
}
