package at.ac.tuwien.sepm.service.semesterPlaning;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.service.TimeFrame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Georg Plaz
 * This Class provides utility, for the class LVA.
 */
public class LVAUtil {
    /** This constant serves as an identifier for the VO-Dates associated with this LVA.*/
    public static final int TIMES =0;
    /** This constant serves as an identifier for the UW-Dates associated with this LVA.*/
    public static final int TIMES_UE =1;
    /** This constant serves as an identifier for the Exam-Dates associated with this LVA.*/
    public static final int TIMES_EXAM =2;

    /**
     * This methods will provide an Iterator, which returns all TimeFrames saved for the given LVA, for the given types. Since they are
     * inserted in order, they will also be returned in order.
     * @param lva the LVA which containes the TimeFrames.
     * @param consideredTimes The Kinds of TimeFrames which should be considered when iterating. See constants in this class.
     * @return an Iterator, which provides TimeFrames of the given types
     */
    public static Iterator<TimeFrame> iterator(LVA lva,List<Integer> consideredTimes){
        return new TimeIterator(lva,consideredTimes);
    }
    /**
     * This methods will provide an Iterator, which returns all TimeFrames saved for the given LVA, for the given type. Since they are
     * inserted in order, they will also be returned in order.
     * @param lva the LVA which containes the TimeFrames.
     * @param timeID The Kind of TimeFrames which should be considered when iterating. See constants in this class.
     * @return an Iterator, which provides TimeFrames of the given type
     */
    public static Iterator<TimeFrame> iterator(LVA lva,int timeID){
        ArrayList<Integer> timeIDs = new ArrayList<Integer>();
        timeIDs.add(timeID);
        return new TimeIterator(lva,timeIDs);
    }
    /**
     * This methods will provide an Iterator, which returns all(!) TimeFrames saved for the given LVA. Since they are
     * inserted in order, they will also be returned in order.
     * @param lva the LVA which containes the TimeFrames.
     * @return an Iterator, which provides TimeFrames of the given type
     */
    public static Iterator<TimeFrame> iterator(LVA lva){
        ArrayList<Integer> timeIDs = new ArrayList<Integer>();
        timeIDs.add(TIMES);
        timeIDs.add(TIMES_UE);
        timeIDs.add(TIMES_EXAM);

        return new TimeIterator(lva,timeIDs);
    }

    /**
     * this method will test, if two LVAs have overlapping TimeFrames, of the give type
     * @param a the first LVA to use for the test
     * @param b the second LVA to use for the test
     * @param timeID the type of TimeFrame which should be used for the test. See this classes constants
     * @return the result of the test
     */
    public static boolean intersect(LVA a,LVA b,int timeID){
        ArrayList<Integer> timeIDs = new ArrayList<Integer>();
        timeIDs.add(timeID);
        return intersect(a,b,timeIDs,timeIDs);
    }
    /**
     * this method will test, if two LVAs have overlapping TimeFrames. All types of TimeFrame will be used
     * @param a the first LVA to use for the test
     * @param b the second LVA to use for the test
     * @return the result of the test
     */
    public static boolean intersectAll(LVA a,LVA b){
        ArrayList<Integer> timeIDs = new ArrayList<Integer>();
        timeIDs.add(TIMES);
        timeIDs.add(TIMES_UE);
        timeIDs.add(TIMES_EXAM);
        return intersect(a,b,timeIDs,timeIDs);
    }

    /**
     * this method will test, if two LVAs have overlapping TimeFrames, of the give type
     * @param a the first LVA to use for the test
     * @param b the second LVA to use for the test
     * @param consideredTimesA the types of TimeFrame in LVA a, which should be used for the test. See this classes constants
     * @param consideredTimesB the types of TimeFrame in LVA b, which should be used for the test. See this classes constants
     * @return the result of the test
     */
    public static boolean intersect(LVA a,LVA b, List<Integer> consideredTimesA,List<Integer> consideredTimesB){
        return intersect(a,b,0,consideredTimesA,consideredTimesB);
    }

    /**
     *
     * @param a the first LVA to use for the test
     * @param b the second LVA to use for the test
     * @param secondsBetween how many seconds may lie between the two LVAs. Negative values for allowing overlapping.
     * @param consideredTimesA the types of TimeFrame in LVA a, which should be used for the test. See this classes constants
     * @param consideredTimesB the types of TimeFrame in LVA b, which should be used for the test. See this classes constants
     * @return
     */
    public static boolean intersect(LVA a,LVA b,int secondsBetween, List<Integer> consideredTimesA,List<Integer> consideredTimesB){
        Iterator<TimeFrame> iterA = iterator(a,consideredTimesA);
        Iterator<TimeFrame> iterB = iterator(b,consideredTimesB);


        TimeFrame tfA;
        TimeFrame tfB;
        if(iterA.hasNext()&&iterB.hasNext()){
            tfA=iterA.next();
            tfB=iterB.next();
            while(true){
                if(tfA.after(tfB,secondsBetween)){
                    if(!iterB.hasNext()){
                        return false;
                    }
                    tfB=iterB.next();
                }else if(tfA.before(tfB,secondsBetween)){
                    if(!iterA.hasNext()){
                        return false;
                    }
                    tfA=iterA.next();
                }else{
                    return true;
                }
            }
        }
		/*
		int index1=0;
		int index2=0;
		if(iterA.hasNext()&&iterB.hasNext()){
			tfA=iterA.next();
			tfB=iterB.next();


			while(index1 < times.size() && index2 < b.times.size()){
				if(times.get(index1).after(b.times.get(index2))){
					index2++;
					System.out.println("index2++");
				}else if(b.times.get(index2).after(times.get(index1))){
					index1++;

					System.out.println("index1++");
				}else{
					return true;
				}
			}
		}*/
        return false;
    }
    private static class TimeIterator implements Iterator<TimeFrame>{
        private LVA lva;
        ArrayList<Iterator<TimeFrame>> allIter= new ArrayList<Iterator<TimeFrame>>();
        ArrayList<TimeFrame> allLastFrames = new ArrayList<TimeFrame>();

        public TimeIterator(LVA lva,List<Integer> consideredTimesA){
            this.lva = lva;
            ArrayList<TimeFrame> times = lva.getTimes();
            ArrayList<TimeFrame> timesUE = lva.getTimesUE();
            ArrayList<TimeFrame> timesExam = lva.getTimesExam();
            if(times!=null && consideredTimesA.contains(TIMES)){
                //System.out.println("debug0.0: "+times.size());
                allIter.add(times.iterator());
            }
            if(timesUE!=null && consideredTimesA.contains(TIMES_UE)){
                //System.out.println("debug0.1: "+timesUE.size());
                allIter.add(timesUE.iterator());
            }
            if(timesExam!=null && consideredTimesA.contains(TIMES_EXAM)){
                //System.out.println("debug0.2: "+timesExam.size());
                allIter.add(timesExam.iterator());
            }
            for(Iterator<TimeFrame> iter:allIter){
                if(iter.hasNext()){
                    //System.out.println("iter==null: "+iter==null);
                    allLastFrames.add(iter.next());
                }else{
                    allLastFrames.add(null);
                }
            }
        }
        @Override
        public boolean hasNext() {
            //System.out.println("debug2");
            for(TimeFrame frame: allLastFrames){
                //System.out.println("  debug2.0: "+iter.hasNext());
                if(frame!=null){
                    return true;
                }
            }
            return false;
        }

        @Override
        public TimeFrame next() {
            TimeFrame firstFrame=null;
            boolean change=true;
            while(change){
                change=false;
                for(TimeFrame frame: allLastFrames){
                    if(firstFrame==null){
                        firstFrame=frame;
                    }else{
                        if(frame!=null && frame.from().isBefore(firstFrame.from())){
                            change=true;
                            firstFrame = frame;
                        }
                    }
                }
            }
            int index = allLastFrames.indexOf(firstFrame);
            if(allIter.get(index).hasNext()){
                allLastFrames.set(index, allIter.get(index).next());
            }else{
                allLastFrames.set(index, null);
            }
            //System.out.println("debug: "+firstFrame);
            return firstFrame;
        }

        @Override
        public void remove() {
            // TODO Auto-generated method stub

        }

    }

}
