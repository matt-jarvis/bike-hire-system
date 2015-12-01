package view;

import model.hire.Hire;
import model.invoice.InvoiceOut;

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

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import controller.Controller;
import model.bike.Available;
import model.bike.Bike;
import model.customer.Customer;

/*
 * A panel that enables the user to create a new Hire object.
 * 
 * Uses JodaTime (external library) to handle all date input.
 */
public class HireCreatePanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private Controller controller;

	private JTextField custIdField;
	private JTextField bikeIdField;
	private JTextField returnDateField;
	private JButton submitBtn;
	private JButton clearBtn;

	public HireCreatePanel()
	{

		controller = Controller.getInstance();

		setLayout(new BorderLayout(0, 0));

		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(headerPanel, BorderLayout.NORTH);
		
		JLabel headerLabel = new JLabel("Please enter hire details below...", SwingConstants.CENTER);
		headerPanel.add(headerLabel, BorderLayout.NORTH);

		JPanel formPanel = new JPanel();
		add(formPanel, BorderLayout.CENTER);
		formPanel.setLayout(new GridLayout(8,5));

		JLabel custIdLbl = new JLabel("Customer ID:", SwingConstants.RIGHT);
		custIdField = new JTextField();
		JLabel bikeIdLbl = new JLabel("Bike ID:", SwingConstants.RIGHT);
		bikeIdField = new JTextField();
		JLabel returnDateLbl = new JLabel("Return date:", SwingConstants.RIGHT);
		returnDateField = new JTextField();
		submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ButtonListener());
		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ButtonListener());

		// GridLayout requires padding ("")
		formPanel.add(new JLabel(""));
		formPanel.add(custIdLbl);
		formPanel.add(custIdField);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(bikeIdLbl);
		formPanel.add(bikeIdField);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(returnDateLbl);
		formPanel.add(returnDateField);
		formPanel.add(new JLabel("(dd-mm-yyyy)"));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));
		formPanel.add(submitBtn);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		// Padding
		for(int i = 0; i < 20; i++)
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
		Customer customer;
		Bike bike;
		LocalDate returnDate;
		DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");

		@Override
		public void actionPerformed(ActionEvent e)
		{
			pressed = (JButton) e.getSource();

			if(pressed == submitBtn)
			{
				if (fieldEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please complete all fields");
				}
				else if(validCustomerID() && validBikeID() && validReturnDate())
				{	
					// Create hire and invoice
					Hire hire = new Hire(customer, bike, returnDate);
					InvoiceOut invoice = new InvoiceOut(hire);

					int confirm = JOptionPane.showConfirmDialog(null, 
							invoice.toString()
							+ "\n\nPayment successful?", 
							"Confirm payment", 
							JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION)
					{
						hire.payForHire();
						controller.addInvoiceOut(invoice);
						controller.addHire(invoice.getHire());
						JOptionPane.showMessageDialog(null, "New hire created: " + hire.getHireID());
						clearTextFields();
					}
					else
					{
						hire = null;
						invoice = null;
					}
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
			if(custIdField.getText().isEmpty() ||
					bikeIdField.getText().isEmpty() ||
					returnDateField.getText().isEmpty())
				return true;
			return false;
		}

		/*
		 * Determines whether the String entered for the customer's ID
		 * matches that of an existing customer in the controller's 
		 * customers list.
		 * 
		 * @return	true if a matching customer ID is found
		 * 			false otherwise.
		 */
		private boolean validCustomerID()
		{
			String a = custIdField.getText();
			String b = "";

			for (int i = 0; i < controller.getCustomers().size(); i++)
			{
				b = controller.getCustomers().get(i).getCustomerID();

				if (a.compareToIgnoreCase(b) == 0)
				{
					customer = controller.getCustomers().get(i);
					return true;
				}
			}

			JOptionPane.showMessageDialog(null, "Customer ID not recognised. Please try again.");
			return false;
		}

		/*
		 * Determines whether the String entered for the bike's ID
		 * matches that of an existing bike in the controller's 
		 * bikes list.
		 * 
		 * @return	true if a matching bike ID is found
		 * 			false otherwise.
		 */
		private boolean validBikeID()
		{
			String a = bikeIdField.getText();
			String b = "";

			for (int i = 0; i < controller.getBikes().size(); i++)
			{
				b = controller.getBikes().get(i).getBikeID();

				if (a.compareToIgnoreCase(b) == 0)
				{
					if (controller.getBikes().get(i).getState() instanceof Available)
					{
						bike = controller.getBikes().get(i);
						return true;
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Bike currently unavailable.");
						return false;
					}
				}

			}

			JOptionPane.showMessageDialog(null, "Bike ID not recognised. Please try again.");
			return false;
		}

		/*
		 * Tries to convert the String entered for the desired return 
		 * date into a LocalDate object. Alerts user if not possible 
		 * or the date entered is not valid.
		 * 
		 * @return	true if date was converted and is valid.
		 * 			false otherwise.
		 */
		private boolean validReturnDate()
		{
			try
			{
				returnDate = LocalDate.parse(returnDateField.getText(), dateFormat);
			} catch(IllegalArgumentException iae)
			{
				JOptionPane.showMessageDialog(null, "Return date format could not be recognised.");
				return false;
			}

			if (returnDate.isBefore(LocalDate.now()))
			{
				JOptionPane.showMessageDialog(null, "Return date must be in the future.");
				return false;
			}
			else if(returnDate.isEqual(LocalDate.now()))
			{
				JOptionPane.showMessageDialog(null, "Hires must last a minimum of 1 day.");
				return false;
			}
			else
			{
				return true;
			}
		}

		/*
		 * Clears all user input from the fields.
		 */
		private void clearTextFields()
		{
			custIdField.setText("");
			bikeIdField.setText("");
			returnDateField.setText("");
		}
	}
}
