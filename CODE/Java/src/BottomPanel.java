import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BottomPanel extends JPanel {

	private JButton savebtn = new JButton("Save");
	private JButton learnbtn = new JButton("Learn!");
	private JButton testbtn = new JButton("Test!");
	private JTextField txt = new JTextField(10);
	private JButton refreshbtn = new JButton("Refresh");
	private Main link;
	
	public BottomPanel() {
		
		savebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					link.saveImage();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		learnbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					link.upDateModel();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		testbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					link.testQuery();
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		refreshbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				link.clearScreen();
				txt.setText("");
			}
			
		});
		
		this.add(txt);
		this.add(savebtn);
		this.add(testbtn);
		this.add(refreshbtn);
		this.add(learnbtn);
		this.setOpaque(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.setVisible(true);
	}
	
	public void link(Main fd) {
		this.link = fd;
	}
	
	public String getText() {
		return txt.getText().toString();
	}

}