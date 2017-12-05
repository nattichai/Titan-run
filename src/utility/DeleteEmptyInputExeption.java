package utility;

public class DeleteEmptyInputExeption extends Exception {
	public String getMessage() {
		return "No characters to be deleted";
	}
}
