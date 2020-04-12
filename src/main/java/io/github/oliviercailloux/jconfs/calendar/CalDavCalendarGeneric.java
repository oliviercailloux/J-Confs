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

public class CalDavCalendarGeneric implements CalendarOnCloudInterface{
	String url;
	String userName;
	String password;
	String calendarID;
	CredentialsProvider credsProvider = new BasicCredentialsProvider();
	CloseableHttpClient httpclient;
	HttpHost hostTarget;	
	CalDAVCollection collectionCalendarsOnline;
	int port;
	
	
	
	
	
	public CalDavCalendarGeneric(String url, String userName, String password, String calendarID,int port) {
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.calendarID = calendarID;
		this.port=port;
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
		credsProvider.setCredentials(new AuthScope(getUrl(), this.port), new UsernamePasswordCredentials(getUserName(), getPassword()));
	}
}
