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
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFileChooser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gui.Instructions;
import gui.Window;











//Include the Dropbox SDK.
import com.dropbox.core.*;

import java.io.*;
import java.util.Locale;

public class Classifier {
	 public static final int CATEGORY_COUNT = 30;
	 public static int COUNT;
	 public static int next = 0;
	 public static int confidence = 0;
	 private static DbxClient client;
	 private static JSONParser parser;
	 private static JSONArray data;
	 private static JSONArray result;
	 private static FileReader file;
	 private static File selectedFile;
	 public static PrintWriter log;
	 public static Log jsonLog;
	 public static SimpleDateFormat date;
	 public static Date time;
	 public static Movie currentMovie;
	public static Window gui_frame;
	public static Instructions gui_instructions;
	public static String absolutePath;
	public static String[] categories = {"Technology","Mystery","Science","Catastrophic","Nature","Animals","Geography","Adventure","Environment","Traveling",
		"Health","Drugs","Economics","Crime","Politics","Biography","Society","Religion","Culture","Psychology","Philosophy","Art and Artists","History",
		"Conspiracy","Military and War","Media","Comedy", "Housing","Sports","I can't do this"};
 
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, DbxException {	
		//initDropBox();
		linkDropbBoxAccount();
		
		//Collections.shuffle(Arrays.asList(categories));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui_instructions = new Instructions();
					gui_instructions.setVisible(true);
					
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						  @Override
						  public void run() {
							  gui_instructions.setVisible(false);
							  gui_frame = new Window();
								gui_frame.setVisible(true);
								createCategories();
								try {
									classify();
								} catch (IOException | ParseException
										| DbxException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						  }
						}, 60*1000);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void initDropBox() throws IOException, DbxException{
		final String APP_KEY = "yt8x7msxfol3kcy";
        final String APP_SECRET = "4wlmi3yvfdm6cwt";

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig(
            "JavaTutorial/1.0", Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        
     // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
        DbxAuthFinish authFinish = webAuth.finish(code);
        String accessToken = authFinish.accessToken;
        System.out.println("Access token: " + accessToken);
        client = new DbxClient(config, accessToken);
        System.out.println("Linked account: " + client.getAccountInfo().displayName);
	}
	
	public static void linkDropbBoxAccount() throws DbxException{
		DbxRequestConfig config = new DbxRequestConfig(
	            "JavaTutorial/1.0", Locale.getDefault().toString());
		final String accessToken = "zHhsOYOLWfkAAAAAAAABXldaBbE5xDnJypPKGPZiVOlGhRdSgx2eIMP8aNiC2KX0";
		client = new DbxClient(config, accessToken);
        System.out.println("Linked account: " + client.getAccountInfo().displayName);
		
	}
	
	public static void createFolder(String folderName) throws DbxException {
		client.createFolder("/" + folderName);
	}
	
	public static void uploadToDropbox(String filePath, String fileName) throws DbxException,
	IOException {
		File inputFile = new File(filePath);
		FileInputStream fis = new FileInputStream(filePath);
		try {
			client.uploadFile("/" + gui_frame.getName() + "/" + fileName,
					DbxWriteMode.add(), inputFile.length(), fis);
		} finally {
			fis.close();
		}
	}
	
	public static void createCategories(){
		for(int i=0; i<CATEGORY_COUNT; i++){
			Window.constructCategory(categories[i],i);
		}
	}
	
	public static String getTime(Date time){
		date = new SimpleDateFormat ("yyyy/MM/dd '-' hh:mm:ss");
		return date.format(time);
	}
	
	public static void classify() throws FileNotFoundException, IOException, ParseException, DbxException{
		absolutePath = new File(".").getCanonicalPath();
		parser = new JSONParser();
		new File(absolutePath+"/output").mkdir();
		
		//select input file
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("./"));
	    fc.showOpenDialog(null);
	    selectedFile = fc.getSelectedFile();
	    String path = selectedFile.getAbsolutePath();
	    
	    //load user name
	    Window.loadUserName();
	    
	    //initialize log file
	  	String fileName = absolutePath+"/output/" + gui_frame.getName() + ".log";
	  	log = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
	  	jsonLog = new Log();
	  		
	  	//log init time
	  	time = new Date();
	  	log.println(getTime(time) + " initialized log file");
	  	jsonLog.setLogInitTime(getTime(time));
	  		
	  	//log screen resolution
	  	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  	log.println("screen resoluton: " +screenSize.getWidth()+" x "+screenSize.getHeight());
	  	double[] screenResolution = {screenSize.getWidth(), screenSize.getHeight()};
	  	jsonLog.setScreenResolution(screenResolution);
	  	
	  	//log participant name
	  	log.println("set name: " +gui_frame.getName());
	  	jsonLog.setParticipantName(gui_frame.getName());
        
		//log source file
		file = new FileReader(path);
		log.println("selected source file: " + selectedFile.getName());
		jsonLog.setSourceFile(selectedFile.getName());
		
		data = (JSONArray) parser.parse(file);
		COUNT = data.size();
		
		result = new JSONArray();
		
		parseMovie(0);
		
		
		gui_frame.validate();
		gui_frame.logCategoryPositions();		
	}
	
	public static String[] splitString(String string){
		String[] result = string.split(" ");	
		return result;
	}
	
	public static void parseMovie(int i) throws FileNotFoundException, IOException, ParseException{
		if(currentMovie != null){
			jsonLog.addMovie(Classifier.currentMovie);
		}
		
		JSONObject movie = (JSONObject) data.get(i);

		String id = movie.get("id").toString();
		String []title = splitString((String) movie.get("imdb_title"));
		String []csfdTitle = splitString((String) movie.get("csfd_title"));
		String[] description = splitString((String) movie.get("imdb_desc"));
		
		gui_frame.setPosition(Integer.toString(i+1) + " / " + COUNT);
		gui_frame.setId(id);
		gui_frame.setImdbTitle(title);
		gui_frame.setcsfdTitle(csfdTitle);
		gui_frame.setDescription(description);
		
		next += 1;
			
		time = new Date();
		String s = "displayed content of movie ID=" +id;
		log.println(getTime(time) + " " +s );
		jsonLog.addEvent(new Event(getTime(time), s));
		
		currentMovie = new Movie(id, getTime(time));
		
		gui_frame.validate();
		gui_frame.logPanelPostiions();
		gui_frame.logButtonPositions();
		gui_frame.logLabelPositions();
		gui_frame.logImdbTitle();
		gui_frame.logcsfdTitle();
		gui_frame.logDescription();
	}
	
	@SuppressWarnings("unchecked")
	public static void addResult(int i){
		JSONObject movie = (JSONObject) data.get(i);
		 movie.put("primary_category", Window.getPrimaryCategory());	
		 movie.put("secondary_category", Window.getSecondaryCategory());
		 movie.put("confidence", confidence);
		 result.add(movie);
	}
	
	public static void saveResult() throws IOException, DbxException{
		String resultfileName = absolutePath+"/output/"+gui_frame.getName() + "_result.json";
		FileWriter file = new FileWriter(resultfileName);
        try {
            file.write(result.toJSONString());
 
        } catch (IOException e) {
            e.printStackTrace();
 
        }
        file.close();
        String uploadFileName = gui_frame.getName() + "_result.json";
        uploadToDropbox(resultfileName, uploadFileName);
	}
	
	//Create and save JSON log
	@SuppressWarnings("unchecked")
	public static void constructJsonLog() throws IOException, DbxException{
		JSONObject jResult = new JSONObject();
		jResult.put("logInitTime", jsonLog.getLogInitTime());
		jResult.put("participantName", jsonLog.getParticipantName());
		jResult.put("sourceFile", jsonLog.getSourceFile());
		jResult.put("screenResolution", jsonLog.getScreenResolution());
		jResult.put("events", jsonLog.getEvents());
		
		JSONObject positions = new JSONObject();
		positions.put("categories", jsonLog.getCategories());
		positions.put("movies", jsonLog.getMovies());
		
		jResult.put("positions", positions);
			
		//save JSON log
		String resultfileName = absolutePath+"/output/"+gui_frame.getName() + "_log.json";
		FileWriter file = new FileWriter(resultfileName);
        try {
            file.write(jResult.toJSONString());
 
        } catch (IOException e) {
            e.printStackTrace();
 
        }
        file.close();
        String uploadFileName = gui_frame.getName() + "_log.json";
        uploadToDropbox(resultfileName, uploadFileName);
	}
}