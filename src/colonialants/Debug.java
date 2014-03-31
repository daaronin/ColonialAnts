/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

/**
 *
 * @author George McDaid
 */
public class Debug {
    
    public static boolean DEBUG = true;
    
    public Debug(){
        
    }
    
    public static void O(String s){
        if(DEBUG){
            System.out.println(s);
        }
    }
    
    public static void O(int s){
        if(DEBUG){
            System.out.println(s);
        }
    }
    
    public static void O(){
        if(DEBUG){
            System.out.println();
        }
    }
}
