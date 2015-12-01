package view;

import model.hire.Complete;
import model.hire.Hire;
import model.invoice.InvoiceIn;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import controller.Controller;

/*
 * A panel that contains a table populated with the details 
 * of all hire objects. Also enables active/late hires to be 
 * returned and displays statistics based on the hires stored 
 * in the controller's hires list.
 */
public class HireViewPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private Controller controller;

	private JTable hireTable;
	private String[] columns = {"Hire ID", "Customer ID", "Bike ID", 
			"Start date", "End date", "Returned on", "Status"};
	private String[][] data;
	private JScrollPane scrollPane;
	private JPanel bottomPanel;
	private JLabel totalLbl;
	private JLabel activeLbl;
	private JLabel lateLbl;
	private JLabel completeLbl;
	private JLabel total;
	private JLabel active;
	private JLabel late;
	private JLabel complete;
	private JButton returnBtn;

	public HireViewPanel()
	{
		controller = Controller.getInstance();

		setLayout(new BorderLayout());
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(2,9));

		totalLbl = new JLabel("Total hires: ", SwingConstants.RIGHT);
		activeLbl = new JLabel("Active: ", SwingConstants.RIGHT);
		lateLbl = new JLabel("Late: ", SwingConstants.RIGHT);
		completeLbl = new JLabel("Complete: ", SwingConstants.RIGHT);
		total = new JLabel();
		active = new JLabel();
		late = new JLabel();
		complete = new JLabel();
		
		returnBtn = new JButton("Return hire");
		returnBtn.addActionListener(new ButtonListener());

		initPanel();
	}

	/*
	 * Initialises the panel to ensure all data is up-to-date.
	 */
	public void initPanel()
	{
		controller.refreshLateHires();
		
		removeAll();
		bottomPanel.removeAll();
		drawTable();
		add(scrollPane, BorderLayout.CENTER);

		total.setText(Integer.toString(controller.getReport().getTotalHires()));
		active.setText(Integer.toString(controller.getReport().getNoOfActiveHires()));
		late.setText(Integer.toString(controller.getReport().getNoOfLateHires()));
		complete.setText(Integer.toString(controller.getReport().getNoOfCompleteHires()));

		bottomPanel.add(totalLbl);
		bottomPanel.add(total);
		bottomPanel.add(activeLbl);
		bottomPanel.add(active);
		bottomPanel.add(new JLabel(""));
		bottomPanel.add(new JLabel(""));
		bottomPanel.add(new JLabel(""));
		bottomPanel.add(new JLabel(""));
		bottomPanel.add(returnBtn);
		
		bottomPanel.add(lateLbl);
		bottomPanel.add(late);
		bottomPanel.add(completeLbl);
		bottomPanel.add(complete);
		bottomPanel.add(new JLabel(""));
		bottomPanel.add(new JLabel(""));
		bottomPanel.add(new JLabel(""));
		bottomPanel.add(new JLabel(""));

		add(bottomPanel, BorderLayout.PAGE_END);
	}

	/*
	 * Draws the table with the appropriate dimensions.
	 */
	public void drawTable()
	{
		populateTable();
		hireTable = new JTable(data, columns);
		scrollPane = new JScrollPane(hireTable);
		hireTable.setPreferredScrollableViewportSize(new Dimension(800, 200));
	}

	/*
	 * Populates the table with up-to-date data.
	 */
	private void populateTable()
	{
		data = new String[controller.getHires().size()][7];

		int row = 0;
		String dateReturned;

		while(row != controller.getHires().size())
		{	

			try
			{
				dateReturned = controller.getHires().get(row).getDateReturned().toString();
			}catch(NullPointerException npe)
			{
				dateReturned = "N/A";
			}
			data[row][0] = controller.getHires().get(row).getHireID();
			data[row][1] = controller.getHires().get(row).getCustomer().getCustomerID();
			data[row][2] = controller.getHires().get(row).getBike().getBikeID();
			data[row][3] = 	controller.getHires().get(row).getStartDate().toString();
			data[row][4] = controller.getHires().get(row).getEndDate().toString();
			data[row][5] = dateReturned;
			data[row][6] = controller.getHires().get(row).getState().toString();
			row++;
		}
	}

	/* 
	 * Listen for Return hire button click.
	 * Prompt user for hire ID when button is pressed.
	 * Return hire and display invoice if possible, alert 
	 * user if not.
	 */
	private class ButtonListener implements ActionListener
	{

		JButton pressed;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			pressed = (JButton) e.getSource();

			if (pressed == returnBtn)
			{
				Hire hire = null;
				String hireID = "";
				boolean found = false;

				// Get valid hire ID
				while (hire == null)
				{			
					// Prompt user for a hire id
					hireID = JOptionPane.showInputDialog("Please enter a hire ID");

					// Break if user has closed the prompt window
					if (hireID == null)
						break;

					// Search for hire ID
					for (int i = 0; i < controller.getHires().size(); i++)
					{
						if (hireID.compareToIgnoreCase(controller.getHires().get(i).getHireID()) == 0)
						{
							found = true;

							// Check if hire is eligible for return
							if (controller.getHires().get(i).getState() instanceof Complete)
							{
								JOptionPane.showMessageDialog(null, "Hire not suitable for return. Please try again.");
								break;
							}
							else
							{

								hire = controller.getHires().get(i);

								// Check for damage
								int bikeIsDamaged = JOptionPane.showConfirmDialog(null, 
										"Is the bike damaged?", 
										"Damage Check", 
										JOptionPane.YES_NO_OPTION);

								// Break if user has closed the prompt window
								if (bikeIsDamaged == JOptionPane.CLOSED_OPTION)
									break;

								if (bikeIsDamaged == JOptionPane.YES_OPTION)
									hire.returnHire(true);
								else if (bikeIsDamaged == JOptionPane.NO_OPTION)
									hire.returnHire(false);

								// Display invoice and confirm return
								InvoiceIn invoice = new InvoiceIn(hire);
								controller.addInvoiceIn(invoice);
								JOptionPane.showMessageDialog(null, "Hire returned \n\n" + invoice.toString());
								
								initPanel();
								break;
							}
						}
					}

					if (!found == true)
					{
						JOptionPane.showMessageDialog(null, "Hire ID not recognised. Please try again.");
						found = false;
					}
				}

			}

		}

	}

}
