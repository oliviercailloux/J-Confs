package io.github.oliviercailloux.jconfs.location;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.locationiq.client.ApiException;

class DistanceDurationTests {

	/**
	 * 
	 * Unit test in order to verify the good get of distance and duration. Carreful
	 * this test depend on API calculation so it might fail in the future Important
	 * : A sleep of 1 second as been added due to the request limit per second of
	 * the API
	 * 
	 * 
	 * @throws ApiException
	 * @throws InterruptedException
	 */

	@Test
	void testGetDistanceDuration() throws ApiException, InterruptedException {
		int distanceExpected = 11168;
		int durationExpected = 1048;
		Address departure;
		Address arrival;
		DistanceDuration result = DistanceDuration.newDistanceDuration("13 Rue Cloche Percé, 75004 Paris",
				"15 Avenue du général de gaulle, 92800 puteaux");
		TimeUnit.SECONDS.sleep(1);
		result.calculateDistanceDuration();
		assertEquals(distanceExpected, result.getDistance());
		assertEquals(durationExpected, result.getDuration());

	}
}
