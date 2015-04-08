package work;

import org.json.simple.JSONArray;

public class Word {
	private String value;
	private JSONArray position;
	
	@SuppressWarnings("unchecked")
	public Word(String value, double[] pos){
		position = new JSONArray();
		this.value = value;
		
		for(int i=0; i<4; i++){
			position.add(pos[i]);
		}
	}

	public String getValue() {
		return value;
	}

	public JSONArray getPosition() {
		return position;
	}
}
