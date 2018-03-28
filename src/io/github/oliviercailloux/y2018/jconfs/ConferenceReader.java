package io.github.oliviercailloux.y2018.jconfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import ezvcard.VCard;
import ezvcard.ValidationWarning;
import ezvcard.io.text.*;

/**
 * A ConferenceReader is an Object that enables to read a vcf file and create an
 * object Conference
 * 
 * @author stanislas
 *
 */
public class ConferenceReader {

	private VCardReader reader;

	public ConferenceReader(File VCFfile) throws FileNotFoundException  {
		this.reader = new VCardReader(new File("Jean_Dupont.vcf"));
		
		
	}

	public Conference createConference() throws IOException {
		System.out.println(reader.readNext());
		VCard Vcard =reader.readNext();
		System.out.println("test");
		return null;
	}
}
