package view;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	// Attributes
	private int width = 1000, height = 800;
	private String titleName = "Virtual Machine";
	
	// Component
	private ConsolePanel consolePanel;
	private PrintPanel printPanel;
	private ProcessListPanel processListPanel;
	
	// Constructor
	public MainFrame() {
		// Set Attributes
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(this.width, this.height);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setTitle(this.titleName);
		this.getContentPane().setLayout(new GridLayout(2, 1));
		
		// Create Components
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1,2));
		this.processListPanel = new ProcessListPanel();
		panel1.add(this.processListPanel);
		this.consolePanel = new ConsolePanel();
		panel1.add(this.consolePanel);
		this.getContentPane().add(panel1);
		this.printPanel = new PrintPanel();
		this.getContentPane().add(this.printPanel);
	}
	
	public void initialize() {
		this.consolePanel.initialize(this.width, this.height);
		this.setVisible(true);
	}
	
	public void addContent(String content) {this.consolePanel.addContent(content);}
	public void print(String content) {this.printPanel.print(content);}
	public void addProcessID(String process, int id) {this.processListPanel.addProcessID(process, id);}
}
