
/**
 *
 * @author Joseph Ryan
 */
public class Mushroom {
    
    /**
     * The following are the attribute codes
     * 0. edibility:                edible=e, poisonous=p
     * 1. cap-shape:                bell=b,conical=c,convex=x,flat=f,
     *                              knobbed=k,sunken=s
     * 2. cap-surface:              fibrous=f,grooves=g,scaly=y,smooth=s
     * 3. cap-color:                brown=n,buff=b,cinnamon=c,gray=g,green=r,
     *                              pink=p,purple=u,red=e,white=w,yellow=y
     * 4. bruises?:                 bruises=t,no=f
     * 5. odor:                     almond=a,anise=l,creosote=c,fishy=y,foul=f,
     *                              musty=m,none=n,pungent=p,spicy=s
     * 6. gill-attachment:          attached=a,descending=d,free=f,notched=n
     * 7. gill-spacing:             close=c,crowded=w,distant=d
     * 8. gill-size:                broad=b,narrow=n
     * 9. gill-color:               black=k,brown=n,buff=b,chocolate=h,gray=g,
     *                              green=r,orange=o,pink=p,purple=u,red=e,
     *                              white=w,yellow=y
     *10. stalk-shape:              enlarging=e,tapering=t
     *11. stalk-root:               bulbous=b,club=c,cup=u,equal=e,
     *                              rhizomorphs=z,rooted=r,missing=?
     *12. stalk-surface-above-ring: fibrous=f,scaly=y,silky=k,smooth=s
     *13. stalk-surface-below-ring: fibrous=f,scaly=y,silky=k,smooth=s
     *14. stalk-color-above-ring:   brown=n,buff=b,cinnamon=c,gray=g,orange=o,
     *                              pink=p,red=e,white=w,yellow=y
     *15. stalk-color-below-ring:   brown=n,buff=b,cinnamon=c,gray=g,orange=o,
     *                              pink=p,red=e,white=w,yellow=y
     *16. veil-type:                partial=p,universal=u
     *17. veil-color:               brown=n,orange=o,white=w,yellow=y
     *18. ring-number:              none=n,one=o,two=t
     *19. ring-type:                cobwebby=c,evanescent=e,flaring=f,large=l,
     *                              none=n,pendant=p,sheathing=s,zone=z
     *20. spore-print-color:        black=k,brown=n,buff=b,chocolate=h,green=r,
     *                              orange=o,purple=u,white=w,yellow=y
     *21. population:               abundant=a,clustered=c,numerous=n,
     *                              scattered=s,several=v,solitary=y
     *22. habitat:                  grasses=g,leaves=l,meadows=m,paths=p,
     *                              urban=u,waste=w,woods=d
     */
    String[] attr = new String[23];
    boolean edible;
    
    /**
     * Receive the attributes and assign them
     * to this mushroom
     * @param att 
     *          The attributes of the mushroom
     */
    public Mushroom(String[] att){
        for(int i = 0; i < att.length-1; i++){
            this.attr[i] = att[i+1];
        }
        
        this.edible = att[0].equals("e");
    }
}
