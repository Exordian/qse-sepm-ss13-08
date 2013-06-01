package at.ac.tuwien.sepm.service.semesterPlanning;

import at.ac.tuwien.sepm.dao.LvaDateDao;
import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.service.TimeFrame;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * This methods will provide an Iterator, which returns all TimeFrames saved for the given LVA, for the given types. Since they are
     * inserted in order, they will also be returned in order.
     * @param lva the LVA which containes the TimeFrames.
     * @param consideredTimes The Kinds of TimeFrames which should be considered when iterating. See constants in this class.
     * @return an Iterator, which provides TimeFrames of the given types
     */
    public static Iterator<LvaDate> iterator(LVA lva,List<Integer> consideredTimes){
        return new TimeIterator(lva,consideredTimes);
    }
    /**
     * This methods will provide an Iterator, which returns all TimeFrames saved for the given LVA, for the given type. Since they are
     * inserted in order, they will also be returned in order.
     * @param lva the LVA which containes the TimeFrames.
     * @param timeID The Kind of TimeFrames which should be considered when iterating. See constants in this class.
     * @return an Iterator, which provides TimeFrames of the given type
     */
    public static Iterator<LvaDate> iterator(LVA lva,int timeID){
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
    public static Iterator<LvaDate> iterator(LVA lva){
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
        Iterator<LvaDate> iterA = iterator(a,consideredTimesA);
        Iterator<LvaDate> iterB = iterator(b,consideredTimesB);


        LvaDate tfA;
        LvaDate tfB;
        if(iterA.hasNext()&&iterB.hasNext()){
            tfA=iterA.next();
            tfB=iterB.next();
            while(true){
                if(tfA.getTime().after(tfB.getTime(),secondsBetween)){
                    if(!iterB.hasNext()){
                        return false;
                    }
                    tfB=iterB.next();
                }else if(tfA.getTime().before(tfB.getTime(),secondsBetween)){
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
					logger.debug("index2++");
				}else if(b.times.get(index2).after(times.get(index1))){
					index1++;

					logger.debug("index1++");
				}else{
					return true;
				}
			}
		}*/
        return false;
    }
    private static class TimeIterator implements Iterator<LvaDate>{
        private LVA lva;
        ArrayList<Iterator<LvaDate>> allIter= new ArrayList<Iterator<LvaDate>>();
        ArrayList<LvaDate> allLastFrames = new ArrayList<LvaDate>();
        Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
        public TimeIterator(LVA lva,List<Integer> consideredTimesA){
            this.lva = lva;
            ArrayList<LvaDate> times = lva.getLectures();
            ArrayList<LvaDate> timesUE = lva.getExercises();
            ArrayList<LvaDate> timesExam = lva.getExams();
            if(times!=null && consideredTimesA.contains(TIMES)){
                allIter.add(times.iterator());
            }
            if(timesUE!=null && consideredTimesA.contains(TIMES_UE)){
                allIter.add(timesUE.iterator());
            }
            if(timesExam!=null && consideredTimesA.contains(TIMES_EXAM)){
                allIter.add(timesExam.iterator());
            }
            for(Iterator<LvaDate> iter:allIter){
                if(iter.hasNext()){
                    allLastFrames.add(iter.next());
                }else{
                    allLastFrames.add(null);
                }
            }
        }
        @Override
        public boolean hasNext() {
            for(LvaDate frame: allLastFrames){
                if(frame!=null){
                    return true;
                }
            }
            return false;
        }

        @Override
        public LvaDate next() {
            LvaDate firstFrame=null;
            boolean change=true;
            while(change){
                change=false;
                for(LvaDate frame: allLastFrames){
                    if(firstFrame==null){
                        firstFrame=frame;
                    }else{
                        if(frame!=null && frame.getTime().from().isBefore(firstFrame.getTime().from())){
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
            return firstFrame;
        }

        @Override
        public void remove() {
            //do nothing

        }

    }

}