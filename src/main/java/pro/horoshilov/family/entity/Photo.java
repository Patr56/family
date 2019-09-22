package pro.horoshilov.family.entity;

/**
 * Фотография.
 */
public class Photo {

    /** Идентификатор. */
    private Long id;

    /** Ссылка на фото. */
    private String url;

    private Type type;

    public Photo(String url, Type type) {
        this.url = url;
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
}
