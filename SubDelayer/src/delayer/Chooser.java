package delayer;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Chooser {
	public static String chooseSubtitle(JLabel fileChooseLabel, String username, JButton syncButton) {
		String absolutePath = "";

		// Set the sync button font color again to black
		syncButton.setForeground(Color.BLACK);

		// File chooser with default location
		@SuppressWarnings("serial")
		JFileChooser chooser = new JFileChooser(username){
			//Change default icon image
		    @Override
		    protected JDialog createDialog( Component parent ) throws HeadlessException {
		        JDialog dialog = super.createDialog( parent );
				URL resource = dialog.getClass().getResource("/cc.png");
				BufferedImage image;
				try {
					image = ImageIO.read(resource);
					dialog.setIconImage(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
		        return dialog;
		    }
		};

		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("SRT file", "srt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
			absolutePath = chooser.getSelectedFile().getAbsolutePath();
			String name = chooser.getSelectedFile().getName();
			// Change the label to the subtitle file
			fileChooseLabel.setText(name.toString());
		}

		return absolutePath;
	}

	public static void setDestinationPathLabel(JLabel destinationPathLabel, Path absolutePath) { 
		System.out.println("absolutePath " + absolutePath);
		String onlyPath = String.valueOf(absolutePath).substring(0, String.valueOf(absolutePath).lastIndexOf("\\")) + "\\";
		System.out.println("onlyPath " + onlyPath);
		destinationPathLabel.setText(String.valueOf(onlyPath));
	}

	public static void changeDestination(JLabel destinationPathLabel) {
		@SuppressWarnings("serial")
		JFileChooser chChange = new JFileChooser(destinationPathLabel.getText()){
			//Change default icon image
		    @Override
		    protected JDialog createDialog( Component parent ) throws HeadlessException {
		        JDialog dialog = super.createDialog( parent );
				URL resource = dialog.getClass().getResource("/cc.png");
				BufferedImage image;
				try {
					image = ImageIO.read(resource);
					dialog.setIconImage(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
		        return dialog;
		    }
		};
		chChange.setDialogTitle("Select destination folder");
		chChange.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (chChange.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println(chChange.getCurrentDirectory());
			System.out.println(chChange.getSelectedFile());

			destinationPathLabel.setText(chChange.getSelectedFile() + "");

		} else {
			System.out.println("No Selection ");
		}
	}

}
