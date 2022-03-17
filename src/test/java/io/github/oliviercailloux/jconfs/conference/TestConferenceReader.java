package io.github.oliviercailloux.jconfs.conference;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import net.fortuna.ical4j.data.ParserException;
import org.junit.jupiter.api.Test;

public class TestConferenceReader {

	protected ConferenceReader Confr;
	protected ConferencesFromICal confI;
	protected Set<Conference> setOfConf;

	@Test
	public void testLocalCalendar() throws IOException, ParserException, InvalidConferenceFormatException {
		Confr = new ConferenceReader();
		confI = new ConferencesFromICal();
		setOfConf = new LinkedHashSet<>();
		setOfConf.addAll(confI.retrieve("Calendartest2"));
		Iterator<Conference> iteratorConf = setOfConf.iterator();
		assertEquals(setOfConf.size(), 4);
		Conference conferenceTest = iteratorConf.next();
		assertEquals(conferenceTest.getTitle(), "Java");
		assertEquals(conferenceTest.getCity(), "Toronto");
		assertEquals(conferenceTest.getCountry(), "Canada");
		assertEquals(conferenceTest.getFeeRegistration().get(), 22);
	}

}
