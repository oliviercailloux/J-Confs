package io.github.oliviercailloux.jconfs.calendar;

import com.github.caldav4j.CalDAVCollection;
import com.github.caldav4j.methods.CalDAV4JMethodFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import io.github.oliviercailloux.jaris.collections.ImmutableCompleteMap;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader;
import io.github.oliviercailloux.jconfs.calendar.JARiS.FruuxKeysCredential;
import java.nio.file.Path;

/**
 * @author machria & sbourg Builder which create the connection with online calendar
 */
public class CalDavCalendarGeneric {
  protected CredentialsProvider credsProvider = new BasicCredentialsProvider();
  protected CloseableHttpClient httpclient;
  protected HttpHost hostTarget;
  protected CalDAVCollection collectionCalendarsOnline;
  protected static int port = 443;
  protected String postUrl;

  /**
   * Constructor for a generic calendar object
   * 
   * @param url
   * @param userName
   * @param password
   * @param calendarID
   * @param port
   * @param postUrl
   */
  public CalDavCalendarGeneric(ImmutableCompleteMap<FruuxKeysCredential, String> myAuth,
      String postUrl) {
    this.postUrl = postUrl;
    this.credsProvider = new BasicCredentialsProvider();
    credsProvider.setCredentials(new AuthScope(myAuth.get(FruuxKeysCredential.API_URL), port),
        new UsernamePasswordCredentials(myAuth.get(FruuxKeysCredential.API_USERNAME), myAuth.get(FruuxKeysCredential.API_PASSWORD)));
    httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
    hostTarget = new HttpHost(myAuth.get(FruuxKeysCredential.API_URL), port, "https");
    collectionCalendarsOnline =
        new CalDAVCollection(this.postUrl + "/calendars/" + myAuth.get(FruuxKeysCredential.API_USERNAME) + "/" + myAuth.get(FruuxKeysCredential.API_CalendarID),
            hostTarget, new CalDAV4JMethodFactory(), null);
  }
}
