package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.LoggerFactory;



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
	
	public static void main(String[]args){
		Gui(new Display());
	}
	public static void Gui(Display display){
		final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GuiConference.class);
		
		// setup the SWT window
		Shell shell = new Shell(display, SWT.RESIZE | SWT.CLOSE | SWT.MIN);
		shell.setText("J-Confs");
	
		// initialize a grid layout manager
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		shell.setLayout(gridLayout);
		shell.setLocation(300, 100);
		shell.layout(true, true);
		shell.setSize(new Point(912, 796));
		
		// add the group for the researcher into shell
		Group grp_researcher = new Group(shell, SWT.NONE);
		GridData gd_researcher = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_researcher.heightHint =300;
		gd_researcher.widthHint = 900;
		grp_researcher.setLayoutData(gd_researcher);
		grp_researcher.setText("Researcher");
		
		// add the group for the conference into shell
		Group grp_conf = new Group(shell, SWT.NONE);
		GridData gp_conf = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gp_conf.heightHint =300;
		gp_conf.widthHint = 900;
		grp_conf.setLayoutData(gp_conf);
		grp_conf.setText("Conference");
		
		// create the label and the field text for the group researcher
		Label lblLogin = new Label(grp_researcher, SWT.NONE);
		lblLogin.setAlignment(SWT.RIGHT);
		lblLogin.setBounds(25,41,55,40);
		lblLogin.setText("Login");
		Text txt_login = new Text(grp_researcher, SWT.BORDER);
		txt_login.setBounds(100,40,200,20);
		
		Label lblSurname = new Label(grp_researcher, SWT.NONE);
		lblSurname.setAlignment(SWT.RIGHT);
		lblSurname.setBounds(25, 100, 55, 40);
		lblSurname.setText("Surname");
		Text txt_Surname= new Text(grp_researcher, SWT.BORDER);
		txt_Surname.setBounds(100, 99, 200, 21);
		//block the input in the field
		txt_Surname.addVerifyListener(new VerifyListener() {
			 public void verifyText(VerifyEvent e) {
			      e.doit = false;      
			   }
		 });
		
		Label lblFirstname = new Label(grp_researcher, SWT.NONE);
		lblFirstname.setAlignment(SWT.RIGHT);
		lblFirstname.setBounds(400, 100, 58, 40);
		lblFirstname.setText("Firstname");
		Text txt_Firstname= new Text(grp_researcher, SWT.BORDER);
		txt_Firstname.setBounds(475, 99, 200, 21);
		//block the input in the field
		txt_Firstname.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				e.doit = false;      
			}
		});
		
		Label lblPhone = new Label(grp_researcher, SWT.NONE);
		lblPhone.setAlignment(SWT.RIGHT);
		lblPhone.setBounds(25, 180, 55, 40);
		lblPhone.setText("Phone");
		Text txt_Phone= new Text(grp_researcher, SWT.BORDER);
		txt_Phone.setBounds(100, 179, 200, 21);
		//block the input in the field
		txt_Phone.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				e.doit = false;      
			}
		});
		
		Label lblGroup= new Label(grp_researcher, SWT.NONE);
		lblGroup.setAlignment(SWT.RIGHT);
		lblGroup.setBounds(320, 179, 55, 40);
		lblGroup.setText("Group");
		Text txt_Group= new Text(grp_researcher, SWT.BORDER);
		txt_Group.setBounds(395, 179, 200, 21);
		//block the input in the field
		txt_Group.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				e.doit = false;      
			}
		});
		
		Label lblMail = new Label(grp_researcher, SWT.NONE);
		lblMail.setAlignment(SWT.RIGHT);
		lblMail.setBounds(25, 260, 55, 40);
		lblMail.setText("Mail");
		Text txt_Mail= new Text(grp_researcher, SWT.BORDER);
		txt_Mail.setBounds(100, 260, 400, 21);
		//block the input in the field
		txt_Mail.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				e.doit = false;      
			}
		});
		
		Label lblOffice= new Label(grp_researcher, SWT.NONE);
		lblOffice.setAlignment(SWT.RIGHT);
		lblOffice.setBounds(620, 179, 55, 15);
		lblOffice.setText("Office");
		Text txt_Office= new Text(grp_researcher, SWT.BORDER);
		txt_Office.setBounds(695, 179, 200, 21);
		//block the input in the field
		txt_Office.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				e.doit = false;      
			}
		});
		
		Button btn_researcher= new Button(grp_researcher, SWT.NONE);
		btn_researcher.setBounds(600, 36, 200, 25);
		btn_researcher.setText("search Researcher");
		
		btn_researcher.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event)  {
				String login = txt_login.getText();
				
				try {
					Researcher researcher = ResearcherBuilder.create(login);
					//unblock for the input automatic from the button Search
					txt_Surname.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = true;
						 }
					 });
					txt_Surname.setText(researcher.getLastname());
					//block again after the field filled
					txt_Surname.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = false;
						   }
					 });
					
					
					//unblock for the input automatic from the button Search
					txt_Firstname.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = true;
						 }
					 });
					txt_Firstname.setText(researcher.getFirstname());
					//block again after the field filled
					txt_Firstname.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = false;
						   }
					 });
					
					//unblock for the input automatic from the button Search
					txt_Phone.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = true;
						 }
					 });
					txt_Phone.setText(researcher.getPhone());
					//block again after the field filled
					txt_Phone.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = false;
						   }
					 });
					
					//unblock for the input automatic from the button Search
					txt_Group.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = true;
						 }
					 });
					txt_Group.setText(researcher.getGroup());
					//block again after the field filled
					txt_Group.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = false;
						   }
					 });
					
					//unblock for the input automatic from the button Search
					txt_Mail.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = true;
						 }
					 });
					txt_Mail.setText(researcher.getMail());
					//block again after the field filled
					txt_Mail.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = false;
						   }
					 });
					
					//unblock for the input automatic from the button Search
					txt_Office.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = true;
						 }
					 });
					txt_Office.setText(researcher.getOffice());
					//block again after the field filled
					txt_Office.addVerifyListener(new VerifyListener() {
						 public void verifyText(VerifyEvent e) {
						      e.doit = false;
						   }
					 });
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| ClassCastException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
		
		// create the label and the field text for the group conference
		Label labelTitle = new Label(grp_conf, SWT.NONE);
		labelTitle.setText("Title  ");
		labelTitle.setBounds(25,41,55,40);
		Text textTitle = new Text(grp_conf, SWT.BORDER);
		textTitle.setBounds(100,40,200,20);
		
		Label labelFee = new Label(grp_conf, SWT.NONE);
		labelFee.setText("Registration Fee");
		labelFee.setBounds(400,41,90,40);
		Text textFee = new Text(grp_conf, SWT.BORDER);
		textFee.setBounds(500,40,200,20);
		
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
		Label labelCity = new Label(grp_conf, SWT.NONE);
		labelCity.setText("City ");
		labelCity.setBounds(25, 100, 55, 40);
		Text textCity = new Text(grp_conf, SWT.BORDER);
		textCity.setBounds(100, 99, 200, 21);
		
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
		
		//not allow the integers
		Label labelCountry = new Label(grp_conf, SWT.NONE);
		labelCountry.setText("Country  ");
		labelCountry.setBounds(400, 100, 58, 40);
		Text textCountry = new Text(grp_conf, SWT.BORDER);
		textCountry.setBounds(475, 99, 200, 21);
		
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
		Label labelDateStart = new Label(grp_conf, SWT.NONE);
		labelDateStart.setText("Date Start ");
		labelDateStart.setBounds(25, 180, 55, 40);
		DateTime dateStart = new DateTime(grp_conf, SWT.DATE | SWT.DROP_DOWN);
		dateStart.setBounds(100, 179, 200, 21);
		
		Label labelDateEnd = new Label(grp_conf, SWT.NONE);
		labelDateEnd.setText("Date End ");
		labelDateEnd.setBounds(400, 180, 55, 40);
		DateTime dateEnd = new DateTime(grp_conf, SWT.DATE | SWT.DROP_DOWN);
		dateEnd.setBounds(475, 179, 200, 21);
		
		Button buttonSubmit = new Button(grp_conf, SWT.PUSH);
		buttonSubmit.setText("Create calendar");
		buttonSubmit.setBounds(80, 260, 200, 25);
		buttonSubmit.addSelectionListener(new SelectionAdapter() {
			//this function save the value in the fields of GUI in a conference and write-read a ICalendar
			public void widgetSelected(SelectionEvent event)  {
				LOGGER.debug("Button clicked : Ical created");
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
				} catch (DateTimeParseException e1) {
					e1.printStackTrace();
				}
				try {
					conf.setEndDate(end);
				} catch (DateTimeParseException e1) {
					e1.printStackTrace();
				}
				
				
				conf.setFeeRegistration(fee);
				conf.setTitle(title);
				
				if (start.compareTo(end) >= 0 ) {
					MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
					mb.setText("Failed");
					mb.setMessage("Date Start can't be lower or equel to Date End");
					mb.open();
				} else {

							try {
								ConferenceWriter.writeCalendarFiles(title,conf);
								MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								mb.setText("Success");
								mb.setMessage("The iCalendar has created in the file " + title + ".ics");
								mb.open();
							} catch (ValidationException | ParseException | IOException | ParserException
									| URISyntaxException e) {
								e.printStackTrace();
							}
		
				}
			}
				
			
		});
		
		
		
		Button buttonGenerate = new Button(grp_conf, SWT.PUSH);
		buttonGenerate.setText("Generate OM");
		buttonGenerate.setBounds(500, 260, 200, 25);
		buttonGenerate.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent event)  {
				//this function save the value in the fields of GUI in a conference and fill the mission order
				LOGGER.debug("Button clicked : OM generated");
				URL url = null;
				try {
					url = new URL("http://www.conference.com");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				Conference conf = new Conference(url);
				String surname = txt_Surname.getText();
				String firstname = txt_Firstname.getText();
				String email = txt_Mail.getText();
				Researcher researcher = new Researcher(surname,firstname);
				researcher.setMail(email);
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
				} catch (DateTimeParseException e1) {
					e1.printStackTrace();
				}
				try {
					conf.setEndDate(end);
				} catch (DateTimeParseException e1) {
					e1.printStackTrace();
				}
				
				
				conf.setFeeRegistration(fee);
				conf.setTitle(title);
				if (start.compareTo(end) >= 0 ) {
					MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
					mb.setText("Failed");
					mb.setMessage("Date Start can't be lower or equel to Date End");
					mb.open();
				} 
				else {
					try {
						GenerateOM.generateOM(conf,researcher);
						MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						mb.setText("Success");
						String filename = new String("OM_" + city + "-" + country + "_" + start + ".ods");
						mb.setMessage("File saved in : " + filename);
						mb.open();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		
		});

// To dev at the second iteration
//		Button btn_openCalendar= new Button(grp_researcher, SWT.NONE);
//		btn_openCalendar.setBounds(600, 260, 200, 25);
//		btn_openCalendar.setText("Open Calendar");
//		btn_openCalendar.addListener(SWT.Selection, new Listener() {
//			
//			@Override
//			public void handleEvent(Event event) {
//				Preconditions.checkArgument((txt_login.getText()!=""));
//				//checkcalendarexist(txt_login.getText()+".ics"); //to do
//				LOGGER.debug("click open calendar");
//				//GuiListConferences gulist=new GuiListConferences(txt_login.getText());
//			}
//		});
		
		Button buttonYS = new Button(grp_conf, SWT.PUSH);
		buttonYS.setText("Generate YS");
		buttonYS.setBounds(700, 260, 200, 25);
		buttonYS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event)  {
				URL url = null;
				try {
					url = new URL("http://www.conference.com");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				Conference conf = new Conference(url);
				String surname = txt_Surname.getText();
				String firstname = txt_Firstname.getText();
				String email = txt_Mail.getText();
				String phone  = txt_Phone.getText();
				Researcher researcher = new Researcher(surname,firstname);
				researcher.setMail(email);
				researcher.setPhone(phone);
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
				} catch (DateTimeParseException e1) {
					e1.printStackTrace();
				}
				try {
					conf.setEndDate(end);
				} catch (DateTimeParseException e1) {
					e1.printStackTrace();
				}
				
				
				conf.setFeeRegistration(fee);
				conf.setTitle(title);
				if (start.compareTo(end) >= 0 ) {
					MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
					mb.setText("Failed");
					mb.setMessage("Date Start can't be lower or equel to Date End");
					mb.open();
				} 
				else {
					try {
						String fileName = conf.getCity() + "-" + conf.getCountry()+ ".fodt";
					
						GenerateOMYS.fillYSOrderMission(researcher, conf, fileName);
				
					}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			
			});
		
		Color col = new Color(display, 211, 214, 219);
		Color col2 = new Color(display, 250, 250, 250);
		txt_Surname.setBackground(col);
		txt_Firstname.setBackground(col);
		txt_Phone.setBackground(col);
		txt_Group.setBackground(col);
		txt_Mail.setBackground(col);
		txt_Office.setBackground(col);
		shell.setBackground(col2);
		col2.dispose();
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