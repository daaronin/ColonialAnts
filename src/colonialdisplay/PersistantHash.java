package colonialdisplay;

import colonialants.Ant;
import colonialants.CommonScents;
import colonialants.TerrainLocation;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistantHash {
    
    private static File antFile;
    private static File terrainFile;

	static public void dump(HashMap<Integer, String> map){
		Iterator<Entry<Integer, String>> iter =
				(Iterator<Entry<Integer, String>>) map.entrySet().iterator();
		
		while (iter.hasNext()){
			Entry<Integer, String> entry = iter.next();
			int key = entry.getKey();
			String value = entry.getValue();
			System.out.println( key + "=>" + value);
		}
	}
	
	@SuppressWarnings("unchecked")
	static public HashMap<Integer, Object> read(String filename){
	
		HashMap<Integer, Object> map = null;
		FileInputStream stream = null;
		
		try{
			stream = new FileInputStream(filename);
		} catch (FileNotFoundException e){
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		InputStream buffer = new BufferedInputStream(stream);
		ObjectInput input = null;
		
		try {
			input = new ObjectInputStream(buffer);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		try {
			map = (HashMap<Integer, Object>) input.readObject();
		} catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	static public void setupFile(String ants, String terrain){
            try{
            antFile = new File(ants);
                    terrainFile = new File(terrain);
			
			if (!antFile.exists()){
				antFile.createNewFile();
			}else{
                            antFile.delete();
                            antFile.createNewFile();
                        }
                        
                        if (!terrainFile.exists()){
				terrainFile.createNewFile();
			}else{
                            terrainFile.delete();
                            terrainFile.createNewFile();
                        }
            }catch(Exception e){
                
            }
        }        
        
	static public void writeAnts(ArrayList<Ant> ants){
            
		try {
			
			
			FileOutputStream ostream = new FileOutputStream(antFile.getAbsoluteFile());
			OutputStream bostream = new BufferedOutputStream(ostream);
			
			ObjectOutput output = new ObjectOutputStream(bostream);
			
			output.writeObject(DeepCopy.copy(ants));
			output.close();
			System.out.println("Done!");
		} catch (IOException e){
                    e.printStackTrace();
		} 
                    
            
	}
        
        static public void writeTerrain(HashMap<Integer, TerrainLocation> map, String path){
		try {
			File file = new File(path);
			
			if (!file.exists()){
				file.createNewFile();
			}else{
                            file.delete();
                            file.createNewFile();
                        }
			
			FileOutputStream ostream = new FileOutputStream(file.getAbsoluteFile());
			OutputStream bostream = new BufferedOutputStream(ostream);
			
			ObjectOutput output = new ObjectOutputStream(bostream);
			
			output.writeObject(map);
			output.close();
			System.out.println("Done!");
		} catch (IOException e){
                    e.printStackTrace();
		}
	}
        
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//
//		PersistantHash hd = new PersistantHash();
//		hd.map.put(1, "one");
//		hd.map.put(2, "two");
//		hd.map.put(3, "three");
//		hd.map.put(4, "four");
//		hd.map.put(5, "five");
//		hd.map.put(6, "six");
//		hd.map.put(7, "seven");
//		hd.map.put(8, "eight");
//		hd.map.put(9, "nine");
//		hd.map.put(10, "ten");
//		hd.write(hd.map, "mymap.dat");
//		HashMap<Integer, String> bup = hd.read("mymap.dat");
//		hd.dump(bup);
//	}

}








