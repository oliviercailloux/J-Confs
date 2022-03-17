package io.github.oliviercailloux.jconfs.conference;

import java.util.Set;

/**
 * it's a main to test the method ConferencesFiltredByDate
 * 
 * @author stanislas
 *
 */
public class ConferencesShowerTest {
	public static void main(String[] args) throws Exception {
		ConferencesShower testShower = new ConferencesShower(new ConferencesFromICal());
		Set<Conference> testConf = testShower.conferencesFiltredByDate();
		for (Conference conf : testConf) {
			System.out.println(conf.getTitle());
			System.out.println(conf.getStartDate());
		}
	}
}
