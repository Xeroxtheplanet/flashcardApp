package flashcardApp;

public class Card {
	String question; //the question you want to ask
	String answer; //the answer to your question
	Card next; //Next card in list
	Card prev; //Previous card in list
	
	public Card(String question, String answer, Object next, Object prev) { //purely CLI from this point on, jframe stuff removed
		this.question = ""; 
		this.answer = ""; 
		this.next = (Card) next; 
		this.prev = (Card) prev; 
	}
	

}
