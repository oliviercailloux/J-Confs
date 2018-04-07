import java.io.File;

/*
 * reading the file world.map in the resources1 folder
 */

import org.mapsforge.map.reader.MapFile;

/**
 * 
 * Create a MapFile frome ressources1 directory, print "done" and close it
 *
 */
public class TestMap {
	public static void main(String [] args) {
		
		
		File fichier=new File ("resources1/world.map");
		/*
		 * opens the given map file,
		 *  reads its header data and validates them.
		 */
		MapFile map =new MapFile(fichier);
		
		System.out.println("done");
		map.close();
	}
}
