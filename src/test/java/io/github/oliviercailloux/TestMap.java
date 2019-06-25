package io.github.oliviercailloux;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import org.mapsforge.map.reader.MapFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMap {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestMap.class);

	/**
	 * this main create a MapFile from a file in classPath
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		URL fileURL = TestMap.class.getClassLoader().getResource("world.map");
		if (fileURL == null)
			throw new FileNotFoundException("file doesn't exist");
		LOGGER.debug("URL", fileURL);
		File fichier = new File(fileURL.getFile());
		LOGGER.debug("File", fichier);
		if (!fichier.isFile())
			throw new FileNotFoundException("it's not a file");

		MapFile map = new MapFile(fichier);
		LOGGER.debug("MapFile", map);

		System.out.println("done");
		map.close();
	}
}
