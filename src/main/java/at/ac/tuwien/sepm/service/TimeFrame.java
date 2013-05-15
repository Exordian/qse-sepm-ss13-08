package at.ac.tuwien.sepm.service;

import org.joda.time.DateTime;


public class TimeFrame {
	private final DateTime from;
	private final DateTime to;
	public TimeFrame(DateTime from,DateTime to){
		if(from.isAfter(to)){
			throw new RuntimeException("\"from\" must lie befor \"to\"!");
		}
		this.from = from;
		this.to=to;
	}
	public boolean intersect(TimeFrame other){
		return !(before(other) || after(other));
	}
	public boolean before(TimeFrame other){
		return to.isBefore(other.from);
	}
	public boolean after(TimeFrame other){
		return other.to.isBefore(from);
	}
	public boolean before(TimeFrame other,int secondsBetween){
		return to.plusSeconds(secondsBetween).isBefore(other.from);
	}
	public boolean after(TimeFrame other,int secondsBetween){
		return other.to.plusSeconds(secondsBetween).isBefore(from);
	}
	public DateTime from() {
		return from;
	}
	public DateTime to() {
		return to;
	}
	public String toString(){
		return "from:"+from+", to:"+to;
	}

}
