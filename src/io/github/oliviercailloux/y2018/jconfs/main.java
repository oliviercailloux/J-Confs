package io.github.oliviercailloux.y2018.jconfs;

public class main  {
	public static void main(String [] args) throws Exception {
		Conference conf;
		conf = ReadCalendarFiles.createConference("basic.ics");
		System.out.println(" On va Lire l'objet conference créé à partir du fichier ics ");
		System.out.println(conf);
		System.out.println(" \n \n  On va Lire le fichier ics");
		ReadCalendarFiles.ReadCalendarFile("basic.ics");
	
		
	
		
	}
}
