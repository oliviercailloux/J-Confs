package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.validate.ValidationException;


/**
 * This class create the GUI to edit the conference
 * @author huong, camille
 *
 */
public class GuiConference {
	public static void main(String[] args){
		
		// setup the SWT window
		Display display = new Display();
		Shell shell = new Shell(display, SWT.RESIZE | SWT.CLOSE | SWT.MIN);
		shell.setText("Conference");
		
		// initialize a grid layout manager
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);
		
		// create the label and the field text
		Label labelTitle = new Label(shell, SWT.NONE);
		labelTitle.setText("Title : ");
		Text textTitle = new Text(shell, SWT.BORDER);
		
		
		Label labelFee = new Label(shell, SWT.NONE);
		labelFee.setText("Registration Fee : ");
		Text textFee = new Text(shell, SWT.BORDER);
		
		Label labelCity = new Label(shell, SWT.NONE);
		labelCity.setText("City : ");
		Text textCity = new Text(shell, SWT.BORDER);
		

		Label labelCountry = new Label(shell, SWT.NONE);
		labelCountry.setText("Country : ");
		Text textCountry = new Text(shell, SWT.BORDER);
		
		//create Date Selection as a drop-down
		Label labelDateStart = new Label(shell, SWT.NONE);
		labelDateStart.setText("Date Start : ");
		DateTime dateStart = new DateTime(shell, SWT.DATE | SWT.DROP_DOWN);
		
		Label labelDateEnd = new Label(shell, SWT.NONE);
		labelDateEnd.setText("Date End : ");
		DateTime dateEnd = new DateTime(shell, SWT.DATE | SWT.DROP_DOWN);
	
		Button buttonSubmit = new Button(shell, SWT.PUSH);
		buttonSubmit.setText("Submit");
		buttonSubmit.addSelectionListener(new SelectionAdapter() {
			// the function proceed() executed once we press the button Submit
			public void widgetSelected(SelectionEvent event) {
				try {
					proceed();
				} catch (ValidationException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParserException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				
			}
			//this function save the value in the fields of GUI in a conference and write-read a ICalendar
			private void proceed() throws ParseException, ValidationException, IOException, ParserException, URISyntaxException {
				URL url = new URL("http://www.conference.com");
				Conference conf = new Conference(url);
				String title = textTitle.getText();
				Double fee = Double.parseDouble(textFee.getText());
				String city = textCity.getText();
				String country = textCountry.getText();
				String dStart = Integer.toString(dateStart.getDay());
				String mStart = Integer.toString(dateStart.getMonth());
				String yStart = Integer.toString(dateStart.getYear());
				String start = yStart + "-" + mStart + "-" + dStart;
				String dEnd = Integer.toString(dateEnd.getDay());
				String mEnd = Integer.toString(dateEnd.getMonth());
				String yEnd = Integer.toString(dateEnd.getYear());
				String end = yEnd + "-" + mEnd + "-" + dEnd;
				conf.setCity(city);
				conf.setCountry(country);
				conf.setStartDate(start);
				conf.setEndDate(end);
				conf.setFeeRegistration(fee);
				conf.setTitle(title);
				ConferenceWriter.writeCalendarFiles(conf);
				System.out.println("The iCalendar has created in the file mycalendar.ics ");
				
			}
		});
		
		// tear down the SWT window
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
		
			if (!display.readAndDispatch())
		
				display.sleep();
		
			}
		 
		display.dispose();
		
		}

}
	
