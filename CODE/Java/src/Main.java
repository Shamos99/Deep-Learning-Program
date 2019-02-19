import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Main {

	private FreeDraw drawWindow = new FreeDraw();
	private BottomPanel bottompanel = new BottomPanel();
	private JFrame mainFrame = new JFrame();

	public Main() {

		mainFrame.setResizable(false); // not gonna lie, this woulda made my life easy if i'd known earlier

		bottompanel.link(this); // link bottom panel to inform for button clicks

		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(drawWindow, BorderLayout.NORTH);
		mainFrame.add(bottompanel, BorderLayout.SOUTH);
		mainFrame.setTitle("HandWriting Recognition");
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void saveImage() throws IOException {

		String letter = this.bottompanel.getText();

		if (drawWindow.hasDrawn() && letter.length() > 0) {

			// Create file to store image data
			String cmd = System.getProperty("user.home") + "/Desktop/" + "MOI/Deep Learning/data.txt";
			File file = new File(cmd);
			file.createNewFile();

			int data[][] = drawWindow.getImage();
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true)); // append mode

			if (file.length() != 0) {
				out.write("&"); // just to seperate each image
			}

			for (int x = 0; x < data.length; x++) {
				for (int y = 0; y < data.length; y++) {
					out.write(Integer.toString(data[x][y]));
					if (y != (data.length - 1)) {
						out.write(" ");
					}
				}
				if (x != (data.length - 1)) {
					out.newLine();
				}
			}
			out.close();

			// Store answer

			cmd = System.getProperty("user.home") + "/Desktop/" + "MOI/Deep Learning/letter.txt";
			file = new File(cmd);
			BufferedWriter out2 = new BufferedWriter(new FileWriter(file, true)); // append mode
			out2.write(letter);
			out2.newLine();
			out2.close();
			
			JOptionPane.showMessageDialog(mainFrame, "Saved!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
			
		} else {
			// Do the Alert thing
			JOptionPane.showMessageDialog(mainFrame, "Bitch, draw or write a letter!", "Fuck You",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void upDateModel() throws IOException {
		
		JDialog dialog = this.unclosableDialog("Wait Bitch", "My AI likes to take its time");
			//loading screen if you will
		
		String cmd = System.getProperty("user.home") + "/Desktop/" + "MOI/Deep Learning/data.txt";
		File file = new File(cmd);
		
		if(file.exists()) {
			dialog.setVisible(true);
			
			String path = System.getProperty("user.home") + "/Desktop/" + "MOI/Deep Learning/TrainAlphabetModel.py";
			String python = "/Library/Frameworks/Python.framework/Versions/3.7/bin/python3";
			ProcessBuilder pb = new ProcessBuilder(python,path);
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String ret = new String(in.readLine());	
			System.out.println(ret);
			
			dialog.dispose();
			
			JOptionPane.showMessageDialog(mainFrame, "Learning Complete!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
			
		}
		else {
			//error window
			// Do the Alert thing
						JOptionPane.showMessageDialog(mainFrame,"Save something first!", "Fuck You",
								JOptionPane.WARNING_MESSAGE);
		}
		
	}

	public void clearScreen() {
		drawWindow.clear();
	}

	public void testQuery() throws IOException, InterruptedException {
		
		JDialog dialog = this.unclosableDialog("Wait Bitch", "My AI likes to take its time");
		

		String letter = this.bottompanel.getText();

		if (drawWindow.hasDrawn()) {
			
			dialog.setVisible(true);	//loading screen if you will
			
			int data[][] = drawWindow.getImage();
			StringBuilder str = new StringBuilder();
			
			for(int x=0;x<data.length;x++) {
				for(int y=0;y<data.length;y++) {
					str.append(data[x][y]);
					str.append(",");
				}
			}
			str.deleteCharAt(str.length()-1);
			
			String path = System.getProperty("user.home") + "/Desktop/" + "MOI/Deep Learning/AlphabetModel.py";
			String python = "/Library/Frameworks/Python.framework/Versions/3.7/bin/python3";
			ProcessBuilder pb = new ProcessBuilder(python,path,str.toString());
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String ret = new String(in.readLine());	
			System.out.println(ret);
			
			dialog.dispose();
			
			//show the letter
			JOptionPane.showMessageDialog(mainFrame, "AI guessed: " + ret, "My AI",
					JOptionPane.WARNING_MESSAGE);
			
		} else {
			// Do the Alert thing
			JOptionPane.showMessageDialog(mainFrame, "Bitch, draw a letter!", "Fuck You",
					JOptionPane.WARNING_MESSAGE);	
		}
	}
	
	private JDialog unclosableDialog(String title,String message) {

		JOptionPane op = new JOptionPane(message,JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,null,new Object[] {},null); 
		JDialog dialog = new JDialog();
		dialog.setTitle(title);
		//dialog.setModal(true);
		dialog.setContentPane(op);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();
		return dialog;
	}
	

	public static void main(String args[]) throws IOException {
		Main obj = new Main();
		
		
	}
}
