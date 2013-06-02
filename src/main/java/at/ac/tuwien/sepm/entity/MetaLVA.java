package at.ac.tuwien.sepm.entity;

import at.ac.tuwien.sepm.service.LvaType;
import at.ac.tuwien.sepm.service.Semester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MetaLVA {
    
    private Integer id;
    private String nr;
    private String name;
    private float ects=-1;
    private ArrayList<MetaLVA> precursor = new ArrayList<MetaLVA>();
    private LvaType type;
    //private boolean hasExercise;
    private float priority=-1;
    private Semester semestersOffered;
    private int module;
    
    private ArrayList<LVA> lvas;
    private HashMap<Integer,LVA> lvasMap;
    
    private boolean completed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getECTS() {
        return ects;
    }

    public void setECTS(float ects) {
        this.ects = ects;
    }

    public List<MetaLVA> getPrecursor() {
        return precursor;
    }

    public void setPrecursor(List<MetaLVA> precursors) {
        this.precursor = new ArrayList<MetaLVA>(precursors);
    }

    public LvaType getType() {
        return type;
    }

    public void setType(LvaType type) {
        this.type = type;
    }

    /*public boolean hasExercise() {
        return hasExercise;
    }

    public void setHasExercise(boolean hasExercise) {
        this.hasExercise = hasExercise;
    }*/

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }

    public List<LVA> getLVAs() {
        return lvas;
    }

    /**
     * also sets the lvas MetaLVA to this, if it is null.
     * @param lvas the List of LVAs, which are part of this MetaLVA
     */
    public void setLVAs(List<LVA> lvas) {
        this.lvas = new ArrayList<LVA>(lvas);
        lvasMap= new HashMap<Integer,LVA>(lvas.size());
        for(LVA l:lvas){
            int key=l.getYear()*2;
            if(l.getSemester()==Semester.W){
                key++;
            }
            lvasMap.put(key, l);
            if(l.getMetaLVA()==null){
                l.setMetaLVA(this);
            }
        }
    }
    /**
     * also sets the lva MetaLVA to this, if it is null.
     * @param lva the List of LVAs, which are part of this MetaLVA
     */
    public void setLVA(LVA lva) {
        this.lvas = new ArrayList<LVA>();
        lvas.add(lva);
        setLVAs(lvas);
    }
    public LVA getLVA(int year, Semester sem){
        int key=year*2;
        if(sem==Semester.W){
            key++;
        }
        return lvasMap.get(key);
    }
    public boolean containsLVA(int year, Semester sem){
        int key=year*2;
        if(sem== Semester.W){
            key++;
        }
        return lvasMap.containsKey(key);
    }

    public Semester getSemestersOffered() {
        return semestersOffered;
    }
    public void setSemestersOffered(Semester semestersOffered) {
        this.semestersOffered = semestersOffered;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public String toString(){
        String toReturn="<MetaLVA:";
        if(nr!=null){
            toReturn+=" nr:"+nr;
        }
        if(name!=null){
            toReturn+=" name:"+name;
        }
        if(ects!=-1){
            toReturn+=" ects:"+ects;
        }
        if(priority!=-1){
            toReturn+=" priority:"+priority;
        }
        return toReturn+">";
    }
    public String toShortString(){
        String toReturn="<MetaLVA:";
        if(nr!=null){
            if(nr.length()>3){
                toReturn+=" nr:"+nr.substring(4)+"..";
            }else{
                toReturn+=" nr:"+nr;
            }
        }
        if(name!=null){
            if(name.length()>15){
                toReturn+=" name:"+name.substring(0,15)+"..";
            }else{
                toReturn+=" name:"+name;
            }
        }
        if(priority!=-1){
            toReturn+=" prio:"+priority;
        }
        return toReturn+">";
    }

}
