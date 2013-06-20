package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.ui.template.WideComboBox;

/**
 * Author: Georg Plaz
 */
public enum Grade {
    NOT_SET, GRADE_1,GRADE_2,GRADE_3,GRADE_4, GRADE_5,SUCCESS,NO_SUCCESS;

    @Override
    public String toString() {
        switch (this) {
            case NOT_SET:
                return "Nicht beurteilt";
            case GRADE_1:
                return "1";
            case GRADE_2:
                return "2";
            case GRADE_3:
                return "3";
            case GRADE_4:
                return "4";
            case GRADE_5:
                return "5";
            case SUCCESS:
                return "Mit Erfolg bestanden";
            case NO_SUCCESS:
                return "Nicht Bestanden";
            default:
                return "Name für Note nicht definiert!";
        }
    }
    public static Grade getGrade(int grade){
        switch(grade){
            case 1:
                return GRADE_1;
            case 2:
                return GRADE_2;
            case 3:
                return GRADE_3;
            case 4:
                return GRADE_4;
            case 5:
                return GRADE_5;
            default:
                return NOT_SET;
        }
    }
    public static WideComboBox getComboBox(){
        return new WideComboBox(new Grade[]{NOT_SET,GRADE_1,GRADE_2,GRADE_3,GRADE_4,GRADE_5,SUCCESS});
    }
}
