package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.MetaLVA;
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
    public static final int LECTURE_TIMES =0;
    /** This constant serves as an identifier for the UW-Dates associated with this LVA.*/
    public static final int EXERCISES_TIMES =1;
    /** This constant serves as an identifier for the Exam-Dates associated with this LVA.*/
    public static final int EXAM_TIMES =2;

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    public static String formatMetaLVA(List<MetaLVA> metaLVAs, int indentCount){
        String toReturn = "";
        String indent = "";
        for(int i=0;i< indentCount;i++){
            indent+="\t";
        }
        int index=0;
        for(MetaLVA m:metaLVAs){
            toReturn+="\n"+indent+(index++)+") "+m;
        }
        if(toReturn.length()==0){
            return "";
        }
        return toReturn.substring(1);
    }
    public static String formatShortMetaLVA(List<MetaLVA> metaLVAs, int indentCount){
        String toReturn = "";
        String indent = "";
        for(int i=0;i< indentCount;i++){
            indent+="\t";
        }
        int index=0;
        for(MetaLVA m:metaLVAs){
            toReturn+="\n"+indent+(index++)+") "+m.toShortString();
        }
        if(toReturn.length()==0){
            return "";
        }
        return toReturn.substring(1);
    }
    public static String formatDetailedMetaLVA(List<MetaLVA> metaLVAs, int indentCount){
        String toReturn = "";
        String indent = "";
        for(int i=0;i< indentCount;i++){
            indent+="\t";
        }
        int index=0;
        for(MetaLVA m:metaLVAs){
            toReturn+="\n"+indent+(index++)+") "+m.toDetailedString(indentCount + 1);
        }
        if(toReturn.length()==0){
            return "";
        }
        return toReturn.substring(1);
    }
    public static String formatShortDetailedMetaLVA(List<MetaLVA> metaLVAs, int indentCount){
        String toReturn = "";
        String indent = "";
        for(int i=0;i< indentCount;i++){
            indent+="\t";
        }
        int index=0;
        for(MetaLVA m:metaLVAs){
            toReturn+="\n"+indent+(index++)+") "+m.toShortDetailedString(indentCount + 1);
        }
        if(toReturn.length()==0){
            return "";
        }
        return toReturn.substring(1);
    }

    public static String formatLVA(List<LVA> lvas,int indentCount) {
        String toReturn = "";
        String indent = "";
        for(int i=0;i< indentCount;i++){
            indent+="\t";
        }
        int index=0;
        for(LVA m:lvas){
            toReturn+="\n"+indent+(index++)+") "+m;
        }
        if(toReturn.length()==0){
            return "";
        }
        return toReturn.substring(1);
    }
    public static String formatShortLVA(List<LVA> lvas,int indentCount) {
        String toReturn = "";
        String indent = "";
        for(int i=0;i< indentCount;i++){
            indent+="\t";
        }
        int index=0;
        for(LVA lva :lvas){
            toReturn+="\n"+indent+(index++)+") "+ lva.toShortString();
        }
        if(toReturn.length()==0){
            return "";
        }
        return toReturn.substring(1);
    }
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
        timeIDs.add(LECTURE_TIMES);
        timeIDs.add(EXERCISES_TIMES);
        timeIDs.add(EXAM_TIMES);

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
    public static boolean intersectAllTypes(LVA a, LVA b){
        ArrayList<Integer> timeIDs = new ArrayList<Integer>();
        timeIDs.add(LECTURE_TIMES);
        timeIDs.add(EXERCISES_TIMES);
        timeIDs.add(EXAM_TIMES);
        return intersect(a,b,timeIDs,timeIDs);
    }

    /**
     * intersects all LVAs from the given List and returns an array, containging the result from the intersectings.
     * in row i, the intersection of LVA nr i with the LVAs from nr (n-i) to nr n are stored.
     * For example if you have three LVAs lva0, lva1 and lva2. lva0 intersects with lva2, but not lva1 and lva1 intersects with lva2. This method will return this array:
     *  boolea[][] =
     *  0, 1
     *  1
     *
     * @param lva the List of LVAs which shall be used for the intersect
     * @param typesToIntersect the types used for this intersect
     * @return a boolean array with the result of the intersection.
     */
    public static boolean[][] intersectToArray(List<LVA> lva,int timeBetween,List<Integer> typesToIntersect,float tolerance){
        //String debug="";
        boolean[][] toReturn = new boolean[lva.size()][];
        for(int i=0;i<lva.size();i++){
            toReturn[i] = new boolean[lva.size()-i];
            for(int j=i;j<lva.size();j++){
                toReturn[i][j-i]=intersect(lva.get(i),lva.get(j),timeBetween,typesToIntersect,typesToIntersect,tolerance);
            }
        }
        //logger.debug("toReturn: \n"+debug);
        //debug ="test:\n";
        //logger.debug(""+debug);
        return toReturn;
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
       return intersect(a,b,secondsBetween,consideredTimesA,consideredTimesB,0);
    }

    /**
     *
     * @param a the first LVA to use for the test
     * @param b the second LVA to use for the test
     * @param secondsBetween how many seconds may lie between the two LVAs. Negative values for allowing overlapping.
     * @param consideredTimesA the types of TimeFrame in LVA a, which should be used for the test. See this classes constants
     * @param consideredTimesB the types of TimeFrame in LVA b, which should be used for the test. See this classes constants
     * @param tolerance the amount of allowed intersections
     * @return
     */
    public static boolean intersect(LVA a,LVA b,int secondsBetween, List<Integer> consideredTimesA,List<Integer> consideredTimesB,float tolerance){
        int totalLVAs=0;
        Iterator<LvaDate> iterA = iterator(a,consideredTimesA);
        Iterator<LvaDate> iterB = iterator(b,consideredTimesB);
        if(consideredTimesA.contains(LVAUtil.LECTURE_TIMES) && a.getLectures()!=null){
            totalLVAs+=a.getLectures().size();
        }
        if(consideredTimesA.contains(LVAUtil.EXERCISES_TIMES) && a.getExercises()!=null){
            totalLVAs+=a.getExercises().size();
        }
        if(consideredTimesA.contains(LVAUtil.EXAM_TIMES) && a.getExams()!=null){
            totalLVAs+=a.getExams().size();
        }
        if(consideredTimesB.contains(LVAUtil.LECTURE_TIMES) && b.getLectures()!=null){
            totalLVAs+=b.getLectures().size();
        }
        if(consideredTimesB.contains(LVAUtil.EXERCISES_TIMES) && b.getExercises()!=null){
            totalLVAs+=b.getExercises().size();
        }
        if(consideredTimesB.contains(LVAUtil.EXAM_TIMES) && b.getExams()!=null){
            totalLVAs+=b.getExams().size();
        }

        int intersected=0;
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
                    intersected++;
                    if(intersected>(totalLVAs*tolerance)){
                        return true;
                    }
                    if(!iterA.hasNext() || !iterB.hasNext()){
                        return false;
                    }
                    tfA=iterA.next();
                    tfB=iterB.next();
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
            if(times!=null && consideredTimesA.contains(LECTURE_TIMES)){
                allIter.add(times.iterator());
            }
            if(timesUE!=null && consideredTimesA.contains(EXERCISES_TIMES)){
                allIter.add(timesUE.iterator());
            }
            if(timesExam!=null && consideredTimesA.contains(EXAM_TIMES)){
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
