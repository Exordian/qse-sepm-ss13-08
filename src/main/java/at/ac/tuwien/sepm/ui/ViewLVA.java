package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.TimeFrame;
import org.joda.time.DateTime;


/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 22:42
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewLVA extends StandardSimpleInsidePanel {
    private LvaDate lvaDate;

    public ViewLVA() {
        init();
        addImage();
        lvaDate = new LvaDate();
        addReturnButton();
        addContent();
    }

    public void setLVADateEntity(LvaDate lvaDate) {
        this.lvaDate=lvaDate;

        addTitle(lvaDate.getName());

        this.repaint();
        this.revalidate();
    }

    private void addContent() {

    }
}
