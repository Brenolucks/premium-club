package brenolucks.premiumclub.exceptions;

public class UserExistWithPlanException extends RuntimeException {
    public UserExistWithPlanException(String message) {
        super(message);
    }
}
