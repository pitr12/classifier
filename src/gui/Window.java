package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import org.json.simple.parser.ParseException;

import work.Classifier;

public class Window extends JFrame {
	
	//fonts
	private static Font globalBoldFont = new Font("Courier 10 Pitch", Font.BOLD, 24);
	private static Font MsgFont = new Font("Courier 10 Pitch", Font.BOLD, 20);
	private static Font globalPlainFont = new Font("Courier 10 Pitch", Font.PLAIN, 24);
	private static Font categorySmallLabelFont = new Font("Courier 10 Pitch", Font.PLAIN,16);
	
	//panels
	private JPanel contentPane;
	private JPanel leftPane;
	private static JPanel rightPane;
	private JPanel imdbTitle;
	private JPanel csfdTitle;
	private JPanel description;
	private JPanel footer;
	
	//labels
	private JLabel imdbTitleLabel;
	private JLabel csfdTitleLabel;
	private JLabel descriptionLabel;
	private JLabel id;
	
	//buttons
	private JButton next;
	
	//araylists
	private static int[] states = new int [Classifier.CATEGORY_COUNT];
	private ArrayList<JLabel> imdbTitleList = new ArrayList<JLabel>();
	private ArrayList<JLabel> csfdTitleList = new ArrayList<JLabel>();
	private ArrayList<JLabel> descriptionList = new ArrayList<JLabel>();
	private static ArrayList<JRadioButton> group = new ArrayList<JRadioButton>();
	private static ArrayList<JLabel> descritons = new ArrayList<JLabel>();
	
	private static String name;
	
	public static void  changeButtonState(int i){
		switch(states[i]){
		case 0: states[i] = 1;
				group.get(i).setSelected(true);
				descritons.get(i).setText("Primary");
				break;
		case 1: states[i] = 2;
				group.get(i).setSelected(true);
				descritons.get(i).setText("Secondary");
				break;
		case 2: states[i] = 0;
				group.get(i).setSelected(false);
				descritons.get(i).setText("");
				break;
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public static void constructCategory(String name, final int index){
		JPanel panel = new JPanel();
		GridLayout categoryLayout = new GridLayout(2,1);
		panel.setLayout(categoryLayout);
		
		JRadioButton button = new JRadioButton(name);
		button.setFont(globalPlainFont);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeButtonState(index);
				Classifier.log.println(Classifier.getTime() + " Clicked radio button - category: " + group.get(index).getText());
				String new_state;
				switch(states[index]){
				case 0: new_state = "deselected"; break;
				case 1: new_state = "primary category"; break;
				case 2: new_state = "secondary category"; break;
				default: new_state = "";
				}
				Classifier.log.println(Classifier.getTime() + " category " +group.get(index).getText() + " set as " +new_state);
			}
		});
		group.add(button);
		panel.add(button);
		
		JLabel state = new JLabel("");
		state.setFont(categorySmallLabelFont);
		descritons.add(state);
		panel.add(state);
		
		rightPane.add(panel);
		rightPane.revalidate();
		rightPane.repaint();
	}
	
	public static String getPrimaryCategory(){
		for(int i=0; i<Classifier.CATEGORY_COUNT; i++){
			if(states[i] == 1){
				return group.get(i).getText();
			}
		}
		return null;
	}
	
	public static String getSecondaryCategory(){
		for(int i=0; i<Classifier.CATEGORY_COUNT; i++){
			if(states[i] == 2){
				return group.get(i).getText();
			}
		}
		return null;
	}
	
	public void clearContent(){
		imdbTitleList.removeAll(imdbTitleList);
		csfdTitleList.removeAll(csfdTitleList);
		descriptionList.removeAll(descriptionList);
		
		imdbTitle.removeAll();
		csfdTitle.removeAll();
		description.removeAll();
		
		deselectButtons();
	}
	
	public void deselectButtons(){
		for(int i=0; i<Classifier.CATEGORY_COUNT; i++){
			final int j = i;
			states[j] = 0;
			group.get(j).setSelected(false);
			descritons.get(j).setText("");
		}
	}
	
	public void setId(String new_id){
		String text = "ID: " +new_id;
		id.setText(text);
	}
	
	public void setImdbTitle(String[] title) {
		int size = title.length;
		for(int i=0; i<size; i++){
			JLabel label = new JLabel(title[i]);
			label.setFont(globalPlainFont);
			label.setBorder(new EmptyBorder(5,5,5,5));
			imdbTitle.add(label);
			imdbTitleList.add(label);
		}
		
		imdbTitle.revalidate();
		imdbTitle.repaint();
	}
	
	public void setcsfdTitle(String[] title) {
		int size = title.length;
		for(int i=0; i<size; i++){
			JLabel label = new JLabel(title[i]);
			label.setFont(globalPlainFont);
			label.setBorder(new EmptyBorder(5,5,5,5));
			csfdTitle.add(label);
			csfdTitleList.add(label);
		}
		
		csfdTitle.revalidate();
		csfdTitle.repaint();
	}
	
	public void setDescription(String[] desc) {
		int size = desc.length;
		for(int i=0; i<size; i++){
			JLabel label = new JLabel(desc[i]);
			label.setFont(globalPlainFont);
			label.setBorder(new EmptyBorder(5,5,5,5));
			description.add(label);
			descriptionList.add(label);
		}
		
		description.revalidate();
		description.repaint();
	}
	
	public boolean checkPrimaryCategory(){
		int count = 0;
		for(Integer state : states){
			if(state == 1){
				count++;
			}
		}
		if (count == 1)
			return true;
		
		JOptionPane jopt = new JOptionPane();
	    String result;
	    result = "Please Select One Primary Category!";
	    JLabel resLabel = new JLabel(result);
	    resLabel.setFont(MsgFont);
	    jopt.showMessageDialog( null, resLabel, "Results", JOptionPane.PLAIN_MESSAGE );
	    
	    Classifier.log.println(Classifier.getTime() + " Displayed primary category message dialog warning");
		
		return false;
	}
	
	public boolean checkSecondaryCategory(){
		int count = 0;
		for(Integer state : states){
			if(state == 2){
				count++;
			}
		}
		if (count == 0 || count == 1)
			return true;
		
		JOptionPane jopt = new JOptionPane();
	    String result;
	    result = "Please Select Only One Secondary Category!";
	    JLabel resLabel = new JLabel(result);
	    resLabel.setFont(MsgFont);
	    jopt.showMessageDialog( null, resLabel, "Results", JOptionPane.PLAIN_MESSAGE );
	    
	    Classifier.log.println(Classifier.getTime() + " Displayed secondary category message dialog warning");
	    
		return false;
	}
	
	
	public boolean checkValidity(){
		if(checkPrimaryCategory() && checkSecondaryCategory()){
			return true;
		}
		return false;
	}
	
	public static void loadUserName(){
		String str = JOptionPane.showInputDialog("Please insert your name: ");
		name = str.replaceAll("\\s+","");
	}
	
	public void logPanelPostiions(){		
		Classifier.log.println("panel positions:");
		Classifier.log.println(this.getBounds() + " - frame");
		Classifier.log.println(contentPane.getBounds() + " - contentPane");
		Classifier.log.println(leftPane.getBounds() + " - leftPane");
		Classifier.log.println(rightPane.getBounds() + " - rightPane");
		Classifier.log.println(imdbTitle.getBounds() + " - imdbTitle panel");
		Classifier.log.println(csfdTitle.getBounds() + " - csfdTitle panel");
		Classifier.log.println(description.getBounds() + " - description panel");
		Classifier.log.println(footer.getBounds() + " - footer panel");
	}
	
	public void logButtonPositions(){
		Classifier.log.println(Classifier.getTime() + " Button positions:");
		Classifier.log.println(next.getBounds() + " - next");
	}
	
	public void logLabelPositions(){
		Classifier.log.println(Classifier.getTime() + " Label positions:");
		Classifier.log.println(imdbTitleLabel.getBounds() + " - imdb title label");
		Classifier.log.println(csfdTitleLabel.getBounds() + " - csfd title label");
		Classifier.log.println(descriptionLabel.getBounds() + " - description label");
		Classifier.log.println(id.getBounds() + " - id label");
	}
	
	public void logCategoryPositions(){
		Classifier.log.println(Classifier.getTime() + " Category positions:");
		rightPane.validate();
		
		for(int i=0; i<Classifier.CATEGORY_COUNT; i++){					
			Classifier.log.println(group.get(i).getBounds() + " - " + group.get(i).getText());
		}
	}
	
	public void logImdbTitle(){
		imdbTitle.validate();
		Classifier.log.println(Classifier.getTime() + " IMDB Title text positions:");
		int size = imdbTitleList.size();
		for(int i=0; i<size; i++){
			Classifier.log.println(imdbTitleList.get(i).getBounds() + " - " + imdbTitleList.get(i).getText());
		}
	}
	
	public void logcsfdTitle(){
		csfdTitle.validate();
		Classifier.log.println(Classifier.getTime() + " CSFD Title text positions:");
		int size = csfdTitleList.size();
		for(int i=0; i<size; i++){
			Classifier.log.println(csfdTitleList.get(i).getBounds() + " - " + csfdTitleList.get(i).getText());
		}
	}
	
	public void logDescription(){
		description.validate();
		Classifier.log.println(Classifier.getTime() + " Description text positions:");
		int size = descriptionList.size();
		for(int i=0; i<size; i++){
			Classifier.log.println(descriptionList.get(i).getBounds() + " - " + descriptionList.get(i).getText());
		}
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		
		//window main config
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(0, 0, 500, 500);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//create content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridLayout contenPaneLayout = new GridLayout(1,2);
		contenPaneLayout.setHgap(30);
		contentPane.setLayout(contenPaneLayout);
		setContentPane(contentPane);
		
		//create main left panel
		leftPane = new JPanel();
		leftPane.setLayout(new GridBagLayout());
		contentPane.add(leftPane);
		
		//create main right panel
		rightPane = new JPanel();
		rightPane.setBorder(new EmptyBorder(50, 30, 30, 0) );
		GridLayout rightPaneLayout = new GridLayout(0,2);
		rightPaneLayout.setHgap(20);
		rightPaneLayout.setVgap(20);
		rightPane.setLayout(rightPaneLayout);
		contentPane.add(rightPane);
		
		//imdbTitle
		imdbTitleLabel = new JLabel("IMDB title:");
		imdbTitleLabel.setFont(globalBoldFont);
		imdbTitleLabel.setBorder(new EmptyBorder(5,5,5,5));
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0; 
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.025;
		leftPane.add(imdbTitleLabel,c);
		
		imdbTitle = new JPanel();
		FlowLayout imdbTitleLayout = new FlowLayout(FlowLayout.LEFT);
		imdbTitle.setLayout(imdbTitleLayout);
		c.gridy = 1;
		c.weighty = 0.125;
		leftPane.add(imdbTitle,c);
		
		//csfdTitle
		csfdTitleLabel = new JLabel("CSFD title:");
		csfdTitleLabel.setBorder(new EmptyBorder(5,5,5,5));
		csfdTitleLabel.setFont(globalBoldFont);
		c.gridy = 2;
		c.weighty = 0.025;
		leftPane.add(csfdTitleLabel,c);
		
		csfdTitle = new JPanel();
		csfdTitle.setLayout(imdbTitleLayout);
		c.gridy = 3;
		c.weighty = 0.125;
		leftPane.add(csfdTitle, c);
		
		//description
		descriptionLabel = new JLabel("Description:");
		descriptionLabel.setBorder(new EmptyBorder(5,5,5,5));
		descriptionLabel.setFont(globalBoldFont);
		c.gridy = 4;
		c.weighty = 0.025;
		leftPane.add(descriptionLabel,c);
				
		description = new JPanel();
		description.setLayout(imdbTitleLayout);
		c.gridy = 5;
		c.weighty = 0.65;
		leftPane.add(description, c);
		
		//left panel footer
		footer = new JPanel();
		footer.setLayout(new GridLayout());
		c.gridy = 6;
		c.weighty = 0.025;
		c.fill = GridBagConstraints.NONE;
		leftPane.add(footer, c);	
		
		//id label
		id = new JLabel("ID:");
		id.setFont(categorySmallLabelFont);
		footer.add(id);
		
		//button next
		next = new JButton("Next");
		next.setFont(globalPlainFont);
		next.setBorder(new EmptyBorder(10,50,10,50));
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Classifier.log.println(Classifier.getTime() + " clicked button Next");
				
				if(checkValidity()){
					Classifier.addResult(Classifier.next - 1);
					int next = Classifier.next + 1;
					
					if(next > Classifier.COUNT){
						try {
							Classifier.saveResult();
							Classifier.log.println(Classifier.getTime() + " Application finished");
							Classifier.log.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						System.exit(EXIT_ON_CLOSE);
					}
					
					if(next == Classifier.COUNT){
						Window.this.next.setText("Finish");
					}
					
					clearContent();
					try {
						Classifier.parseMovie(Classifier.next);
					} catch (IOException | ParseException e1) {
						e1.printStackTrace();
					}
					
					validate();
					logPanelPostiions();
					logButtonPositions();
					logLabelPositions();
					logImdbTitle();
					logcsfdTitle();
					logDescription();
				}
				
			}
		});
		footer.add(next);
		
		this.addWindowListener(new java.awt.event.WindowAdapter(){
			public void windowClosing(WindowEvent winEv){
				Classifier.log.println(Classifier.getTime() + " Application closed");
				Classifier.log.close();
			}
		});	
	}

}
