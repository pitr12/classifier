package gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import org.json.simple.parser.ParseException;

import work.Category;
import work.Classifier;
import work.Event;
import work.Word;

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	//fonts
	private static Font globalBoldFont = new Font("Courier 10 Pitch", Font.BOLD, 24);
	private static Font globalPlainFontSmaller = new Font("Courier 10 Pitch", Font.PLAIN, 22);
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
	private static ArrayList<JPanel> category_panels = new ArrayList<JPanel>();
	
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
		return name;
	}
	
	public static void constructCategory(String name, final int index){
		JPanel panel = new JPanel();
		GridLayout categoryLayout = new GridLayout(2,1);
		panel.setLayout(categoryLayout);
		
		JRadioButton button = new JRadioButton(name);
		button.setFont(globalPlainFontSmaller);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeButtonState(index);
				Classifier.time = new Date();
				String new_state;
				switch(states[index]){
				case 0: new_state = "deselected"; break;
				case 1: new_state = "primary category"; break;
				case 2: new_state = "secondary category"; break;
				default: new_state = "";
				}
				Classifier.time = new Date();
				String s2 = "Clicked button val=" + group.get(index).getText() + " and set as " + new_state;
				Classifier.log.println(Classifier.getTime(Classifier.time) + " " + s2);
				Classifier.jsonLog.addEvent(new Event(Classifier.getTime(Classifier.time), s2));
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
		
		category_panels.add(panel);
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
	
	@SuppressWarnings("static-access")
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
	    
	    Classifier.time = new Date();
	    Classifier.log.println(Classifier.getTime(Classifier.time) + " Displayed primary category message dialog warning");
		Classifier.jsonLog.addEvent(new Event(Classifier.getTime(Classifier.time), "Displayed primary category message dialog warning"));

		
		return false;
	}
	
	@SuppressWarnings("static-access")
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
	    
	    Classifier.time = new Date();
	    Classifier.log.println(Classifier.getTime(Classifier.time) + " Displayed secondary category message dialog warning");
		Classifier.jsonLog.addEvent(new Event(Classifier.getTime(Classifier.time), "Displayed secondary category message dialog warning"));

	    
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
	
	public double[] getFramePosition(){
		double [] result = new double[4];
		Rectangle frameR = this.getBounds();
		
		result[0] = frameR.getMinX();
		result[1] = frameR.getMinY();
		result[2] = frameR.getMaxX();
		result[3] = frameR.getMaxY();
				
		return result;
	}
	
	public double[] getContentPanePosition(){
		double [] result = new double[4];
		Rectangle contentPaneR = contentPane.getBounds();
		double [] frameP = getFramePosition();
		
		
		result[0] = frameP[0] + contentPaneR.getMinX();
		result[1] = frameP[1] + contentPaneR.getMinY();
		result[2] = frameP[0] + contentPaneR.getMaxX();
		result[3] = frameP[1] + contentPaneR.getMaxY();
		
		return result;
	}
	
	public double[] getLefttPanePosition(){
		double [] result = new double[4];
		Rectangle LeftPaneR = leftPane.getBounds();
		double [] contentP = getContentPanePosition();
		
		
		result[0] = contentP[0] + LeftPaneR.getMinX();
		result[1] = contentP[1] + LeftPaneR.getMinY();
		result[2] = contentP[0] + LeftPaneR.getMaxX();
		result[3] = contentP[1] + LeftPaneR.getMaxY();
		
		return result;
	} 
	
	public double[] getRightPanePosition(){
		double [] result = new double[4];
		Rectangle RightPaneR = rightPane.getBounds();
		double [] contentP = getContentPanePosition();
		
		
		result[0] = contentP[0] + RightPaneR.getMinX();
		result[1] = contentP[1] + RightPaneR.getMinY();
		result[2] = contentP[0] + RightPaneR.getMaxX();
		result[3] = contentP[1] + RightPaneR.getMaxY();
		
		return result;
	} 
	
	public double[] getImdbTitlePanePosition(){
		double [] result = new double[4];
		Rectangle ImdbTitlePaneR = imdbTitle.getBounds();
		double [] leftP = getLefttPanePosition();
		
		
		result[0] = leftP[0] + ImdbTitlePaneR.getMinX();
		result[1] = leftP[1] + ImdbTitlePaneR.getMinY();
		result[2] = leftP[0] + ImdbTitlePaneR.getMaxX();
		result[3] = leftP[1] + ImdbTitlePaneR.getMaxY();
		
		return result;
	} 
	
	public double[] getCsfdTitlePanePosition(){
		double [] result = new double[4];
		Rectangle CsfdTitlePaneR = csfdTitle.getBounds();
		double [] leftP = getLefttPanePosition();
		
		
		result[0] = leftP[0] + CsfdTitlePaneR.getMinX();
		result[1] = leftP[1] + CsfdTitlePaneR.getMinY();
		result[2] = leftP[0] + CsfdTitlePaneR.getMaxX();
		result[3] = leftP[1] + CsfdTitlePaneR.getMaxY();
		
		return result;
	} 
	
	public double[] getDescriptionPanePosition(){
		double [] result = new double[4];
		Rectangle descriptionPaneR = description.getBounds();
		double [] leftP = getLefttPanePosition();
		
		
		result[0] = leftP[0] + descriptionPaneR.getMinX();
		result[1] = leftP[1] + descriptionPaneR.getMinY();
		result[2] = leftP[0] + descriptionPaneR.getMaxX();
		result[3] = leftP[1] + descriptionPaneR.getMaxY();
		
		return result;
	} 
	
	public double[] getFooterPanePosition(){
		double [] result = new double[4];
		Rectangle footerPaneR = footer.getBounds();
		double [] leftP = getLefttPanePosition();
		
		
		result[0] = leftP[0] + footerPaneR.getMinX();
		result[1] = leftP[1] + footerPaneR.getMinY();
		result[2] = leftP[0] + footerPaneR.getMaxX();
		result[3] = leftP[1] + footerPaneR.getMaxY();
		
		return result;
	} 
	
	public double[] getNextBtnPosition(){
		double [] result = new double[4];
		Rectangle nextBtn = next.getBounds();
		double [] footerP = getFooterPanePosition();
		
		
		result[0] = footerP[0] + nextBtn.getMinX();
		result[1] = footerP[1] + nextBtn.getMinY();
		result[2] = footerP[0] + nextBtn.getMaxX();
		result[3] = footerP[1] + nextBtn.getMaxY();
		
		return result;
	} 
	
	public double[] getImdbLabelPosition(){
		double [] result = new double[4];
		Rectangle imdbLabel = imdbTitleLabel.getBounds();
		double [] leftP = getLefttPanePosition();
		
		
		result[0] = leftP[0] + imdbLabel.getMinX();
		result[1] = leftP[1] + imdbLabel.getMinY();
		result[2] = leftP[0] + imdbLabel.getMaxX();
		result[3] = leftP[1] + imdbLabel.getMaxY();
		
		return result;
	} 
	
	public double[] getCsfdLabelPosition(){
		double [] result = new double[4];
		Rectangle csfdLabel = csfdTitleLabel.getBounds();
		double [] leftP = getLefttPanePosition();
		
		
		result[0] = leftP[0] + csfdLabel.getMinX();
		result[1] = leftP[1] + csfdLabel.getMinY();
		result[2] = leftP[0] + csfdLabel.getMaxX();
		result[3] = leftP[1] + csfdLabel.getMaxY();
		
		return result;
	} 
	
	public double[] getDescLabelPosition(){
		double [] result = new double[4];
		Rectangle descLabel = descriptionLabel.getBounds();
		double [] leftP = getLefttPanePosition();
		
		
		result[0] = leftP[0] + descLabel.getMinX();
		result[1] = leftP[1] + descLabel.getMinY();
		result[2] = leftP[0] + descLabel.getMaxX();
		result[3] = leftP[1] + descLabel.getMaxY();
		
		return result;
	} 
	
	public double[] getIdLabelPosition(){
		double [] result = new double[4];
		Rectangle idLabel = id.getBounds();
		double [] footerP = getFooterPanePosition();
		
		
		result[0] = footerP[0] + idLabel.getMinX();
		result[1] = footerP[1] + idLabel.getMinY();
		result[2] = footerP[0] + idLabel.getMaxX();
		result[3] = footerP[1] + idLabel.getMaxY();
		
		return result;
	}
	
	public double[] getCategoryPosition(Rectangle category){
		double [] result = new double[4];
		double [] rightP = getRightPanePosition();
		
		
		result[0] = rightP[0] + category.getMinX();
		result[1] = rightP[1] + category.getMinY();
		result[2] = rightP[0] + category.getMaxX();
		result[3] = rightP[1] + category.getMaxY();
		
		return result;
	}
	
	public double[] getImdbTitlePosition(Rectangle title){
		double [] result = new double[4];
		double [] imdbP = getImdbTitlePanePosition();
		
		
		result[0] = imdbP[0] + title.getMinX();
		result[1] = imdbP[1] + title.getMinY();
		result[2] = imdbP[0] + title.getMaxX();
		result[3] = imdbP[1] + title.getMaxY();
		
		return result;
	}
	
	public double[] getCsfdTitlePosition(Rectangle title){
		double [] result = new double[4];
		double [] csfdP = getCsfdTitlePanePosition();
		
		
		result[0] = csfdP[0] + title.getMinX();
		result[1] = csfdP[1] + title.getMinY();
		result[2] = csfdP[0] + title.getMaxX();
		result[3] = csfdP[1] + title.getMaxY();
		
		return result;
	}
	
	public double[] getDescriptionPosition(Rectangle title){
		double [] result = new double[4];
		double [] descriptionP = getDescriptionPanePosition();
		
		
		result[0] = descriptionP[0] + title.getMinX();
		result[1] = descriptionP[1] + title.getMinY();
		result[2] = descriptionP[0] + title.getMaxX();
		result[3] = descriptionP[1] + title.getMaxY();
		
		return result;
	}
	
	public void logPanelPostiions(){	
		Classifier.log.println("panel positions:");
		
		double [] frameP = getFramePosition();
		Classifier.log.println(frameP[0] + " " + frameP[1] + " " + frameP[2] + " " + frameP[3] + " - frame");
		
		double [] contentP = getContentPanePosition();
		Classifier.log.println(contentP[0] + " " + contentP[1] + " " + contentP[2] + " " + contentP[3] + " - contentPane");
		
		double [] LeftP = getLefttPanePosition();
		Classifier.log.println(LeftP[0] + " " + LeftP[1] + " " + LeftP[2] + " " + LeftP[3] + " - leftPane");
		
		double [] RightP = getRightPanePosition();
		Classifier.log.println(RightP[0] + " " + RightP[1] + " " + RightP[2] + " " + RightP[3] + " - rightPane");
		
		double [] imdbTitleP = getImdbTitlePanePosition();
		Classifier.log.println(imdbTitleP[0] + " " + imdbTitleP[1] + " " + imdbTitleP[2] + " " + imdbTitleP[3] + " - imdbTitlePane");
		
		double [] csfdTitleP = getCsfdTitlePanePosition();
		Classifier.log.println(csfdTitleP[0] + " " + csfdTitleP[1] + " " + csfdTitleP[2] + " " + csfdTitleP[3] + " - csfdTitlePane");
		
		double [] descriptionP = getDescriptionPanePosition();
		Classifier.log.println(descriptionP[0] + " " + descriptionP[1] + " " + descriptionP[2] + " " + descriptionP[3] + " - descriptionPane");
		
		double [] footerP = getFooterPanePosition();
		Classifier.log.println(footerP[0] + " " + footerP[1] + " " + footerP[2] + " " + footerP[3] + " - footerPane");
		
		Classifier.currentMovie.setPanelPositions(frameP, contentP, LeftP, RightP, imdbTitleP, csfdTitleP, descriptionP, footerP);
	}
	
	public void logButtonPositions(){
		Classifier.time = new Date();
		Classifier.log.println(Classifier.getTime(Classifier.time) + " Button positions:");
		
		double [] nextP = getNextBtnPosition();
		Classifier.log.println(nextP[0] + " " + nextP[1] + " " + nextP[2] + " " + nextP[3] + " - Next button");
		Classifier.currentMovie.setBtnPosition(nextP);
	}
	
	public void logLabelPositions(){
		Classifier.time = new Date();
		Classifier.log.println(Classifier.getTime(Classifier.time) + " Label positions:");
		
		double [] imdbLabelP = getImdbLabelPosition();
		Classifier.log.println(imdbLabelP[0] + " " + imdbLabelP[1] + " " + imdbLabelP[2] + " " + imdbLabelP[3] + " - imdb title label");
		
		double [] csfdLabelP = getCsfdLabelPosition();
		Classifier.log.println(csfdLabelP[0] + " " + csfdLabelP[1] + " " + csfdLabelP[2] + " " + csfdLabelP[3] + " - csfd title label");
		
		double [] descLabelP = getDescLabelPosition();
		Classifier.log.println(descLabelP[0] + " " + descLabelP[1] + " " + descLabelP[2] + " " + descLabelP[3] + " - description label");
		
		double [] idLabelP = getIdLabelPosition();
		Classifier.log.println(idLabelP[0] + " " + idLabelP[1] + " " + idLabelP[2] + " " + idLabelP[3] + " - id label");
		
		Classifier.currentMovie.setLabelPositions(imdbLabelP, csfdLabelP, descLabelP, idLabelP);
	}
	
	public void logCategoryPositions(){
		Classifier.time = new Date();
		Classifier.log.println(Classifier.getTime(Classifier.time) + " Category positions:");
		rightPane.validate();
		
		for(int i=0; i<Classifier.CATEGORY_COUNT; i++){			
			double [] categoryP = getCategoryPosition(category_panels.get(i).getBounds());
			Classifier.log.println(categoryP[0] + " " + categoryP[1] + " " + categoryP[2] + " " + categoryP[3] + " " + group.get(i).getText());
			Classifier.jsonLog.addCategory(new Category(group.get(i).getText(), categoryP));
		}
	}
	
	public void logImdbTitle(){
		imdbTitle.validate();
		Classifier.time = new Date();
		Classifier.log.println(Classifier.getTime(Classifier.time) + " IMDB Title text positions:");
		int size = imdbTitleList.size();
		for(int i=0; i<size; i++){
			double [] titleP = getImdbTitlePosition(imdbTitleList.get(i).getBounds());
			Classifier.log.println(titleP[0] + " " + titleP[1] + " " + titleP[2] + " " + titleP[3] + " " + imdbTitleList.get(i).getText());
			Classifier.currentMovie.addWordToImdbTitle(new Word(imdbTitleList.get(i).getText(), titleP));
		}
	}
	
	public void logcsfdTitle(){
		csfdTitle.validate();
		Classifier.time = new Date();
		Classifier.log.println(Classifier.getTime(Classifier.time) + " CSFD Title text positions:");
		int size = csfdTitleList.size();
		for(int i=0; i<size; i++){
			double [] titleP = getCsfdTitlePosition(csfdTitleList.get(i).getBounds());
			Classifier.log.println(titleP[0] + " " + titleP[1] + " " + titleP[2] + " " + titleP[3] + " " + csfdTitleList.get(i).getText());
			Classifier.currentMovie.addWordToCsfdTitle(new Word(csfdTitleList.get(i).getText(), titleP));
		}
	}
	
	public void logDescription(){
		description.validate();
		Classifier.time = new Date();
		Classifier.log.println(Classifier.getTime(Classifier.time) + " Description text positions:");
		int size = descriptionList.size();
		for(int i=0; i<size; i++){
			double [] titleP = getDescriptionPosition(descriptionList.get(i).getBounds());
			Classifier.log.println(titleP[0] + " " + titleP[1] + " " + titleP[2] + " " + titleP[3] + " " + descriptionList.get(i).getText());
			Classifier.currentMovie.addWordToDescription(new Word(descriptionList.get(i).getText(), titleP));
		}
	}
	
	public void setBtnLbl(String text){
		next.setText(text);
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		
		//window main config
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(0, 0, 500, 500);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("Classifier");
		
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
				Classifier.time = new Date();
				Classifier.log.println(Classifier.getTime(Classifier.time) + " clicked button Next");
				Classifier.jsonLog.addEvent(new Event(Classifier.getTime(Classifier.time), "Clicked button Next"));
				
				if(checkValidity()){
					Classifier.addResult(Classifier.next - 1);
					Classifier.currentMovie.setEndTime(Classifier.getTime(Classifier.time));
					int next = Classifier.next + 1;
					
					if(next > Classifier.COUNT){
						try {
							Classifier.saveResult();
							Classifier.jsonLog.addMovie(Classifier.currentMovie);
							Classifier.log.println(Classifier.getTime(Classifier.time) + " Application finished");
							Classifier.jsonLog.addEvent(new Event(Classifier.getTime(Classifier.time), "Application finished"));
							Classifier.log.close();
							Classifier.constructJsonLog();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						System.exit(EXIT_ON_CLOSE);
					}
					
					
					clearContent();
					
					setBtnLbl("Next  " + (Classifier.next + 2) + "/" + Classifier.COUNT);
					if(next == Classifier.COUNT){
						Window.this.next.setText("Finish");
					}
					
					try {
						
						Classifier.parseMovie(Classifier.next);
					} catch (IOException | ParseException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		footer.add(next);
		
		this.addWindowListener(new java.awt.event.WindowAdapter(){
			public void windowClosing(WindowEvent winEv){
				Classifier.time = new Date();
				Classifier.log.println(Classifier.getTime(Classifier.time) + " Application closed");
				Classifier.jsonLog.addEvent(new Event(Classifier.getTime(Classifier.time), "Application closed"));
				Classifier.log.close();
				try {
					Classifier.constructJsonLog();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
	}

}
