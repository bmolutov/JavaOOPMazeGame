package invalidMapException;


public class InvalidMapException extends Exception {
    private String errorMessage;
    
    public InvalidMapException(String newErrorMessage) {
        super(newErrorMessage);
    }
    public String toString() {
        return errorMessage;
    }
}