package io.github.oliviercailloux.y2018.jconfs;

import java.io.File;
import java.io.IOException;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Address;
import ezvcard.property.Title;
import ezvcard.property.Url;


public class VCardWriter {
	public static void main(String [] args) throws IOException {
		
		Url url = new Url("URL test");
		Title profession = new Title("Maitre de conference");
		Double registrationFee = 12.05;
		String city= "Paris";
		String country= "France";
		String address= "34 rue de la conf";
		
		VCard vcard = new VCard();
		
		MemberVcard member = new MemberVcard(url);
		
		member.setTitle(profession);
		member.setFeeRegistration(registrationFee);
		member.setCity(city);
		member.setCountry(country);
		member.setAddress(address);
		
		Address adr = new Address();
		
		adr.setStreetAddress(member.getAddress());
		adr.setLocality(member.getCity());
		adr.setCountry(member.getCountry());
		
		vcard.addAddress(adr);
		/*vcard.addTitle(conference.getTitle().getValue());*/
		vcard.addTitle(member.getTitle());
		vcard.addUrl(member.getUrl());
		
		vcard.addNote("registation fee: " + member.getFeeRegistration()+ " euros");

		
		File file = new File("membertest.vcf");
		Ezvcard.write(vcard).go(file);
	
		
	}
}
