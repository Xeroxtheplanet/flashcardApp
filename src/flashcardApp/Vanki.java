package flashcardApp;
import java.util.ArrayList;
import java.util.Scanner;


public class Vanki { //going to apply COMP2000 knowledge by replacing returnToMain with State pattern refactoring
	
	enum State{
		START, //program starting originally 0
		CHECKINPUT, //when you need to select the letters that are specified on the select screen originally 2
		PROGRESSION, //originally 1
		QUESTIONPROGRESSION, //Originally 3
		CHECKPROGRESSION, //check progression originally 4
		FLASHPREREVEAL, //ORIGINALLY 5
		FLASHREVEAL //ORIGINALLY 6
	};
	
	boolean run = true;
	int checkToProgress = 0; //States and use cases: 0 Return to start, 1 Progress onwards
	int returnToMain = 0; //States and use cases: 0 (start), 1 (inner progression), 2 (initial enter), 3 question progression, 4 Check progression
	State programState = State.START;
	ArrayList<Card> currentCards = new ArrayList<Card>();
	
	public Vanki() {
		
	}
	
	
	
	
	public void run() {//Current errors: Need exception character handling for initial enter, handle empty space entering before something is selected
		
		
		
	while(Runtime.exitLoop==false && programState == State.START) { //Scanner stuff solved, now to begin building the writing to files part. Migration to git complete. //orignally returnToMain ==0
		
		//Read csv file here?
		
			
		programState = State.CHECKINPUT; //returnToMain = 2;
			
		
		if (programState == State.CHECKINPUT) { //Scanner must be held within this if block otherwise it will instantly terminate the application
			Scanner mainScan = new Scanner(System.in); //Stops the program from going into an infinite loop. Whenever a scanner should be used, it becomes a breakpoint that has the input values contained in it from previous inputs. Be aware of this and always build
			System.out.println("Type c to enter a card. Type f to find file to write to. Type x to exit. Press v to view current cards.");
			
			
			if(programState == State.CHECKINPUT && mainScan.hasNext("f")) { //Returns in the case that the next line contains f (prevents the double line issue that comes with using contentEquals from the nextLine())
				 
				programState = State.PROGRESSION;//returnToMain=1;
			
				System.out.println("Getting file...");
			
				if(SysManip.GetFile()==null) {
					System.out.println("File does not exist");
					programState = State.START;
				}
			
				System.out.println("Your file to write to is "+ SysManip.GetFile().getPath());
			
				System.out.println("Would you like to continue? y/n");
					
					
				mainScan.nextLine();
				
				if(mainScan.hasNext("y")) {
						
					programState = State.START;
				}
				
				else if(mainScan.hasNext("n") || mainScan.hasNext("x")){
					
					mainScan.close(); //Closes scanner AND input source.
					Runtime.exitLoop = true;
					
				}
					
				
			 }
		
		
			 else if(programState == State.CHECKINPUT && mainScan.hasNext("c")) {
			
				programState = State.PROGRESSION;
			
				Card temp = new Card("", "", null, null); //temporary storage for card that will be added to array list.
		
				System.out.println("Enter question here: ");
		
				mainScan.nextLine(); 
				
				temp.question = mainScan.nextLine();
				System.out.println("Your question was: " + temp.question); 
				
				System.out.println("Enter answer here: ");
				
				temp.answer = mainScan.nextLine();
				System.out.println("Your answer was: " + temp.answer);
				
				currentCards.add(temp);
				
				if(!currentCards.isEmpty() && currentCards.size()>1) {
					temp.prev = currentCards.get(currentCards.indexOf(temp)-1); //Grabs previous card that was entered into current cards and assigns it as prev of the current card being entered. Probably should have used linkedlist but still works
					currentCards.get(currentCards.indexOf(temp)-1).next = temp;
				}
				
				programState = State.START;
				
			}
			 
			 else if(programState == State.CHECKINPUT && mainScan.hasNext("v")) { //Currently working on this bit. cardNumber appears no to be returning the question properly
				 	
				 	String acceptable = "0123456789"; //note replace this with regex, I tried this with something in an assignment and it was buggy
				 	String tempString = "";
					
				 	programState = State.PROGRESSION;
				 	
				 	System.out.println("You have " +currentCards.size()+" cards. Insert number to view corresponding card.");
				 	mainScan.nextLine();
				 	tempString = mainScan.nextLine();
				 	
				 	if(acceptable.indexOf(tempString)==-1) { //If acceptable characters are not in the next line the user has entered. Note: this will apply to negative numbers entered, so this needs to be accounted for in the future.
				 		System.out.println("Error, input must be a number");
				 		programState = State.START;
				 	}
				 	else if(Integer.parseInt(tempString)>=currentCards.size()) { //If numbers are larger than or equal to amount of currentCards' size.
				 		System.out.println("Error, input number is larger than current amount of cards");
				 		programState = State.START;
				 	}
				 	else if(Integer.parseInt(tempString)<0) { //If numbers are larger than amount of currentCards' size.
				 		System.out.println("Error, input number must be positive");
				 		programState = State.START;
				 	}
				 	else { //Else is needed, otherwise it continues to execute past the if points rather than returning to main
				 		programState = State.QUESTIONPROGRESSION;
				 		if (programState == State.QUESTIONPROGRESSION) { //Essential to ensure if there is an error, that it defaults back to this.
				 			
				 			programState = State.CHECKPROGRESSION;
				 			int cardNumber = Integer.parseInt(tempString); //Moving this here seems to have solved some problems (the main issue was that I was using getInteger instead of parseInt, which actually casts it to a primitive int value)
				 	
				 			System.out.println("Your question for this card was: " + 
				 				currentCards.get(cardNumber).question);
				 				
				 			while(programState == State.CHECKPROGRESSION) { //A loop to catch errors in input. It will default back to this if the input is not g.
				 				Scanner checkScan = new Scanner(System.in); //Note that this is necessary to stop the program at the next point otherwise it will result in an infinite loop, cannot use mainScan because something has already been put into it so it would cause an infinite loop
				 				programState = State.FLASHPREREVEAL;
				 				if(programState == State.FLASHPREREVEAL) {
				 					System.out.println("Do you recall your answer? Input g to reveal your answer");
				 					programState = State.FLASHREVEAL;
				 				}
				 		
				 				if(checkScan.hasNext("g") && programState == State.FLASHREVEAL) { //The actual purpose of the flashcard lol
				 					System.out.println(currentCards.get(cardNumber).answer);
				 					programState = State.START;
				 				}
				 				else if(!checkScan.hasNext("g") && programState == State.FLASHREVEAL) { //if input not correct go back to start of else statement
				 					System.out.println("Your input was not accepted, you must input g to reveal your answer");
				 					programState = State.CHECKPROGRESSION;
				 				}
				 				else {
				 					programState = State.START;
				 					checkScan.close(); //Prevents a memory leak
				 				}
				 			}
				 		}
				 		
				 		//returnToMain =0; //Temporary program breaking point until I can fix bugs or implement more things
				 		
				 	}
				}
		
		
			 else if(programState == State.CHECKINPUT && mainScan.hasNext("x")) {
				Runtime.exitLoop=true; //Ensures the loop no longer runs.
				mainScan.close(); //Prevents memory leak.
			}
			
			 else if(programState == State.CHECKINPUT) { //Exception handling clause, if letter is not c, v, f or x throw this
				 System.out.println("You need to enter one of the letters specified");
				 programState = State.START;
			 }
		
			
			}
		}
		 
		
		}
	}
		
		

