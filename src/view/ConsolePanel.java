package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ConsolePanel extends JPanel {

	// Attributes
	private Color bgColor = Color.LIGHT_GRAY;
	private int maxContentNum = 40;
	private int contentFontSize;
	private Font font;
	private JList<String> list;
	private DefaultListModel<String> stringList;

	// Working Variable
	private int contentIndex = 0;

	// Constructor
	public ConsolePanel() {
		// Set Attributes
		this.setBackground(this.bgColor);

		// Create Component
		this.stringList = new DefaultListModel<String>();
		this.list = new JList<String>(this.stringList);
		this.setLayout(new BorderLayout());

		JLabel label = new JLabel("Console");
		label.setHorizontalAlignment(JLabel.CENTER);
		this.add(label, BorderLayout.NORTH);
	}

	public void initialize(int frameW, int frameH) {
		this.contentFontSize = frameH / this.maxContentNum;
		this.font = new Font(null, Font.PLAIN, this.contentFontSize);
		this.list.setFont(font);
		JScrollPane pane = new JScrollPane(this.list);
		pane.setPreferredSize(new Dimension(frameW, frameH - 50));
		pane.setAutoscrolls(true);
		this.add(pane);
	}

	public void addContent(String content) {
		this.contentIndex++;
		this.stringList.addElement(this.contentIndex + " ) " + content);
	}
}
