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
}
