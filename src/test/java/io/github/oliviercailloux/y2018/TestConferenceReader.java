package io.github.oliviercailloux.y2018;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ConferenceReader;
import io.github.oliviercailloux.y2018.jconfs.ConferencesFromICal;

public class TestConferenceReader {

	
	protected ConferenceReader Confr;
	protected ConferencesFromICal confI;
	protected Set<Conference> setOfConf;
	@Before
	public void setUp() throws Exception {
		Confr=new ConferenceReader();
		confI=new ConferencesFromICal();
		setOfConf=new LinkedHashSet<Conference>();
		setOfConf.addAll(confI.retrive("test2"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
	 assertEquals(setOfConf.size(), 3);
	 assertEquals(((Conference) setOfConf.toArray()[0]).getTitle(),"java");
	 assertTrue(((Conference) setOfConf.toArray()[1]).getCountry()!="France");
	}
	

}
