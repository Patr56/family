package pro.horoshilov.family.entity;

public class FailureResponse extends BaseResponse {

    private final String error;

    public FailureResponse(final String error) {
        super(BaseResponse.FAILURE);

        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "FailureResponse{" +
                "error='" + error + '\'' +
                ", status=" + status +
                '}';
    }
}
