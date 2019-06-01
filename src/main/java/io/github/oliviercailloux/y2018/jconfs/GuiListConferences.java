package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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

/**
 * @author nikola
 *Gui where we can show conferences of a user
 */
public class GuiListConferences {
	private static final Logger LOGGER = LoggerFactory.getLogger(GuiListConferences.class);
	private ArrayList<Conference> listConferencesUser;
	private List listConferences;
	private Shell shell;
	private String calendarName;

	/**
	 * Create a GUI where they will have conferences of the user
	 * @throws ParseException 
	 * @throws ParserException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * @throws InvalidConferenceFormatException 
	 */
	public GuiListConferences() throws IOException, ParserException, InvalidConferenceFormatException {
		calendarName="threeConferences";
	    Display display=new Display();
	    shell=createShell(display);
	    shell.open();

	}
	
	/** Create a shell with all field of a conference and a list of conferences
	 * of a file
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
		GridLayout layout = new GridLayout(2,false);
		shell.setLayout(layout);
		
		listConferences = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	    this.getConferences();
	    GridData gridDatalist = new GridData();
	    gridDatalist.grabExcessHorizontalSpace = true;
	    gridDatalist.grabExcessVerticalSpace = true;
	    //gridDatalist.widthHint = 550;
	    //gridDatalist.heightHint = 150;
		listConferences.setLayoutData(gridDatalist);
		
		Group groupInfoConf = new Group(shell, SWT.NONE);
		groupInfoConf.setText ("Details of your conference");
		GridLayout gridLayoutDetails=new GridLayout(4, false);
		groupInfoConf.setLayout (gridLayoutDetails);
		
		GridData gridData=new GridData();
		gridData.horizontalSpan=3;
		gridData.widthHint = 500;
		gridData.heightHint = 30;
		
		Label labelTitle= new Label(groupInfoConf, SWT.NONE);
		labelTitle.setText("Title :");
		Text txtTitle = new Text(groupInfoConf,SWT.SINGLE | SWT.BORDER);
		txtTitle.setLayoutData(gridData);
		
		Label labelUrl= new Label(groupInfoConf, SWT.NONE);
		labelUrl.setText("URL :");
		Text txtUrl = new Text(groupInfoConf,SWT.SINGLE | SWT.BORDER);
		txtUrl.setLayoutData(gridData);
		
		Label labelfee= new Label(groupInfoConf, SWT.NONE);
		labelfee.setText("Fee :");
		Text txtRegisFee = new Text(groupInfoConf,SWT.SINGLE | SWT.BORDER);
		txtRegisFee.setLayoutData(gridData);
		
		Label labelCountry= new Label(groupInfoConf, SWT.NONE);
		labelCountry.setText("Country :");
		Text txtCoutry = new Text(groupInfoConf,SWT.SINGLE | SWT.BORDER);
		txtCoutry.setLayoutData(gridData);
		
		Label labelCity = new Label(groupInfoConf, SWT.NONE);
		labelCity.setText("City :");
		Text txtCity=new Text(groupInfoConf,SWT.SINGLE | SWT.BORDER);
		txtCity.setLayoutData(gridData);
		
		Label labelDateStart = new Label(groupInfoConf, SWT.NONE);
		labelDateStart.setText("Date start :");
		DateTime datestart=new DateTime(groupInfoConf, SWT.DEFAULT);
		datestart.setLayoutData(gridData);

		Label labelDateEnd = new Label(groupInfoConf, SWT.NONE);
		labelDateEnd.setText("Date end :");
		DateTime dateend=new DateTime(groupInfoConf, SWT.DEFAULT);
		dateend.setLayoutData(gridData);
		
		Button btnsave=new Button(groupInfoConf, SWT.PUSH);
		btnsave.setText("Save Conference");
		GridData gridDataBtn = new GridData(SWT.RIGHT, SWT.BOTTOM, false, false);
		btnsave.setLayoutData(gridDataBtn);
	

		listConferences.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (listConferences.getSelectionIndex()>=0) {
					Conference conferenceSelected;
					conferenceSelected=listConferencesUser.get(listConferences.getSelectionIndex());
					txtTitle.setText(conferenceSelected.getTitle());
					txtCity.setText(conferenceSelected.getCity());
					txtCoutry.setText(conferenceSelected.getCountry());
					txtUrl.setText(conferenceSelected.getUrl().toString());
					txtRegisFee.setText(conferenceSelected.getFeeRegistration().toString());
					setDateofConf(datestart,conferenceSelected.getStartDate());
					setDateofConf(dateend,conferenceSelected.getEndDate());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});
		
		btnsave.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//edit of a conference
				if(isDateValid(datestart, dateend)&& listConferences.getSelectionIndex()>=0) {
					//ConferenceWriter.deleteConference(listConferencesUser.get(listConferences.getSelectionIndex()));
					//ConferenceWriter.addConference(new conference(.........));
					listConferences.removeAll();
					try {
						getConferences();
					} catch (NumberFormatException | IOException | ParserException|InvalidConferenceFormatException e1) {
						e1.printStackTrace();
					}
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
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
		while (!this.shell.isDisposed())
		{
			if (!shell.getDisplay().readAndDispatch()) {
				shell.getDisplay().sleep();
			}
		}
	}
		
	/**
	 * We retrieve and display in a list the conferences stored in the ical file of the identified user.
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParserException
	 * @throws ParseException
	 * @throws InvalidConferenceFormatException 
	 */
	public void getConferences() throws IOException, ParserException, InvalidConferenceFormatException {
		ConferencesRetriever retriever = new ConferencesFromICal();
		ConferencesShower conflist=new ConferencesShower(retriever);
		listConferencesUser=new ArrayList<Conference>(conflist.searchConferenceInFile(this.calendarName));
	    for (Conference conf: this.listConferencesUser) {
	      listConferences.add(conf.getTitle());
	    }
	}
	
	/** this method implement the date of a conference in the good format in the GUI
	 * @param fieldDate field of the GUI
	 * @param dateread date of the conference
	 */
	public void setDateofConf(DateTime fieldDate,LocalDate dateread) {
		fieldDate.setDate(dateread.getYear(), dateread.getMonthValue()-1, dateread.getDayOfMonth());
	}
	
	/**This method check the validity of date choose by the searcher :
	 * Date of start < of Date of End 
	 * @param datestart field that contains the start date of the conference
	 * @param dateend field that contains the end date of the conference
	 * @return
	 */
	public boolean isDateValid(DateTime datestart,DateTime dateend) {
		LocalDate lstart=LocalDate.of(datestart.getYear(), datestart.getMonth()+1, datestart.getDay());
		LocalDate leend=LocalDate.of(dateend.getYear(), dateend.getMonth()+1, dateend.getDay());
		if (lstart.compareTo(leend)>0||lstart.compareTo(leend)==0) {
			LOGGER.debug("conference not save : startday > enday");
			MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
			mb.setText("Failed");
			mb.setMessage("Date Start can't be lower or equal to Date End");
			mb.open();
			return false;
		}
		return true;
	}
	
	public static void main(String[]args) throws IOException, ParserException, InvalidConferenceFormatException{
		new GuiListConferences().display();
	}
}


