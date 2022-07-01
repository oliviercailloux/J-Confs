package io.github.oliviercailloux.jconfs.conference;

import static org.junit.jupiter.api.Assertions.*;

import com.locationiq.client.ApiException;
import io.github.oliviercailloux.jconfs.conference.Conference.ConferenceBuilder;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class CheckAddressTest {

  /**
   * The test add one address (Université Paris Dauphine) to the conference "Python". 
   * It respects the contract of the Conference class.
   * @throws InterruptedException 
   * @throws ApiException 
   */
  @Test
  public void testaddAddress() throws ApiException, InterruptedException {
    ConferenceBuilder conf1 = new ConferenceBuilder();
    conf1.setTitle("Python");
    conf1.setCountry("England");
    Instant deb1 = Instant.parse("2022-06-09T10:15:30.00Z");
    conf1.setStartDate(deb1);
    Instant deb2 = Instant.parse("2022-06-10T16:22:52.966Z");
    conf1.setEndDate(deb2);
    conf1.setCity("London");
    conf1.setRegistrationFee(10);
    conf1.setAddress("Université Paris Dauphine");
    Conference c1 = conf1.build();
    assertEquals(c1.getAddress().get().getAddressName(), "Université Paris Dauphine");
    assertEquals(c1.getAddress().get().getLatitude(), "48.87015115");
  }
  
}
