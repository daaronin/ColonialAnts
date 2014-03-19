package colonialdisplay;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureMapper {

	Texture t = null;
        public static HashMap<String, Rectangle2D.Float> textureMap = new HashMap<String, Rectangle2D.Float>();
	
	
	public Texture initSheet(String filename, String imageFormat){
		InputStream stream = ResourceLoader.getResourceAsStream(filename);
		try {
			t = TextureLoader.getTexture(imageFormat, stream);
		} catch (IOException e) {
			System.err.println("Texture loader error");
			System.exit(-1);
		}
		
		return t;
	}
        
        public void addSpriteLocation(String name, Rectangle2D.Float p){
            textureMap.put(name, p);
        }
        
        public Rectangle2D.Float getSpriteLocation(String name){
            return textureMap.get(name);
        }
	
	public int getSheetID(){
		return t.getTextureID();
	}
	

}
