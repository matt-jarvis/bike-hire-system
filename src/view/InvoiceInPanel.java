package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.DecimalFormat;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import controller.Controller;

/*
 * A panel that contains a table populated with the details 
 * of all InvoiceIn objects.
 */
public class InvoiceInPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private Controller controller;

	private JTable invoiceInTable;
	private String[] columns = {"Invoice ID", "Hire ID", "Customer ID", 
								"Bike ID", "Days late", "Late fee", "Bike damaged?"};
	private String[][] data;
	private JScrollPane scrollPane;
	private final DecimalFormat POUND = new DecimalFormat("£###,###,#00.00");

	public InvoiceInPanel()
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
		invoiceInTable = new JTable(data, columns);
		invoiceInTable.setPreferredScrollableViewportSize(new Dimension(800, 200));
		scrollPane = new JScrollPane(invoiceInTable);
	}

	/*
	 * Populates the table with up-to-date data.
	 */
	private void populateTable()
	{
		data = new String[controller.getInvoicesIn().size()][7];

		int row = 0;

		while(row != controller.getInvoicesIn().size())
		{
			data[row][0] = controller.getInvoicesIn().get(row).getInvoiceInID();
			data[row][1] = controller.getInvoicesIn().get(row).getHire().getHireID();
			data[row][2] = controller.getInvoicesIn().get(row).getHire().getCustomer().getCustomerID();
			data[row][3] = controller.getInvoicesIn().get(row).getHire().getBike().getBikeID();
			data[row][4] = Integer.toString(controller.getInvoicesIn().get(row).getHire().getDaysLate());
			data[row][5] = POUND.format(controller.getInvoicesIn().get(row).getLateFee());
			
			if (controller.getInvoicesIn().get(row).wasBikeDamaged())
				data[row][6] = "yes";
			else
				data[row][6] = "no";
			row++;
		}
	}
}

