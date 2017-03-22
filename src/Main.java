
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
    
//    static String override = "dataset.txt";
    
    static boolean entropy = false;
    
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
            }
            
            //Build tree
            if(args[1].equals("0")){
                System.out.println("> GENERATING TREE (probability of error)...\n");
                Node root = DTL(mushrooms,traits,mushrooms);
                System.out.println("> FINAL TREE (proability of error):");
                System.out.println("-----------------------------------");
                System.out.println("  "+root.name);
                printTree(root,0);
                System.out.println("  Number of nodes: "+numberOfNodes);
                System.out.println("-----------------------------------\n");
            }else{
                entropy = true;
                System.out.println("> GENERATING TREE (entropy)...");
                Node root = DTL(mushrooms,traits,mushrooms);
                System.out.println("> FINAL TREE (entropy):");
                System.out.println("-----------------------------------");
                System.out.println("  "+root.name);
                printTree(root,0);
                System.out.println("  Number of nodes: "+numberOfNodes);
                System.out.println("-----------------------------------\n");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("- ERROR: Data File"+dataFile.getName()+" not found");
        }
        System.out.println("> CLOSING DECISON TREE...");
    }
    
    static int numberOfNodes = 1;
    private static void printTree(Node node, int i){
        for(Branch b: node.children){
            System.out.println(String.join("", Collections.nCopies(i, "        "))+"  -- "+b.name+" -> "+b.child.name); 
            numberOfNodes++;
            printTree(b.child, i+1);
        }
    }
    
    public static Node DTL(ArrayList<Mushroom> examples, Trait[] attr, ArrayList<Mushroom> parent_examples){
        Node node;
        
        if(examples.isEmpty())
            return plurality_value(parent_examples);
        else if(allSame(examples)!=-1)
            return new Node((allSame(examples)==1?"EDIBLE":"POISONOUS"));
        else if(attr.length==0)
            return plurality_value(examples);
        else{
            Trait A = importance(attr,examples);
            node = new Node(A.traitNum());
            for(int i = 0; i < A.size; i++){
                ArrayList<Mushroom> exs = removeMushrooms(examples,A,i);
                node.children.add(new Branch(A.type(i),DTL(exs,removeTrait(attr,A.trait),examples)));
            }
        }
        
        return node;
    }
    
    public static Node plurality_value(ArrayList<Mushroom> examples){
        int e = 0, p = 0;
        for(Mushroom m: examples){
            if(m.edible) e++;
            else p++;
        }
        return new Node((e>p?"EDIBLE":"POISONOUS"));
    }
    
    public static Trait importance(Trait[] attr, ArrayList<Mushroom> examples){
        DecimalFormat df = new DecimalFormat("#.000");
        
        System.out.println("> Looking at Attributes...");
        int opt = 0;
        for(int i = 0; i < attr.length; i++){
            if(i%4 == 0 && i!=0) System.out.println();
            attr[i].calculate(examples);
            if(!entropy && attr[i].percentError < attr[opt].percentError) opt = i;
            if(entropy && attr[i].gain > attr[opt].gain) opt = i;
            System.out.print(" | "+attr[i].traitNum()+": "+df.format((entropy?attr[i].gain:attr[i].percentError)));
            
        }
        
        //Select lowest error attribute
        System.out.println("\n> Attribute selected: <"+attr[opt].traitNum()+">\n");
        return attr[opt];
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
    
    public static Trait[] removeTrait(Trait[] traits, int trait){
        Trait[] ret = new Trait[traits.length-1];
        
        int found = 0;
        
        for(int i = 0; i < ret.length; i++){
            if(traits[i].trait==trait) found = 1;
            ret[i] = traits[i+found];
        }
        
        return ret;
    }
       
}