
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Xazav
 */
public class Main {
    
    //Static info
    
    /**
     * The main for the program. Takes in a datafile name
     * and a number indicating either building a tree on
     * </br> 0 - probability of error
     * </br> 1 - entropy
     * @param args 
     *          data_file_name 0|1
     */
    public static void main(String[] args){
        ArrayList<Mushroom> mushrooms = new ArrayList<>();
        Trait[] traits = new Trait[22];
        File dataFile = new File(args[0]);
        
        
        System.out.println("> LAUNCHING DECISON TREE...");
        
        //Build data elements
        try {
            Scanner line = new Scanner(dataFile);
            System.out.println("> READING DATA FROM: "+dataFile);
            System.out.println("> POPULATING MUSHROOM DATA...");
            
            while(line.hasNext()){ //Read the next Mushroom
                Scanner s = new Scanner(line.next().replaceAll(",", " "));
                int iter = 0;
                String[] attr = new String[23];
                
                while(s.hasNext()){ //Read the next trait
                    attr[iter] = s.next();
                    iter++;
                    if(iter>attr.length-1){
                        mushrooms.add(new Mushroom(attr));
                        break;
                    }
                }
            }
            System.out.println("> GENERATING TRAIT DATA...");
            for(int i = 0; i < traits.length; i++){
                traits[i] = new Trait(mushrooms,i);
                //System.out.print(traits[i].toString());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("- ERROR: Data File"+dataFile.getName()+" not found");
        }
         
        //Build tree
        if(args[1].equals("0")){
            System.out.println("> GENERATING TREE (probability of error)...");
            probTree(mushrooms,traits);
        }else{
            System.out.println("> GENERATING TREE (entropy)...");
            entropyTree(mushrooms,traits);
        }
        
        
        System.out.println("> CLOSING DECISON TREE...");
    }
    
    
    public static void probTree(ArrayList<Mushroom> mushrooms, Trait[] traits){
        DecimalFormat df = new DecimalFormat("#.00");
        
        //Calculate the probability of Error per trait
        System.out.println("> Trait prob...");
        for(Trait t: traits){
            double per = 0;
            for(int i = 0; i < t.size; i++){
                per += t.prob(i);
            }
            System.out.println("  -- "+t.traitNum()+": "+df.format(per));
        }
    }
    
    public static void entropyTree(ArrayList<Mushroom> mushrooms, Trait[] traits){
        
    }
    
    
}
