package io.github.oliviercailloux.y2018.jconfs;

import java.net.URL;

public class Main  {
	public static void main (String [] args) throws Exception {
		Conference conferenceToWrite = new Conference(new URL("http://www.test.com"));
		conferenceToWrite.setTitle("coucou");
		conferenceToWrite.setCountry("America");
		conferenceToWrite.setCity("New_YorkS");
		conferenceToWrite.setEndDate("2017-01-04");
		conferenceToWrite.setStartDate("2017-06-06");
		conferenceToWrite.setFeeRegistration(12.5);
		ConferenceWriter.writeCalendarFiles(conferenceToWrite);
		
		System.out.println("done");



	}
}
