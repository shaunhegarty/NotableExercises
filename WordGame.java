import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//Takes two strings as command line arguments
//If the strings are the same size, the program will attempt to make a word chain between the two
//by changing only one letter at a time
//Currently solved recursively, and not very efficiently, doesn't function well for words of length 5 or higher
//unless the chain relatively easy. 


public class WordGame {
    static final String FILE = "websters-dictionary.txt";
    static ArrayList<String> dictionary;
    static ArrayList<String> path;
    static String end;
    static String start;
    static int leniency; //0 - Strict; 1 - Lenient;

    public static void main(String[] args) {
        ////Setup
        path = new ArrayList<>();
        dictionary = loadDictionary();
        leniency = 0;
        
        //Take in values
        start = args[0].toUpperCase();
        end = args[1].toUpperCase();
        String current = start;
        path.add(current);
        
        //Start solving
        nextStep(current);
        if(!path.get(path.size()-1).equals(end) && start.length() < 5){
            System.out.println("Target not reached, trying some leniency.");
            leniency = 1;
            try{
                nextStep(current);
            } catch (StackOverflowError e){
                System.out.println("Too difficult");
            }
        } else if (!path.get(path.size()-1).equals(end)){
            System.out.println("Target not reached.");
        } else {
            for(String word : path){
                System.out.print(word + " > ");
            }
        }

        
    }
    
    public static ArrayList<String> loadDictionary(){
        ArrayList<String> words = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE)))
        {   
            String line;
            
            while((line = reader.readLine()) != null)
            {
                words.add(line.toUpperCase());
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        
        return words;     
    }
    
    public static int difference(String a, String b){
        //returns the number of characters which differ, or -1 if string lengths do not match
        if(a.length() != b.length()){
            return -1;
        }

        int count = 0;
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) != b.charAt(i)){
                count++;
            }
        }            
        return count;

    }
    
    public static void nextStep(String current){
        if(current.equals(end)){
            return;
        }
        for(String word : dictionary){
            if(isValid(word, current)){  
                if(word.equals(end)){
                    path.add(word);
                    return;
                } else {
                    path.add(word);
                    nextStep(word);
                    if(!path.get(path.size() - 1).equals(end)){
                        path.remove(path.size() - 1);
                    } else {
                        return;
                    }
                }                
            }
        }
        if(leniency == 1){
            String side = sideStep(current);
            if(!side.equals(current)){
                path.add(side);
                nextStep(side);
            } 
        }
    }
    
    public static boolean isValid(String word, String current){
        return (word.length() == current.length() && difference(word, current) == 1 && difference(word, end) < difference(current, end));
    }
    
    public static String sideStep(String curr){
        for(String word : dictionary){
            if(word.length() == end.length() && difference(word, curr) == 1 && word.compareTo(curr) > 0){
                return word;
            }
        }
        return curr;
    }
    
    
}
