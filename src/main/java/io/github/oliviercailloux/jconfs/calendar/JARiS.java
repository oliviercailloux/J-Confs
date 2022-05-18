package io.github.oliviercailloux.jconfs.calendar;

import io.github.oliviercailloux.jaris.collections.ImmutableCompleteMap;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader.ClassicalCredentials;
import io.github.oliviercailloux.jconfs.conference.Conference;
import java.nio.file.Path;
import com.google.common.collect.ImmutableSet;
import java.util.*;


public class JARiS {
  
  public static enum FruuxKeysCredential {
    API_USERNAME,
    API_PASSWORD,
    API_URL,
    API_CalendarID,
  }
  
  public static ImmutableCompleteMap<FruuxKeysCredential, String> myAuth;
  public static CredentialsReader<FruuxKeysCredential> reader;
  
  
  public ImmutableCompleteMap<FruuxKeysCredential, String> editFruuxKeys(String project_username , String project_password , String project_url , String project_calendarID){
    
    System.setProperty("API_USERNAME", project_username);
    System.setProperty("API_PASSWORD", project_password);
    System.setProperty("API_URL", project_url);
    System.setProperty("API_CalendarID", project_calendarID);
    reader = CredentialsReader.using(FruuxKeysCredential.class, Path.of("my file.txt"));
    myAuth = reader.getCredentials();
    return myAuth ;

  }
  
  
public void printFruuxKeys(){
    
    System.out.println("API_USERNAME : " + myAuth.get(FruuxKeysCredential.API_USERNAME));
    System.out.println("API_PASSWORD : " + myAuth.get(FruuxKeysCredential.API_PASSWORD));
    System.out.println("API_URL : " + myAuth.get(FruuxKeysCredential.API_URL));
    System.out.println("API_CalendarID : " + myAuth.get(FruuxKeysCredential.API_CalendarID));
    
  }

public String getFruuxUsername() {
  
  return myAuth.get(FruuxKeysCredential.API_USERNAME);
  
}

public String getFruuxPassword() {
  
  return myAuth.get(FruuxKeysCredential.API_PASSWORD);
  
}

public String getFruuxURL() {
  
  return myAuth.get(FruuxKeysCredential.API_URL);
  
}

public String getFruuxCalendarID() {
  
  return myAuth.get(FruuxKeysCredential.API_CalendarID);
  
}
  
  
  
  
 


  
}
