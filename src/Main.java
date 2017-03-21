
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


/**
 *
 * @author Joseph Ryan
 */
public class Main {
    
    static String override = "dataset.txt";
    
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
        File dataFile = new File((override.equals("")?args[0]:override));
        
        
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
            }
        } catch (FileNotFoundException ex) {
            System.out.println("- ERROR: Data File"+dataFile.getName()+" not found");
        }
         
        //Build tree
        if(args[1].equals("0")){
            System.out.println("> GENERATING TREE (probability of error)...");
            Node root = probTree(mushrooms,traits, null);
            System.out.println(root.name);
            printTree(root,0);
            System.out.println("Number of nodes: "+numberOfNodes);
        }else{
            System.out.println("> GENERATING TREE (entropy)...");
            entropyTree(mushrooms,traits);
        }
        
        
        System.out.println("> CLOSING DECISON TREE...");
    }
    
    static int numberOfNodes = 1;
    private static void printTree(Node node, int i){
        for(Branch b: node.children){
            System.out.println(String.join("", Collections.nCopies(i, "        "))+"-- "+b.name+" -> "+b.child.name); 
            numberOfNodes++;
            printTree(b.child, i+1);
        }
    }
    
    public static Node probTree(ArrayList<Mushroom> mushrooms, Trait[] traits, Node parent){
        DecimalFormat df = new DecimalFormat("#.000");
        
        int same = allSame(mushrooms);
        if(same!=-1){
            return new Node((same==1?"EDIBLE":"POISONOUS"),parent);
        }
        
        if(mushrooms.size()==0)
            System.out.println("ERROR");
        
        //Calculate the probability of Error per trait
        System.out.println("> <"+(parent!=null?parent.name:"start")+"> Trait prob ");
        int opt = 0;
        for(int i = 0; i < traits.length; i++){
            if(i%4 == 0 && i!=0) System.out.println();
            traits[i].calculate(mushrooms);
            if(traits[i].percentError < traits[opt].percentError) opt = i;
            System.out.print(" | "+traits[i].traitNum()+": "+df.format(traits[i].percentError));
            
        }
        
        //Select lowest error node
        System.out.println("\n> Node selected: <"+traits[opt].traitNum()+">\n");
        Node node = new Node(""+traits[opt].traitNum(),parent);
        
        //Create children branches
        for(int i = 0; i < traits[opt].size; i++){
            ArrayList<Mushroom> tempM = removeMushrooms(mushrooms,traits[opt],i);
            Trait[] tempT = removeTrait(traits,opt);
            if(tempM.size()>0)
                node.children.add(new Branch(traits[opt].type(i),probTree(tempM,tempT,node)));
//            else if(traits[opt].prob(i) == 0)
//                node.children.add(new Branch(traits[opt].type(i),new Node("EDIBLE",node)));
//            else if(traits[opt].prob(i) == 1)
//                node.children.add(new Branch(traits[opt].type(i),new Node("POISONOUS",node)));
         
        }
        
        return node;
    }
    
    public static int allSame(ArrayList<Mushroom> mushrooms){
        int op = -1;
        for(Mushroom m: mushrooms){
            if(op==-1){ 
                op = (m.edible?1:0);
            }else if((op==0 && m.edible) || (op==1 && !m.edible)){
                op = -1;
                break;
            }
        }
        return op;
    }
    
    public static ArrayList<Mushroom> removeMushrooms(ArrayList<Mushroom> mushrooms, Trait t, int i){
        ArrayList<Mushroom> ret = new ArrayList<>();
        
        for(Mushroom m: mushrooms){
            if(m.attr[t.trait].equals(t.type(i)))
                ret.add(m);
        }
        
        return ret;
    }
    
    public static Trait[] removeTrait(Trait[] traits, int low){
        Trait[] ret = new Trait[traits.length-1];
        
        for(int i = 0; i < ret.length; i++){
            ret[i] = traits[(i>=low)?i+1:i];
        }
        
        return ret;
    }
    
    public static void entropyTree(ArrayList<Mushroom> mushrooms, Trait[] traits){
        
    }
       
}