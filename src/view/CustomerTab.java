package view;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * A tabbed pane that allows the user to access the 
 * sections concerned with creating and viewing 
 * customers.
 */
public class CustomerTab extends JTabbedPane
{

	private static final long serialVersionUID = 1L;
	
	private JPanel create; 
	private JPanel view;

	public CustomerTab()
	{
		super(JTabbedPane.TOP);

		create = new CustomerCreatePanel();
		addTab("Create", null, create, null);

		view = new CustomerViewPanel();
		addTab("View", null, view, null);

		// Update data to reflect changes made when user clicks on the view tab
		addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent ce) 
			{
				if (getSelectedIndex() == 1)
					((CustomerViewPanel) view).initTable();
			}	
		});
	}

}
