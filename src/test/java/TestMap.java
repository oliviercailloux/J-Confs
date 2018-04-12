import java.io.File;

import org.mapsforge.map.reader.MapFile;

public class TestMap {
	public static void main(String[] args) {

		/*
		 * 
		 */

		File fichier = new File("src/test/resources/world.map");

		MapFile map = new MapFile(fichier);
		System.out.println("done");
		map.close();
	}
}
