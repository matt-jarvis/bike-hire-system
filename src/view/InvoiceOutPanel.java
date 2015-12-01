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
 * of all InvoiceOut objects.
 */
public class InvoiceOutPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private Controller controller;

	private JTable invoiceOutTable;
	private String[] columns = {"Invoice ID", "Hire ID", "Customer ID", 
								"Bike ID", "Bike cost", "Deposit", "Total"};
	private String[][] data;
	private JScrollPane scrollPane;
	private final DecimalFormat POUND = new DecimalFormat("£###,###,#00.00");

	public InvoiceOutPanel()
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
		invoiceOutTable = new JTable(data, columns);
		invoiceOutTable.setPreferredScrollableViewportSize(new Dimension(800, 200));
		scrollPane = new JScrollPane(invoiceOutTable);
	}

	/*
	 * Populates the table with up-to-date data.
	 */
	private void populateTable()
	{
		data = new String[controller.getInvoicesOut().size()][7];

		int row = 0;

		while(row != controller.getInvoicesOut().size())
		{
			data[row][0] = controller.getInvoicesOut().get(row).getInvoiceOutID();
			data[row][1] = controller.getInvoicesOut().get(row).getHire().getHireID();
			data[row][2] = controller.getInvoicesOut().get(row).getHire().getCustomer().getCustomerID();
			data[row][3] = controller.getInvoicesOut().get(row).getHire().getBike().getBikeID();
			data[row][4] = POUND.format(controller.getInvoicesOut().get(row).getHire().getCost());
			data[row][5] = POUND.format(controller.getInvoicesOut().get(row).getHire().getBike().getDeposit());
			data[row][6] = POUND.format(controller.getInvoicesOut().get(row).getTotal());
			row++;
		}
	}
}

