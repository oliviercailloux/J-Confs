package io.github.oliviercailloux.jconfs.conference;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.oliviercailloux.jconfs.conference.Conference.ConferenceBuilder;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class TestNumberOfParticipants {

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
  }

  @Test
  public void testThreeParticipants() {
    ConferenceBuilder conf2 = new ConferenceBuilder();
    conf2.setTitle("UML");
    conf2.setCity("Madrid");
    conf2.setCountry("Spain");
    Instant deb1 = Instant.parse("2022-06-08T10:22:52.966Z");
    conf2.setStartDate(deb1);
    Instant deb2 = Instant.parse("2022-06-11T16:22:52.966Z");
    conf2.setEndDate(deb2);
    conf2.setParticipant("Sarah");
    assertThrows(IllegalStateException.class, () -> conf2.setParticipant("Lea"),
        "Only one participant can attend a conference");
  }
}
