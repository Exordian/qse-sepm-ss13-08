package at.ac.tuwien.sepm.ui.calender.cal;

import at.ac.tuwien.sepm.entity.Date;
import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.service.DateService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TimeFrame;
import at.ac.tuwien.sepm.ui.SmallInfoPanel;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Markus MUTH
 */

public class DateLabel extends JTextPane implements Comparable<DateLabel>{
    private static final int MAX_TEXT_LENGTH=20;
    private Date date;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    private boolean twoLine = false;
    private DateService dateService;

    private Font popUpFont;

    public DateLabel(Date date, DateService dateService, boolean twoLine, Font popUpFont) {
        this.date=date;
        this.twoLine = twoLine;
        this.dateService=dateService;
        setContentType("text/html");
        if(date.getName()==null || date.getName().trim().equals("")){
            this.setText("...");
        } else if (twoLine) {
            this.setText("<nobr><font size=2 face=\"Arial\"><b>"+formatStartDate(date.getTime().from())+" - "+formatStartDate(date.getTime().to()) +"</b><br>"+ date.getName()+"</font></nobr>");
        } else {
            this.setText("<nobr><font size=2 face=\"Arial\">"+cutText(date.getName(), 0)+"</font></b>");
            setBorder(BorderFactory.createEmptyBorder());
        }
        //this.setFont(new Font("Arial", Font.PLAIN, 10));
        this.addMouseListener(new PrivateMouseListener());

        setEditable(false);
        //setWrapStyleWord(false);
        setAutoscrolls(false);
        setFocusable(false);
        this.popUpFont = popUpFont;
    }

    public void changeColor (boolean isActualMonth) {
        if(isActualMonth) {
            this.setOpaque(true);
            if(twoLine){
                setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
            }else{
                setBorder(BorderFactory.createEmptyBorder());
            }
            if(date instanceof LvaDate) {
                this.setBackground(new Color(223, 233, 255));
            } else if (date instanceof DateEntity) {
                if (((DateEntity)date).getDescription().equals("Dies ist ein freier Tag, das heisst: KEINE UNI YEAAH!!!")) {
                    this.setBackground(new Color(246, 242, 122));
                } else {
                    this.setBackground(new Color(195, 255, 194));
                }
            }
        } else {
            setBorder(BorderFactory.createEmptyBorder());
            setOpaque(false);
        }
    }

    public DateTime getStart() {
        return date.getTime().from();
    }

    public DateTime getStop() {
        return date.getTime().to();
    }

    public TimeFrame getTime() {
        return date.getTime();
    }

    private String formatStartDate(DateTime d) {
        String result = "";
        if(d.getHourOfDay()<10) {
            result = "0" + d.getHourOfDay();
        } else {
            result += d.getHourOfDay();
        }
        result += ":";
        if(d.getMinuteOfHour()<10) {
            result += "0" + d.getMinuteOfHour();
        } else {
            result += d.getMinuteOfHour();
        }

        return result;
    }

    private boolean consistsOf(char c, String s) {
        for(int i=0; i<s.length(); i++) {
            if(c != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private String cutText(String text, int diff) {
        if(text.length() > (MAX_TEXT_LENGTH-diff)) {
            text = text.substring(0,MAX_TEXT_LENGTH-diff) + "...";
        }
        return text;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(DateLabel o) {
        return date.getTime().from().compareTo(o.getTime().from());
    }

    class PrivateMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                if(getDate() instanceof LvaDate) {
                    PanelTube.backgroundPanel.viewLvaDate((LvaDate) getDate(), DateTime.now());
                } else if (getDate() instanceof DateEntity) {
                    PanelTube.backgroundPanel.viewDate((DateEntity) getDate(), DateTime.now());
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.getButton()==3) {
                boolean troll = false;
                if (date instanceof DateEntity) {
                    if (((DateEntity)date).getDescription().equals("Dies ist ein freier Tag, das heisst: KEINE UNI YEAAH!!!"))
                        troll = true;
                }
                PopUpMenu menu = new PopUpMenu(troll);
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private class PopUpMenu extends JPopupMenu {
        private JMenuItem edit;
        private JMenuItem showRoom;

        public PopUpMenu(boolean troll){
            if (troll) {
                edit = new JMenuItem("Als 'nicht frei' markieren");
                showRoom = new JMenuItem();
                add(edit);
            } else {
                edit = new JMenuItem("Bearbeiten");
                showRoom = new JMenuItem("Wegbeschreibung");
                add(edit);
                add(showRoom);
            }
            edit.setFont(popUpFont);
            //add(new Separator());
            showRoom.setFont(popUpFont);
            addActionListeners();
        }

        private void addActionListeners() {
            edit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(date instanceof LvaDate) {
                        PanelTube.backgroundPanel.viewLvaDate((LvaDate)date, null);
                    } else if(date instanceof DateEntity) {
                        if (((DateEntity)date).getDescription().equals("Dies ist ein freier Tag, das heisst: KEINE UNI YEAAH!!!")) {
                            Object[] options = {"Ja", "Abbrechen"};
                            if (JOptionPane.showOptionDialog(DateLabel.this, "Wollen Sie den Tag wieder als 'nicht frei' markieren?", "Bestätigung",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) == JOptionPane.YES_OPTION) {
                                try {
                                    DateLabel.this.dateService.deleteDate(((DateEntity)date).getId());
                                    PanelTube.backgroundPanel.viewSmallInfoText("Der freie Tag wurde wieder als 'nicht frei' markiert.", SmallInfoPanel.Success);
                                    PanelTube.calendarPanel.refresh();
                                } catch (ServiceException e1) {
                                    log.error(e1.getMessage());
                                    PanelTube.backgroundPanel.viewSmallInfoText("Der Tag konnte nicht als 'nicht frei' markiert werden.", SmallInfoPanel.Error);
                                }
                            } else {
                                PanelTube.backgroundPanel.viewSmallInfoText("Der freie Tag bleibt weiter als frei markiert.", SmallInfoPanel.Info);
                            }
                        } else
                            PanelTube.backgroundPanel.viewDate((DateEntity)date, null);
                    }
                }
            });
            showRoom.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(date instanceof LvaDate) {
                        if(!PanelTube.backgroundPanel.openRoomBrowser(((LvaDate) date).getRoom()))
                            PanelTube.backgroundPanel.viewSmallInfoText("Keine Wegbeschreibung gefunden.", SmallInfoPanel.Info);
                    } else {
                        PanelTube.backgroundPanel.viewSmallInfoText("Es existieren keine Wegbeschreibungen zu privaten Terminen.", SmallInfoPanel.Warning);
                    }
                }
            });

        }
    }
}
