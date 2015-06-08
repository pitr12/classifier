package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SpringLayout.Constraints;
import javax.swing.border.EmptyBorder;

import java.awt.Component;

public class Instructions extends JFrame {

	private JPanel contentPane;
	private static Font globalPlainFont = new Font("Courier 10 Pitch", Font.PLAIN, 24);

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Instructions frame = new Instructions();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Instructions() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("Categories");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(15, 2));
		contentPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JLabel label1 = new JLabel("Technology");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Mystery");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Science");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Catastrophic");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Nature");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Animals");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Geography");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Adventure");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Environment");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Traveling");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Health");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Drugs");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Economics");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Crime");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Politics");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Biography");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Society");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Religion");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Culture");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Psychology");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Philosophy");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Art and Artists");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("History");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Conspiracy");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Military and War");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Media");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Comedy");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Housing");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("Sports");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
		
		label1 = new JLabel("I can't do this");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(globalPlainFont);
		contentPane.add(label1);
				
		
	}

}
