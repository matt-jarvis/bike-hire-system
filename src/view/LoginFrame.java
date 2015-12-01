package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/*
 * Displays a login window and prompts for a username
 * and password. 
 * 
 * Username:	user
 * Password:	pass
 */
public class LoginFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JPasswordField passField;
	private JTextField userField;
	private JButton signInBtn;

	public LoginFrame() 
	{	
		setTitle("Northampton Bike Hire");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 340, 180);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel();
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		
		JLabel headerLbl = new JLabel("Please enter your login details:", SwingConstants.CENTER);
		headerPanel.add(headerLbl, BorderLayout.NORTH);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(3, 2));
		loginPanel.setBorder(new EmptyBorder(0, 20, 20, 70));
		mainPanel.add(loginPanel, BorderLayout.CENTER);
		
		JLabel userLbl = new JLabel("Username:", SwingConstants.RIGHT);
		JLabel passLbl = new JLabel("Password:", SwingConstants.RIGHT);
		userField = new JTextField(15);
		passField = new JPasswordField(15);
		signInBtn = new JButton("Sign in");
		signInBtn.addActionListener(new ButtonListener());
		
		loginPanel.add(userLbl);
		loginPanel.add(userField);
		loginPanel.add(passLbl);
		loginPanel.add(passField);
		loginPanel.add(new JLabel(""));
		loginPanel.add(signInBtn);

		setResizable(false);
		setVisible(true);
	}

	/*
	 * Processes user input when the Submit button is clicked.
	 */
	private class ButtonListener implements ActionListener
	{

		JButton pressed;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			pressed = (JButton) e.getSource();

			if(pressed == signInBtn)
			{
				String username = userField.getText();
				String password = new String(passField.getPassword());

				// Grant access to the application only if the username & password is correct
				if(username.equals("user") && password.equals("pass"))
				{
					// Grant access
					new AppFrame();
					dispose();
				}
				else	
				{
					// Deny access
					JOptionPane.showMessageDialog(null, "Login details incorrect. Please try again.");
				}
			}

		}

	}
}
