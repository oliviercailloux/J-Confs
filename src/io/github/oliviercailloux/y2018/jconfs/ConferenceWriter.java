package io.github.oliviercailloux.y2018.jconfs;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;

import ezvcard.Ezvcard;
import ezvcard.VCard;


public class ConferenceWriter {
	
	public static void main(String [] args) throws IOException {
		
		URL url = null;
		String title = "Coucou";
		Instant startDate = Instant.now();
		Instant endDate= Instant.now();
		Double registrationFee = 12.05;
		String city= "Paris";
		String country= "France";
		
		VCard vcard = new VCard();
		
		Conference conference = new Conference(url);
		
		conference.setTitle(title);
		
		conference.setStartDate(startDate);
		conference.setEndDate(endDate);
		conference.setFeeRegistration(registrationFee);
		conference.setCity(city);
		conference.setCountry(country);
		
		vcard.setFormattedName(conference.toString());
		File file = new File("vcard.vcf");
		Ezvcard.write(vcard).go(file);		
		
		
	}
}
