import java.util.Random;
import java.util.ArrayList;

/////Solve the birthday problem through the use of trials
/////Birthday problem: What is the probability that 2 or more people in a room of n people share a birthday
/////Can pass n and trials as cmd args


public class BirthdayProblem {
    public static void main(String[] args){
        ArrayList<Integer> birthdays = new ArrayList<>();
        

        int n = 23;
        if(args.length > 0){
            n = Integer.parseInt(args[0]);
        }
        int trials = 100000;
        if(args.length > 1){
            trials = Integer.parseInt(args[1]);
        }        
        int count = 0;
        long beforeTime = System.currentTimeMillis();
        for(int i = 0; i < trials; i++){
            populateList(n, 365, birthdays);          
            if(multipleOccurrences(birthdays)){
                count++;
            }
            birthdays.clear();
        }
        long afterTime = System.currentTimeMillis();
        
        double probability = (double) count / trials;
        
        System.out.println("Probability that two people have"
                + " the same birthday is " + probability*100 + "%");
        System.out.println("The calculation took " + (afterTime - beforeTime) + " ms");
    }
    
    static boolean multipleOccurrences(ArrayList<Integer> birthdays){
        for(int i = 0; i < birthdays.size(); i++){
            for(int j = 0; j < birthdays.size(); j++){
                if(birthdays.get(i).equals(birthdays.get(j)) && i != j){
                    return true;
                }
            }
        }
        return false;
    }
    
    static void populateList(int size, int range, ArrayList<Integer> list){
        Random generator = new Random();
        for(int i = 0; i < size; i++){
            list.add(generator.nextInt(365));
        }
    }
    
}
