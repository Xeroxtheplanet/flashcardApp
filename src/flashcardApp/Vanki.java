package flashcardApp;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import java.io.*;
import java.awt.event.*;


public class Vanki { //Crappy anki clone to get experience with scanner, swing.
	
	
	
	boolean run = true;
	int checkToProgress = 0; //States and use cases: 0 Return to start, 1 Progress onwards
	int returnToMain = 0; //States and use cases: 0 (start), 1 (inner progression), 2 (outer progression), 3 question progression, 4 Check progression
	ArrayList<Card> currentCards = new ArrayList<Card>();
	
	public Vanki() {
		
	}
	
	
	
	
	public void run() {
		
		
		
	while(Runtime.exitLoop==false&&returnToMain ==0) { //Scanner stuff solved, now to begin building the writing to files part. Migrating to git (possibly later, git native doesn't work on eclipse)
		
			
		returnToMain = 2;
			
		
		if (returnToMain==2) { //Scanner must be held within this if block otherwise it will instantly terminate the application
			Scanner mainScan = new Scanner(System.in); //Stops the program from going into an infinite loop. Whenever a scanner should be used, it becomes a breakpoint that has the input values contained in it from previous inputs. Be aware of this and always build
			System.out.println("Type c to enter a card. Type f to find file to write to. Type x to exit. Press v to view current cards."); //Hashcode to test for differing objects.
			
			 if(returnToMain ==2 && mainScan.hasNext("f")) { //Returns in the case that the next line contains f (prevents the double line issue that comes with using contentEquals from the nextLine())
				 
				returnToMain=1;
			
				System.out.println("Getting file...");
			
				if(SysManip.GetFile()==null) { //returning null for some reason?
					System.out.println("File does not exist");
					returnToMain =0;
					mainScan.close();
				}
			
				System.out.println("Your file to write to is "+ SysManip.GetFile().getPath());
			
				System.out.println("Would you like to continue? y/n");
					
					
				mainScan.nextLine(); //Rather than creating new scanners (which always exist under the previous scanner) progress mainScan by one line.
				
				if(mainScan.hasNext("y")) {
						
					returnToMain = 0; 
				}
				
				else if(mainScan.hasNext("n")||mainScan.hasNext("x")){
					
					mainScan.close(); //Closes scanner AND input source.
					Runtime.exitLoop = true;
					
				}
					
				
			 }
		
		
			 else if(returnToMain == 2 && mainScan.hasNext("c")) {
			
				returnToMain=1;
			
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
				
				returnToMain = 0;
				
			}
			 
			 else if(returnToMain == 2 && mainScan.hasNext("v")) { //Currently working on this bit. cardNumber appears no to be returning the question properly
				 	
				 	String acceptable = "0123456789";
				 	String tempString = "";
					
				 	returnToMain = 1;
				 	
				 	System.out.println("You have " +currentCards.size()+" cards. Insert number to view corresponding card.");
				 	mainScan.nextLine();
				 	tempString = mainScan.nextLine();
				 	
				 	if(acceptable.indexOf(tempString)==-1) { //If acceptable characters are not in the next line the user has entered. Note: this will apply to negative numbers entered, so this needs to be accounted for in the future.
				 		System.out.println("Error, input must be a number");
				 		returnToMain = 0;
				 	}
				 	else if(Integer.parseInt(tempString)>=currentCards.size()) { //If numbers are larger than or equal to amount of currentCards' size.
				 		System.out.println("Error, input number is larger than current amount of cards");
				 		returnToMain = 0;
				 	}
				 	else if(Integer.parseInt(tempString)<0) { //If numbers are larger than amount of currentCards' size.
				 		System.out.println("Error, input number must be positive");
				 		returnToMain = 0;
				 	}
				 	else { //Else is needed, otherwise it continues to execute past the if points rather than returning to main
				 		returnToMain = 3;
				 		if (returnToMain==3) { //Essential to ensure if there is an error, that it defaults back to this.
				 			returnToMain = 4;
				 			int cardNumber = Integer.parseInt(tempString); //Moving this here seems to have solved some problems (the main issue was that I was using getInteger instead of parseInt, which actually casts it to a primitive int value)
				 	
				 			System.out.println("Your question for this card was: " + 
				 				currentCards.get(cardNumber).question);
				 				
				 			while(returnToMain ==4) { //A loop to catch errors in input. It will default back to this if the input is not g.
				 				Scanner checkScan = new Scanner(System.in); //Note that this is necessary to stop the program at the next point otherwise it will result in an infinite loop, cannot use mainScan because something has already been put into it so it would cause an infinite loop
				 				returnToMain = 5;
				 				if(returnToMain==5) {
				 					System.out.println("Do you recall your answer? Input g to reveal your answer");
				 					returnToMain = 6;
				 				}
				 		
				 				if(checkScan.hasNext("g") && returnToMain==6) { //The actual purpose of the flashcard lol
				 					System.out.println(currentCards.get(cardNumber).answer);
				 					returnToMain = 0;
				 				}
				 				else if(!checkScan.hasNext("g") && returnToMain == 6) { //if input not correct go back to start of else statement
				 					System.out.println("Your input was not accepted, you must input g to reveal your answer");
				 					returnToMain = 4;
				 				}
				 				else {
				 					returnToMain = 0;
				 					checkScan.close(); //Prevents memory leak
				 				}
				 			}
				 		}
				 		
				 		returnToMain =0; //Temporary program breaking point until I can fix bugs or implement more things
				 		
				 	}
				}
		
		
			 else if(returnToMain == 2 && mainScan.hasNext("x")) {
				Runtime.exitLoop=true; //Ensures the loop no longer runs.
				mainScan.close(); //Prevents memory leak.
			}
		
			
			}
		}
		 
		
		}
	}
		
		

