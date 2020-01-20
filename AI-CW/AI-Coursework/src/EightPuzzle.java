
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class EightPuzzle{
    public static HashSet<ArrayList<String>> setOfStates = new HashSet<>();
    public static ArrayList<HashSet<ArrayList<String>>> finalSetOfStates = new ArrayList<>();
    public static Stack<ArrayList<String>> stack = new Stack();
    public static ArrayList<ArrayList<String>> statesInFirstInputStateOnly = new ArrayList<>();

    public static void updateStackAndHashSet(State state) throws IOException{
        if(!setOfStates.contains(state.state)){
        	//If the set of states does not contain the state that is passed to this function
        	//then it is added to the hashset that would contain all the states and also pushes it to the Stack
            setOfStates.add((ArrayList<String>) state.state.clone());
            printInTextFile(state.state.get(0)+" "+state.state.get(1)+" "+state.state.get(2)+"\n"+
            		state.state.get(3)+" "+state.state.get(4)+" "+state.state.get(5)+"\n"+
            		state.state.get(6)+" "+state.state.get(7)+" "+state.state.get(8)+"\n \n",true);
            stack.push((ArrayList<String>) state.state.clone());
        }
        else{
        	//If it does not contain the state that is passed then, the latest move is obtained and it 
        	// and that move is blocked for that state (as it already exists in the set of states)
        	// and then we pop the stack to go the previous state
        	// also set the latest move made by that state "" so that we know that the state has not made any move but 
        	// instead went back to the previous state. 
            String move = state.getLatestMove();
            if(move.equals("UP"))
                state.canMoveUp=false;
            if(move.equals("DOWN"))
                state.canMoveDown=false;
            if(move.equals("LEFT"))
                state.canMoveLeft=false;
            if(move.equals("RIGHT"))
                state.canMoveRight=false;
            stack.pop();
            state.setLatestMove("");
        }
    }

    public static void confirmMoves(State state) throws IOException{
    	//Once a move is made using the makeMoves function, that move is tested first and then
    	//if it passes the test, this function is called
        while (stack.size()>0){
        	//the stack and possibly hashset are updated for each iteration of the while loop
            updateStackAndHashSet(state);
            if(stack.size()>0){
            	//we then makeMoves with the last element of the stack
                makeMoves(new State(stack.lastElement(),state.getLatestMove()));
            }
        }
    }

    public static void makeMoves(State state) throws IOException{
    	//Here we use the attributes of the state and see if can make any move
    	//The order of depth is UP,DOWN,LEFT,RIGHT
    	//So the upward movement is tested first, then the downward movement, then the leftward movement
    	//and finally the rightward movement
    	//If any of the test returns true then that move is made first before reaching the next if statement
 
        boolean cantMove=true;
        if(state.canMoveUp) {
            if(state.testUpwardMovement()) {
                cantMove=false;
                state.setLatestMove("UP");
                confirmMoves(state.moveUp());
            }
        }
        if(state.canMoveDown) {
            cantMove=false;
            if(state.testDownwardMovement()) {
                cantMove=false;
                state.setLatestMove("DOWN");
                confirmMoves(state.moveDown());
            }
        }
        if(state.canMoveLeft) {
            if (state.testLeftwardMovement()) {
                cantMove=false;
                state.setLatestMove("LEFT");
                confirmMoves(state.moveLeft());
            }
        }
        if(state.canMoveRight) {
            if(state.testRightwardMovement()) {
                cantMove=false;
                state.setLatestMove("RIGHT");
                confirmMoves(state.moveRight());
            }
        }

        if(cantMove){
            stack.pop();
        }
    }

    public static void reinitialisePuzzleState(){
        setOfStates.clear();
        stack.clear();
    }


    public static void printStatesInFirstSetButNotInSecondSet() throws IOException {
    	//This function finds all the states that are generated from the first input state that are not
    	// in the states that are generated from the second input state
        Iterator position = finalSetOfStates.get(0).iterator();
        ArrayList<String> StateInFirstSet = new ArrayList<>();
        FileWriter writer = new FileWriter("PrintStates.txt",true);
        while(position.hasNext()){
            StateInFirstSet = (ArrayList<String>) position.next();
            if(!finalSetOfStates.get(1).contains(StateInFirstSet)){
                writer.write(StateInFirstSet.get(0)+" "+StateInFirstSet.get(1)+" "+StateInFirstSet.get(2)+"\n");
                writer.write(StateInFirstSet.get(3)+" "+StateInFirstSet.get(4)+" "+StateInFirstSet.get(5)+"\n");
                writer.write(StateInFirstSet.get(6)+" "+StateInFirstSet.get(7)+" "+StateInFirstSet.get(8)+"\n \n");
                writer.write("\n");
                statesInFirstInputStateOnly.add((ArrayList<String>) StateInFirstSet);
            }
        }
        writer.close();
    }

    public static void printInTextFile(String printWord, boolean append) throws IOException {
    	//This function prints the string to be printed in the text file
        FileWriter writer = new FileWriter("PrintStates.txt",append);
        writer.write(printWord);
        writer.close();
    }
    
    public static void calculateStates(ArrayList<String> input) throws IOException{
    	
    	//This function only runs once when the user inputs the state
    	//a state is created, and pushed to both stack and the hashset
    	//Then we make moves with that state using the makeMoves Function
    	//We also print the first state here
        State state = new State(new ArrayList<String>(input), "");
        stack.push(new ArrayList<String>(input));
        setOfStates.add(new ArrayList<String>(input));
        printInTextFile(state.state.get(0)+" "+state.state.get(1)+" "+state.state.get(2)+"\n"+
        		state.state.get(3)+" "+state.state.get(4)+" "+state.state.get(5)+"\n"+
        		state.state.get(6)+" "+state.state.get(7)+" "+state.state.get(8)+"\n \n",true);
        makeMoves(state);
        finalSetOfStates.add((HashSet<ArrayList<String>>) setOfStates.clone());
      

    }
    
    
    public static void main(String[] args) throws IOException {

    	try{
        
    	//This part accepts input from user and calculates all the possible states
    	printInTextFile("",false);
        Scanner in = new Scanner(System.in);
        System.out.println("Enter first input state...");
        String[] input1 = in.nextLine().split(",");   
        System.out.print("Please wait till the program computes...");
        printInTextFile("..............These are the states that can be obtained from the first input state.........\n",true);
        calculateStates(new ArrayList(Arrays.asList(input1)));
        System.out.println("Done");
        reinitialisePuzzleState();
        System.out.print("Enter second input state...");
        String[] input2 = in.nextLine().split(",") ;
        printInTextFile("..............These are the states that can be obtained from the second input state.........\n",true);
        System.out.print("Please wait till the program computes...");
        calculateStates(new ArrayList(Arrays.asList(input2)));
        System.out.println("Done");
        
        //Printing output
        System.out.print("Printing states that are in first set only...");
        printInTextFile("..............These are the states that can be obtained from the first input state only..." +
                "......\n",true);
        printStatesInFirstSetButNotInSecondSet();
        System.out.println("Done");
        printInTextFile("Number of elements in first set "+ finalSetOfStates.get(0).size()+"\n",true);
        System.out.println("Number of elements in first set "+ finalSetOfStates.get(0).size());
        printInTextFile("Number of elements in second set "+ finalSetOfStates.get(1).size()+"\n",true);
        System.out.println("Number of elements in second set "+ finalSetOfStates.get(1).size());
        printInTextFile("Number of elements that are in first set only "+ finalSetOfStates.get(1).size()+"\n",true);
        System.out.println("Number of states that are in first set only "+ statesInFirstInputStateOnly.size());
        System.out.println("And the program is finished");
        printInTextFile("And the program is finished"+"\n",true);
    	
    	}
    	catch (Exception e){
    		//Error message for invalid input
    		System.out.println("Please enter proper input like...a,b,c,d,e,f,g,h,0 where 0 is a space");
    	}

    }
}