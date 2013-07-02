package at.ac.tuwien.sepm.ui.startUp;

import at.ac.tuwien.sepm.ui.StandardInsidePanel;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Georg Plaz
 */
public abstract class SimpleDisplayPanel extends JPanel{
    private double width;
    private double height;
    public int smallSpace;// = 10;
    public int bigSpace;//=20;

    public int labelXLeft;// = bigSpace;
    public int labelWidthLeft;// = 180;

    public int inputXLeft;// = labelXLeft + labelWidthLeft +smallSpace;
    public int inputWidthLeft;// = 180;

    public int labelXRight;// = inputXLeft + inputWidthLeft +bigSpace*2;
    public int labelWidthRight;// = 180;

    public int inputXRight;// = labelXRight + labelWidthRight +smallSpace;
    public int inputWidthRight;// = 180;

    public int oHeight = 25;

    public int lastLeftTopHeight;
    public int lastLeftBottomHeight;
    public int lastRightTopHeight;
    public int lastRightBottomHeight;

    public int buttonWidth=150;

    private List<JComponent> allLabels;
    private List<JComponent> allInputs;

    private StandardInsidePanel standardInsidePanel;
    private JComponent lastLabel;
    
    public SimpleDisplayPanel(double width, double height,StandardInsidePanel standardInsidePanel){
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
        this.standardInsidePanel = standardInsidePanel;

        sInit();
        //super.init();
    }
    public void sInit(){
        this.setLayout(null);
        setOpaque(false);

        allLabels = new LinkedList<JComponent>();
        allInputs = new LinkedList<JComponent>();
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
    public void addText(String s,Integer font,boolean left){
        addRow(new JTextArea(s),font,null,null,left,true);
    }
    public void addEmptyArea(int space,boolean left){
        addEmptyArea(space,left,true);
    }
    public void addEmptyArea(int space,boolean left,boolean top){
        if(top){
            if(left){
                lastLeftTopHeight +=space;
            }else{
                lastRightTopHeight +=space;
            }
        }else{
            if(left){
                lastLeftBottomHeight -=space;
            }else{
                lastRightBottomHeight -=space;
            }
        }
    }
    public void addRow(JComponent label, JComponent input,boolean left){
        addRow(label,input,left,true);

    }
    public void addRow(JComponent label, JComponent input,boolean left,boolean top){
        addRow(label,null,input,null,left,top);
    }
    public void addRow(JComponent label,Integer fontLabel, JComponent input,Integer fontInput,boolean left,boolean top){
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

            if(fontLabel!=null){
                label.setFont(standardInsidePanel.standardTextFont.deriveFont(fontLabel));
            }else{
                label.setFont(standardInsidePanel.standardTextFont);
            }

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



            if(label instanceof JButton){
                tempLabelWidth = buttonWidth;
                if(fontLabel!=null){
                    label.setFont(standardInsidePanel.standardButtonFont.deriveFont(fontLabel));
                }else{
                    label.setFont(standardInsidePanel.standardButtonFont);
                }
            }else{
                if(input!=null){

                }else{
                    tempLabelWidth=(tempInputX - tempLabelX) + tempInputWidth;
                }
            }
            int tempToAdd = label.getPreferredSize().height;
            if(!top){
                tempHeight-=tempToAdd;
            }
            label.setBounds(tempLabelX, tempHeight,tempLabelWidth,label.getPreferredSize().height);
            label.setBounds(tempLabelX, tempHeight,tempLabelWidth,label.getPreferredSize().height);
            if(!top){
                tempHeight+=tempToAdd;
            }
            tempMax = label.getPreferredSize().height;
        }
        if(input!=null){

            add(input);
            if(fontInput!=null){
                input.setFont(standardInsidePanel.standardTextFont.deriveFont(fontInput));
            }else{
                input.setFont(standardInsidePanel.standardTextFont);
            }
            int tempWidth=tempInputWidth;
            if(input instanceof JButton){
                if(fontInput!=null){
                    input.setFont(standardInsidePanel.standardButtonFont.deriveFont(fontInput));
                }else{
                    input.setFont(standardInsidePanel.standardButtonFont);
                }
                JButton inputButton = (JButton)input;
                tempWidth=buttonWidth;//inputButton.setBounds(tempInputX+(tempInputWidth-buttonWidth), tempHeight, buttonWidth, input.getPreferredSize().height);
            }else if(input instanceof JProgressBar){
                JProgressBar inputButton = (JProgressBar)input;
                tempWidth=buttonWidth;//inputButton.setBounds(tempInputX+(tempInputWidth-buttonWidth), tempHeight, buttonWidth, input.getPreferredSize().height);
            }else{
                if(input instanceof JTextComponent){
                    JTextComponent inputText = (JTextComponent)input;
                    inputText.setBorder(new JTextField().getBorder());
                }
            }
            if(!top){
                tempHeight-=input.getPreferredSize().height;
            }
            input.setBounds(tempInputX+(tempInputWidth-tempWidth), tempHeight, tempWidth, input.getPreferredSize().height);


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
    public void refresh() {};
}
