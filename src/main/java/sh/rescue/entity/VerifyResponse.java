package sh.rescue.entity;

public class VerifyResponse {
    private int errorCode = -1;
    private String message;

    public VerifyResponse() {
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
