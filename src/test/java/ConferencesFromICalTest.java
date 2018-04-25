import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.impl.ServletContextCleaner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ConferenceReader;
import io.github.oliviercailloux.y2018.jconfs.ConferencesFromICal;
import io.github.oliviercailloux.y2018.jconfs.ConferencesShower;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.DateTime;

public class ConferencesFromICalTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestConferenceReader.class);

	/**
	 * this method test retrive method in ConferencesFromIcal
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParserException
	 * @throws ParseException
	 */

	@Test
	public final void retriveTest() throws NumberFormatException, IOException, ParserException, ParseException {
		ConferencesFromICal testConfFromIcal = new ConferencesFromICal();

		// TEST 1
		Set<Conference> setConfTest1 = testConfFromIcal.retrive("test2.ics");

		assertEquals(1, setConfTest1.size());
		LOGGER.debug("setConfTest1 size is 1");

		Conference confTest1 = setConfTest1.iterator().next();
		assertEquals("java", confTest1.getTitle());
		LOGGER.debug("test 1 done");

		// TEST 2
		Set<Conference> setConfTest2 = testConfFromIcal.retrive(new DateTime("20000101"),
				new DateTime("20200101"));
		assertEquals(2, setConfTest2.size());
		LOGGER.debug("setConfTest2 size is 2");
		Iterator<Conference> iteratorTest=setConfTest2.iterator();
		
		Conference confTest21=iteratorTest.next();
		assertEquals("Japon",confTest21.getCountry());
		Conference confTest22=iteratorTest.next();

		assertEquals("Chine",confTest22.getCountry());
		

	}
}
