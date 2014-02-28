package colonialdisplay;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureMapper {

	public static HashMap<String, Texture> textureMap = new HashMap<String, Texture>();
	
	public int getWidth(String name){
		return textureMap.get(name).getTextureHeight();
	}
	
	public int getHeight(String name){
		return textureMap.get(name).getTextureWidth();
	}
	
	public Texture createTexture(String filename, String imageFormat, String name){
		InputStream stream = ResourceLoader.getResourceAsStream(filename);
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture(imageFormat, stream);
			textureMap.put(name, texture);
		} catch (IOException e) {
			System.err.println("Texture loader error");
			System.exit(-1);
		}
		
		return texture;
	}
	
	public int getTextureID(String name){
		return textureMap.get(name).getTextureID();
	}
	
	public int count(){
		return textureMap.size();
	}
	

}
