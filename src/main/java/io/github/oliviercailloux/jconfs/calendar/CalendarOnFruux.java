package io.github.oliviercailloux.jconfs.calendar;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.github.caldav4j.CalDAVCollection;
import com.github.caldav4j.CalDAVConstants;
import com.github.caldav4j.methods.CalDAV4JMethodFactory;

public class CalendarOnFruux implements CalendarOnCloudInterface {
	String url;
	String userName;
	String password;
	String calendarID;
	CredentialsProvider credsProvider = new BasicCredentialsProvider();
	CloseableHttpClient httpclient;
	HttpHost hostTarget;	
	CalDAVCollection collectionCalendarsOnline;
	
	
	public static CalendarOnFruux newInstance() {
		return new CalendarOnFruux();
	}
	
	private CalendarOnFruux() {
		this.url = "dav.fruux.com";
		this.userName = "b3297431258";
		this.password = "jizbr5fuj9gi";
		this.calendarID = "6e8c6372-eba5-43da-9eed-8e5413559c99";
		setCredentials();
		this.httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		this.hostTarget = new  HttpHost(url, 443, "https");
		this.collectionCalendarsOnline = new CalDAVCollection("/calendars/" + userName + "/" + calendarID, hostTarget, new CalDAV4JMethodFactory(), CalDAVConstants.PROC_ID_DEFAULT);
	}
	
	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setCredentials() {
		credsProvider.setCredentials(new AuthScope(getUrl(), 443), new UsernamePasswordCredentials(getUserName(), getPassword()));
	}

	
}
