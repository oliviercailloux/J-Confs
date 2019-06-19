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
		shell.setLayout(gridLayout);

		// add the group for the researcher into shell
		Group grp_researcher = new Group(shell, SWT.NONE);
		grp_researcher.setText("Researcher");
		GridLayout gridLayoutR = new GridLayout(4, false);
		grp_researcher.setLayout(gridLayoutR);

		Group grp_conf = new Group(shell, SWT.NONE);
		grp_conf.setText("Conference");
		GridLayout gridLayoutC = new GridLayout(4, false);
		grp_conf.setLayout(gridLayoutC);

		GridData gridDataTextField = new GridData();
		gridDataTextField.horizontalSpan = 3;
		gridDataTextField.widthHint = 130;
		gridDataTextField.heightHint = 20;

		// create the label and the field text for the group researcher
		Label lblLogin = new Label(grp_researcher, SWT.NONE);
		lblLogin.setText("Login :");
		Text txt_login = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblSurname = new Label(grp_researcher, SWT.NONE);
		lblSurname.setText("Surname");
		Text txt_Surname = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblFirstname = new Label(grp_researcher, SWT.NONE);
		lblFirstname.setText("Firstname");
		Text txt_Firstname = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblPhone = new Label(grp_researcher, SWT.NONE);
		lblPhone.setText("Phone");
		Text txt_Phone = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblGroup = new Label(grp_researcher, SWT.NONE);
		lblGroup.setText("Group");
		Text txt_Group = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblMail = new Label(grp_researcher, SWT.NONE);
		lblMail.setText("Mail");
		Text txt_Mail = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblOffice = new Label(grp_researcher, SWT.NONE);
		lblOffice.setText("Office");
		Text txt_Office = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		defineLayout(gridDataTextField,txt_login, txt_Surname, txt_Firstname, txt_Phone, txt_Group,txt_Mail,txt_Office);
		manageInputField(false, txt_Surname, txt_Firstname, txt_Phone, txt_Group,txt_Mail,txt_Office);

		Button btn_researcher = new Button(grp_researcher, SWT.PUSH);
		btn_researcher.setText("Search");		
		btn_researcher.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event)  {
				String login = txt_login.getText();

				try {
					Researcher researcher = ResearcherBuilder.create(login);
					manageInputField(true, txt_Surname, txt_Firstname, txt_Phone, txt_Group,txt_Mail,txt_Office);
					txt_Surname.setText(researcher.getLastname());
					txt_Firstname.setText(researcher.getFirstname());
					txt_Phone.setText(researcher.getPhone());
					txt_Group.setText(researcher.getGroup());
					txt_Mail.setText(researcher.getMail());
					txt_Office.setText(researcher.getOffice());
					manageInputField(false, txt_Surname, txt_Firstname, txt_Phone, txt_Group,txt_Mail,txt_Office);

				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| ClassCastException e) {
					e.printStackTrace();
				}

			}

		});

		// create the label and the field text for the group conference
		Label labelTitle = new Label(grp_conf, SWT.NONE);
		labelTitle.setText("Title :");
		Text textTitle = new Text(grp_conf, SWT.SINGLE | SWT.BORDER);

		Label labelFee = new Label(grp_conf, SWT.NONE);
		labelFee.setText("Registration \nFee :");
		Text textFee = new Text(grp_conf, SWT.SINGLE | SWT.BORDER);

		Label labelCity = new Label(grp_conf, SWT.NONE);
		labelCity.setText("City :");
		Text textCity = new Text(grp_conf, SWT.SINGLE | SWT.BORDER);

		Label labelCountry = new Label(grp_conf, SWT.NONE);
		labelCountry.setText("Country :");
		Text textCountry = new Text(grp_conf, SWT.SINGLE | SWT.BORDER);

		//allow only positive integers as input and not allow special characters like letter
		restrictionField("number", textFee);
		//not allow the integers
		restrictionField("letter", textCity, textCountry);
		defineLayout(gridDataTextField, textTitle, textFee, textCity, textCountry);

		GridData gridDataDate = new GridData();
		gridDataDate.horizontalSpan = 3;
		gridDataDate.widthHint = 100;
		gridDataDate.heightHint = 20;

		//create Date Selection as a drop-down
		Label labelDateStart = new Label(grp_conf, SWT.NONE);
		labelDateStart.setText("Date Start");
		DateTime dateStart = new DateTime(grp_conf, SWT.DATE | SWT.DROP_DOWN);
		dateStart.setLayoutData(gridDataDate);

		Label labelDateEnd = new Label(grp_conf, SWT.NONE);
		labelDateEnd.setText("Date End");
		DateTime dateEnd = new DateTime(grp_conf, SWT.DATE | SWT.DROP_DOWN);
		dateEnd.setLayoutData(gridDataDate);

		Button buttonSubmit = new Button(grp_conf, SWT.PUSH);
		buttonSubmit.setText("Create calendar");	
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

				String start = "";
				String end = "";
				if (check(start, end, dateStart, dateEnd, conf, city, country, fee, title,
						shell) == true){
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
				String surname = "", firstname = "", email = "", phone = "", title = "",
						city = "", country = "";
				Double fee = 0.00;
				Researcher researcher = null;

				confAndResercher(researcher, surname, firstname, email, phone, title,
						city, country, fee,  txt_Surname,  txt_Firstname, txt_Mail,  txt_Phone,
						textTitle,  textFee,  textCity, textCountry);

				String start = "";
				String end = "";
				if (check(start, end, dateStart, dateEnd, conf, city, country, fee, title,
						shell) == true){
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
		buttonYS.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event)  {
				URL url = null;
				try {
					url = new URL("http://www.conference.com");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				Conference conf = new Conference(url);
				String surname = "", firstname = "", email = "", phone = "", title = "",
						city = "", country = "";
				Double fee = 0.00;
				Researcher researcher = null;
					
				confAndResercher(researcher, surname, firstname, email, phone, title,
						city, country, fee,  txt_Surname,  txt_Firstname, txt_Mail,  txt_Phone,
						textTitle,  textFee,  textCity, textCountry);

				String start = "";
				String end = "";
				if (check(start, end, dateStart, dateEnd, conf, city, country, fee, title,
						shell) == true){
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

	/**
	 * Method that get informations from fields
	 * @param researcher
	 * @param surname
	 * @param firstname
	 * @param email
	 * @param phone
	 * @param title
	 * @param city
	 * @param country
	 * @param fee
	 * @param txt_Surname
	 * @param txt_Firstname
	 * @param txt_Mail
	 * @param txt_Phone
	 * @param textTitle
	 * @param textFee
	 * @param textCity
	 * @param textCountry
	 */
	public static void confAndResercher(Researcher researcher,
			String surname, String firstname, String email, String phone, String title,
			String city, String country, double fee, Text txt_Surname, Text txt_Firstname,
			Text txt_Mail, Text txt_Phone, Text textTitle, Text textFee, Text textCity,
			Text textCountry) {
		surname = txt_Surname.getText();
		firstname = txt_Firstname.getText();
		email = txt_Mail.getText();
		phone  = txt_Phone.getText();
		researcher = new Researcher(surname,firstname);
		researcher.setMail(email);
		researcher.setPhone(phone);
		title = textTitle.getText();
		fee = Double.parseDouble(textFee.getText());
		city = textCity.getText();
		country = textCountry.getText();
	}

	/**
	 * Method that define what char you can write in a field
	 * @param type
	 * @param parameters
	 */
	public static void restrictionField(String type, Text...parameters) {
		for (Text parameter : parameters) {
			parameter.addVerifyListener(new VerifyListener() {
				public void verifyText(VerifyEvent e) {
					String string = e.text;
					char[] chars = new char[string.length()];
					string.getChars(0, chars.length, chars, 0);
					for (int i = 0; i < chars.length; i++) {
						if (type.contains("letter")) {
							if ('0' <= chars[i] && chars[i] <= '9') {
								e.doit = false;
								return;
							}
						}
						else {
							if (!('0' <= chars[i] && chars[i] <= '9')) {
								e.doit = false;
								return;
							}
						}

					}
				}
			});
		}

	}

	/**
	 * Method that return true if dateStart is before dateEnd. It's also set informations for
	 * conference
	 * @param start
	 * @param end
	 * @param dateStart
	 * @param dateEnd
	 * @param conf
	 * @param city
	 * @param country
	 * @param fee
	 * @param title
	 * @param shell
	 * @return true or false
	 */
	public static boolean check(String start, String end, DateTime dateStart, DateTime dateEnd, Conference conf,
			String city, String country, double fee, String title, Shell shell) {
		// add "O" before the day and month if they are 1, 2, 3, 4, 5, 6, 7, 8, 9
		String[] array = {"1","2", "3", "4", "5", "6", "7", "8", "9"};

		String dStart = Integer.toString(dateStart.getDay());
		String mStart = Integer.toString(dateStart.getMonth()+1);
		String yStart = Integer.toString(dateStart.getYear());
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
		;
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
		if (start.compareTo(end) >= 0 ) {
			MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
			mb.setText("Failed");
			mb.setMessage("Date Start can't be lower or equel to Date End");
			mb.open();
			return false;
		}
		return true;
	}

	/**
	 * Method that define the layout grid for all argument of type Text
	 * @param grid
	 * @param grp
	 * @param parameters
	 */
	public static void defineLayout(GridData grid, Text...parameters) {
		for (Text parameter : parameters) {
			parameter.setLayoutData(grid);
		}
	}

	/**
	 * Method that unblock or block the field when needed
	 * @param block
	 * @param parameters
	 */
	public static void manageInputField( boolean block, Text...parameters) {
		for (Text parameter : parameters) {
			parameter.addVerifyListener(new VerifyListener() {
				public void verifyText(VerifyEvent e) {
					e.doit = block;      
				}
			});
		}
	}
}