package io.github.oliviercailloux.jconfs.conference;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.oliviercailloux.jconfs.conference.Conference.ConferenceBuilder;
import java.time.Instant;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for testing just one participant can attend a conference
 * 
 * @author Sarah & Mikhal
 */

public class CheckParticipantsTests {

  /**
   * The test add one participant (Nathan) to the conference "Python". It respects the contract of
   * the Conference class.
   * 
   */

  @Test
  public void testOneParticipant() {
    ConferenceBuilder conf1 = new ConferenceBuilder();
    conf1.setTitle("Python");
    conf1.setCountry("England");
    Instant deb1 = Instant.parse("2022-06-09T10:15:30.00Z");
    conf1.setStartDate(deb1);
    Instant deb2 = Instant.parse("2022-06-10T16:22:52.966Z");
    conf1.setEndDate(deb2);
    conf1.setCity("London");
    conf1.setRegistrationFee(10);
    conf1.setParticipant("Nathan");
    Conference c1 = conf1.build();
    assertEquals(c1.getParticipants(), "[Nathan]");
  }
}
