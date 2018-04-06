package io.github.oliviercailloux.y2018.jconfs;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.TimeZone;

import net.fortuna.ical4j.model.DateTime;

public class Main  {
	public static void main (String [] args) throws Exception {
		Conference conf= new Conference(new URL ("http://www.test.com/"));	
		
		conf=ReadCalendarFiles.createConference("test2.ics");
		System.out.println(" On va Lire l'objet conference créé à partir du fichier ics ");
		System.out.println(conf);
		System.out.println(conf.toString());
		System.out.println(" \n \n  On va Lire le fichier ics");
		ReadCalendarFiles.ReadCalendarFile("test2.ics");




	}
}
