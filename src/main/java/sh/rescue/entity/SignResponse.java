package sh.rescue.entity;

public class SignResponse {
    private int errorCode;
    private String message;
    private String b64SignedMessage;

    public SignResponse() {
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

    public String getB64SignedMessage() {
        return b64SignedMessage;
    }

    public void setB64SignedMessage(String b64SignedMessage) {
        this.b64SignedMessage = b64SignedMessage;
    }
}
