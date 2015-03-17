
import java.util.ArrayList;
//Towers of Hanoi iterative puzzle solver. There's a recursive version too but it needs more work
//Works for any number of rings but the number of operations goes as 2^n
//Standard puzzle is n=6. Can set n with command line arg
//For double digit n comment out print and displayTowers methods as they seriously slow it down


public class TowersOfHanoi {
    static ArrayList<ArrayList<Integer>> columns = new ArrayList<>();
    static int n = 7;
    static int steps = 0;
    static int source = 0;
    static int dest = 2;//Don't touch this for nows
    //static int spare = 2;
    //static ArrayList<Integer> spare = new ArrayList<>();
    
    public static void main(String[] args){
        //set up the towers
        if(args.length != 0){//optional command line arg
            n = Integer.parseInt(args[0]);
        }
        for(int i = 0; i < 3; i++ ){
            columns.add(i, new ArrayList<Integer>());
        }
        for(int j = 0; j < n; j++){
            columns.get(0).add(j, n - j);
        }
        //first move
        if(n%2 != 0){
            dest = 1;
        }
        displayTowers();
        solveHanoi();
        displayTowers();

    }
    
   
    static int topValue(int col){
        if(columns.get(col).isEmpty()){
            return -1;
        } else {
            return columns.get(col).get(columns.get(col).size() - 1);
        }
    }
    
    static void moveRing(int t1, int t2){
        int ring = topValue(t1);
        columns.get(t1).remove(columns.get(t1).size() - 1);
        //System.out.println("Top of tower " + t2 + " is " + towers[t2][topIndex(towers[t2])]);
        columns.get(t2).add(ring);
    }
    
    static void displayTowers(){
        System.out.println("=======================");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < columns.get(i).size(); j++){
                if(columns.get(i).isEmpty()){
                    System.out.print("-");
                } else {
                    System.out.print(columns.get(i).get(j) + "-");
                }
            }
            System.out.print("\n");
        }
        System.out.println("=======================");
    }
    
    
    static void solveHanoi(){
        while(columns.get(dest).size() != n){
            System.out.println("From tower " + findSmallRing() + " to tower " + getMoveLocationSmall());        
            moveRing(findSmallRing(), getMoveLocationSmall());
            displayTowers();
            steps++;
            if(columns.get(dest).size() == n){
                System.out.println("The number of steps was " + steps);
                return;
            }
            System.out.println("From tower " + findNextRing() + " to tower " + getMoveLocationOther());
            moveRing(findNextRing(), getMoveLocationOther());
            displayTowers();
            steps++;
        }
    }
    
    static int findSmallRing(){
        for(int i = 0; i < 3; i++){
            if(topValue(i) == 1){
                return i;
            }
        }
        System.out.println("Puzzle broken, small ring not at top.");
        return -1;
    }
    
    static int findNextRing(){//Returns tower with largest ring
        int nextRing = n+1;
        int ringLocation = -1;
        for(int i = 0; i < 3; i++){
            if(topValue(i) < nextRing && topValue(i) > 1){
                nextRing = topValue(i);
                ringLocation = i;
            }
        }
        return ringLocation;
    }
    
    static int getMoveLocationOther(){
        int location = -1;
        for(int i = 0; i < 3; i++){
            if(i != findNextRing() && topValue(i) != 1){
                location = i;
            } else if(topValue(i) == -1){
                location = i;
            }
        }
        return location;
    }
    
    static int getMoveLocationSmall(){
        for(int i = 0; i < 3; i++){
            if(topValue(i) % 2 == 0){
                return i;
            }
        }
        
        for(int i = 0; i < 3; i++){            
            if(topValue(i) == -1){
                return i;
            }
        }
        return dest;
    }
}
