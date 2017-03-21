
import java.util.ArrayList;


/**
 *
 * @author Joseph Ryan
 */
public class Node {
    ArrayList<Branch> children = new ArrayList<>();
    Node parent;
    
    String name;
    
    public Node(String name, Node parent){
        this.name = name;
        this.parent = parent;
    }
}
