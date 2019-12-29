package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ProcessListPanel extends JPanel {
	private DefaultListModel<String> listmodel;
	
	public ProcessListPanel() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		this.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Process ID List");
		label.setHorizontalAlignment(JLabel.CENTER);
		this.add(label, BorderLayout.NORTH);
		this.listmodel = new DefaultListModel<>();
		JList<String> list = new JList<>(this.listmodel);
		list.setFont(new Font("SansSerif", Font.PLAIN, 20));
		JScrollPane pane = new JScrollPane(list);
		this.add(pane, BorderLayout.CENTER);
	}
	
	public void addProcessID(String dir, int id) {
		this.listmodel.addElement("Process ID: "+id+", Directory: "+dir);
	}

}
