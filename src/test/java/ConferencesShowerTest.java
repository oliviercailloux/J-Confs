import java.io.IOException;
import java.text.ParseException;

import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ConferencesFromICal;
import io.github.oliviercailloux.y2018.jconfs.ConferencesShower;
import net.fortuna.ical4j.data.ParserException;

public class ConferencesShowerTest {
	public static void main(String[]args) throws NumberFormatException, IOException, ParserException, ParseException {
		ConferencesShower testShower=new ConferencesShower(new ConferencesFromICal());
		Conference testConf=testShower.searchConference();
		System.out.println(testConf.getCountry());
	}
}
