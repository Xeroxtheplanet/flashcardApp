package flashcardApp;

public class Runtime {
	static boolean exitLoop = false;
	
	public static void main(String[] args) {
		Vanki vanki = new Vanki();
		while(exitLoop==false) {
			vanki.run();
		}
	}
}