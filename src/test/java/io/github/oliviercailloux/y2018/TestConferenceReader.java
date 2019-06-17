package io.github.oliviercailloux.y2018;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;
import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ConferenceReader;
import io.github.oliviercailloux.y2018.jconfs.ConferencesFromICal;
import io.github.oliviercailloux.y2018.jconfs.InvalidConferenceFormatException;
import net.fortuna.ical4j.data.ParserException;

public class TestConferenceReader {

	
	protected ConferenceReader Confr;
	protected ConferencesFromICal confI;
	protected Set<Conference> setOfConf;

	@SuppressWarnings("unused")
	@Test
	public void test() throws IOException, ParserException, InvalidConferenceFormatException {
	Confr=new ConferenceReader();
	confI=new ConferencesFromICal();
	setOfConf=new LinkedHashSet<Conference>();
	setOfConf.addAll(confI.retrieve("Calendartest2"));
	Iterator<Conference> iteratorConf=setOfConf.iterator();
	 assertEquals(setOfConf.size(), 4);
	 assertEquals((iteratorConf.next().getTitle()),"Java");
	 assertEquals((iteratorConf.next().getCountry()),"USA");
	}
	
}
