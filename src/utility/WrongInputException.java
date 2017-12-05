package utility;

public class WrongInputException extends Exception {
	public String getMessage() {
		return "Allowed characters: a - z, A - Z";
	}
}
