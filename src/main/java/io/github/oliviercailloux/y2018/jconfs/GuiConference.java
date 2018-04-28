package io.github.oliviercailloux.y2018.jconfs;


import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * This class create the GUI to edit the conference
 * @author huong, camille
 *
 */
public class GuiConference {
	public static void main(String[] args){
		// Create the window
		JFrame fenetre = new JFrame("Conference");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		// Create a layout on the panel to have 7 rows  for 7 Text Field + 2 columns for the label and the Field.
		GridLayout gl = new GridLayout(7,2); 
		panel.setLayout(gl);
		
		// Create the label and the Text Field for the Title
		JLabel labelTitle = new JLabel("Enter Title :");
		JTextField textfieldTitle = new JTextField();
		panel.add(labelTitle);
		panel.add(textfieldTitle);
		
		// Create the label and the Text Field for the Registration Fee
		JLabel labelFee = new JLabel("Enter Registration Fee :");
		JTextField textfieldFee = new JTextField();
		panel.add(labelFee);
		panel.add(textfieldFee);
		
		// Create the label and the Text Field for the City
		JLabel labelCity = new JLabel("Enter City :");
		JTextField textfieldCity = new JTextField();
		panel.add(labelCity);
		panel.add(textfieldCity);
		
		// Create the label and the Text Field for the Country
		JLabel labelCountry = new JLabel("Enter Country :");
		JTextField textfieldCountry = new JTextField();
		panel.add(labelCountry);
		panel.add(textfieldCountry);
		
		// Create the label and the Text Field for the Date Start
		JLabel labelStartDate = new JLabel("Enter Date Start (format: dd/mm/yyyy) :");
		JTextField textfieldStartDate = new JTextField();
		panel.add(labelStartDate);
		panel.add(textfieldStartDate);
		
		// Create the label and the Text Field for the Date End
		JLabel labelEndDate = new JLabel("Enter Date End (format: dd/mm/yyyy) :");
		JTextField textfieldEndDate = new JTextField();
		panel.add(labelEndDate);
		panel.add(textfieldEndDate);
		
		// Create the button Submit
		JButton button = new JButton("Submit"); 
		button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                proceed();
            }
            // Show the information of the conference after filling the form
			private void proceed() {
				System.out.println("The conference is created");
				System.out.println("The title is: " + textfieldTitle.getText());
				System.out.println("The Registration Fee is: " + textfieldFee.getText());
				System.out.println("The Date Start is: " + textfieldStartDate.getText());
				System.out.println("The Date End is: " + textfieldEndDate.getText());
				System.out.println("The City is: " + textfieldCity.getText());
				System.out.println("The Country is: " + textfieldCountry.getText());
						 
				
			}
        });
		
		// Add the window and the button on the panel
		panel.add(button);
		fenetre.getContentPane().add(panel);
		fenetre.setSize(600, 300);
		fenetre.setVisible(true);
		
	}
}
