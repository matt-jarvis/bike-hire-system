package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import model.customer.Customer;


/*
 * A panel that enables the user to create a new Customer object.
 */
public class CustomerCreatePanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private Controller controller;

	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField line1Field;
	private JTextField townField;
	private JTextField postcodeField;
	private JButton submitButton;


	public CustomerCreatePanel()
	{
		controller = Controller.getInstance();

		setLayout(new BorderLayout(0, 0));

		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(headerPanel, BorderLayout.NORTH);
		
		JLabel headerLbl = new JLabel("Please enter customer details below...", SwingConstants.CENTER);
		headerPanel.add(headerLbl);

		JPanel formPanel = new JPanel();
		add(formPanel, BorderLayout.CENTER);
		formPanel.setLayout(new GridLayout(8,5));

		JLabel fNameLbl = new JLabel("First name:", SwingConstants.RIGHT);
		fNameField = new JTextField();
		JLabel lNameLbl = new JLabel("Last name:", SwingConstants.RIGHT);
		lNameField = new JTextField();
		JLabel line1Lbl = new JLabel("Line 1:", SwingConstants.RIGHT);
		line1Field = new JTextField();
		JLabel townLbl = new JLabel("Town:", SwingConstants.RIGHT);
		townField = new JTextField();
		JLabel postcodeLbl = new JLabel("Postcode:", SwingConstants.RIGHT);
		postcodeField = new JTextField();
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ButtonListener());

		// GridLayout requires padding ("")
		formPanel.add(new JLabel(""));
		formPanel.add(fNameLbl);
		formPanel.add(fNameField);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(lNameLbl);
		formPanel.add(lNameField);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel("Address", SwingConstants.CENTER));
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(line1Lbl);
		formPanel.add(line1Field);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(townLbl);
		formPanel.add(townField);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(postcodeLbl);
		formPanel.add(postcodeField);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));
		formPanel.add(submitButton);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		// Padding
		for(int i = 0; i < 5; i++)
			formPanel.add(new JLabel(""));

	}
	
	/* 
	 * Processes user input when button is pressed. If user input 
	 * is valid, it is used to create a new Bike object, which is
	 * added to the controller.
	 */
	private class ButtonListener implements ActionListener
	{

		JButton pressed;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			pressed = (JButton) e.getSource();

			if(pressed == submitButton)
			{
				if (fieldEmpty())
					JOptionPane.showMessageDialog(null, "Please complete all fields");
				else
				{	
					Customer customer = new Customer(fNameField.getText(), 
							lNameField.getText(), 
							line1Field.getText(),
							townField.getText(),
							postcodeField.getText());

					controller.addCustomer(customer);
					JOptionPane.showMessageDialog(null, "New customer created: " + customer.getCustomerID());
					clearTextFields();
				}

			}

		}

		/*
		 * Checks for incomplete fields
		 * 
		 * @return	true if one or more fields are incomplete.
		 * 			false otherwise
		 */
		private boolean fieldEmpty()
		{
			if(fNameField.getText().isEmpty() ||
					lNameField.getText().isEmpty() ||
					line1Field.getText().isEmpty() ||
					townField.getText().isEmpty() ||
					postcodeField.getText().isEmpty())
				return true;
			return false;
		}

		/*
		 * Clears all user input from the fields.
		 */
		private void clearTextFields()
		{
			fNameField.setText("");
			lNameField.setText("");
			line1Field.setText("");
			townField.setText("");
			postcodeField.setText("");
		}
	}
}
