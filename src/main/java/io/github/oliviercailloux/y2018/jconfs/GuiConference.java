package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ConferenceWriter;
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
		
		//allow only positive integers as input and not allow special characters like letter 
		textFee.addVerifyListener(new VerifyListener() {
			 public void verifyText(VerifyEvent e) {
				 String string = e.text;
			      char[] chars = new char[string.length()];
			      string.getChars(0, chars.length, chars, 0);
			      for (int i = 0; i < chars.length; i++) {
			         if (!('0' <= chars[i] && chars[i] <= '9')) {
			            e.doit = false;
			            return;
			         }
			      }
			   }
		 });
		
		//not allow the integers
		Label labelCity = new Label(shell, SWT.NONE);
		labelCity.setText("City : ");
		Text textCity = new Text(shell, SWT.BORDER);
		textCity.addVerifyListener(new VerifyListener() {
			 public void verifyText(VerifyEvent e) {
				 String string = e.text;
			      char[] chars = new char[string.length()];
			      string.getChars(0, chars.length, chars, 0);
			      for (int i = 0; i < chars.length; i++) {
			         if ('0' <= chars[i] && chars[i] <= '9') {
			            e.doit = false;
			            return;
			         }
			      }
			   }
		 });
		
		////not allow the integers
		Label labelCountry = new Label(shell, SWT.NONE);
		labelCountry.setText("Country : ");
		Text textCountry = new Text(shell, SWT.BORDER);
		textCountry.addVerifyListener(new VerifyListener() {
			 public void verifyText(VerifyEvent e) {
				 String string = e.text;
			      char[] chars = new char[string.length()];
			      string.getChars(0, chars.length, chars, 0);
			      for (int i = 0; i < chars.length; i++) {
			         if ('0' <= chars[i] && chars[i] <= '9') {
			            e.doit = false;
			            return;
			         }
			      }
			   }
		 });
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
			//this function save the value in the fields of GUI in a conference and write-read a ICalendar
			public void widgetSelected(SelectionEvent event)  {
				URL url = null;
				try {
					url = new URL("http://www.conference.com");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				Conference conf = new Conference(url);
				String title = textTitle.getText();
				Double fee = Double.parseDouble(textFee.getText());
				String city = textCity.getText();
				String country = textCountry.getText();
				// add "O" before the day and month if they are 1, 2, 3, 4, 5, 6, 7, 8, 9
				String[] array = {"1","2", "3", "4", "5", "6", "7", "8", "9"};
				
				String dStart = Integer.toString(dateStart.getDay());
				String mStart = Integer.toString(dateStart.getMonth()+1);
				String yStart = Integer.toString(dateStart.getYear());
				String start = "";
				String dnew = "";
				String mnew = "";
				boolean dayStart = Arrays.asList(array).contains(dStart);
				boolean monthStart = Arrays.asList(array).contains(mStart);
				if(dayStart && !monthStart ){
					dnew = "0"+dStart;
			        start = dnew + "/" + mStart + "/" + yStart;
			            	           
				}
			    else if (!dayStart && monthStart){
			    	mnew = "0"+mStart;
			    	start = dStart + "/" + mnew + "/" + yStart;
				}
			    else if (dayStart && monthStart){
			    	dnew = "0"+dStart;
			    	mnew = "0"+mStart;
					start = dnew + "/" + mnew + "/" + yStart;
			    }
			        else {
			        	start = dStart + "/" + mStart + "/" + yStart;
			   }
				
			    
				String dEnd = Integer.toString(dateEnd.getDay());
				String mEnd = Integer.toString(dateEnd.getMonth()+1);
				String yEnd = Integer.toString(dateEnd.getYear());
				
				String end = "";
				String dnew1 = "";
				String mnew1 = "";
				boolean dayEnd = Arrays.asList(array).contains(dEnd);
				boolean monthEnd = Arrays.asList(array).contains(mEnd);
				if(dayEnd && !monthEnd ){
					dnew1 = "0"+dEnd;
			         end = dnew1 + "/" + mEnd + "/" + yEnd;
			            	           
					}
			        else if (!dayEnd && monthEnd){
			            	mnew1 = "0"+mEnd;
							end = dEnd + "/" + mnew1 + "/" + yEnd;
							
			         }
			        else if (dayEnd && monthEnd){
			        	dnew1 = "0"+dEnd;
		            	mnew1 = "0"+mEnd;
						end = dnew1 + "/" + mnew1 + "/" + yEnd;
						
			        }
			        else {
			            	end = dEnd + "/" + mEnd + "/" + yEnd;
			            }
				    
				conf.setCity(city);
				conf.setCountry(country);
				try {
					conf.setStartDate(start);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				try {
					conf.setEndDate(end);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				
				conf.setFeeRegistration(fee);
				conf.setTitle(title);
				 

							try {
								ConferenceWriter.writeCalendarFiles(conf);
								
							} catch (ValidationException | ParseException | IOException | ParserException
									| URISyntaxException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
				
				System.out.println("The iCalendar has created in the file mycalendar.ics ");
			
			}
			
		});
		
		Color col = new Color(display, 145, 201, 169);
		shell.setBackground(col);
		col.dispose();
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
	
