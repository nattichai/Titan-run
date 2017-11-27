package utility;

public class InvalidInputException extends Exception {
	public static final String wrongInput = "Allowed characters: a - z, A - Z";
	public static final String tooShortInput = "At least 2 characters";
	public static final String tooLongInput = "At most 12 characters";
	public static final String validInput = "press enter to confirm";
	String invalidText;
	boolean isInvalid;

	public InvalidInputException(double length) {
		if (length < 0) {
			invalidText = wrongInput;
		} else if (length < 2) {
			invalidText = tooShortInput;
		} else if (length > 12) {
			invalidText = tooLongInput;
		} else {
			invalidText = validInput;
		}
	}

	public String getInvalidText() {
		return invalidText;
	}
}
