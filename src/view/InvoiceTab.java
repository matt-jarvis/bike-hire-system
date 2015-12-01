package view;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/*
 * A tabbed pane that allows the user to access the 
 * sections concerned with viewing invoices.
 */
public class InvoiceTab extends JTabbedPane
{

	private static final long serialVersionUID = 1L;
	
	private JPanel invoiceOut;
	private JPanel invoiceIn; 

	public InvoiceTab()
	{
		super(JTabbedPane.TOP);
		
		invoiceOut = new InvoiceOutPanel();
		addTab("Out", null, invoiceOut, null);

		invoiceIn = new InvoiceInPanel();
		addTab("In", null, invoiceIn, null);
	}
	
	/*
	 * Required by the AppFrame object that contains 
	 * this tabbed pane.
	 * 
	 * Intends to update invoice table to reflect any new 
	 * invoices added every time the user clicks on the 
	 * invoice tab.
	 */
	public void refreshTab()
	{
		((InvoiceOutPanel) invoiceOut).initTable();
		((InvoiceInPanel) invoiceIn).initTable();
	}
}
