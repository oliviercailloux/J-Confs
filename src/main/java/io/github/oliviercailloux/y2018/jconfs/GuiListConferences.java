package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.osaf.caldav4j.exceptions.CalDAV4JException;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fortuna.ical4j.data.ParserException;
import com.google.common.primitives.Doubles;
import com.google.common.base.Strings;

/**
 * @author nikola 
 * This class GUI uses to show a list of conferences of a searcher 
 * and with the possibility to edit it
 */
public class GuiListConferences {

	private static final Logger LOGGER = LoggerFactory.getLogger(GuiListConferences.class);
	/**
	 * List that stocks all of conferences previously collected
	 */
	private List<Conference> listConferencesUser;
	/**
	 * SWT Widget list, uses for print all conferences
	 */
	private org.eclipse.swt.widgets.List listConferences;
	private Shell shell;
	private String calendarName;
	/**
	 * SWT Widget text, fields filed fill in by the researcher
	 */
	private Text txtTitle;
	private Text txtUrl;
	private Text txtRegisFee;
	private Text txtCoutry;
	private Text txtCity;
	private DateTime dateStart;
	private DateTime dateEnd;
	private Button btnSave;

	public GuiListConferences() throws IOException, ParserException, InvalidConferenceFormatException {
		calendarName = "threeConferences";
		Display display = new Display();
		shell = createShell(display);
		shell.open();
	}

	/**
	 * Create a shell with all field of a conference and the list of conferences of
	 * a specific calendar file
	 * 
	 * @param display
	 * @return the shell
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParserException
	 * @throws ParseException
	 * @throws InvalidConferenceFormatException
	 */
	public Shell createShell(Display display) throws IOException, ParserException, InvalidConferenceFormatException {
		this.shell = new Shell(display);
		shell.setText("My conference");
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		createWidgets();
		createListenerWidgets();
		return shell;
	}

	/**
	 * this method display the GUI in a windows
	 */
	public void display() {
		this.shell.pack();
		this.shell.open();
		while (!this.shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch()) {
				shell.getDisplay().sleep();
			}
		}
	}

	/**
	 * We retrieve the conferences stored in the iCalendar file of the searcher and
	 * display it in a SWT widget list
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParserException
	 * @throws InvalidConferenceFormatException 
	 * @throws CalDAV4JException 
	 */
	public void getConferences() throws IOException, ParserException, InvalidConferenceFormatException {
		ConferencesRetriever retriever = new ConferencesFromICal();
		ConferencesShower conflist = new ConferencesShower(retriever);
		listConferencesUser = new ArrayList<>(conflist.searchConferenceInFile(this.calendarName));
		try {
			listConferencesUser= new ArrayList<>(CalendarOnline.getonlineConferences());
		} catch (CalDAV4JException e) {
			throw new IllegalStateException(e);
		}

		for (Conference conf : this.listConferencesUser) {
			listConferences.add(conf.getTitle());
		}
	}

	/**
	 * this method change the date of a conference in the good format in the widget
	 * Date time of the GUI
	 * 
	 * @param fieldDate field of the GUI
	 * @param dateRead  date of the conference
	 */
	public void setDateofConferences(DateTime fieldDate, LocalDate dateRead) {
		fieldDate.setDate(dateRead.getYear(), dateRead.getMonthValue() - 1, dateRead.getDayOfMonth());
	}

	/**
	 * This method check the validity of a date choose by the searcher : Date of
	 * start must be < of Date of End
	 * 
	 * @return
	 */
	public boolean isDateValid() {
		LocalDate localDateStart = LocalDate.of(dateStart.getYear(), dateStart.getMonth() + 1, dateStart.getDay());
		LocalDate localDateEnd = LocalDate.of(dateEnd.getYear(), dateEnd.getMonth() + 1, dateEnd.getDay());
		if (localDateStart.compareTo(localDateEnd) >= 0) {
			LOGGER.debug("Conference not save : start day >= end day");
			MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
			mb.setText("Failed");
			mb.setMessage("Date Start can't be lower or equal to Date End");
			mb.open();
			return false;
		}
		return true;
	}

	/**
	 * Check that all text fields are filled
	 * 
	 * @return a boolean that say if all fields are filled
	 */
	public boolean isFillIn() {
		if ((Strings.isNullOrEmpty(txtCity.getText()) || Strings.isNullOrEmpty(txtUrl.getText())
				|| Strings.isNullOrEmpty(txtCoutry.getText()) || Strings.isNullOrEmpty(txtTitle.getText())
				|| Strings.isNullOrEmpty(txtRegisFee.getText()))) {

			LOGGER.debug("Conference not save : not all fields filled");
			MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
			mb.setText("Failed");
			mb.setMessage("Conference not save : not all fields filled");
			mb.open();
			return false;
		}
		return true;
	}

	/**
	 * Launch all method that check if all fields are filled correctly
	 * 
	 * @return a boolean that say if all is correct or not
	 */
	public boolean isAllFieldsValid() {
		return isDateValid() && isFillIn();
	}
	
	/** Fill all fields of the GUI with the information of a conference
	 * @param e event that we catch
	 */
	public void fillInAllFields(Event e) {
		if (listConferences.getSelectionIndex() >= 0) {
			Conference conferenceSelected;
			conferenceSelected = listConferencesUser.get(listConferences.getSelectionIndex());
			txtTitle.setText(conferenceSelected.getTitle());
			txtCity.setText(conferenceSelected.getCity());
			txtCoutry.setText(conferenceSelected.getCountry());
			txtUrl.setText(conferenceSelected.getUrl().toString());
			txtRegisFee.setText(conferenceSelected.getFeeRegistration().toString());
			setDateofConferences(dateStart, conferenceSelected.getStartDate());
			setDateofConferences(dateEnd, conferenceSelected.getEndDate());
		}
	}
	
	/** Edit of a conference: delete and save the conference edited
	 * @param e event that we catch
	 */
	public void editConference(Event e) {
		if (isAllFieldsValid() && listConferences.getSelectionIndex() >= 0) {
			LOGGER.warn("Save of Conference not yet implemented");
			listConferences.removeAll();
			try {
				getConferences();
			} catch (IOException | ParserException | InvalidConferenceFormatException e1) {
				throw new IllegalStateException(e1);
			}
		}
	}

	/**Create widgets of the GUI, and disposition of widgets
	 * @throws IOException
	 * @throws ParserException
	 * @throws InvalidConferenceFormatException
	 */
	public void createWidgets() throws IOException, ParserException, InvalidConferenceFormatException {
		listConferences = new org.eclipse.swt.widgets.List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		this.getConferences();
		GridData gridDatalist = new GridData();
		gridDatalist.grabExcessHorizontalSpace = true;
		gridDatalist.grabExcessVerticalSpace = true;
		listConferences.setLayoutData(gridDatalist);

		Group groupInfoConf = new Group(shell, SWT.NONE);
		groupInfoConf.setText("Details of your conference");
		GridLayout gridLayoutDetails = new GridLayout(4, false);
		groupInfoConf.setLayout(gridLayoutDetails);

		GridData gridDataTextField = new GridData();
		gridDataTextField.horizontalSpan = 3;
		gridDataTextField.widthHint = 500;
		gridDataTextField.heightHint = 30;

		Label labelTitle = new Label(groupInfoConf, SWT.NONE);
		labelTitle.setText("Title :");
		this.txtTitle = new Text(groupInfoConf, SWT.SINGLE | SWT.BORDER);
		this.txtTitle.setLayoutData(gridDataTextField);

		Label labelUrl = new Label(groupInfoConf, SWT.NONE);
		labelUrl.setText("URL :");
		this.txtUrl = new Text(groupInfoConf, SWT.SINGLE | SWT.BORDER);
		this.txtUrl.setLayoutData(gridDataTextField);

		Label labelFee = new Label(groupInfoConf, SWT.NONE);
		labelFee.setText("Fee :");
		this.txtRegisFee = new Text(groupInfoConf, SWT.SINGLE | SWT.BORDER);
		this.txtRegisFee.setLayoutData(gridDataTextField);

		Label labelCountry = new Label(groupInfoConf, SWT.NONE);
		labelCountry.setText("Country :");
		this.txtCoutry = new Text(groupInfoConf, SWT.SINGLE | SWT.BORDER);
		this.txtCoutry.setLayoutData(gridDataTextField);

		Label labelCity = new Label(groupInfoConf, SWT.NONE);
		labelCity.setText("City :");
		this.txtCity = new Text(groupInfoConf, SWT.SINGLE | SWT.BORDER);
		this.txtCity.setLayoutData(gridDataTextField);

		Label labelDateStart = new Label(groupInfoConf, SWT.NONE);
		labelDateStart.setText("Date start :");
		this.dateStart = new DateTime(groupInfoConf, SWT.DEFAULT);
		this.dateStart.setLayoutData(gridDataTextField);

		Label labelDateEnd = new Label(groupInfoConf, SWT.NONE);
		labelDateEnd.setText("Date end :");
		this.dateEnd = new DateTime(groupInfoConf, SWT.DEFAULT);
		this.dateEnd.setLayoutData(gridDataTextField);

		btnSave = new Button(groupInfoConf, SWT.PUSH);
		btnSave.setText("Save Conference");
		GridData gridDataBtn = new GridData(SWT.RIGHT, SWT.BOTTOM, false, false);
		btnSave.setLayoutData(gridDataBtn);
	}
	
	/**
	 * Create all listener for all widgets of the GUI
	 */
	private void createListenerWidgets() {
		txtCity.addVerifyListener(ListenerAction::checkTextInput);
		txtCoutry.addVerifyListener(ListenerAction::checkTextInput);
		txtRegisFee.addVerifyListener(ListenerAction::checkDoubleInput);
		listConferences.addListener(SWT.Selection,this::fillInAllFields);
		btnSave.addListener(SWT.Selection, this::editConference);		
	}
	
	public static void main(String[] args) throws IOException, ParserException, InvalidConferenceFormatException {
		new GuiListConferences().display();
	}
}
