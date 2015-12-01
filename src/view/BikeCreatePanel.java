package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import model.bike.Bike;

/*
 * A panel that enables the user to create a new Bike object.
 */
public class BikeCreatePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Controller controller;

	private JTextField inpMake;
	private JTextField inpModel;
	private JTextField inpFunction;
	private JComboBox<String> inpDemographic;
	private JTextField inpColour;
	private JTextField inpDailyRate;
	private JTextField inpDeposit;
	private JButton submitButton;


	public BikeCreatePanel()
	{
		controller = Controller.getInstance();
		setLayout(new BorderLayout(0, 0));

		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(headerPanel, BorderLayout.NORTH);
		
		JLabel headerLabel = new JLabel("Please enter bike details below...", SwingConstants.CENTER);
		headerPanel.add(headerLabel);

		JPanel formPanel = new JPanel();
		add(formPanel, BorderLayout.CENTER);
		formPanel.setLayout(new GridLayout(8, 5));

		inpMake = new JTextField(12);
		inpModel = new JTextField(12);
		inpFunction = new JTextField(12);
		inpDemographic = new JComboBox<String>(new String[] {"- Please select -", "adult", "children", "men", "women", "boy", "girl"});
		((JLabel)inpDemographic.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		inpColour = new JTextField(12);
		inpDailyRate = new JTextField(12);
		inpDeposit = new JTextField(12);
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ButtonListener());
		
		// GridLayout requires padding ("")
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel("Make: ", SwingConstants.RIGHT));
		formPanel.add(inpMake);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel("Model: ", SwingConstants.RIGHT));
		formPanel.add(inpModel);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel("Function: ", SwingConstants.RIGHT));
		formPanel.add(inpFunction);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel("Demographic: ", SwingConstants.RIGHT));
		formPanel.add(inpDemographic);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel("Colour: ", SwingConstants.RIGHT));
		formPanel.add(inpColour);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel("Daily rate:  £", SwingConstants.RIGHT));
		formPanel.add(inpDailyRate);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel("Deposit:  £", SwingConstants.RIGHT));
		formPanel.add(inpDeposit);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));
		formPanel.add(submitButton);
		formPanel.add(new JLabel(""));
		formPanel.add(new JLabel(""));

		setVisible(true);
	}

	/* 
	 * Processes user input when button is pressed. If user input 
	 * is valid, it is used to create a new Bike object, which is
	 * added to the controller.
	 */
	private class ButtonListener implements ActionListener
	{

		JButton pressed;
		double dailyRate = 0;
		double deposit = 0;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			pressed = (JButton) e.getSource();

			if(pressed == submitButton)
			{
				if (fieldEmpty())
					JOptionPane.showMessageDialog(null, "Please complete all fields");
				else if(validDailyRate() && validDeposit())
				{	
					Bike bike = new Bike(inpMake.getText(), 
							inpModel.getText(),
							inpFunction.getText(), 
							(String) inpDemographic.getSelectedItem(),
							inpColour.getText(), 
							dailyRate, 
							deposit);

					controller.addBike(bike);
					JOptionPane.showMessageDialog(null, "New bike registered: " + bike.getBikeID());
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
			if(	inpMake.getText().isEmpty() || 
				inpModel.getText().isEmpty() ||
				inpFunction.getText().isEmpty() || 
				inpDemographic.getSelectedItem() == "- Please select -" ||
				inpColour.getText().isEmpty() || 
				inpDailyRate.getText().isEmpty() || 
				inpDeposit.getText().isEmpty())
				return true;
			return false;
		}

		/*
		 * Tries to convert the String entered for the bike's daily rate
		 * into a double. Alerts user if not possible or the double 
		 * entered is and invalid number.
		 * 
		 * @return	true if double was converted and is valid.
		 * 			false otherwise.
		 */
		private boolean validDailyRate()
		{
			try
			{
				dailyRate = Double.parseDouble(inpDailyRate.getText());
			} catch(NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(null, "Daily rate format cannot be recognised. Please try again.");
				return false;
			}

			if(dailyRate <= 0)
			{
				JOptionPane.showMessageDialog(null, "Daily rate must be greater than £00.00");
				return false;
			}
			return true;
		}

		/*
		 * Tries to convert the String entered for the bike's deposit
		 * into a double. Alerts user if not possible or the double 
		 * entered is and invalid number.
		 * 
		 * @return	true if double was converted and is valid.
		 * 			false otherwise.
		 */
		private boolean validDeposit()
		{
			try
			{
				deposit = Double.parseDouble(inpDeposit.getText());
			} catch(NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(null, "Deposit format cannot be recognised. Please try again.");
				return false;
			}

			if(deposit <= 0)
			{
				JOptionPane.showMessageDialog(null, "Deposit must be greater than £00.00");
				return false;
			}
			return true;
		}
		
		/*
		 * Clears all user input from the fields.
		 */
		private void clearTextFields()
		{
			inpMake.setText("");
			inpModel.setText("");
			inpFunction.setText("");
			inpDemographic.setSelectedIndex(0);
			inpColour.setText(""); 
			inpDailyRate.setText("");
			inpDeposit.setText("");
		}
	}
}
