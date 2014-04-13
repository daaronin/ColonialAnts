/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author George McDaid
 */
public class Debug {
    
    public static boolean DEBUG = true;
    private static boolean LOGGING = false;
    
    private static File LogFile;
    private static BufferedWriter fout;
    
    public Debug(){
        
    }
    
    public static void o(Object s){
        if(DEBUG){
            System.out.println(s.toString());
        }
    }
    
    public static void o(int s){
        if(DEBUG){
            System.out.println(s);
        }
    }
    
    public static void o(){
        if(DEBUG){
            System.out.println();
        }
    }
    
    public static void initLog(String filename){
        LogFile = new File(filename);
        
        try {
            
            if (!LogFile.exists()) {
                LogFile.createNewFile();
            }

            FileWriter fw = new FileWriter(LogFile.getAbsoluteFile());
            fout = new BufferedWriter(fw);

            fout.write("delta, netRes, ants, leaves, res, evap, lay\n");
            
            LOGGING = true;
        
        } catch (IOException ex) {
            Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void logCycle(int delta, int netResources, int ants, int leaves, int resources, double evap, int lay){
        if(LOGGING){
            try {
                fout.write(delta + "," + netResources + "," + ants + "," + leaves + "," + resources + "," + evap + "," + lay + "\n");
            } catch (IOException ex) {
                Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void closeLog(){
        try {
            if(LOGGING){
                fout.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
