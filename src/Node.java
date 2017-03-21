
import java.util.ArrayList;


/**
 *
 * @author Joseph Ryan
 */
public class Node {
    ArrayList<Branch> children = new ArrayList<>();
    String name;
    
    public Node(String name){
        this.name = name;
    }
}
