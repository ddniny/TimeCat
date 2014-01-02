package org.rushme.timecat.tasks;


public class task {
	public String details, startTime, endTime, description, id, addTime;
	public enum State {ACTIVE, EXPIRED, COMPLETED};
	public State getState;
	public enum Importance {LOW, MODERATE, IMPORTANT, CRUCIAL};
	public Importance getImportance;
	public float priority;
	public String[] tags;

	public task(){

	}

	public task(String id, String details, String startTime, String endTime, String description, String state, String importance, float priority, String tags, String addTime){
		this.id = id;
		this.details = details;
		this.startTime = startTime;
		this.endTime = endTime;
		this.addTime = addTime;
		this.description = description;
		if (state.equals("ACTIVE")){
			this.getState = State.ACTIVE;
		}else if(state.equals("EXPIRED")){
			this.getState = State.EXPIRED;
		}else if(state.equals("COMPLETED")){
			this.getState = State.COMPLETED;
		}

		if(importance.equals("LOW")){
			this.getImportance = Importance.LOW;
		}else if(importance.equals("MODERATE")){
			this.getImportance = Importance.MODERATE;
		}else if(importance.equals("IMPORTANT")){
			this.getImportance = Importance.IMPORTANT;
		}else if(importance.equals("CRUCIAL")){
			this.getImportance = Importance.CRUCIAL;
		}

		this.priority = priority;

		this.tags = tags.split(" ");
		for(String s: this.tags){
			System.out.println(s);
		}
	}

	public void setState(String state){
		if (state.equals("ACTIVE")){
			this.getState = State.ACTIVE;
		}else if(state.equals("EXPIRED")){
			this.getState = State.EXPIRED;
		}else if(state.equals("COMPLETED")){
			this.getState = State.COMPLETED;
		}
	}
}
