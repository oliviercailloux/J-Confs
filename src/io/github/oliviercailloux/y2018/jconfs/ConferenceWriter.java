package io.github.oliviercailloux.y2018.jconfs;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Title;
import ezvcard.property.Url;


public class ConferenceWriter {
	
	public static void main(String [] args) throws IOException {
		
		Url url = new Url("urltest");
		Title title = new Title("Coucou");
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
		
		System.out.println(conference.toString());
		vcard.setFormattedName(conference.toString());
		File file = new File("vcardtest.vcf");
		Ezvcard.write(vcard).go(file);		
		
		
	}
}
