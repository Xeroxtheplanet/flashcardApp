package flashcardApp;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Card extends javax.swing.JFrame {
	int sizeX;
	int sizeY;
	String question;
	String answer;
	Card next;
	Card prev;
	
	private javax.swing.JButton Submit; // The submit button.
	private javax.swing.JButton Clear; //The clear button.
	private javax.swing.JButton Exit; //The exit button.
	
	public Card(String question, String answer, Object next, Object prev) {
		this.question = "";
		this.answer = "";
		this.next = (Card) next;
		this.prev = (Card) prev;
	}
	
	void createVisual() {
		JFrame frame = new JFrame("Card");
		JLabel label = new JLabel(question);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //NOTE: Terminates application as well, maybe another way?
		frame.add(label);
		frame.pack(); //Not sure if needed.
		frame.setVisible(true);
		
	}
	

}
