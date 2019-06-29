package io.github.oliviercailloux.map;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.geocode.ReverseGeoCode;

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
	@SuppressWarnings("resource")
	@Test
	public void nearestPlaceTest() throws FileNotFoundException, IOException {
		ReverseGeoCode reverseGeoCode = new ReverseGeoCode(new FileInputStream("src/main/resources/io/github/oliviercailloux/jconfs/map/AU.txt"), true);
		//System.out.println("Nearest to -23.456, 123.456 is " + reverseGeoCode.nearestPlace(-23.456, 123.456));
		assertEquals(reverseGeoCode.nearestPlace(-23.456, 123.456).getName(),"Telfer");
		ReverseGeoCode reverseGeoCode2 = new ReverseGeoCode(new FileInputStream("src/main/resources/io/github/oliviercailloux/jconfs/map/cities15000.txt"), true);
		assertEquals(reverseGeoCode2.nearestPlace(39.913818, 116.363625).getName(),"Beijing");
	}

}
