package io.github.oliviercailloux.jconfs.calendar;

import io.github.oliviercailloux.jaris.collections.ImmutableCompleteMap;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader.ClassicalCredentials;
import java.nio.file.Path;
import com.google.common.collect.ImmutableSet;
import java.util.*;




public class JARiS {
  

//private static final String API_PASSWORD = null;
/*public static enum FruuxKeysCredential {
    API_URL,
    API_CalendarID
  }*/

//private static final String API_USERNAME = null;
  
  /*public static enum ourCredentials{
	  API_USERNAME,
	  API_PASSWORD
  }*/
 
  /*CredentialsReader<FruuxKeysCredential> reader = CredentialsReader.using(FruuxKeysCredential.class, Path.of("my file.txt"));
  ImmutableCompleteMap<FruuxKeysCredential, String> credentials = reader.getCredentials();
  String key1 = credentials.get(FruuxKeysCredential.API_URL);
  String key2 = credentials.get(FruuxKeysCredential.API_CalendarID);
  */
  

  public Object obj;
 


  public static void main(String[] args) {
	String API_USERNAME = "API_USERNAME";
	String API_PASSWORD = "API_PASSWORD";
    System.setProperty(API_USERNAME, "xxx");
    System.setProperty(API_PASSWORD, "xxx");
    System.out.println("a");
   
    CredentialsReader<ClassicalCredentials> reader = CredentialsReader.classicalReader();
    Path test = reader.getFilePath();
    System.out.println(test.toString());
    System.out.println(reader.getKeysType());
    Map<ClassicalCredentials, String> myAuth = reader.getCredentials();
    String key1 = myAuth.get(ClassicalCredentials.API_USERNAME);
    String key2 = myAuth.get(ClassicalCredentials.API_PASSWORD);
    

    System.out.println("init ok ...");    

    


  }
}
