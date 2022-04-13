package io.github.oliviercailloux.jconfs.location;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.locationiq.client.ApiException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class DistanceDurationTests {

	/**
	 * 
	 * Unit test in order to verify the good get of distance and duration. Carreful
	 * this test depend on API calculation so it might fail in the future. Important
	 * : A sleep of 1 second as been added due to the request limit per second of
	 * the API free use.
	 * 
	 * @throws ApiException
	 * @throws InterruptedException
	 */

	@Test
	void testGetDistanceDuration() throws ApiException, InterruptedException {
		Address departure = Address.given("13 Rue Cloche Percé, 75004 Paris", "48.8564037", "2.3572215");
		Address arrival = Address.given("15 Avenue du général de gaulle, 92800 puteaux", "48.8851553", "2.2269999");
		DistanceDuration result = DistanceDuration.newDistanceDuration(departure, arrival);
		TimeUnit.SECONDS.sleep(1);
		assertTrue((5000 <= result.getDistance()) && (result.getDistance() <= 15000));
		assertTrue((1000 <= result.getDuration()) && (result.getDuration() <= 2000));
		assertTrue((100 <= result.getAllSteps().size()) && (result.getAllSteps().size() <= 200));
	}
}
