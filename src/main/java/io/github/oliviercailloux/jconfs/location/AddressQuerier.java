package io.github.oliviercailloux.jconfs.location;

import com.locationiq.client.ApiClient;
import com.locationiq.client.ApiException;
import com.locationiq.client.Configuration;
import com.locationiq.client.api.AutocompleteApi;
import com.locationiq.client.auth.ApiKeyAuth;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class allows you to translate an address into several pieces of informations. It therefore
 * allows, from imprecise information, to propose several addresses or a single address if the
 * information entered is precise.
 * 
 * @author Floryan Kieffer
 * 
 * 
 */

public class AddressQuerier {

  private List<String> addressInformations;
  private List<Address> addressFound;
  private ApiClient clientConnexion;

  /**
   * Static factory method to build a AddressQuerier instance
   * 
   * @param address
   * @throws ApiException
   * @throws InterruptedException
   */
  public static AddressQuerier given(String address) throws ApiException, InterruptedException {
    return new AddressQuerier(address);
  }

  /**
   * Private constructor. The connection to the API is made when the AddressQuerier object is
   * instantiated with the call of the requestAddressInformations method.
   * 
   * @param address
   * @throws ApiException
   * @throws InterruptedException
   */
  private AddressQuerier(String address) throws ApiException, InterruptedException {
    this.requestAddressInformations(address);
  }

  /**
   * This method return a list with a lot of informations about an address research
   * 
   * @return adressInformations
   */
  public List<String> getAddressInformations() {
    return addressInformations;
  }

  /**
   * This method return all address found after a researcher address
   * 
   * @return adressFound
   */
  public List<Address> getAddressFound() {
    return addressFound;
  }

  /**
   * This method allows connection to LocationIQ and its database.
   * 
   * @return ApiClient clientConnexion
   */
  public static ApiClient connexion() {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://eu1.locationiq.com/v1");
    ApiKeyAuth key = (ApiKeyAuth) defaultClient.getAuthentication("key");
    key.setApiKey("d4b9a23eaef07d");
    return defaultClient;
  }

  /**
   * 
   * This method allows to create the ArrayList adressInformations making a precise or not address
   * search (precise is better). The ArrayList is filled according to the different elements to
   * find, that is to say that if for an imprecise search it is possible to assign several addresses
   * to it, all these addresses are entered in the ArrayList.
   * 
   * @param adresse
   * @throws ApiException
   * @return adressInformations
   * @throws InterruptedException
   */
  public List<Address> requestAddressInformations(String address)
      throws ApiException, InterruptedException {
    this.addressInformations = new ArrayList<>();
    if (address == "" || address == null || address.isEmpty()) {
      throw new NullPointerException("Address error");
    }
    if (this.clientConnexion == null) {
      ApiClient defaultClient = AddressQuerier.connexion();
      this.clientConnexion = defaultClient;
    }
    TimeUnit.SECONDS.sleep(1);
    AutocompleteApi api = new AutocompleteApi(this.clientConnexion);
    List<Object> tmp = api.autocomplete(address, 1, null, null, null, null, null, null);
    Iterator<Object> i = tmp.iterator();
    while (i.hasNext()) {
      this.addressInformations.add(i.next().toString());
    }
    int index = 0;
    for (String adr : this.addressInformations) {
      String contenu = adr;
      String hash = contenu.substring(1, contenu.length() - 2);
      this.addressInformations.set(index, hash);
      index++;
    }
    this.TransformeToAddressArray();
    return this.addressFound;
  }

  /**
   * This method retrieves the address, latitude and longitude information for each address found in
   * addressInformations (by AutocompleteApi). From this information it creates several Address
   * objects (as many objects as addresses stored in addressInformations) which it stores in another
   * Address List.
   * 
   * @return addressFound
   */
  private void TransformeToAddressArray() {
    this.addressFound = new ArrayList<>();
    ArrayList<Address> selection = new ArrayList<>();
    for (String s : this.addressInformations) {
      String search1 = "display_name=";
      String search2 = "lat=";
      String search3 = "lon=";
      int posDep = s.indexOf(search1);
      int posArr = s.indexOf(", display_place=");
      String address = s.substring(posDep + search1.length(), posArr);
      int posDepLat = s.indexOf(search2);
      int posArrLat = s.indexOf(", lon=");
      String lat = s.substring(posDepLat + search2.length(), posArrLat);
      int posDepLon = s.indexOf(search3);
      int posArrLon = s.indexOf(", boundingbox=");
      String lon = s.substring(posDepLon + search3.length(), posArrLon);
      Address adr = Address.given(address, lat, lon);
      selection.add(adr);
    }
    this.addressFound = selection;
  }
}
