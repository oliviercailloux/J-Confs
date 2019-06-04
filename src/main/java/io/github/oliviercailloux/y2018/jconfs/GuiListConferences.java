package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fortuna.ical4j.data.ParserException;
import com.google.common.primitives.Doubles;
import com.google.common.base.Strings;

/**
 * @author nikola Gui 
 * uses to show a list of conferences of a searcher and with the possibility to edit it
 */
public class GuiListConferences {

	private static final Logger LOGGER = LoggerFactory.getLogger(GuiListConferences.class);
	/**
	 * List that stocks all of conferences from ConferenceReader
	 */
	private java.util.List<Conference> listConferencesUser;
	/**
	 * SWT Widget list, uses for print all conferences
	 */
	private List listConferences;
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

	/**
	 * Create a GUI where they will have conferences of the user
	 * 
	 * @throws ParseException
	 * @throws ParserException
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws InvalidConferenceFormatException
	 */
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

		listConferences = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
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

		Button btnSave = new Button(groupInfoConf, SWT.PUSH);
		btnSave.setText("Save Conference");
		GridData gridDataBtn = new GridData(SWT.RIGHT, SWT.BOTTOM, false, false);
		btnSave.setLayoutData(gridDataBtn);

		VerifyListener verifyListenerLetter = new VerifyListener() {
			/**
			 * check if the character is a letter or "-" or a whitespace, if not you can't
			 * put the character
			 */
			@Override
			public void verifyText(VerifyEvent e) {
				if (!e.text.matches("[a-zA-ZÀ-ú -]*")) {
					e.doit = false;
				}
			}
		};

		txtCity.addVerifyListener(verifyListenerLetter);
		txtCoutry.addVerifyListener(verifyListenerLetter);

		txtRegisFee.addVerifyListener(new VerifyListener() {

			/**
			 * check that the fee is a double, if not you can't put the character
			 */
			@Override
			public void verifyText(VerifyEvent e) {
				if (Doubles.tryParse(txtRegisFee.getText() + e.text) == null) {
					e.doit = false;
				}
			}
		});

		listConferences.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

		});

		btnSave.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// edit of a conference
				if (isAllFieldsValid() && listConferences.getSelectionIndex() >= 0) {
					// ConferenceWriter.deleteConference(calendarName,listConferencesUser.get(listConferences.getSelectionIndex()));
					// ConferenceWriter.addConference(calendarName,new Conference(.........));
					listConferences.removeAll();
					try {
						getConferences();
					} catch (IOException | ParserException | InvalidConferenceFormatException e1) {
						throw new IllegalStateException(e1);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

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
	 * @throws ParseException
	 * @throws InvalidConferenceFormatException
	 */
	public void getConferences() throws IOException, ParserException, InvalidConferenceFormatException {
		ConferencesRetriever retriever = new ConferencesFromICal();
		ConferencesShower conflist = new ConferencesShower(retriever);
		listConferencesUser = new ArrayList<>(conflist.searchConferenceInFile(this.calendarName));
		for (Conference conf : this.listConferencesUser) {
			listConferences.add(conf.getTitle());
		}
	}

	/**
	 * this method change the date of a conference in the good format in the widget
	 * Datetime of the GUI
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

	public static void main(String[] args) throws IOException, ParserException, InvalidConferenceFormatException {
		new GuiListConferences().display();
	}
}
