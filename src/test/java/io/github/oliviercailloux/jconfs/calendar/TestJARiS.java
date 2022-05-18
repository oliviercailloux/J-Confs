package io.github.oliviercailloux.jconfs.calendar;

import io.github.oliviercailloux.jaris.credentials.CredentialsReader;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader.ClassicalCredentials;
import java.nio.file.Path;
import java.util.Map;

public class TestJARiS {

  public static void main(String[] args) {
    
    String API_USERNAME = "API_USERNAME";
    String API_PASSWORD = "API_PASSWORD";
    System.setProperty(API_USERNAME, "username");
    System.setProperty(API_PASSWORD, "password");
   
    CredentialsReader<ClassicalCredentials> reader = CredentialsReader.classicalReader();
    Map<ClassicalCredentials, String> myAuth = reader.getCredentials();
    
    String key1 = myAuth.get(ClassicalCredentials.API_USERNAME);
    String key2 = myAuth.get(ClassicalCredentials.API_PASSWORD);
    
    System.out.println(API_USERNAME + " : " +key1);
    System.out.println(API_PASSWORD + " : " +key2);
     

  }
}
