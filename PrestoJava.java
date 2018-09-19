/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prestojava;

import java.util.ArrayList;

/**
 *
 * @author Manoj Vemuru
 */
public class PrestoJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String socksString = "1/blue/right,2/blue/right,3/red/right,4/blue/left,5/purple/left,6/red/left,7/green/left,8/red/left,9/blue/left";
        
        //splitting whole string by "," into array
        String socksArray[] = socksString.split(",");
        
        //divde array into two arraylist by left/right
        ArrayList<String> leftSocks = new ArrayList<>();
        ArrayList<String> rightSocks = new ArrayList<>();
        for(String s : socksArray) {
            if(s.contains("right")){
                rightSocks.add(s);
            } else if(s.contains("left")) {
                leftSocks.add(s);
            }
        }
        
        //iterate through right or left and compare with other and remove from other
        for (String rightItem : rightSocks) {
            String key = rightItem.split("/")[1];
            String val = rightItem.split("/")[0];
            int pos;
            boolean needToRemove = false;
            for (pos = 0; pos <leftSocks.size(); pos++) {
                if(leftSocks.get(pos).contains(key)){
                    System.out.println(val +" "+ leftSocks.get(pos).split("/")[0]);
                    needToRemove = true;
                    break;
                }
            }
            if(needToRemove) {
                leftSocks.remove(pos);
            }
        }
    }
    
}
