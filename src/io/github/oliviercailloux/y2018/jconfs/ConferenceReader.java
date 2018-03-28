package io.github.oliviercailloux.y2018.jconfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import ezvcard.VCard;
import ezvcard.ValidationWarning;
import ezvcard.io.text.*;

/**
 * A ConferenceReader is an Object that enables to read a vcf file and create a list of Conference 
 *
 * 
 * @author stanislas
 *
 */
public class ConferenceReader {

	private VCardReader reader;
	private VCard data;

	/*
	 * 
	 */
	public ConferenceReader(File VCFfile) throws IOException  {
		this.reader = new VCardReader(VCFfile);
		this.data=reader.readNext();
	}
	
	/*
	 * Create a liste of confrence by reading a VCard file
	 * 
	 */
	public List<Conference> createConference()  {
		Conference conf;
		List<Conference> confList=new ArrayList <>();
		while (data!=null) {
			conf=new Conference (data.getUrls().get(0));//!! il faudra vérifier que le champ url n'est pas vide
			conf.setCity(data.getAddresses().get(0).getLocality());//!! il faudra vérifier que le champ url n'est pas vide
			conf.setCity(data.getAddresses().get(0).getCountry());//!! il faudra vérifier que le champ url n'est pas vide
			conf.setTitle(data.getTitles().get(0));//!! il faudra vérifier que le champ url n'est pas vide
		// a complete\\
			confList.add(conf);
		
		}
		return confList;
	}
}
