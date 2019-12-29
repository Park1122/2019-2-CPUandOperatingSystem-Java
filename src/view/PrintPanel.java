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
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class PrintPanel extends JPanel {
	private DefaultListModel<String> listmodel;
	
	public PrintPanel() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		this.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Printer");
		label.setHorizontalAlignment(JLabel.CENTER);
		this.add(label, BorderLayout.NORTH);
		this.listmodel = new DefaultListModel<>();
		JList<String> list = new JList<>(this.listmodel);
		list.setFont(new Font("SansSerif", Font.PLAIN, 15));
		JScrollPane pane = new JScrollPane(list);
		this.add(pane, BorderLayout.CENTER);
	}
	
	public void print(String content) {
//		this.listmodel.addElement(content);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				listmodel.addElement(content);
			}
		});
	}

}
