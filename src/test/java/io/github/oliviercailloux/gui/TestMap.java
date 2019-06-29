package io.github.oliviercailloux.gui;
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
		URL fileURL = TestMap.class.getClassLoader().getResource("io/github/oliviercailloux/jconfs/map/world.map");
		if (fileURL == null)
			throw new FileNotFoundException("file doesn't exist");
		LOGGER.debug("URL", fileURL);
		File fichier = new File(fileURL.getFile());
		LOGGER.debug("File", fichier);
		if (!fichier.isFile())
			throw new FileNotFoundException("it's not a file");

		MapFile map = new MapFile(fichier);
		LOGGER.debug("MapFile", map);
		assertEquals(map.startZoomLevel().doubleValue(), 5.0);
		assertEquals(map.startPosition().getLatitude(), 0.0);
		assertEquals(map.startPosition().getLongitude(), 0.0);
		map.startZoomLevel();
		assertEquals(Byte.MAX_VALUE, 127);
		map.close();

	}
}
