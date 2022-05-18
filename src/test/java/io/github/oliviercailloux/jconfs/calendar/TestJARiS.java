package io.github.oliviercailloux.jconfs.calendar;

import io.github.oliviercailloux.jaris.credentials.CredentialsReader;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader.ClassicalCredentials;
import io.github.oliviercailloux.jconfs.calendar.JARiS.FruuxKeysCredential;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestJARiS {

  @Test
  public void testGetClassicalCredential() {
    
    String API_USERNAME = "API_USERNAME";
    String API_PASSWORD = "API_PASSWORD";
    System.setProperty(API_USERNAME, "username");
    System.setProperty(API_PASSWORD, "password");
   
    CredentialsReader<ClassicalCredentials> reader = CredentialsReader.classicalReader();
    Map<ClassicalCredentials, String> myAuth = reader.getCredentials();
    
    assertEquals(API_USERNAME,myAuth.get(ClassicalCredentials.API_USERNAME));
    assertEquals(API_PASSWORD,myAuth.get(ClassicalCredentials.API_PASSWORD));
    
  }
  
  
  @Test
  public void testGetFruuxCredential() {
    
    FruuxKeysCredential testCrendential = new CredentialsReader.FruuxKeysCredential();
    
    final String test_username = "b3297393754";
    final String test_password = "4pq8nzbhzugs";
    final String test_url = "dav.fruux.com";
    final String test_calendarID = "8b3ff300-b8ce-4d85-a255-76ea3dff1338";
    
    CredentialsReader<FruuxKeysCredential> testAuth = editFruuxKeys(test_username,test_password,test_url,test_calendarID);
    assertEquals(test_username,getFruuxUsername());
    assertEquals(test_password,getFruuxUsername());
    assertEquals(test_url,getFruuxUsername());
    assertEquals(test_calendarID,getFruuxUsername());
    
    
  }
  
  
}
