package work;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Log {
	private String logInitTime;
	private JSONArray screenResolution;
	private String participantName;
	private String sourceFile;
	private JSONArray events;
	private JSONArray categories;
	private JSONArray movies;
	
	public Log(){
		this.screenResolution = new JSONArray();
		this.events = new JSONArray();
		this.categories = new JSONArray();
		this.movies = new JSONArray();
	}

	public String getLogInitTime() {
		return logInitTime;
	}

	public void setLogInitTime(String logInitTime) {
		this.logInitTime = logInitTime;
	}

	public JSONArray getEvents() {
		return events;
	}
	
	public JSONArray getCategories() {
		return categories;
	}
	
	public JSONArray getMovies(){
		return movies;
	}
	
	public JSONArray getScreenResolution() {
		return screenResolution;
	}

	@SuppressWarnings("unchecked")
	public void setScreenResolution(double[] screenResolution) {
		this.screenResolution.add(screenResolution[0]);
		this.screenResolution.add(screenResolution[1]);
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	
	@SuppressWarnings("unchecked")
	public void addEvent(Event e){
		JSONObject o = new JSONObject();
		o.put("time", e.getTime());
		o.put("info", e.getInfo());
		events.add(o);
	}	
	
	@SuppressWarnings("unchecked")
	public void addCategory(Category c) {
		JSONObject o = new JSONObject();
		o.put("name", c.getName());
		o.put("position", c.getPosition());
		categories.add(o);
	}	
	
	@SuppressWarnings("unchecked")
	public void addMovie(Movie m) {
		JSONObject o = new JSONObject();
		o.put("id", m.getMovieID());
		o.put("panelPositions", m.getPanelPositions());
		o.put("labelPositions", m.getLabelPositions());
		o.put("imdbTtile", m.getImdbTtile());
		o.put("csfdTtile", m.getCsfdTtile());
		o.put("description", m.getDescription());
		o.put("nextBtn100", m.getNextBtn());
		o.put("nextBtn200", m.getNextBtn200());
		o.put("nextBtn300", m.getNextBtn300());
		o.put("startTime", m.getStartTime());
		o.put("endTime", m.getEndTime());
		movies.add(o);
	}	
}
