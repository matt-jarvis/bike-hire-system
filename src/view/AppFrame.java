package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Controller;

/*
 * The main frame in which all content for the Rental System
 * is contained.
 */
public class AppFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Controller controller;

	// Tabs for each section of the Rental System
	private JTabbedPane mainTab;
	private JTabbedPane hireTab;
	private JTabbedPane customerTab;
	private JTabbedPane bikeTab;
	private JTabbedPane invoiceTab;

	public AppFrame()
	{
		controller = Controller.getInstance();
		initialiseData();

		setTitle("Northampton Bike Hire Company: Rental System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 890, 440);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel();
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new GridLayout(2, 1));
		headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.add(headerPanel, BorderLayout.NORTH);

		JLabel headerLbl = new JLabel("Northampton Bike Hire Company");
		headerLbl.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		headerLbl.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(headerLbl, BorderLayout.NORTH);

		JLabel subHeaderLbl = new JLabel("Rental System");
		subHeaderLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		subHeaderLbl.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(subHeaderLbl, BorderLayout.NORTH);

		mainTab = new JTabbedPane(JTabbedPane.TOP);
		mainPanel.add(mainTab, BorderLayout.CENTER);

		hireTab = new HireTab();
		mainTab.addTab("Hire", null, hireTab, null);

		customerTab = new CustomerTab();
		mainTab.addTab("Customer", null, customerTab, null);

		bikeTab = new BikeTab();
		mainTab.addTab("Bike", null, bikeTab, null);

		invoiceTab = new InvoiceTab();
		mainTab.addTab("Invoice", null, invoiceTab, null);

		// Update data to reflect changes made when user clicks on certain tabs
		mainTab.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent ce)
			{
				if (mainTab.getSelectedIndex() == 2)
					((BikeViewPanel) ((BikeTab) bikeTab).getViewPanel())
							.initPanel();
				else if (mainTab.getSelectedIndex() == 3)
					((InvoiceTab) invoiceTab).refreshTab();
			}
		});

		// Try to save data when window closes and confirm closure if not
		// possible
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				try
				{
					controller.serialiseBikeHireData();
				} catch (IOException ioe)
				{
					int close = JOptionPane
							.showConfirmDialog(
									null,
									"Data could not be saved. \n"
											+ "Are you sure you want to exit? (all session data will be lost)",
									"WARNING", JOptionPane.YES_NO_OPTION);

					if (close == JOptionPane.YES_OPTION)
						setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					else
						setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				}

			}
		});

		setResizable(false);
		setVisible(true);
	}

	// Tries to deserialise previous application data. Alerts user if not
	// successful.
	private void initialiseData()
	{
		String msg;

		try
		{
			controller.deserialiseBikeHireData();
			return;
		} catch (FileNotFoundException e)
		{
			msg = "Error loading program data: file could not be found.";
		} catch (IOException e1)
		{
			msg = "Error loading program data.";
		} catch (ClassNotFoundException e2)
		{
			msg = "Error loading program data: data was saved by a different application";
		}

		askToGenerateData(msg);
	}

	private void askToGenerateData(String msg)
	{
		int generate = JOptionPane.showConfirmDialog(null, msg
				+ "\nWould you like to generate some dummy data?",
				"Error: Generate Data instead?", JOptionPane.YES_NO_OPTION);

		if (generate == JOptionPane.YES_OPTION)
			if (!controller.generateData())
				JOptionPane.showMessageDialog(null, "Could not generate dummy data");
	}
}
