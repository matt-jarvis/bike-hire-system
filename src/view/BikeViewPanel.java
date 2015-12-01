package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import controller.Controller;
import model.bike.Bike;

/*
 * A panel that contains a table populated with the details 
 * of all bike objects. Also enables damaged bikes to be 
 * repaired and displays statistics based on the bikes stored 
 * in the controller's bikes list.
 */
public class BikeViewPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private Controller controller;
	
	
	private JTable bikeTable;
	private String[] columns = {"Bike ID", "Make", "Model", "Function", 
								"Demographic", "Colour", "Daily rate", "Status"};
	private String[][] data;
	private JScrollPane scrollPane;
	private DecimalFormat pound = new DecimalFormat("£###,###,#00.00");
	private JPanel bottomPanel;
	private JLabel totalLbl;
	private JLabel availableLbl;
	private JLabel onHireLbl;
	private JLabel damagedLbl;
	private JLabel total;
	private JLabel available;
	private JLabel onHire;
	private JLabel damaged;
	private JLabel mostPopularLbl;
	private JLabel mostPopular;
	private JLabel leastPopularLbl;
	private JLabel leastPopular;
	private JButton repairBtn;
	
	public BikeViewPanel()
	{
		controller = Controller.getInstance();
		
		setLayout(new BorderLayout());
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(2,9));
		
		totalLbl = new JLabel("Total bikes: ", SwingConstants.RIGHT);
		total = new JLabel();
		availableLbl = new JLabel("Available: ", SwingConstants.RIGHT);
		available = new JLabel();
		onHireLbl = new JLabel("On hire: ", SwingConstants.RIGHT);
		onHire = new JLabel();
		damagedLbl = new JLabel("Damaged: ", SwingConstants.RIGHT);
		damaged = new JLabel();
		mostPopularLbl = new JLabel("Most popular: ", SwingConstants.RIGHT);
		mostPopular = new JLabel();
		leastPopularLbl = new JLabel("Least popular: ", SwingConstants.RIGHT);
		leastPopular = new JLabel();
		repairBtn = new JButton("Repair bike");
		repairBtn.addActionListener(new ButtonListener());
		
		initPanel();
	}
	
	/*
	 * Initialises the panel to ensure all data is up-to-date.
	 */
	public void initPanel()
	{
		removeAll();
		bottomPanel.removeAll();
		
		drawTable();
		add(scrollPane, BorderLayout.CENTER);
		
		total.setText(Integer.toString(controller.getReport().getTotalBikes()));
		available.setText(Integer.toString(controller.getReport().getNoOfBikesAvailable()));
		onHire.setText(Integer.toString(controller.getReport().getNoOfBikesOnHire()));
		damaged.setText(Integer.toString(controller.getReport().getNoOfBikesDamaged()));
		mostPopular.setText(controller.getReport().getMostPopularBike());
		leastPopular.setText(controller.getReport().getLeastPopularBike());
		
		bottomPanel.add(totalLbl);
		bottomPanel.add(total);
		bottomPanel.add(availableLbl);
		bottomPanel.add(available);
		bottomPanel.add(new JLabel(""));
		bottomPanel.add(new JLabel(""));
		bottomPanel.add(mostPopularLbl);
		bottomPanel.add(mostPopular);
		bottomPanel.add(repairBtn);
		
		bottomPanel.add(onHireLbl);
		bottomPanel.add(onHire);
		bottomPanel.add(damagedLbl);
		bottomPanel.add(damaged);
		bottomPanel.add(new JLabel(""));
		bottomPanel.add(new JLabel(""));
		bottomPanel.add(leastPopularLbl);
		bottomPanel.add(leastPopular);
		bottomPanel.add(new JLabel(""));
		
		add(bottomPanel, BorderLayout.PAGE_END);
	}

	/*
	 * Draws the table with the appropriate dimensions.
	 */
	public void drawTable()
	{
		populateTable();
		bikeTable = new JTable(data, columns);
		scrollPane = new JScrollPane(bikeTable);
		bikeTable.setPreferredScrollableViewportSize(new Dimension(800, 200));
	}
	
	/*
	 * Populates the table with up-to-date data.
	 */
	private void populateTable()
	{
		data = new String[controller.getBikes().size()][8];
		
		int row = 0;

		while(row != controller.getBikes().size())
		{
			data[row][0] = controller.getBikes().get(row).getBikeID();
			data[row][1] = controller.getBikes().get(row).getMake();
			data[row][2] = controller.getBikes().get(row).getModel();
			data[row][3] = controller.getBikes().get(row).getFunction();
			data[row][4] = controller.getBikes().get(row).getDemographic();
			data[row][5] = controller.getBikes().get(row).getColour();
			data[row][6] = pound.format(controller.getBikes().get(row).getDailyRate());
			data[row][7] = controller.getBikes().get(row).getStatus();
			row++;
		}
	}
	
	/* 
	 * Listen for Repair bike button click.
	 * Prompt user for bike ID when button is pressed.
	 * Repair bike if damaged, alert user if not.
	 */
	private class ButtonListener implements ActionListener
	{

		JButton pressed;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			pressed = (JButton) e.getSource();

			if(pressed == repairBtn)
			{
				Bike bike = null;
				String bikeID = "";

				// Get valid customer ID
				while (bike == null)
				{
					bikeID = JOptionPane.showInputDialog("Please enter a bike ID:");
					
					if (bikeID == null)
						break;
					
					for (int i = 0; i < controller.getBikes().size(); i++)
						if (bikeID.compareToIgnoreCase(controller.getBikes().get(i).getBikeID()) == 0)
						{
							bike = controller.getBikes().get(i);
							
							if (bike.repairBike())
								JOptionPane.showMessageDialog(null, "Bike repaired.");
							else
								JOptionPane.showMessageDialog(null, "Bike is not damaged.");
							
							initPanel();
							break;
						}
					
					if (bike == null)
						JOptionPane.showMessageDialog(null, "Bike ID not recognised. Please try again.");
				}			
			}
			
		}


	}
}
