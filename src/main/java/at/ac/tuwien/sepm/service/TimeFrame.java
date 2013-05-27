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

    /**
     * tests, whether the two TimeFrames overlap
     * @param other the other TimeFrame for the test
     * @return the result of the test
     */
	public boolean intersect(TimeFrame other){
		return !(before(other) || after(other));
	}

    /**
     * tests, whether this TimeFrame lies before the other
     * @param other the other TimeFrame for testing
     * @return the result of the test
     */
	public boolean before(TimeFrame other){
		return to.isBefore(other.from);
	}

    /**
     * tests, whether this TimeFrame lies after the other.
     * @param other the other TimeFrame for testing
     * @return the result of the test
     */
	public boolean after(TimeFrame other){
		return other.to.isBefore(from);
	}
    /**
     * tests, whether this TimeFrame lies before the other plus the given seconds.
     * @param other the other TimeFrame for testing
     * @param secondsBetween the amount of seconds, which will be added to the this TimeFrame before testing. Negative values for allowing overlapping.
     * @return the result of the test
     */
	public boolean before(TimeFrame other,int secondsBetween){
		return to.plusSeconds(secondsBetween).isBefore(other.from);
	}
    /**
     * tests, whether this TimeFrame lies after the other plus the given seconds.
     * @param other the other TimeFrame for testing
     * @param secondsBetween the amount of seconds, which will be added to the other TimeFrame before testing. Negative values for allowing overlapping.
     * @return the result of the test
     */
	public boolean after(TimeFrame other,int secondsBetween){
		return other.to.plusSeconds(secondsBetween).isBefore(from);
	}
    /**
     * tests, whether there is at least a certain amount of seconds between two TimeFrames.
     * Use negative values, to allow certain overlapping
     * @param other the other TimeFrame for the test
     * @param secondsBetween the amount of seconds, which must lie in between the two TimeFrames. Negative values for allowing overlapping
     * @return the result of the test
     */
    public boolean intersect(TimeFrame other,int secondsBetween){
        return !(before(other,secondsBetween) || after(other,secondsBetween));
    }
    /**
     * requests the first date of this TimeFrame
     * @return the first date of this TimeFrame
     */
	public DateTime from() {
		return from;
	}
    /**
     * requests the second date of this TimeFrame
     * @return the second date of this TimeFrame
     */
	public DateTime to() {
		return to;
	}

    /**
     * a readable representation of this object
     * equivalent to: "<TimeFrame: from:"+timeFrame.from()+", to:"+timeFrame.to()+">"
     * @return
     *
     */
	public String toString(){
		return "<TimeFrame: from:"+from+", to:"+to+">";
	}

}
