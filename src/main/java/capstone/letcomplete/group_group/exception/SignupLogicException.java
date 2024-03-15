package capstone.letcomplete.group_group.exception;

public class SignupLogicException extends RuntimeException {
    public SignupLogicException() {
        super();
    }

    public SignupLogicException(String message) {
        super(message);
    }

    public SignupLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignupLogicException(Throwable cause) {
        super(cause);
    }
}
