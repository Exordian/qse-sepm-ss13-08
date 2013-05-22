package at.ac.tuwien.sepm.service.semesterPlaning;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.service.TimeFrame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 15.05.13
 * Time: 19:55
 * To change this template use File | Settings | File Templates.
 */
public class LVAUtil {
    public static final int TIMES =0;
    public static final int TIMES_UE =1;
    public static final int TIMES_EXAM =2;

    public static Iterator<TimeFrame> iterator(LVA lva,List<Integer> consideredTimesA){
        return new TimeIterator(lva,consideredTimesA);
    }
    public static Iterator<TimeFrame> iterator(LVA lva,int timeID){
        ArrayList<Integer> timeIDs = new ArrayList<Integer>();
        timeIDs.add(timeID);
        return new TimeIterator(lva,timeIDs);
    }
    public static Iterator<TimeFrame> iterator(LVA lva){
        ArrayList<Integer> timeIDs = new ArrayList<Integer>();
        timeIDs.add(TIMES);
        timeIDs.add(TIMES_UE);
        timeIDs.add(TIMES_EXAM);

        return new TimeIterator(lva,timeIDs);
    }
    public static String bla(){
        return new String();
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
    public static boolean intersect(LVA a,LVA b,int timeID){
        ArrayList<Integer> timeIDs = new ArrayList<Integer>();
        timeIDs.add(timeID);
        return intersect(a,b,timeIDs,timeIDs);
    }
    public static boolean intersectAll(LVA a,LVA b){
        ArrayList<Integer> timeIDs = new ArrayList<Integer>();
        timeIDs.add(TIMES);
        timeIDs.add(TIMES_UE);
        timeIDs.add(TIMES_EXAM);
        return intersect(a,b,timeIDs,timeIDs);
    }
    public static boolean intersect(LVA a,LVA b, List<Integer> consideredTimesA,List<Integer> consideredTimesB){
        return intersect(a,b,0,consideredTimesA,consideredTimesB);
    }
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
}
