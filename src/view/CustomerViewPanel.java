package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import controller.Controller;

/*
 * A panel that contains a table populated with the details 
 * of all customer objects.
 */
public class CustomerViewPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	
	private JTable customerTable;
	private String[] columns = {"Customer ID", "First name",
			"Last name", "Line 1", "Town", "Postcode"};
	private String[][] data;
	private JScrollPane scrollPane;
	
	
	public CustomerViewPanel()
	{
		controller = Controller.getInstance();

		setLayout(new BorderLayout());
		initTable();
	}
	
	/*
	 * Initialises the table to ensure all data is up-to-date.
	 */
	public void initTable()
	{
		removeAll();
		drawTable();
		add(scrollPane, SwingConstants.CENTER);
	}
	
	/*
	 * Draws the table with the appropriate dimensions.
	 */
	public void drawTable()
	{
		populateTable();
		customerTable = new JTable(data, columns);
		scrollPane = new JScrollPane(customerTable);
		customerTable.setPreferredScrollableViewportSize(new Dimension(800, 200));
	}
	
	/*
	 * Populates the table with up-to-date data.
	 */
	private void populateTable()
	{
		data = new String[controller.getCustomers().size()][6];
		
		int row = 0;

		while(row != controller.getCustomers().size())
		{
			data[row][0] = controller.getCustomers().get(row).getCustomerID();
			data[row][1] = controller.getCustomers().get(row).getFirstName();
			data[row][2] = controller.getCustomers().get(row).getLastName();
			data[row][3] = controller.getCustomers().get(row).getAddressLine1();
			data[row][4] = controller.getCustomers().get(row).getAddressTown();
			data[row][5] = controller.getCustomers().get(row).getAddressPostcode();
			row++;
		}
	}
}
