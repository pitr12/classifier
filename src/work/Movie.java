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
	private JSONArray nextBtn;
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
		nextBtn = new JSONArray();
	}
	
	public void setEndTime(String time){
		this.endTime = time;
	}
	
	@SuppressWarnings("unchecked")
	public void setBtnPosition(double [] arr){
		for(int i=0; i<4; i++){
			nextBtn.add(arr[i]);
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
	public void setLabelPositions(double[] imdbP, double[] csfdP, double[] descP, double[] idP){
		JSONArray imdbL = new JSONArray();
		JSONArray csfdL = new JSONArray();
		JSONArray descL = new JSONArray();
		JSONArray idL = new JSONArray();
		
		for(int i=0; i<4; i++){
			imdbL.add(imdbP[i]);
			csfdL.add(csfdP[i]);
			descL.add(descP[i]);
			idL.add(idP[i]);
		}
		
		labelPositions.put("imdbTitleLabel", imdbL);
		labelPositions.put("csfdTitleLabel", csfdL);
		labelPositions.put("descriptionLabel", descL);
		labelPositions.put("idLabel", idL);
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
		return nextBtn;
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
