package at.ac.tuwien.sepm.ui.helper;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 03.06.13
 * Time: 02:35
 * To change this template use File | Settings | File Templates.
 */
public class WideComboBox extends JComboBox{

    public WideComboBox() {
    }

    public WideComboBox(final Object items[]){
        super(items);
    }

    public WideComboBox(Vector items) {
        super(items);
    }

    public WideComboBox(ComboBoxModel aModel) {
        super(aModel);
    }

    private boolean layingOut = false;

    public void doLayout(){
        try{
            layingOut = true;
            super.doLayout();
        }finally{
            layingOut = false;
        }
    }

    public Dimension getSize(){
        Dimension dim = super.getSize();
        if(!layingOut)
            dim.width = Math.max(dim.width, getPreferredSize().width);
        return dim;
    }
}
