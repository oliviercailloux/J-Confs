package io.github.oliviercailloux.jconfs.conference;

import static org.junit.jupiter.api.Assertions.assertEquals;

// import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.Instant;
import java.util.Iterator;
import java.util.Set;
import net.fortuna.ical4j.data.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConferencesFromICalTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(ConferencesFromICalTest.class);

  /**
   * this method test retrive method in ConferencesFromIcal
   * 
   * @throws NumberFormatException
   * @throws IOException
   * @throws ParserException
   * @throws InvalidConferenceFormatException
   */

  public final void retriveTest() throws Exception {
    ConferencesFromICal testConfFromIcal = new ConferencesFromICal();

    // TEST 1
    Set<Conference> setConfTest1 = testConfFromIcal.retrieve("Calendartest2");

    assertEquals(4, setConfTest1.size());
    LOGGER.debug("setConfTest1 size is 4");

    Conference confTest1 = setConfTest1.iterator().next();
    assertEquals("Java", confTest1.getTitle());
    LOGGER.debug("test 1 done");

  }

  public final void retriveTest2() throws Exception {
    ConferencesFromICal testConfFromIcal = new ConferencesFromICal();

    // TEST 2
    Set<Conference> setConfTest2 = testConfFromIcal.retrieve(
        Instant.parse("20-10-2000T16:22:52.966Z"), Instant.parse("20-10-2020T16:22:52.966Z"));
    assertEquals(2, setConfTest2.size());
    LOGGER.debug("setConfTest2 size is 2");
    Iterator<Conference> iteratorTest = setConfTest2.iterator();

    Conference confTest21 = iteratorTest.next();
    assertEquals("Chine", confTest21.getCountry());
    Conference confTest22 = iteratorTest.next();

    assertEquals("Japon", confTest22.getCountry());

  }
}
