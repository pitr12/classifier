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
	
	public Log(){
		this.screenResolution = new JSONArray();
		this.events = new JSONArray();
		this.categories = new JSONArray();
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
}
