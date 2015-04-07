package work;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JFileChooser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gui.Window;

public class Classifier {
	 public static final int CATEGORY_COUNT = 30;
	 public static final int COUNT = 10;
	 public static int next = 0;
	 private static JSONParser parser;
	 private static JSONArray data;
	 private static JSONArray result;
	 private static FileReader file;
	 private static File selectedFile;
	 public static PrintWriter log;
	 public static SimpleDateFormat date;
	 public static Date time;
	public static Window gui_frame;
	public static String[] categories = {"Art and Artists","Media","Biography","Comedy","Conspiracy","Crime","Drugs","Military and War","Politics","Psychology",
		"Environment","Nature","Health","Society","Philosophy","Mystery","Religion","Economics","History","Science","Technology",
		"Sports","Traveling","Catastriphic","Nothing","Adventure","Culture","Housing","Geography","Animals"};
 
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {	
		Collections.shuffle(Arrays.asList(categories));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui_frame = new Window();
					gui_frame.setVisible(true);
					createCategories();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		classify();
	}
	
	public static void createCategories(){
		for(int i=0; i<CATEGORY_COUNT; i++){
			Window.constructCategory(categories[i],i);
		}
	}
	
	public static String getTime(){
		date = new SimpleDateFormat ("yyyy/MM/dd '-' hh:mm:ss");
		time = new Date();
		return date.format(time);
	}
	
	public static void classify() throws FileNotFoundException, IOException, ParseException{
		parser = new JSONParser();
		
		//select input file
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("./"));
	    fc.showOpenDialog(null);
	    selectedFile = fc.getSelectedFile();
	    String path = selectedFile.getAbsolutePath();
	    
	    //load user name
	    Window.loadUserName();
	    
	    //initialize log file
	  	String fileName = gui_frame.getName() + ".log";
	  	log = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
	  	log.println(getTime() + " initialized log file");
	  		
	  	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  	log.println("screen resoluton: " +screenSize.getWidth()+" x "+screenSize.getHeight());
	  	log.println("set name: " +gui_frame.getName());
        
		
		file = new FileReader(path);
		log.println("selected source file: " + selectedFile.getName());
		
		data = (JSONArray) parser.parse(file);
		
		result = new JSONArray();
		
		parseMovie(0);
		
		gui_frame.setBtnLbl("Next  " + (next + 1) + "/" + COUNT);
		
		gui_frame.validate();
		gui_frame.logPanelPostiions();
		gui_frame.logButtonPositions();
		gui_frame.logLabelPositions();
		gui_frame.logCategoryPositions();
		gui_frame.logImdbTitle();
		gui_frame.logcsfdTitle();
		gui_frame.logDescription();
	}
	
	public static String[] splitString(String string){
		String[] result = string.split(" ");	
		return result;
	}
	
	public static void parseMovie(int i) throws FileNotFoundException, IOException, ParseException{
		JSONObject movie = (JSONObject) data.get(i);

		String id = movie.get("id").toString();
		String []title = splitString((String) movie.get("imdb_title"));
		String []csfdTitle = splitString((String) movie.get("csfd_title"));
		String[] description = splitString((String) movie.get("imdb_desc"));
		
		gui_frame.setId(id);
		gui_frame.setImdbTitle(title);
		gui_frame.setcsfdTitle(csfdTitle);
		gui_frame.setDescription(description);
		
		next += 1;
			
		log.println(getTime() + " displayed content of movie ID=" +id );
	}
	
	@SuppressWarnings("unchecked")
	public static void addResult(int i){
		JSONObject movie = (JSONObject) data.get(i);
		 movie.put("primary_category", Window.getPrimaryCategory());	
		 movie.put("secondary_category", Window.getSecondaryCategory());
		 result.add(movie);
	}
	
	public static void saveResult() throws IOException{
		String resultfileName = "./"+gui_frame.getName() + "_result.json";
		FileWriter file = new FileWriter(resultfileName);
        try {
            file.write(result.toJSONString());
 
        } catch (IOException e) {
            e.printStackTrace();
 
        }
        file.close();
	}
}