package io.github.oliviercailloux.jconfs.map;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.geocode.ReverseGeoCode;
import io.github.oliviercailloux.jconfs.gui.GuiConference;

/**
 * 
 * @author huong, camille
 *
 */
public class TestGeocode {
	/**
	 * Method that test if the code still works after a little modification
	 * & test the new file cities 15000
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void nearestPlaceTest() throws FileNotFoundException, IOException {
		URL resourceUrl = GuiConference.class.getResource("cities15000.txt");
		ReverseGeoCode reverseGeoCode = new ReverseGeoCode(resourceUrl.openStream(), true);
		assertEquals(reverseGeoCode.nearestPlace(39.913818, 116.363625).getName(),"Beijing");
	}

}
