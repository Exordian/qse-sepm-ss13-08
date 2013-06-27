package at.ac.tuwien.sepm.service;

public enum Semester {
    W,S,W_S,UNKNOWN;
    public String toString(){
        switch(this){
            case W:
                return "Winter";
            case S:
                return "Sommer";
            case W_S:
                return "W. u. S.";
            default:
                return "Unbekannt";
        }
    }
    public String toShortString(){
        switch(this){
            case W:
                return "WS";
            case S:
                return "SS";
            case W_S:
                return "WS/SS";
            default:
                return "n.a.";
        }
    }
    public static Semester parse(String s){
        for(Semester sem:Semester.values()){
            if(s.equals(sem.toString()) || s.equals(sem.toShortString())){
                return sem;
            }
        }
        return null;
    }
}
