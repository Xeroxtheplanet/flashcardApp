package flashcardApp;
import java.awt.AWTEvent;
import java.awt.event.MouseListener;

public class Button {
	int posX;
	int posY;
	String text;
	int state;
	
	public Button(int X, int Y, String text, int state) {
		posX = X;
		posY = Y;
		this.state = state;
		this.text = text;
		
	};

	
	public int selectState(int ButtonState) {
		switch(ButtonState){
			case 1: state = 1; //do nothing
			case 2: state = 2; //move x and y (requires mouseevent)
			case 3: state = 3; //change text
			case 4: state = 4; //remove (automatic memory management in Java, so would be collected in garbage)
		}
		return ButtonState;
	};
	
	
}
