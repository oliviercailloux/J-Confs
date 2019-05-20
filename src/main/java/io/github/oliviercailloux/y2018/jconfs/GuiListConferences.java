package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import net.fortuna.ical4j.data.ParserException;

/**
 * @author nikola
 *Gui where we can show conferences of a user
 */
public class GuiListConferences {

	final private Shell shell;
	private Set<Conference> listeconfuser;
	
	/*public GuiListConferences(String loginResearcher) {
	    Display display = new Display();
	    this.shell = new Shell(display);
	    shell.setText("Shell");
	    shell.setSize(200, 200);
	    shell.open();
	}*/
	
	/**
	 * Create a shell where they will have conferences of the user
	 */
	public GuiListConferences() {
	    Display display = new Display();
		FillLayout layout = new FillLayout(SWT.VERTICAL);
	    this.shell = new Shell(display);
	    shell.setText("My conference");
		layout.marginHeight = 200;
		layout.marginWidth = 200;
		shell.setLayout(layout);
	    shell.open();
	}
	
	/**
	 * this method display the GUI in a windows
	 */
	public void display() {
		this.shell.pack();

		this.shell.open();

		while (!this.shell.isDisposed())

		{
			if (!shell.getDisplay().readAndDispatch()) {
				shell.getDisplay().sleep();

			}

		}
	}
	
	public static void main(String[]args) throws NumberFormatException, IOException, ParserException, ParseException{
		GuiListConferences guil=new GuiListConferences();
		guil.getconferences();
		guil.display();
		
	}
	
	/**
	 * We retrieve and display in a list the conferences stored in the ical file of the identified user.
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParserException
	 * @throws ParseException
	 */
	public void getconferences() throws NumberFormatException, IOException, ParserException, ParseException {
		String calendarName="CalendarOcailloux";
		ConferencesRetriever retriever = new ConferencesFromICal();
		ConferencesShower conflist=new ConferencesShower(retriever);
		this.listeconfuser=conflist.searchConferenceInFile(calendarName);
		final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	    for (Conference conf: this.listeconfuser) {
	      list.add(conf.toString());
	    }
	}
 
}


