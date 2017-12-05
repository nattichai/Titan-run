package utility;

public class ShortInputException extends Exception {
	public String getMessage() {
		return "At least 2 characters";
	}
}
