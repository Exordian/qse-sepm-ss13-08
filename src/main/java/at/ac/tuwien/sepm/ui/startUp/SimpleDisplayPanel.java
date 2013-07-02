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
    /**
     * adds text
     * @param s the text to add
     * @param left if the text should be added to the right half or the left half of the panel
     */
    public void addText(String s,boolean left){
        addRow(new JTextArea(s),null,left);
    }

    /**
     * adds text
     * @param s the text to add
     * @param font the additional font
     * @param left if the text should be added to the right half or the left half of the panel
     */
    public void addText(String s,Integer font,boolean left){
        addRow(new JTextArea(s),font,null,null,left,true);
    }
    /**
    * adds empty space
    * @param space height of the empty space
    * @param left if the space should be added right or left
    */
    public void addEmptyArea(int space,boolean left){
        addEmptyArea(space,left,true);
    }

    /**
     * adds empty space
     * @param space height of the empty space
     * @param left if the space should be added right or left
     * @param top if the space should be added on top or from the bottom
     */
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
    /**
     * adds a new Row.
     * @param leftComp the component, which will be added on the left
     * @param rightComp the component, which will be added on the right
     * @param left if the row is added to the left or to the right
     * @param top if the should be added to the top (if false, it will be added to the bottom)
     */
    public void addRow(JComponent leftComp, JComponent rightComp,boolean left,boolean top){
        addRow(leftComp,null,rightComp,null,left,top);
    }

    /**
     * adds a new Row.
     * @param leftComp the component, which will be added on the left
     * @param leftFont the font extra, which will be applied to the left component
     * @param rightComp the component, which will be added on the right
     * @param rightFont the font extra, which will be applied to the right component
     * @param left if the row is added to the left or to the right
     * @param top if the should be added to the top (if false, it will be added to the bottom)
     */
    public void addRow(JComponent leftComp,Integer leftFont, JComponent rightComp,Integer rightFont,boolean left,boolean top){
        allLabels.add(leftComp);
        allInputs.add(rightComp);
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
        if(leftComp !=null){

            if(leftFont !=null){
                leftComp.setFont(standardInsidePanel.standardTextFont.deriveFont(leftFont));
            }else{
                leftComp.setFont(standardInsidePanel.standardTextFont);
            }

            if(leftComp instanceof JTextComponent){
                JTextComponent textLabel = (JTextArea) leftComp;
                if(leftComp instanceof JTextArea){
                    JTextArea areaLabel = (JTextArea) leftComp;
                    areaLabel.setWrapStyleWord(true);
                    areaLabel.setLineWrap(true);
                }
                textLabel.setEditable(false);
            }
            add(leftComp);



            if(leftComp instanceof JButton){
                tempLabelWidth = buttonWidth;
                if(leftFont !=null){
                    leftComp.setFont(standardInsidePanel.standardButtonFont.deriveFont(leftFont));
                }else{
                    leftComp.setFont(standardInsidePanel.standardButtonFont);
                }
            }else{
                if(rightComp !=null){

                }else{
                    tempLabelWidth=(tempInputX - tempLabelX) + tempInputWidth;
                }
            }
            int tempToAdd = leftComp.getPreferredSize().height;
            if(!top){
                tempHeight-=tempToAdd;
            }
            leftComp.setBounds(tempLabelX, tempHeight,tempLabelWidth, leftComp.getPreferredSize().height);
            leftComp.setBounds(tempLabelX, tempHeight,tempLabelWidth, leftComp.getPreferredSize().height);
            if(!top){
                tempHeight+=tempToAdd;
            }
            tempMax = leftComp.getPreferredSize().height;
        }
        if(rightComp !=null){

            add(rightComp);
            if(rightFont !=null){
                rightComp.setFont(standardInsidePanel.standardTextFont.deriveFont(rightFont));
            }else{
                rightComp.setFont(standardInsidePanel.standardTextFont);
            }
            int tempWidth=tempInputWidth;
            if(rightComp instanceof JButton){
                if(rightFont !=null){
                    rightComp.setFont(standardInsidePanel.standardButtonFont.deriveFont(rightFont));
                }else{
                    rightComp.setFont(standardInsidePanel.standardButtonFont);
                }
                JButton inputButton = (JButton) rightComp;
                tempWidth=buttonWidth;//inputButton.setBounds(tempInputX+(tempInputWidth-buttonWidth), tempHeight, buttonWidth, rightComp.getPreferredSize().height);
            }else if(rightComp instanceof JProgressBar){
                JProgressBar inputButton = (JProgressBar) rightComp;
                tempWidth=buttonWidth;//inputButton.setBounds(tempInputX+(tempInputWidth-buttonWidth), tempHeight, buttonWidth, rightComp.getPreferredSize().height);
            }else{
                if(rightComp instanceof JTextComponent){
                    JTextComponent inputText = (JTextComponent) rightComp;
                    inputText.setBorder(new JTextField().getBorder());
                }
            }
            if(!top){
                tempHeight-= rightComp.getPreferredSize().height;
            }
            rightComp.setBounds(tempInputX+(tempInputWidth-tempWidth), tempHeight, tempWidth, rightComp.getPreferredSize().height);


            tempMax=Math.max(tempMax, rightComp.getBounds().height);
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
    public void refresh(){};
    public void reset(){};
}
