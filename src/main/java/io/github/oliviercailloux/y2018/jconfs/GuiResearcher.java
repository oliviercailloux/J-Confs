package io.github.oliviercailloux.y2018.jconfs;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;


import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

/**
 * This class create the GUI to edit the conference
 * @author huong, camille
 *
 */
public class GuiResearcher {
	
	public static void main(String[] args){
	
		
		// setup the SWT window
		Display display = new Display();
		Shell shell = new Shell(display);

		shell.setText("JConf");

		GridLayout gridLayout = new GridLayout();

		shell.setLayout(gridLayout);

		shell.setLocation(400, 200);

		shell.layout(true, true);

		final Point newSize = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);

		shell.setSize(new Point(912, 796));
		

		
		Group grp_researcher = new Group(shell, SWT.NONE);
		GridData gd_researcher = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_researcher.heightHint =300;
		gd_researcher.widthHint = 900;
		grp_researcher.setLayoutData(gd_researcher);
		grp_researcher.setText("Researcher");
		
		Label lblLogin = new Label(grp_researcher, SWT.NONE);
		lblLogin.setAlignment(SWT.RIGHT);
		lblLogin.setBounds(25, 16, 55, 20);
		lblLogin.setText("Login");
		Text txt_login = new Text(grp_researcher, SWT.BORDER);
		txt_login.setBounds(83, 15, 78, 21);
		
		Label lblSurname = new Label(grp_researcher, SWT.NONE);
		lblSurname.setAlignment(SWT.RIGHT);
		lblSurname.setBounds(25, 100, 55, 15);
		lblSurname.setText("Surname");
		Text txt_Surname= new Text(grp_researcher, SWT.BORDER);
		txt_Surname.setBounds(83, 99, 200, 21);
		
		
		Label lblFirstname = new Label(grp_researcher, SWT.NONE);
		lblFirstname.setAlignment(SWT.RIGHT);
		lblFirstname.setBounds(400, 100, 55, 15);
		lblFirstname.setText("Firstname");
		Text txt_Firstname= new Text(grp_researcher, SWT.BORDER);
		txt_Firstname.setBounds(458, 99, 200, 21);
		
		Label lblPhone = new Label(grp_researcher, SWT.NONE);
		lblPhone.setAlignment(SWT.RIGHT);
		lblPhone.setBounds(20, 150, 55, 15);
		lblPhone.setText("Phone");
		Text txt_Phone= new Text(grp_researcher, SWT.BORDER);
		txt_Phone.setBounds(78, 149, 200, 21);
		
		Label lblGroup= new Label(grp_researcher, SWT.NONE);
		lblGroup.setAlignment(SWT.RIGHT);
		lblGroup.setBounds(320, 150, 55, 15);
		lblGroup.setText("Group");
		Text txt_Group= new Text(grp_researcher, SWT.BORDER);
		txt_Group.setBounds(376, 149, 200, 21);
		
		Label lblMail = new Label(grp_researcher, SWT.NONE);
		lblMail.setAlignment(SWT.RIGHT);
		lblMail.setBounds(20, 200, 55, 15);
		lblMail.setText("Mail");
		Text txt_Mail= new Text(grp_researcher, SWT.BORDER);
		txt_Mail.setBounds(78, 199, 400, 21);
		
		Label lblOffice= new Label(grp_researcher, SWT.NONE);
		lblOffice.setAlignment(SWT.RIGHT);
		lblOffice.setBounds(600, 150, 55, 15);
		lblOffice.setText("Office");
		Text txt_Office= new Text(grp_researcher, SWT.BORDER);
		txt_Office.setBounds(658, 149, 150, 21);
		
		Button btn_researcher= new Button(grp_researcher, SWT.NONE);
		btn_researcher.setBounds(250, 16, 200, 25);
		btn_researcher.setText("search Researcher");
		
		btn_researcher.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event)  {
				String login = txt_login.getText();
				
				try {
					Researcher researcher = ResearcherBuilder.create(login);
					System.out.println(researcher.getSurname());
					txt_Surname.setText(researcher.getSurname());
					System.out.println(researcher.getFirstname());
					txt_Firstname.setText(researcher.getFirstname());
					System.out.println(researcher.getPhone());
					txt_Phone.setText(researcher.getPhone());
					System.out.println(researcher.getGroup());
					txt_Group.setText(researcher.getGroup());
					System.out.println(researcher.getMail());
					txt_Mail.setText(researcher.getMail());
					System.out.println(researcher.getOffice());
					txt_Office.setText(researcher.getOffice());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| ClassCastException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
				
		shell.pack();
	    shell.open();
	    while (!shell.isDisposed())
	      if (!display.readAndDispatch())
	        display.sleep();

	 
	    display.dispose();


	}
}
