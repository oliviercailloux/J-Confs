import java.io.File;

import org.mapsforge.map.reader.MapFile;

public class TestMap {
	public static void main(String [] args) {
		File fichier=new File ("world.map");
		
		MapFile map =new MapFile(fichier);
		
		map.close();
	}
}
