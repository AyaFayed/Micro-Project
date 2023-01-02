package components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import components.CPU;

public class MainGUI extends JFrame implements ActionListener {
	JPanel mainPanel;
	
	
	// init text fields
	TextField addLatency;
	TextField subLatency;
	TextField mulLatency;
	TextField divLatency;
	
	
	public static void main(String[] args) {
		new MainGUI();
	}

	public MainGUI() {
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("Micro Project");
		setMinimumSize(new Dimension(500, 500));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		init();
	}

	void init() {
		this.getContentPane().removeAll();
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(7, 7));
		for(int i = 0; i < 24; i++) {
			mainPanel.add(new JPanel());
		}
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(4, 2));
		JLabel addLatencyLabel = new JLabel("Add Latency");
		addLatencyLabel.setFont(new Font("",Font.BOLD,20));
		addLatency = new TextField();
		addLatency.setFont(new Font("",Font.BOLD,20));
		
		JLabel subLatencyLabel = new JLabel("Sub Latency");
		subLatencyLabel.setFont(new Font("",Font.BOLD,20));
		subLatency = new TextField();
		subLatency.setFont(new Font("",Font.BOLD,20));
		
		JLabel mulLatencyLabel = new JLabel("Mul Latency");
		mulLatencyLabel.setFont(new Font("",Font.BOLD,20));
		mulLatency = new TextField();
		mulLatency.setFont(new Font("",Font.BOLD,20));
		
		JLabel divLatencyLabel = new JLabel("Div Latency");
		divLatencyLabel.setFont(new Font("",Font.BOLD,20));
		divLatency = new TextField();
		divLatency.setFont(new Font("",Font.BOLD,20));
		
		center.add(addLatencyLabel);
		center.add(addLatency);
		
		center.add(subLatencyLabel);
		center.add(subLatency);
		
		center.add(mulLatencyLabel);
		center.add(mulLatency);
		
		center.add(divLatencyLabel);
		center.add(divLatency);
		mainPanel.add(center, BorderLayout.CENTER);
		
		for(int i = 0; i < 24; i++) {
			if(i == 6) {
				JButton start = new JButton("start");
				start.setFont(new Font("",Font.BOLD,20));
				JPanel p = new JPanel(new GridLayout(3, 1));
				p.add(new JPanel());
				p.add(start);
				start.addActionListener(this);
				mainPanel.add(p);
				continue;
			}
			mainPanel.add(new JPanel());
		}
		
		this.add(mainPanel);
		this.repaint();
		this.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("start")) {
			CPU.getInstance().setAddLatency(Integer.parseInt(addLatency.getText()));
			CPU.getInstance().setMulLatency(Integer.parseInt(mulLatency.getText()));
			CPU.getInstance().setDivLatency(Integer.parseInt(divLatency.getText()));
			CPU.getInstance().setSubLatency(Integer.parseInt(subLatency.getText()));
			this.getContentPane().removeAll();
		}
		this.repaint();
		this.revalidate();
	}
}
