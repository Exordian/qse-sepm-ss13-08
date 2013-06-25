package at.ac.tuwien.sepm.entity;

import at.ac.tuwien.sepm.service.LvaType;
import at.ac.tuwien.sepm.service.Semester;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class MetaLVA implements Comparable<MetaLVA>{
    
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
        if(id!=null){
            toReturn+=" id:"+id;
        }
        if(nr!=null){
            toReturn+=" nr:"+nr;
        }
        if(type!=null){
            toReturn+=" type:"+type;
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
        if(id!=null){
             toReturn+=" id:"+id;
        }
        if(nr!=null){
            if(nr.length()>3){
                toReturn+=" nr:"+".."+nr.substring(4);
            }else{
                toReturn+=" nr:"+nr;
            }
        }
        if(type!=null){
            toReturn+=" type:"+type;
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
    public String toDetailedString(int indentCount){
        String toReturn=toString();
        String indent="";
        for(int i=0;i<indentCount;i++){
            indent+="\t";
        }
        int index =0;
        for(LVA lva:lvas){
            toReturn+="\n"+(index++)+") "+indent+lva;
        }
        return toReturn;
    }
    public String toVeryDetailedString(int indentCount){
        String toReturn=toString();
        String indent="";
        for(int i=0;i<indentCount;i++){
            indent+="\t";
        }
        int index=0;
        for(LVA lva:lvas){
            toReturn+="\n"+indent+(index++)+") "+lva.toDetailedString(indentCount+1);
        }
        return toReturn;
    }
    public String toShortDetailedString(int indentCount){
        String toReturn=toShortString();
        String indent="";
        for(int i=0;i<indentCount;i++){
            indent+="\t";
        }
        int index=0;
        for(LVA lva:lvas){
            toReturn+="\n"+indent+(index++)+") "+lva.toShortString();
        }
        return toReturn;
    }

    @Override
    public int compareTo(MetaLVA o) {
        if(name!=null){
            return (int)((o.priority-priority)*100);
        }else{
            return (int)(o.priority-priority);
        }
    }
    public static Comparator<MetaLVA> getAlphabeticalNameComparator(){
        return new Comparator<MetaLVA>() {
            @Override
            public int compare(MetaLVA o1, MetaLVA o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
    }
    public static Comparator<MetaLVA> getNrComparator(){
        return new Comparator<MetaLVA>() {
            @Override
            public int compare(MetaLVA o1, MetaLVA o2) {
                return o1.getNr().compareTo(o2.getNr());
            }
        };
    }

    public boolean equalsForMerge(MetaLVA o) {
        System.out.println(o);
        if (this == o) return true;
        if (o == null) return false;
        /*for(LVA lva: lvas){
            if(!lva.equalsForMerge(o.getLVA(lva.getYear(),lva.getSemester()))){
                return false;
            }
        }*/
        if (Float.compare(o.ects, ects) != 0) return false;
        if (module != o.module) return false;
        if (lvas != null ? !lvas.equals(o.lvas) : o.lvas != null) return false;
        if (name != null ? !name.equals(o.name) : o.name != null) return false;
        if (nr != null ? !nr.equals(o.nr) : o.nr != null) return false;
        if (type != o.type) return false;

        return true;
    }

}
