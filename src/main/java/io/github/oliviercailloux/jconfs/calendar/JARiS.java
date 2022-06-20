package io.github.oliviercailloux.jconfs.calendar;

import io.github.oliviercailloux.jaris.collections.ImmutableCompleteMap;
import java.nio.file.*;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader.ClassicalCredentials;
import io.github.oliviercailloux.jconfs.conference.Conference;
import java.nio.file.Path;
import com.google.common.collect.ImmutableSet;
import java.util.*;

public class JARiS {

	public static enum FruuxKeysCredential {
		API_USERNAME, API_PASSWORD, API_URL, API_CalendarID,
	}
	

	public static ImmutableCompleteMap<FruuxKeysCredential, String> myAuth;
	public static CredentialsReader<FruuxKeysCredential> reader;

	public ImmutableCompleteMap<FruuxKeysCredential, String> editFruuxKeys() {


		reader = CredentialsReader.using(FruuxKeysCredential.class, Path.of("API_Credentials_Fruux.txt"));
		myAuth = reader.getCredentials();
		return myAuth;

	}

	public void printFruuxKeys() {

		System.out.println("API_USERNAME : " + myAuth.get(FruuxKeysCredential.API_USERNAME));
		System.out.println("API_PASSWORD : " + myAuth.get(FruuxKeysCredential.API_PASSWORD));
		System.out.println("API_URL : " + myAuth.get(FruuxKeysCredential.API_URL));
		System.out.println("API_CalendarID : " + myAuth.get(FruuxKeysCredential.API_CalendarID));

	}


}
