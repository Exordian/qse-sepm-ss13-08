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
    protected int smallSpace;// = 10;
    protected int bigSpace;//=20;

    protected int labelXLeft;// = bigSpace;
    protected int labelWidthLeft;// = 180;

    protected int inputXLeft;// = labelXLeft + labelWidthLeft +smallSpace;
    protected int inputWidthLeft;// = 180;

    protected int labelXRight;// = inputXLeft + inputWidthLeft +bigSpace*2;
    protected int labelWidthRight;// = 180;

    protected int inputXRight;// = labelXRight + labelWidthRight +smallSpace;
    protected int inputWidthRight;// = 180;

    protected int oHeight = 25;


    protected int buttonWidth=150;

    private List<JComponent> allLabels;
    private List<JComponent> allInputs;

    private JComponent lastLabel;
    int lastLeftTopHeight;
    int lastLeftBottomHeight;
    int lastRightTopHeight;
    int lastRightBottomHeight;
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

        lastLeftTopHeight =bigSpace;
        lastRightTopHeight =bigSpace;

        lastLeftBottomHeight = (int) (height-bigSpace);
        lastRightBottomHeight = (int) (height-bigSpace);

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
            lastLeftTopHeight +=space;
        }else{
            lastRightTopHeight +=space;
        }
    }
    public void addRow(JComponent label, JComponent input,boolean left){
        addRow(label,input,left,true);

    }
    public void addRow(JComponent label, JComponent input,boolean left,boolean top){
        allLabels.add(label);
        allInputs.add(input);
        int tempLabelX,tempLabelWidth,tempInputX,tempInputWidth,tempHeight;
        if(left){
            if(!top){
                tempHeight = lastLeftBottomHeight;
            }else{
                tempHeight = lastLeftTopHeight;
            }

            tempLabelX = labelXLeft;
            tempLabelWidth = labelWidthLeft;
            tempInputX = inputXLeft;
            tempInputWidth = inputWidthLeft;
        }else{
            if(!top){
                tempHeight = lastRightBottomHeight;
            }else{
                tempHeight = lastRightTopHeight;
            }
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
            if(!top){
                tempHeight-=label.getPreferredSize().height;
            }

            if(label instanceof JButton){
                tempLabelWidth = buttonWidth;
            }else{
                if(input!=null){

                }else{
                    tempLabelWidth=(tempInputX - tempLabelX) + tempInputWidth;
                }
            }
            label.setBounds(tempLabelX, tempHeight,tempLabelWidth,label.getPreferredSize().height);
            label.setBounds(tempLabelX, tempHeight,tempLabelWidth,label.getPreferredSize().height);
            if(!top){
                tempHeight+=label.getPreferredSize().height;
            }
            tempMax = label.getPreferredSize().height;
        }
        if(input!=null){

            add(input);
            input.setFont(getStartUp().standardTextFont);
            if(!top){
                tempHeight-=input.getPreferredSize().height;
            }
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
            if(!top){
                tempHeight+=input.getPreferredSize().height;
            }

            tempMax=Math.max(tempMax,input.getBounds().height);
        }
        if(top){
            if(left){
                lastLeftTopHeight +=tempMax+smallSpace;
            }else{
                lastRightTopHeight +=tempMax+smallSpace;
            }
        }else{
            if(left){
                lastLeftBottomHeight -=tempMax+smallSpace;
            }else{
                lastRightBottomHeight -=tempMax+smallSpace;
            }
        }

    }
}
