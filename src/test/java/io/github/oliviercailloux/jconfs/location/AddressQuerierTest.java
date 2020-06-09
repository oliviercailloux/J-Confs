package io.github.oliviercailloux.jconfs.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.locationiq.client.ApiException;

/**
 * 
 * @author Floryan
 *
 */

class AddressQuerierTest {

	/**
	 * Test of the creation of an newInstance of AddressQuerier
	 */

	@Test
	public final void testCreateInstance() {
		AddressQuerier t = new AddressQuerier();
		assertEquals(0, t.getAddressFound().size());
		assertEquals(0, t.getAddressInformations().size());
	}

	/**
	 * This method test the correct recovery informations returned by the
	 * autocomplete method of LocationIQ. Important : A sleep of 1 second as been
	 * added due to the request limit per second of the API
	 * 
	 * @throws LocationIq.ApiException
	 * @throws InterruptedException
	 */

	@Test
	public final void testRecoveryAddressInformations() throws ApiException, InterruptedException {
		AddressQuerier t = new AddressQuerier();
		TimeUnit.SECONDS.sleep(1);
		t.recoveryAddressInformations("Université paris dauphine");
		boolean test = (t.getAddressInformations().size() == 2);
		assertTrue(test);
	}

	/**
	 * This method tests the recovery of several addresses informations associated
	 * with a search. In this case we know that Université paris dauphine might
	 * return more than 1 address. Important : A sleep of 1 second as been added due
	 * to the request limit per second of the API
	 * 
	 * @throws LocationIq.ApiException
	 * @throws InterruptedException
	 */

	@Test
	public final void testRecoveryAddressFound() throws ApiException, InterruptedException {
		AddressQuerier t = new AddressQuerier();
		t.recoveryAddressInformations("Université paris dauphine");
		TimeUnit.SECONDS.sleep(1);
		t.recoveryAddressFound();
		boolean test = (t.getAddressFound().size() == 2);
		String test2 = t.getAddressFound().get(0);
		assertTrue(test);
		assertTrue(test2.contains("lat=48.87015115, lon=2.2735218497104"));
	}

	/**
	 * This method tests the builder of TranslationAddress
	 * 
	 * @throws LocationIq.ApiException
	 */

	@Test
	public final void testBuilder() throws ApiException {
		AddressQuerier address = AddressQuerier.AddressQuerierBuilder.build()
				.addressInformations("Avenue jean rostand domont 95330").addressFound().get();
		assertTrue(address.getAddressFound().contains(
				"Avenue Jean Rostand, La Belle Rachée, Domont, Ile-de-France, 95330, France, lat=49.0358446, lon=2.341247"));

	}

}
