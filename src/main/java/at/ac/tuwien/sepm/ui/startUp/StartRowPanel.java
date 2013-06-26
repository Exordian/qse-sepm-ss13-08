package at.ac.tuwien.sepm.ui.startUp;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Georg Plaz
 */
public abstract class StartRowPanel extends JPanel{
    private double width;
    private double height;
    private ViewStartUp startUp;
    public final int smallSpace;// = 10;
    public final int bigSpace;//=20;

    public final int labelXLeft;// = bigSpace;
    public final int labelWidthLeft;// = 180;

    public final int inputXLeft;// = labelXLeft + labelWidthLeft +smallSpace;
    public final int inputWidthLeft;// = 180;

    public final int labelXRight;// = inputXLeft + inputWidthLeft +bigSpace*2;
    public final int labelWidthRight;// = 180;

    public final int inputXRight;// = labelXRight + labelWidthRight +smallSpace;
    public final int inputWidthRight;// = 180;

    public final int oHeight = 25;


    public final int buttonWidth=200;

    List<JComponent> allLabels;
    List<JComponent> allInputs;

    JComponent lastLabel;
    int lastLeftHeight;
    int lastRightHeight;
    public StartRowPanel(double width, double height, ViewStartUp startUp){
        smallSpace = 10;
        bigSpace=20;

        labelXLeft = bigSpace;
        labelWidthLeft = 180;

        inputXLeft = labelXLeft + labelWidthLeft +smallSpace;
        inputWidthLeft = (int) (width/2-(inputXLeft+bigSpace));

        labelXRight = inputXLeft + inputWidthLeft +bigSpace*2;
        labelWidthRight = 180;

        inputXRight = labelXRight + labelWidthRight +smallSpace;
        inputWidthRight =(int) (width-(inputXRight+bigSpace));

        lastLeftHeight =bigSpace;
        lastRightHeight=bigSpace;

        this.width=width;
        //this.rightWidth = (width-bigSpace)-rightX;
        this.height=height;
        this.startUp = startUp;
        sInit();
        //super.init();
    }
    public void sInit(){
        this.setLayout(null);
        setOpaque(false);

        allLabels = new LinkedList<JComponent>();
        allInputs = new LinkedList<JComponent>();
    }
    public ViewStartUp getStartUp() {
        return startUp;
    }

    public double getPanelHeight() {
        return height;
    }

    public double getPanelWidth() {
        return width;
    }
    public void addText(String s,boolean left){
        addRow(new JTextArea(s),null,left);
    }
    public void addEmptyArea(int space,boolean left){
        if(left){
            lastLeftHeight+=space;
        }else{
            lastRightHeight+=space;
        }
    }
    public void addRow(JComponent label, JComponent input,boolean left){
        allLabels.add(label);
        allInputs.add(input);
        int tempLabelX,tempLabelWidth,tempInputX,tempInputWidth,tempHeight;
        if(left){
            tempHeight = lastLeftHeight;
            tempLabelX = labelXLeft;
            tempLabelWidth = labelWidthLeft;
            tempInputX = inputXLeft;
            tempInputWidth = inputWidthLeft;
        }else{
            tempHeight = lastRightHeight;
            tempLabelX = labelXRight;
            tempLabelWidth = labelWidthRight;
            tempInputX = inputXRight;
            tempInputWidth = inputWidthRight;
        }
        
        int tempMax = 0;
        if(label!=null){

            //label.setBackground(new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
            label.setFont(getStartUp().standardTextFont);

            if(label instanceof JTextComponent){
                JTextComponent textLabel = (JTextArea)label;
                if(label instanceof JTextArea){
                    JTextArea areaLabel = (JTextArea)label;
                    areaLabel.setWrapStyleWord(true);
                    areaLabel.setLineWrap(true);
                }
                textLabel.setEditable(false);
            }
            add(label);
            if(input!=null){
                label.setBounds(tempLabelX, tempHeight, tempLabelWidth,label.getPreferredSize().height);
                label.setBounds(tempLabelX, tempHeight, tempLabelWidth,label.getPreferredSize().height);
            }else{
                label.setBounds(tempLabelX, tempHeight,(tempInputX - tempLabelX) + tempInputWidth,label.getPreferredSize().height);
                label.setBounds(tempLabelX, tempHeight,(tempInputX - tempLabelX) + tempInputWidth,label.getPreferredSize().height);
            }
            tempMax = label.getPreferredSize().height;
        }
        if(input!=null){

            add(input);
            input.setFont(getStartUp().standardTextFont);
            if(input instanceof JButton){
                JButton inputButton = (JButton)input;
                inputButton.setBounds(tempInputX+(tempInputWidth-buttonWidth), tempHeight, buttonWidth, input.getPreferredSize().height);
            }else if(input instanceof JProgressBar){
                JProgressBar inputButton = (JProgressBar)input;
                inputButton.setBounds(tempInputX+(tempInputWidth-buttonWidth), tempHeight, buttonWidth, input.getPreferredSize().height);
            }else{
                input.setBounds(tempInputX, tempHeight, tempInputWidth, input.getPreferredSize().height);
                if(input instanceof JTextComponent){
                    JTextComponent inputText = (JTextComponent)input;
                    inputText.setBorder(new JTextField().getBorder());
                }
            }

            tempMax=Math.max(tempMax,input.getBounds().height);
        }
        if(left){
            lastLeftHeight +=tempMax+smallSpace;
        }else{
            lastRightHeight +=tempMax+smallSpace;
        }

    }
}
