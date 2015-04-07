package work;

import org.json.simple.JSONArray;

public class Category {
	private String name;
	private JSONArray position;
	
	@SuppressWarnings("unchecked")
	public Category(String name, double [] arr){
		this.position = new JSONArray();
		this.name = name;
		
		for(int i=0; i<4; i++){
			this.position.add(arr[i]);
		}
		
	}

	public String getName() {
		return name;
	}

	public JSONArray getPosition() {
		return position;
	}
}
