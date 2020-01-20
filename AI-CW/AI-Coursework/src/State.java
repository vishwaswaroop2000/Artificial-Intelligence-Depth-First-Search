import java.util.*;
public class State {
    protected ArrayList<String> state = new ArrayList<>();
    protected boolean canMoveUp,canMoveDown,canMoveRight,canMoveLeft;
    private String latestMove;

    public State(ArrayList<String> state, String latestMove){
    	//contructor for initialsing objects
    	//it assigns the state attribute to the object and then decides all
    	//other attributes by the possible moves that can be made from that state
        this.state = state;
        if(this.state.get(0).equals("0")){
            this.canMoveUp=false;
            this.canMoveDown=true;
            this.canMoveLeft=false;
            this.canMoveRight=true;
        }
        if(this.state.get(1).equals("0") ){
            this.canMoveUp=false;
            this.canMoveDown=true;
            this.canMoveLeft=true;
            this.canMoveRight=true;
        }
        if(this.state.get(2).equals("0")){
            this.canMoveUp=false;
            this.canMoveDown=true;
            this.canMoveLeft=true;
            this.canMoveRight=false;
        }
        if(this.state.get(3).equals("0")){
            this.canMoveUp=true;
            this.canMoveDown=true;
            this.canMoveLeft=false;
            this.canMoveRight=true;
        }
        if(this.state.get(4).equals("0")){
            this.canMoveUp=true;
            this.canMoveDown=true;
            this.canMoveLeft=true;
            this.canMoveRight=true;
        }
        if(this.state.get(5).equals("0")){
            this.canMoveUp=true;
            this.canMoveDown=true;
            this.canMoveLeft=true;
            this.canMoveRight=false;
        }
        if(this.state.get(6).equals("0")){
            this.canMoveUp=true;
            this.canMoveDown=false;
            this.canMoveLeft=false;
            this.canMoveRight=true;
        }
        if(this.state.get(7).equals("0")){
            this.canMoveUp=true;
            this.canMoveDown=false;
            this.canMoveLeft=true;
            this.canMoveRight=true;
        }
        if(this.state.get(8).equals("0")){
            this.canMoveUp=true;
            this.canMoveDown=false;
            this.canMoveLeft=true;
            this.canMoveRight=false;
        }
        if(latestMove.equals("UP")){
            this.canMoveDown=false;
        }
        if(latestMove.equals("DOWN")){
            this.canMoveUp=false;
        }
        if(latestMove.equals("LEFT")){
            this.canMoveRight=false;
        }
        if(latestMove.equals("RIGHT")){
            this.canMoveLeft=false;
        }
    }

    public State getPositionOfSpaceAndMove(int instruction){

        int positionOfSpace = this.state.indexOf("0");
        String temporaryVariable = this.state.get(positionOfSpace);
        this.state.set(positionOfSpace, this.state.get(positionOfSpace + instruction));
        this.state.set(positionOfSpace + instruction, temporaryVariable);
        return this;

    }

    public boolean getPositionOfSpaceAndTestMovement(int instruction){
    	
    	//This function gets the position of the space in the state and 
    	//tests if it can make the move using instruction given
    	// the instruction would be -3 for up, 3 for down, -1 for left, 1 for right 
        boolean existsInSetOfPositions = false;
        int positionOfSpace = this.state.indexOf("0");
        String temporaryVariable = this.state.get(positionOfSpace);
        if(positionOfSpace>=6&&instruction==3)//error validation
            instruction=0;
        if(positionOfSpace==8&&instruction==1)
            instruction=0;
        this.state.set(positionOfSpace, this.state.get(positionOfSpace + instruction));
        this.state.set(positionOfSpace + instruction, temporaryVariable);
        //If a move can be made using the instruction given, it checks if that state exists in the hashset
        if(EightPuzzle.setOfStates.contains(this.state)) {
            existsInSetOfPositions = true;
        }
        int positionOfSpaceForTesting = this.state.indexOf("0");
        String temporaryVariableForTesting = this.state.get(positionOfSpaceForTesting);
        this.state.set(positionOfSpaceForTesting, this.state.get(positionOfSpaceForTesting - instruction));
        this.state.set(positionOfSpaceForTesting - instruction, temporaryVariableForTesting);
        return !existsInSetOfPositions;
    }

    public void setLatestMove(String move){//sets the latest move
        this.latestMove=move;
    }

    public String getLatestMove(){//gets the latest move
        return latestMove;
    }
    public State moveUp(){//moves the space up
        return getPositionOfSpaceAndMove(-3);
    }

    public State moveDown(){//moves the space down
        return getPositionOfSpaceAndMove(3);
    }

    public State moveLeft(){//moves the space right
        return getPositionOfSpaceAndMove(-1);
    }

    public State moveRight(){//moves the space left
        return getPositionOfSpaceAndMove(+1);
    }

    public boolean testUpwardMovement(){//tests upward movement
        return getPositionOfSpaceAndTestMovement(-3);
    }

    public boolean testDownwardMovement(){//tests downward movement
        return getPositionOfSpaceAndTestMovement(3);
    }

    public boolean testLeftwardMovement(){//tests rightward movement
        return getPositionOfSpaceAndTestMovement(-1);
    }

    public boolean testRightwardMovement(){//tests leftward movement
        return getPositionOfSpaceAndTestMovement(1);
    }

}
