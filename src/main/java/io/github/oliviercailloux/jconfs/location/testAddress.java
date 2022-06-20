package io.github.oliviercailloux.jconfs.location;

import com.locationiq.client.ApiException;
import java.util.Optional;

public class testAddress {

  public static void main(String[] args) throws ApiException, InterruptedException {
    Address address;
    AddressQuerier addQ;
    
    addQ = AddressQuerier.given("Bibliothèque paris");
    
    System.out.println("getAddressInformations");
    System.out.println(addQ.getAddressInformations());
    
    address = addQ.getAddressFound().get(0);
    System.out.println(address.getAddress());
    System.out.println(address.getLatitude());
    System.out.println(address.getLongitude());
    
  }
}
