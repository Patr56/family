package pro.horoshilov.family.entity;

public class SuccessResponse<Body> extends BaseResponse {

    private final Body body;

    public SuccessResponse(final Body body) {
        super(BaseResponse.SUCCESS);
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "SuccessResponse{" +
                "body=" + body +
                ", status=" + status +
                '}';
    }
}
