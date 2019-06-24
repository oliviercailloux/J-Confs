import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import io.github.oliviercailloux.geocode.ReverseGeoCode;
/**
 * 
 * @author huong, camille
 *
 */
public class TestGeocode {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		@SuppressWarnings("resource")
		ReverseGeoCode reverseGeoCode = new ReverseGeoCode(new FileInputStream("src/main/resources/io/github/oliviercailloux/y2018/jconfs/AU.txt"), true);
		
		System.out.println("Nearest to -23.456, 123.456 is " + reverseGeoCode.nearestPlace(-23.456, 123.456));
	}

}
