package at.ac.tuwien.sepm.ui;

import java.awt.*;
import java.net.URL;

public class UIHelper {

    public static void openURL(URL url) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(url.toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
