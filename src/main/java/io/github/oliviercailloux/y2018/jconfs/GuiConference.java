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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(GuiConference.class);
	private Shell shell;
	private Conference conf;
	private Text textTitle;
	private Text textCity;
	private Text textCountry;
	private Text textFee;
	private Text textLogin;
	private Text textSurname;
	private Text textFirstname;
	private Text textPhone;
	private Text textGroup;
	private Text textMail;
	private Text textOffice;
	private DateTime dateStart;
	private DateTime dateEnd;

	public void Gui(Display display){

		// setup the SWT window
		shell = new Shell(display, SWT.RESIZE | SWT.CLOSE | SWT.MIN);
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
		textLogin = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblSurname = new Label(grp_researcher, SWT.NONE);
		lblSurname.setText("Surname");
		textSurname = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblFirstname = new Label(grp_researcher, SWT.NONE);
		lblFirstname.setText("Firstname");
		textFirstname = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblPhone = new Label(grp_researcher, SWT.NONE);
		lblPhone.setText("Phone");
		textPhone = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblGroup = new Label(grp_researcher, SWT.NONE);
		lblGroup.setText("Group");
		textGroup = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblMail = new Label(grp_researcher, SWT.NONE);
		lblMail.setText("Mail");
		textMail = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		Label lblOffice = new Label(grp_researcher, SWT.NONE);
		lblOffice.setText("Office");
		textOffice = new Text(grp_researcher, SWT.SINGLE | SWT.BORDER);

		defineLayout(gridDataTextField,textLogin, textSurname, textFirstname, textPhone, textGroup,textMail,textOffice);
		manageInputField(false, textSurname, textFirstname, textPhone, textGroup,textMail,textOffice);

		Button btn_researcher = new Button(grp_researcher, SWT.PUSH);
		btn_researcher.setText("Search");
		btn_researcher.addListener(SWT.Selection, this::searchResearcher);

		// create the label and the field text for the group conference
		Label labelTitle = new Label(grp_conf, SWT.NONE);
		labelTitle.setText("Title :");
		textTitle = new Text(grp_conf, SWT.SINGLE | SWT.BORDER);

		Label labelFee = new Label(grp_conf, SWT.NONE);
		labelFee.setText("Registration \nFee :");
		textFee = new Text(grp_conf, SWT.SINGLE | SWT.BORDER);

		Label labelCity = new Label(grp_conf, SWT.NONE);
		labelCity.setText("City :");
		textCity = new Text(grp_conf, SWT.SINGLE | SWT.BORDER);

		Label labelCountry = new Label(grp_conf, SWT.NONE);
		labelCountry.setText("Country :");
		textCountry = new Text(grp_conf, SWT.SINGLE | SWT.BORDER);

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
		dateStart = new DateTime(grp_conf, SWT.DATE | SWT.DROP_DOWN);
		dateStart.setLayoutData(gridDataDate);

		Label labelDateEnd = new Label(grp_conf, SWT.NONE);
		labelDateEnd.setText("Date End");
		dateEnd = new DateTime(grp_conf, SWT.DATE | SWT.DROP_DOWN);
		dateEnd.setLayoutData(gridDataDate);

		Button buttonSubmit = new Button(grp_conf, SWT.PUSH);
		buttonSubmit.setText("Create calendar");	
		buttonSubmit.addListener(SWT.Selection, this::generateCalendar);

		Button buttonGenerate = new Button(grp_conf, SWT.PUSH);
		buttonGenerate.setText("Generate OM");
		buttonSubmit.addListener(SWT.Selection, this::generateOm);

		Button buttonYS = new Button(grp_conf, SWT.PUSH);
		buttonYS.setText("Generate YS");
		buttonSubmit.addListener(SWT.Selection, this::generateYs);

		Color col = new Color(display, 211, 214, 219);
		Color col2 = new Color(display, 250, 250, 250);
		textSurname.setBackground(col);
		textFirstname.setBackground(col);
		textPhone.setBackground(col);
		textGroup.setBackground(col);
		textMail.setBackground(col);
		textOffice.setBackground(col);
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
	 * Method that return true if dateStart is before dateEnd.
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public boolean dateCheck(String start, String end) {
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
	public String dateFormat(DateTime date) {
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
	public void defineLayout(GridData grid, Text...parameters) {
		for (Text parameter : parameters) {
			parameter.setLayoutData(grid);
		}
	}

	/**
	 * Method that block or unblock the field when needed
	 * @param block
	 * @param parameters
	 */
	public void manageInputField(boolean block, Text...parameters) {
		for (Text parameter : parameters) {
			if (block == true)
				parameter.addVerifyListener(ListenerAction::inputFieldUnblock);
			else
				parameter.addVerifyListener(ListenerAction::inputFieldBlock);
		}
	}

	/**
	 * Method that define what char you can write in a field
	 * @param text
	 * @param parameters
	 */
	public void restrictionField(boolean text,Text...parameters) {
		for (Text parameter : parameters) {
			if (text == true) {
				parameter.addVerifyListener(ListenerAction::checkTextInput);
			}
			else {
				parameter.addVerifyListener(ListenerAction::checkNumberInput);
			}
		}
	}

	public void searchResearcher(@SuppressWarnings("unused") Event e) {
		try {
			Researcher researcher = ResearcherBuilder.create(textLogin.getText());
			manageInputField(true, textSurname, textFirstname, textPhone, textGroup,textMail,textOffice);
			textSurname.setText(researcher.getLastname());
			textFirstname.setText(researcher.getFirstname());
			textPhone.setText(researcher.getPhone());
			textGroup.setText(researcher.getGroup());
			textMail.setText(researcher.getMail());
			textOffice.setText(researcher.getOffice());
			manageInputField(false, textSurname, textFirstname, textPhone, textGroup,textMail,textOffice);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| ClassCastException e1) {
			throw new RuntimeException(e1);
		}
	}

	/**
	 * Method that generate and store a calendar 
	 * @param e Event that we can catch
	 */
	public void generateCalendar(@SuppressWarnings("unused") Event e) {
		LOGGER.debug("Button clicked : Ical created");
		URL url = null;
		try {
			url = new URL("http://www.conference.com");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		conf = new Conference(url);
		conf.setCity(textCity.getText());
		conf.setCountry(textCountry.getText());
		conf.setFeeRegistration(Double.parseDouble(textFee.getText()));
		conf.setTitle(textTitle.getText());
		String start = dateFormat(dateStart);
		String end = dateFormat(dateEnd);
		conf.setStartDate(start);
		conf.setEndDate(end);

		if (dateCheck(start, end) == true){
			try {
				ConferenceWriter.addConference(textTitle.getText(),conf);
				MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
				mb.setText("Success");
				mb.setMessage("The iCalendar has created in the file " + textTitle.getText() + ".ics");
				mb.open();
			} catch (ValidationException | IOException | ParserException
					| URISyntaxException e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	/**
	 * Method that generate an ods file which is an order mission 
	 * @param e Event that we can catch
	 */
	public void generateOm(@SuppressWarnings("unused") Event e) {
		LOGGER.debug("Button clicked : OM generated");
		URL url = null;
		try {
			url = new URL("http://www.conference.com");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		Researcher researcher = new Researcher(textSurname.getText(),textFirstname.getText());
		researcher.setMail(textMail.getText());
		researcher.setPhone(textPhone.getText());

		conf = new Conference(url);
		conf.setCity(textCity.getText());
		conf.setCountry(textCountry.getText());
		conf.setFeeRegistration(Double.parseDouble(textFee.getText()));
		conf.setTitle(textTitle.getText());
		String start = dateFormat(dateStart);
		String end = dateFormat(dateEnd);
		conf.setStartDate(start);
		conf.setEndDate(end);

		if (dateCheck(start, end) == true){
			try {
				GenerateOM.generateOM(conf,researcher);
				MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
				mb.setText("Success");
				String filename = new String("OM_" + textCity.getText() + "-" + textCountry.getText() + "_" + start + ".ods");
				mb.setMessage("File saved in : " + filename);
				mb.open();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Method that generate a fodt file 
	 * @param e event that we catch
	 */
	public void generateYs(@SuppressWarnings("unused") Event e) {
		URL url = null;
		try {
			url = new URL("http://www.conference.com");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		Researcher researcher = new Researcher(textSurname.getText(),textFirstname.getText());
		researcher.setMail(textMail.getText());
		researcher.setPhone(textPhone.getText());

		conf = new Conference(url);
		conf.setCity(textCity.getText());
		conf.setCountry(textCountry.getText());
		conf.setFeeRegistration(Double.parseDouble(textFee.getText()));
		conf.setTitle(textTitle.getText());
		String start = dateFormat(dateStart);
		String end = dateFormat(dateEnd);
		conf.setStartDate(start);
		conf.setEndDate(end);

		if (dateCheck(start, end) == true){
			try {
				String fileName = conf.getCity() + "-" + conf.getCountry()+ ".fodt";
				GenerateOMYS.fillYSOrderMission(researcher, conf, fileName);
			}catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new GuiConference().Gui(new Display());
	}
}