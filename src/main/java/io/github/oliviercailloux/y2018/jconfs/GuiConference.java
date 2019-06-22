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
			@Override
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
		restrictionField(false,textFee);
		//not allow the integers
		restrictionField(true,textCity, textCountry);
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
			@Override
			public void widgetSelected(SelectionEvent event)  {
				LOGGER.debug("Button clicked : Ical created");
				URL url = null;
				try {
					url = new URL("http://www.conference.com");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				
				Conference conf = new Conference(url);
				conf.setCity(textCity.getText());
				conf.setCountry(textCountry.getText());
				conf.setFeeRegistration(Double.parseDouble(textFee.getText()));
				conf.setTitle(textTitle.getText());

				String start = "";
				String end = "";
				if (dateCheck(start, end, shell) == true){
					try {
						ConferenceWriter.addConference(textTitle.getText(),conf);
						MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						mb.setText("Success");
						mb.setMessage("The iCalendar has created in the file " + textTitle.getText() + ".ics");
						mb.open();
					} catch (ValidationException | IOException | ParserException
							| URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
		});


		Button buttonGenerate = new Button(grp_conf, SWT.PUSH);
		buttonGenerate.setText("Generate OM");
		buttonGenerate.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event)  {
				//this function save the value in the fields of GUI in a conference and fill the mission order
				LOGGER.debug("Button clicked : OM generated");
				URL url = null;
				try {
					url = new URL("http://www.conference.com");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}

				Researcher researcher = new Researcher(txt_Surname.getText(),txt_Firstname.getText());
				researcher.setMail(txt_Mail.getText());
				researcher.setPhone(txt_Phone.getText());

				Conference conf = new Conference(url);
				conf.setCity(textCity.getText());
				conf.setCountry(textCountry.getText());
				conf.setFeeRegistration(Double.parseDouble(textFee.getText()));
				conf.setTitle(textTitle.getText());

				String start = "";
				String end = "";
				if (dateCheck(start, end, shell) == true){
					try {
						GenerateOM.generateOM(conf,researcher);
						MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						mb.setText("Success");
						String filename = new String("OM_" + textCity.getText() + "-" + textCountry.getText() + "_" + start + ".ods");
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
			@Override
			public void widgetSelected(SelectionEvent event)  {
				URL url = null;
				try {
					url = new URL("http://www.conference.com");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}

				Researcher researcher = new Researcher(txt_Surname.getText(),txt_Firstname.getText());
				researcher.setMail(txt_Mail.getText());
				researcher.setPhone(txt_Phone.getText());

				Conference conf = new Conference(url);
				conf.setCity(textCity.getText());
				conf.setCountry(textCountry.getText());
				conf.setFeeRegistration(Double.parseDouble(textFee.getText()));
				conf.setTitle(textTitle.getText());
				
				String start = dateFormat(dateStart);
				String end = dateFormat(dateEnd);
				conf.setStartDate(start);
				conf.setEndDate(end);
				
				if (dateCheck(start, end, shell) == true){
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
	 * Method that define what char you can write in a field
	 * @param text
	 * @param parameters
	 */
	public static void restrictionField(boolean text,Text...parameters) {
		for (Text parameter : parameters) {
			if (text == true) {
				parameter.addVerifyListener(ListenerAction::checkTextInput);
			}
			else {
				parameter.addVerifyListener(ListenerAction::checkNumberInput);
			}
		}
	}

	/**
	 * Method that return true if dateStart is before dateEnd.
	 * @param start
	 * @param end
	 * @param shell
	 * @return boolean
	 */
	public static boolean dateCheck(String start, String end, Shell shell) {
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
	 * Return a string that represents the date with the format dd/mm/yyyy
	 * @param date
	 * @return dateString
	 */
	public static String dateFormat(DateTime date) {
		String day = Integer.toString(date.getDay());
		String month = Integer.toString(date.getMonth()+1);
		String year = Integer.toString(date.getYear());
		String dateString = "";
		String dayNew = "";
		String monthNew = "";
		boolean dayFormat = date.getDay() <= 9;
		boolean monthFormat = date.getMonth()+1 <= 9;
		if(dayFormat && !monthFormat ){
			dayNew = "0"+day;
			dateString = dayNew + "/" + month + "/" + year;
		}
		else if (!dayFormat && monthFormat){
			monthNew = "0"+month;
			dateString = day + "/" + monthNew + "/" + year;
		}
		else if (dayFormat && monthFormat){
			dayNew = "0"+day;
			monthNew = "0"+month;
			dateString = dayNew + "/" + monthNew + "/" + year;
		}
		else {
			dateString = day + "/" + month + "/" + year;
		}
		return dateString;
	}

	/**
	 * Method that define the layout grid for all argument of type Text
	 * @param grid
	 * @param parameters
	 */
	public static void defineLayout(GridData grid, Text...parameters) {
		for (Text parameter : parameters) {
			parameter.setLayoutData(grid);
		}
	}

	/**
	 * Method that block or unblock the field when needed
	 * @param block
	 * @param parameters
	 */
	public static void manageInputField(boolean block, Text...parameters) {
		for (Text parameter : parameters) {
			if (block == true)
				parameter.addVerifyListener(ListenerAction::inputFieldUnblock);
			else
				parameter.addVerifyListener(ListenerAction::inputFieldBlock);
		}
	}
}