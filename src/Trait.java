import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Joseph Ryan
 */
public class Trait{

    final String[][] traitType = {//{"edibility"                ,"2"},
                                 {"cap-shape"                ,"6"},
                                 {"cap-surface"              ,"4"},
                                 {"cap-color"                ,"10"},
                                 {"bruises?"                 ,"2"},
                                 {"odor"                     ,"9"},
                                 {"gill-attachment"          ,"4"},
                                 {"gill-spacing"             ,"3"},
                                 {"gill-size"                ,"2"},
                                 {"gill-color"               ,"12"},
                                 {"stalk-shape"              ,"2"},
                                 {"stalk-root"               ,"7"},
                                 {"stalk-surface-above-ring" ,"4"},
                                 {"stalk-surface-below-ring" ,"4"},
                                 {"stalk-color-above-ring"   ,"9"},
                                 {"stalk-color-below-ring"   ,"9"},
                                 {"veil-type"                ,"2"},
                                 {"veil-color"               ,"4"},
                                 {"ring-number"              ,"3"},
                                 {"ring-type"                ,"9"},
                                 {"spore-print-color"        ,"9"},
                                 {"population"               ,"6"},
                                 {"habitat"                  ,"7"}};
    
    
    int sum;
    int trait;
    int size;
    double percentError;
    String[][] counts;

    public Trait(ArrayList<Mushroom> mushrooms, int trait){
        this.trait = trait;
        //calculateMush(mushrooms);
    }
    
    public void calculate(ArrayList<Mushroom> mushrooms){
        counts = new String[Integer.parseInt(traitType[trait][1])][3];

        //Collect data
        for(Mushroom m: mushrooms){
            for(int i = 0; i < counts.length; i++){
                if(counts[i][0]==null){
                    counts[i][0] = m.attr[trait];
                    counts[i][1] = "1";
                    counts[i][2] = (m.edible?"1":"0");
                    break;
                }else if(counts[i][0].equals(m.attr[trait])){
                    counts[i][1] = ""+(Integer.parseInt(counts[i][1])+1);
                    counts[i][2] = (m.edible?""+(Integer.parseInt(counts[i][2])+1):""+Integer.parseInt(counts[i][2]));
                    break;
                }
            }
        }

        //Store additional data
        size = 0;
        sum = 0;
        for(int i = 0; i < counts.length; i++){
            if(counts[i][1]==null) break;
            size++;
            sum+=Integer.parseInt(counts[i][1]);
        }
        
        //Calculate Percent error
        this.percentError = 0;
        for(int i = 0; i < size; i++){
            this.percentError += prob(i);
        }
        
        if(this.percentError==0 || this.percentError==1)
            System.out.println("TEST");
    }
    
    public String name(){
        return traitType[trait][0];
    }
    
    public String traitNum(){
        if(trait+1 < 10)
            return "0"+(trait+1);
        else
            return ""+(trait+1);
    }

    public String type(int i){
        return counts[i][0];
    }

    public int count(int i){
        return Integer.parseInt(counts[i][1]);
    }
    
    public double prob(int i){
        double select = 1.0*Integer.parseInt(counts[i][1])/sum;
        double edible = 1.0*Integer.parseInt(counts[i][2])/sum;
        return select*edible;
    }

    public String toString(){
        DecimalFormat df = new DecimalFormat("#.00");
        String ret = "";

        ret+=("> Data Distribution for "+traitType[trait][0]+": ")+"\n";
        for(int i = 0; i < size; i++){
            ret+=("  -- "+counts[i][0]+": "+counts[i][1]+" ("+df.format(100.0*count(i)/sum)+"%)")+"\n";
        }
        ret+=("  -- total: "+(sum)+" instances")+"\n";

        return ret;
    }
}
