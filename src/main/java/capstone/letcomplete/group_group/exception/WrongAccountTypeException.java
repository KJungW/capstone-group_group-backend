package capstone.letcomplete.group_group.exception;

public class WrongAccountTypeException extends RuntimeException{
    public WrongAccountTypeException() {
        super();
    }

    public WrongAccountTypeException(String message) {
        super(message);
    }

    public WrongAccountTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongAccountTypeException(Throwable cause) {
        super(cause);
    }
}
