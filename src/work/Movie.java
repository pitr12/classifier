package work;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Movie {
	private String movieID;
	private JSONObject panelPositions;
	private JSONObject labelPositions;
	private JSONArray imdbTtitle;
	private JSONArray csfdTtitle;
	private JSONArray description;
	private JSONArray nextBtn100;
	private JSONArray nextBtn200;
	private JSONArray nextBtn300;
	private String startTime;
	private String endTime;
	
	public Movie(String id, String startTime){
		this.movieID = id;
		this.startTime = startTime;
		
		panelPositions = new JSONObject();
		labelPositions = new JSONObject();
		imdbTtitle = new JSONArray();
		csfdTtitle = new JSONArray();
		description = new JSONArray();
		nextBtn100 = new JSONArray();
		nextBtn200 = new JSONArray();
		nextBtn300 = new JSONArray();
	}
	
	public void setEndTime(String time){
		this.endTime = time;
	}
	
	@SuppressWarnings("unchecked")
	public void setBtn100Position(double [] arr){
		for(int i=0; i<4; i++){
			nextBtn100.add(arr[i]);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setBtn200Position(double [] arr){
		for(int i=0; i<4; i++){
			nextBtn200.add(arr[i]);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setBtn300Position(double [] arr){
		for(int i=0; i<4; i++){
			nextBtn300.add(arr[i]);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setPanelPositions(double []frameP, double []contentP, double []leftP, double []rightP, double []imdbP, double []csfdP, double []descP, double []footerP){
		JSONArray frameA = new JSONArray();
		JSONArray contentA = new JSONArray();
		JSONArray leftA = new JSONArray();
		JSONArray rightA = new JSONArray();
		JSONArray imdbA = new JSONArray();
		JSONArray csfdA = new JSONArray();
		JSONArray descA = new JSONArray();
		JSONArray footerA = new JSONArray();
		
		for(int i=0; i<4; i++){
			frameA.add(frameP[i]);
			contentA.add(contentP[i]);
			leftA.add(leftP[i]);
			rightA.add(rightP[i]);
			imdbA.add(imdbP[i]);
			csfdA.add(csfdP[i]);
			descA.add(descP[i]);
			footerA.add(footerP[i]);
		}
		
		panelPositions.put("frame", frameA);
		panelPositions.put("contentPane", contentA);
		panelPositions.put("leftPane", leftA);
		panelPositions.put("rightPane", rightA);
		panelPositions.put("imdbTitlePane", imdbA);
		panelPositions.put("csfdTitlePane", csfdA);
		panelPositions.put("descriptionPane", descA);
		panelPositions.put("footerPane", footerA);
		
	}
	
	@SuppressWarnings("unchecked")
	public void setLabelPositions(double[] imdbP, double[] csfdP, double[] descP, double[] idP, double[] posP){
		JSONArray imdbL = new JSONArray();
		JSONArray csfdL = new JSONArray();
		JSONArray descL = new JSONArray();
		JSONArray idL = new JSONArray();
		JSONArray posL = new JSONArray();
		
		for(int i=0; i<4; i++){
			imdbL.add(imdbP[i]);
			csfdL.add(csfdP[i]);
			descL.add(descP[i]);
			idL.add(idP[i]);
			posL.add(posP[i]);
		}
		
		labelPositions.put("imdbTitleLabel", imdbL);
		labelPositions.put("csfdTitleLabel", csfdL);
		labelPositions.put("descriptionLabel", descL);
		labelPositions.put("idLabel", idL);
		labelPositions.put("positionLabel", posL);
	}

	public String getMovieID() {
		return movieID;
	}

	public JSONObject getPanelPositions() {
		return panelPositions;
	}

	public JSONObject getLabelPositions() {
		return labelPositions;
	}

	public JSONArray getImdbTtile() {
		return imdbTtitle;
	}

	public JSONArray getCsfdTtile() {
		return csfdTtitle;
	}

	public JSONArray getDescription() {
		return description;
	}

	public JSONArray getNextBtn() {
		return nextBtn100;
	}
	
	public JSONArray getNextBtn200() {
		return nextBtn200;
	}
	
	public JSONArray getNextBtn300() {
		return nextBtn300;
	}
	
	public String getStartTime(){
		return startTime;
	}
	
	public String getEndTime(){
		return endTime;
	}
	
	@SuppressWarnings("unchecked")
	public void addWordToImdbTitle(Word w){
		JSONObject o = new JSONObject();
		o.put("value", w.getValue());
		o.put("position", w.getPosition());
		imdbTtitle.add(o);
	}
	
	@SuppressWarnings("unchecked")
	public void addWordToCsfdTitle(Word w){
		JSONObject o = new JSONObject();
		o.put("value", w.getValue());
		o.put("position", w.getPosition());
		csfdTtitle.add(o);
	}
	
	@SuppressWarnings("unchecked")
	public void addWordToDescription(Word w){
		JSONObject o = new JSONObject();
		o.put("value", w.getValue());
		o.put("position", w.getPosition());
		description.add(o);
	}
}
