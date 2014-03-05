package artillery;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLoader 
{
	private static ImageLoader single = new ImageLoader();
	
	private HashMap<String, Sprite>sprites = new HashMap<String, Sprite>();
	
	
	
	public static ImageLoader get()
	{
		return single;
	}
	
	public Sprite getSprtie(String ref)
	{
		if(sprites.get(ref) != null)
		{
			return (Sprite) sprites.get(ref);
		}
		

		BufferedImage sourceImage = null;
		
		try
		{
			URL url = this.getClass().getClassLoader().getResource(ref);
			
			sourceImage = ImageIO.read(url);
		}
		catch(IOException e)
		{
			
		}
		
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
		
		// draw our source image
		image.getGraphics().drawImage(sourceImage,0,0,null);
		
		// create a sprite, add it then return it
		Sprite sprite = new Sprite(image);
		sprites.put(ref,sprite);
		
		return sprite;
	}
	
}
