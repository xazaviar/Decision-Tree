import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Joseph Ryan
 */
public class Trait{

    final String[] traitType = {"cap-shape","cap-surface","cap-color","bruises?",
                                "odor","gill-attachment","gill-spacing","gill-size",
                                "gill-color","stalk-shape","stalk-root",
                                "stalk-surface-above-ring","stalk-surface-below-ring",
                                "stalk-color-above-ring","stalk-color-below-ring",
                                "veil-type","veil-color","ring-number","ring-type",
                                "spore-print-color","population","habitat"};
    
    final String[][] traitOpt = {{"b","c","x","f","k","s"},
                                 {"f","g","y","s"},
                                 {"n","b","c","g","r","p","u","e","w","y"},
                                 {"t","f"},
                                 {"a","l","c","y","f","m","n","p","s"},
                                 {"a","d","f","n"},
                                 {"c","w","d"},
                                 {"b","n"},
                                 {"k","n","b","h","g","r","o","p","u","e","w","y"},
                                 {"e","t"},
                                 {"b","c","u","e","z","r","m"},
                                 {"f","y","k","s"},
                                 {"f","y","k","s"},
                                 {"n","b","c","g","o","p","e","w","y"},
                                 {"n","b","c","g","o","p","e","w","y"},
                                 {"p","u"},
                                 {"n","o","w","y"},
                                 {"n","o","t"},
                                 {"c","e","f","l","n","p","s","z"},
                                 {"k","n","b","h","r","o","u","w","y"},
                                 {"a","c","n","s","v","y"},
                                 {"g","l","m","p","u","w","d"}};
    
    int sum;
    int size;
    int trait;
    double percentError;
    double gain;
    String[][] counts;

    public Trait(ArrayList<Mushroom> mushrooms, int trait){
        this.trait = trait;
        //counts = new String[traitOpt[trait].length][3];
        //this.size = traitOpt[trait].length;
        //calculateMush(mushrooms);
    }
    
    public void calculate(ArrayList<Mushroom> mushrooms){
        //Clear Counts Array
        counts = new String[traitOpt[trait].length][3];
        
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
        sum = 0;
        size = 0;
        int posSum = 0;
        for(int i = 0; i < counts.length; i++){
            if(counts[i][1]==null) break;
            size++;
            sum+=Integer.parseInt(counts[i][1]);
            posSum+=Integer.parseInt(counts[i][2]);
        }
        
            
        //Calculate Percent error
        this.percentError = 0;
        for(int i = 0; i < size; i++){
            this.percentError += prob(i);
        }
        
        //Calculate the gain
        this.gain = Entropy2(1.0*posSum/sum);//Entropy((1.0*posSum/sum),1.0*(1-posSum/sum));
        for(int i = 0; i < size; i++){
            double num = 1.0*Integer.parseInt(counts[i][2])/Integer.parseInt(counts[i][1]);
            double ent = Entropy2(num);
            this.gain= this.gain-((1.0*Integer.parseInt(counts[i][1]))/sum*ent);
        }
        System.out.print("");
    }
    
    public String name(){
        return traitType[trait];
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
        double select = 1.0*count(i)/sum;
        
        int neg = Integer.parseInt(counts[i][1])-Integer.parseInt(counts[i][2]);
        double edible = (Integer.parseInt(counts[i][2])>=neg?1.0*neg/count(i):1.0*Integer.parseInt(counts[i][2])/count(i));
        return select*edible;
    }
    
    public double Entropy(double p, double n){
        double p1 = (p!=0 && p!=1?p*(Math.log10(Math.pow(p, -1))/Math.log10(2)):0);
        double p2 = (n!=0 && n!=1?n*(Math.log10(Math.pow(n, -1))/Math.log10(2)):0); 
        return p1+p2;
    }
    
    public double Entropy2(double p){
        if(p<=0 || p>=1) return 0;
        else{
            double v1 = Math.log(p)/Math.log(2);
            double v2 = Math.log(1-p)/Math.log(2);
            return -p*v1-(1-p)*v2;
        } 
    }

    public String toString(){
        DecimalFormat df = new DecimalFormat("#.00");
        String ret = "";

        ret+=("> Data Distribution for "+traitType[trait]+": ")+"\n";
        for(int i = 0; i < traitOpt[trait].length; i++){
            ret+=("  -- "+counts[i][0]+": "+counts[i][1]+" | "+counts[i][2]+" ("+df.format(100.0*count(i)/sum)+"%)")+"\n";
        }
        ret+=("  -- total: "+(sum)+" instances")+"\n";

        return ret;
    }
}
