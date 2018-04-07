import java.io.File;

/*
 * reading the file world.map in the resources1 folder
 */

import org.mapsforge.map.reader.MapFile;

public class TestMap {
	public static void main(String [] args) {
		
		
		File fichier=new File ("resources1/world.map");
		
		MapFile map =new MapFile(fichier);
		
		System.out.println("done");
		map.close();
	}
}
