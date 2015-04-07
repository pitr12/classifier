package work;

public class Event {
	private String time;
	private String info;
	
	public Event(String time2, String s) {
		this.time = time2;
		this.info = s;
	}

	public String getTime() {
		return time;
	}
	
	public String getInfo() {
		return info;
	}
}
