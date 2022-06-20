package io.github.oliviercailloux.jconfs.gui;

import com.github.caldav4j.exceptions.CalDAV4JException;
import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;
import io.github.oliviercailloux.jaris.collections.ImmutableCompleteMap;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader;
import io.github.oliviercailloux.jconfs.calendar.CalDavCalendarGeneric;
import io.github.oliviercailloux.jconfs.calendar.CalendarOnline;
import io.github.oliviercailloux.jconfs.calendar.JARiS.FruuxKeysCredential;
import io.github.oliviercailloux.jconfs.conference.Conference;
import io.github.oliviercailloux.jconfs.conference.Conference.ConferenceBuilder;
import io.github.oliviercailloux.jconfs.conference.InvalidConferenceFormatException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import net.fortuna.ical4j.data.ParserException;
import org.eclipse.swt.SWT;
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

/**
 * @author nikola This class GUI uses to show a list of conferences of a searcher and with the
 *         possibility to edit it. It takes conferences from fruux
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
  private Button btnClear;
  private Button btnDelete;
  private Display display;

  protected ImmutableCompleteMap<FruuxKeysCredential, String> myAuth;

  public void gui(Display displayGui, ImmutableCompleteMap<FruuxKeysCredential, String> Auth) throws Exception {
    this.display = displayGui;
    this.myAuth = Auth;

    // setup the SWT window
    shell = new Shell(display, SWT.RESIZE | SWT.CLOSE | SWT.MIN);
    shell.setText("J-Confs list");

    // initialize a grid layout manager
    GridLayout gridLayout = new GridLayout();
    shell.setLayout(gridLayout);
    Group groupListConf = new Group(shell, SWT.NONE);
    // add title where we list conferences
    groupListConf.setText("Your conferences");
    groupListConf.setLayout(gridLayout);
    listConferences =
        new org.eclipse.swt.widgets.List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
    this.getConferences();
    GridData gridDatalist = new GridData();
    gridDatalist.grabExcessHorizontalSpace = true;
    gridDatalist.grabExcessVerticalSpace = true;
    gridDatalist.heightHint = 200;
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
    labelTitle.setText("Title * :");
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
    labelCountry.setText("Country * :");
    this.txtCoutry = new Text(groupInfoConf, SWT.SINGLE | SWT.BORDER);
    this.txtCoutry.setLayoutData(gridDataTextField);

    Label labelCity = new Label(groupInfoConf, SWT.NONE);
    labelCity.setText("City * :");
    this.txtCity = new Text(groupInfoConf, SWT.SINGLE | SWT.BORDER);
    this.txtCity.setLayoutData(gridDataTextField);

    Label labelDateStart = new Label(groupInfoConf, SWT.NONE);
    labelDateStart.setText("Date start * :");
    this.dateStart = new DateTime(groupInfoConf, SWT.DEFAULT);
    this.dateStart.setLayoutData(gridDataTextField);

    Label labelDateEnd = new Label(groupInfoConf, SWT.NONE);
    labelDateEnd.setText("Date end * :");
    this.dateEnd = new DateTime(groupInfoConf, SWT.DEFAULT);
    this.dateEnd.setLayoutData(gridDataTextField);

    btnSave = new Button(groupInfoConf, SWT.PUSH);
    btnSave.setText("Save Conference");
    GridData gridDataBtn = new GridData(SWT.RIGHT, SWT.BOTTOM, false, false);
    gridDataBtn.widthHint = 200;
    btnSave.setLayoutData(gridDataBtn);

    btnDelete = new Button(groupInfoConf, SWT.PUSH);
    btnDelete.setText("Delete Conference");
    btnDelete.setLayoutData(gridDataBtn);

    btnClear = new Button(groupInfoConf, SWT.PUSH);
    btnClear.setText("Clear fields");
    btnClear.setLayoutData(gridDataBtn);

    txtCity.addVerifyListener(ListenerAction::checkTextInput);
    txtCoutry.addVerifyListener(ListenerAction::checkTextInput);
    txtRegisFee.addVerifyListener(ListenerAction::checkDoubleInput);
    listConferences.addListener(SWT.Selection, this::fillInAllFields);
    btnSave.addListener(SWT.Selection, event -> {
      try {
        editConference(event);
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    });
    btnClear.addListener(SWT.Selection, this::clearwidget);
    btnDelete.addListener(SWT.Selection, event -> {
      try {
        deleteConference(event);
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    });

    // tear down the SWT window
    shell.pack();
    shell.open();
    while (!shell.isDisposed()) {
      if (!displayGui.readAndDispatch())
        displayGui.sleep();
    }
    displayGui.dispose();

  }

  /**
   * We retrieve the conferences stored in the iCalendar file of the searcher and display it in a
   * SWT widget list
   * 
   * @throws NumberFormatException
   * @throws IOException
   * @throws ParserException
   * @throws InvalidConferenceFormatException
   * @throws CalDAV4JException
   */
  public void getConferences() throws Exception {
    try {
      listConferencesUser = new ArrayList<>(new CalendarOnline(
          new CalDavCalendarGeneric(this.myAuth, ""))
              .getOnlineConferences());
    } catch (CalDAV4JException e) {
      throw new IllegalStateException(e);
    }

    for (Conference conf : this.listConferencesUser) {
      listConferences.add(conf.getTitle());
    }
  }

  /**
   * this method change the date of a conference in the good format in the widget Date time of the
   * GUI
   * 
   * @param fieldDate field of the GUI
   * @param dateRead date of the conference
   */
  public void setDateofConferences(DateTime fieldDate, LocalDate dateRead) {
    fieldDate.setDate(dateRead.getYear(), dateRead.getMonthValue() - 1, dateRead.getDayOfMonth());
  }

  /**
   * This method check the validity of a date choose by the searcher : Date of start must be < of
   * Date of End
   * 
   * @return
   */
  public boolean isDateValid() {
    LocalDate localDateStart =
        LocalDate.of(dateStart.getYear(), dateStart.getMonth() + 1, dateStart.getDay());
    LocalDate localDateEnd =
        LocalDate.of(dateEnd.getYear(), dateEnd.getMonth() + 1, dateEnd.getDay());
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

    if ((Strings.isNullOrEmpty(txtCity.getText()) || Strings.isNullOrEmpty(txtCoutry.getText())
        || Strings.isNullOrEmpty(txtTitle.getText()))) {

      LOGGER.debug("Conference not save : not all fields filled");
      MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
      mb.setText("Failed");
      mb.setMessage("Conference not save : the city, the country or the title is not filled");
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

  /**
   * Fill all fields of the GUI with the information of a conference
   * 
   * @param e event that we catch
   */
  public void fillInAllFields(@SuppressWarnings("unused") Event e) {
    if (listConferences.getSelectionIndex() >= 0) {
      Conference conferenceSelected;
      conferenceSelected = listConferencesUser.get(listConferences.getSelectionIndex());
      txtTitle.setText(conferenceSelected.getTitle());
      txtCity.setText(conferenceSelected.getCity());
      txtCoutry.setText(conferenceSelected.getCountry());
      if (txtUrl != null) {
        txtUrl.setText(conferenceSelected.getUrlAsShortString());
      }
      if (txtRegisFee != null) {
        txtRegisFee.setText(conferenceSelected.getFeeRegistration().toString());
      }
      setDateofConferences(dateStart,
          LocalDate.ofInstant(conferenceSelected.getStartDate(), ZoneOffset.UTC));
      setDateofConferences(dateEnd,
          LocalDate.ofInstant(conferenceSelected.getEndDate(), ZoneOffset.UTC));
    }
  }

  /**
   * Edit and add a conference: Delete from fruux the current selected conference by the user and
   * add a new conference with the value enter by the user The widget list is updates with new
   * online conferences
   * 
   * @param e event that we catch
   * @throws Exception
   */
  public void editConference(@SuppressWarnings("unused") Event e) throws Exception {
    if (isAllFieldsValid()) {
      if (listConferences.getSelectionIndex() >= 0) {
        removeConference();
      }
      addConference(!(Strings.isNullOrEmpty(txtUrl.getText())),
          !(Strings.isNullOrEmpty(txtRegisFee.getText())));
      listConferences.removeAll();
      listConferences.deselectAll();
      try {
        getConferences();
      } catch (InvalidConferenceFormatException e1) {
        throw new IllegalStateException(e1);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    CredentialsReader<FruuxKeysCredential> reader = CredentialsReader.using(FruuxKeysCredential.class, Path.of("API_Credentials_Fruux.txt"));
    ImmutableCompleteMap<FruuxKeysCredential, String> Auth = reader.getCredentials();

    new GuiListConferences().gui(new Display(),Auth);
  }

  /**
   * Delete the conference in fruux that had been selected by the user
   * 
   * @param e vent that we catch
   * @throws Exception
   */
  public void deleteConference(@SuppressWarnings("unused") Event e) throws Exception {
    if (listConferences.getSelectionIndex() >= 0) {
      removeConference();
    }
    listConferences.removeAll();
    listConferences.deselectAll();
    clearwidget(e);
    try {
      getConferences();
    } catch (InvalidConferenceFormatException e1) {
      throw new IllegalStateException(e1);
    }
  }

  /**
   * Clear all widgets of the GUI
   * 
   * @param e
   */
  public void clearwidget(@SuppressWarnings("unused") Event e) {
    txtCity.setText("");
    txtCoutry.setText("");
    txtRegisFee.setText("");
    txtTitle.setText("");
    txtUrl.setText("");
    listConferences.deselectAll();
  }

  /**
   * Call the method from CalendarOnline to push in fruux the new conference
   * 
   * @param url boolean : true if an url is informed
   * @param fees boolean : true if a fee is informed
   */
  public void addConference(boolean url, boolean fees) {
    CalendarOnline instanceCalendarOnline = new CalendarOnline(
        new CalDavCalendarGeneric(this.myAuth, ""));
    LocalDate localDateStart =
        LocalDate.of(dateStart.getYear(), dateStart.getMonth() + 1, dateStart.getDay());
    LocalDate localDateEnd =
        LocalDate.of(dateEnd.getYear(), dateEnd.getMonth() + 1, dateEnd.getDay());
    ConferenceBuilder theBuild = new ConferenceBuilder();
    theBuild = theBuild.setTitle(txtTitle.getText())
        .setStartDate(localDateStart.atStartOfDay(ZoneOffset.UTC).toInstant())
        .setEndDate(localDateEnd.atStartOfDay(ZoneOffset.UTC).toInstant())
        .setCity(txtCity.getText()).setCountry(txtCoutry.getText());

    if (url) {
      URL urlConference;
      try {
        urlConference = new URL(txtUrl.getText());
      } catch (MalformedURLException e1) {
        throw new IllegalStateException(e1);
      }
      theBuild = theBuild.setUrl(urlConference);
    }

    if (fees) {
      theBuild = theBuild.setRegistrationFee(Doubles.tryParse(txtRegisFee.getText()).intValue());
    }
    Conference newConference = theBuild.build();
    try {
      instanceCalendarOnline.addOnlineConference(newConference);
    } catch (CalDAV4JException | URISyntaxException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * Call the method from CalendarOnline to delete in fruux a conference
   */
  public void removeConference() {
    CalendarOnline instanceCalendarOnline = new CalendarOnline(
        new CalDavCalendarGeneric(this.myAuth, ""));
    String uidDelete = listConferencesUser.get(listConferences.getSelectionIndex()).getUid();
    try {
      instanceCalendarOnline.deleteOnlineConference(uidDelete);
    } catch (CalDAV4JException e) {
      throw new IllegalStateException(e);
    }
  }
}
