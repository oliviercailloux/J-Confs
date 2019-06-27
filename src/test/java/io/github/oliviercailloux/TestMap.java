package io.github.oliviercailloux;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.mapsforge.map.reader.MapFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMap {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestMap.class);

	/**
	 * Method that test if you can create a MapFile from a file in classPath
	 * @throws FileNotFoundException
	 */
	@Test
	public void openMapTest() throws FileNotFoundException{
		String end;
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
		end = "done";
		map.close();
		assertEquals(end,"done");

	}
}
