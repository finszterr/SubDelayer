package delayer;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.AbstractDocument;
import tabPolicy.FocusTraversalPolicy;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class SubDelay extends JFrame implements FocusTraversalPolicy {

	public static Path absolutePath;

	private static JTextField hoursTextField;
	private static JTextField minutesTextField;
	private static JTextField secondsTextField;
	private static JTextField millisecondsTextField;

	public static int delay = 0;
	static String username = System.getProperty("user.name");

	public static void createAndShowGUI() {
		
		// Set native look of GUI
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e3) {
			e3.printStackTrace();
		} catch (InstantiationException e3) {
			e3.printStackTrace();
		} catch (IllegalAccessException e3) {
			e3.printStackTrace();
		} catch (UnsupportedLookAndFeelException e3) {
			e3.printStackTrace();
		}

		// Frame settings
		JFrame f = new JFrame("SubDelayer");
		f.setLayout(null);
		f.setSize(538, 239);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.getContentPane().setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set JFrame title / taskbar icon
		try {
			URL resource = f.getClass().getResource("/cc.png");
			BufferedImage image = ImageIO.read(resource);
			f.setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Choose the srt file
		JButton chooseButton = new JButton("Choose file...");
		chooseButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chooseButton.setBounds(37, 8, 110, 30);
		chooseButton.setVisible(true);
		f.getContentPane().add(chooseButton);

		JLabel fileChooseLabel = new JLabel("...");
		fileChooseLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fileChooseLabel.setBounds(157, 16, 365, 14);
		f.getContentPane().add(fileChooseLabel);

		JLabel destinationPathLabel = new JLabel();
		destinationPathLabel.setText("...");

		JButton syncButton = new JButton("Synchronize the subtitle");
		syncButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		syncButton.setBounds(0, 177, 538, 34);
		f.getContentPane().add(syncButton);

		chooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				absolutePath = Paths.get(Chooser.chooseSubtitle(fileChooseLabel, username, syncButton));
				//Change the default destination path to the same as the old subtitle's path.
				Chooser.setDestinationPathLabel(destinationPathLabel, absolutePath);				
			}
		});

		// Radio buttons
		JRadioButton addRadioButton = new JRadioButton("Add delay");
		addRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addRadioButton.setSelected(true);
		addRadioButton.setBounds(151, 41, 109, 23);
		f.getContentPane().add(addRadioButton);

		JRadioButton removeRadioButton = new JRadioButton("Remove delay");
		removeRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		removeRadioButton.setBounds(262, 41, 113, 23);
		f.getContentPane().add(removeRadioButton);

		// If you want that after the first radiobutton the tab set the focus
		// to the another radiobutton(s) then ButtonGoup shouldn't be used.
		// To navigate between radiobutton the user have to use arrow keys
		// instead of TAB key.
		ButtonGroup delayRadioGroup = new ButtonGroup();
		delayRadioGroup.add(addRadioButton);
		delayRadioGroup.add(removeRadioButton);

		JLabel hoursLabel = new JLabel("Hour(s)");
		hoursLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		hoursLabel.setBounds(10, 83, 60, 25);
		f.getContentPane().add(hoursLabel);

		hoursTextField = new JTextField();
		hoursTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		hoursTextField.setBounds(61, 83, 50, 25);
		f.getContentPane().add(hoursTextField);
		hoursTextField.setColumns(10);

		// Check values are numeric and within 0-100
		AbstractDocument docH = (AbstractDocument) hoursTextField.getDocument();
		docH.setDocumentFilter(new RestrictedNumberDocumentFilter(0, 100));

		JLabel minutesLabel = new JLabel("Minute(s)");
		minutesLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		minutesLabel.setBounds(120, 83, 60, 25);
		f.getContentPane().add(minutesLabel);

		minutesTextField = new JTextField();
		minutesTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		minutesTextField.setColumns(10);
		minutesTextField.setBounds(192, 83, 50, 25);
		f.getContentPane().add(minutesTextField);

		// Check values are numeric and within 0-100
		AbstractDocument docM = (AbstractDocument) minutesTextField.getDocument();
		docM.setDocumentFilter(new RestrictedNumberDocumentFilter(0, 100));

		JLabel secondsLabel = new JLabel("Second(s)");
		secondsLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		secondsLabel.setBounds(252, 83, 60, 25);
		f.getContentPane().add(secondsLabel);

		secondsTextField = new JTextField();
		secondsTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		secondsTextField.setColumns(10);
		secondsTextField.setBounds(322, 83, 50, 25);
		f.getContentPane().add(secondsTextField);

		// Check values are numeric and within 0-100
		AbstractDocument docS = (AbstractDocument) secondsTextField.getDocument();
		docS.setDocumentFilter(new RestrictedNumberDocumentFilter(0, 100));

		JLabel millisecondsLabel = new JLabel("Milliseconds(s)");
		millisecondsLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		millisecondsLabel.setBounds(382, 83, 80, 25);
		f.getContentPane().add(millisecondsLabel);

		millisecondsTextField = new JTextField();
		millisecondsTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		millisecondsTextField.setColumns(10);
		millisecondsTextField.setBounds(472, 83, 50, 25);
		f.getContentPane().add(millisecondsTextField);

		// Check values are numeric and within 0-100
		AbstractDocument docMilli = (AbstractDocument) millisecondsTextField.getDocument();
		docMilli.setDocumentFilter(new RestrictedNumberDocumentFilter(0, 1000));

		JTextField[] tf = { hoursTextField, minutesTextField, secondsTextField, millisecondsTextField };
		setTextFieldsAcceptOnlyNumbers(tf);

		JLabel destinationLabel = new JLabel("Destination folder:");
		destinationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		destinationLabel.setBounds(10, 120, 110, 25);
		f.getContentPane().add(destinationLabel);

		destinationPathLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		destinationPathLabel.setBounds(120, 121, 380, 25);
		f.getContentPane().add(destinationPathLabel);

		JButton changeDestinationButton = new JButton("Change");
		changeDestinationButton.setBounds(20, 142, 89, 23);
		f.getContentPane().add(changeDestinationButton);

		JLabel milliToSecsLabel = new JLabel("(1 sec = 1000 millisec)");
		milliToSecsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		milliToSecsLabel.setFont(new Font("Tahoma", Font.ITALIC, 12));
		milliToSecsLabel.setBounds(382, 114, 140, 14);
		f.getContentPane().add(milliToSecsLabel);

		// Set the destination folder
		changeDestinationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Chooser.changeDestination(destinationPathLabel);				
			}
		});

		syncButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delay = Synchroniser.getDelay(millisecondsTextField, minutesTextField, secondsTextField,
						hoursTextField);
				Synchroniser.synchronizeSubtitle(syncButton, removeRadioButton, delay, absolutePath);
			}
		});

		// Set the frame visible
		f.setVisible(true);

	}
	
	public static void setTextFieldsAcceptOnlyNumbers(JTextField[] tf) {
		// Add Eventlistener to the JTextFields to accept only nums
		for (int i = 0; i < tf.length; i++) {
			tf[i].addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					// filter out non numeric characters (except: Backspace, Delete keys)
					if (!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)) {
						e.consume();
					}
				}
			});
		}
	}
	
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}
}
