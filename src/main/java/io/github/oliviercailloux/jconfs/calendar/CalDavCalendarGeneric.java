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

/**
 * @author machria & sbourg Builder which create the connection with online calendar
 */
public class CalDavCalendarGeneric {
  protected String url;
  protected String username;
  protected String password;
  protected String calendarId;
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
  public CalDavCalendarGeneric(String url, String userName, String password, String calendarID,
      String postUrl) {
    this.url = url;
    this.username = userName;
    this.password = password;
    this.calendarId = calendarID;
    this.postUrl = postUrl;
    this.credsProvider = new BasicCredentialsProvider();
    credsProvider.setCredentials(new AuthScope(this.url, port),
        new UsernamePasswordCredentials(this.username, this.password));
    httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
    hostTarget = new HttpHost(this.url, port, "https");
    collectionCalendarsOnline =
        new CalDAVCollection(this.postUrl + "/calendars/" + this.username + "/" + this.calendarId,
            hostTarget, new CalDAV4JMethodFactory(), null);
  }
}
