package pro.horoshilov.family.entity;

/**
 * Базовый ответ контроллеров.
 */
public abstract class BaseResponse {
    public static boolean SUCCESS = true;
    public static boolean FAILURE = false;

    protected final Boolean status;

    public BaseResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }
}
