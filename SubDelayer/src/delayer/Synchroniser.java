package delayer;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Synchroniser {
	public static void synchronizeSubtitle(JButton syncButton, JRadioButton removeRadioButton, int delay, Path absolutePath) {
		FileWriter writer = null;
		BufferedReader reader = null;
		int addOrRemoveDelay = 1;	
		boolean firstLine = true;
		String line;
		
		// Start buffered reader
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath.toFile()), "UTF-8"));
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		System.out.println("Create text file " + absolutePath);
		
		// Create text file
		int absolutePathLength = String.valueOf(absolutePath).length();
		File file = new File(
				String.valueOf(absolutePath).substring(0,absolutePathLength-4) + "_NEW.srt");
		
		try {
			if (file.createNewFile()){
				System.out.println("File is created!");
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e1) {
			System.out.println("File creation failed.");
		}
		 
		//Write Content		
		try {
			writer = new FileWriter(file);
		} catch (IOException e1) {
			System.out.println("Failed to write content.");
		}
		
		if (removeRadioButton.isSelected()) {
			addOrRemoveDelay = addOrRemoveDelay*(-1);
		} else {
			addOrRemoveDelay = 1;
		}

		delay = delay * addOrRemoveDelay; // 1 or -1
		
		try {
			while ((line = reader.readLine()) != null)
			{
//				SAMPLE SRT lines:
//					
//				1
//				00:00:06,540 --> 00:00:07,041
//				- There's a reason we met.
//
//				2
//				00:00:08,309 --> 00:00:09,443
//				There's something between us.
				
				// hh:mm:ss,lll
				
			    if (Pattern.compile("\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d").matcher(line).find()) {
				    // Remove the : and , characters from the srt time value --> create an int number
					TimecodeToMillis t = new TimecodeToMillis();
			    	
			    	String startline = line.substring(0, 12);
			        String startLineModified = t.changeTimecode(startline, delay);
				    
			        String endline = line.substring(17, 29);
			        String endLineModified = t.changeTimecode(endline, delay);
				    
			        writer.write(startLineModified);
				    writer.write(" --> ");
				    writer.write(endLineModified);
				    
				    System.out.print(startLineModified);
				    System.out.print(" --> ");
				    System.out.println(endLineModified);
				    writer.write("\r\n");
				    
				    // After successful writing change text color to green
				    syncButton.setForeground(new Color(0, 153, 0));
				    
			    } else {
					System.out.println(line);
					
					// Fix the first line ?1 bug (only in the file, not in sysout)
					if (firstLine) {
						writer.write("1");
						writer.write("\r\n");	
						firstLine = false;
					} else {
						writer.write(line);
						writer.write("\r\n");
					}

			    }


			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Close writer
		try {
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		firstLine = true;
	}
	//TODO: save the right pace the NEW file!

	public static int getDelay(
			JTextField millisecondsTextField, JTextField minutesTextField, JTextField secondsTextField, JTextField hoursTextField) {
		int millisDelay = 0;
		int secsDelay = 0;
		int minsDelay = 0;
		int hoursDelay = 0;
		int delay = 0;

		if (!millisecondsTextField.getText().equals("")) {
			try {
				millisDelay = Integer.parseInt(millisecondsTextField.getText());
			} catch (Exception e) {
				millisDelay = 0;
			}
		} else {
			millisDelay = 0;
		}
		
		if (!minutesTextField.getText().equals("")) {
			try {
				minsDelay = Integer.parseInt(minutesTextField.getText());
			} catch (Exception e) {
				minsDelay = 0;
			}
		} else {
			minsDelay = 0;
		}

		if (!secondsTextField.getText().equals("")) {
			try {
				secsDelay = Integer.parseInt(secondsTextField.getText());
			} catch (Exception e) {
				secsDelay = 0;
			}
		} else {
			secsDelay = 0;
		}
		
		if (!hoursTextField.getText().equals("")) {			
			try {
				hoursDelay = Integer.parseInt(hoursTextField.getText());
			} catch (Exception e) {
				hoursDelay = 0;
			}			
		} else {
			hoursDelay = 0;
		}
		
		delay = millisDelay
				+ secsDelay*1000 
				+ minsDelay*60000 
				+ hoursDelay*3600000;
		return delay;
	}
}
