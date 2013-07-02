package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.service.RoomFinderService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class WegweiserService implements RoomFinderService {
    private static Logger log = LogManager.getLogger(WegweiserService.class);

    //@Value("${timeout}")
    private static int timeout = 10000;

    //@Value("${baseUrl}")
    private static String BASE_URL = "http://www.wegweiser.ac.at/";

    private static String HS_LIST = "/tuwien/hoersaal/";

    private Elements roomList;

    @PostConstruct
    void init() {
        new Thread() {
            public void run() {
                try {
                    URL tuwienRooms = new URL(BASE_URL + HS_LIST);
                    Document tuwienRoomsDoc = Jsoup.parse(tuwienRooms, timeout);

                    roomList = tuwienRoomsDoc.select(".maintext li");
                } catch (IOException e) {
                    log.error("could not pre cache rooms");
                }
            }
        }.start();
    }

    @Override
    public URL getRoomURL(String room) throws ServiceException {
        try {
            if(room == null)
                throw new ServiceException("room not found");
            if(roomList == null || roomList.size() == 0)
                init();
            if(roomList == null || roomList.size() == 0)
                throw new ServiceException("service not available");
            for(Element elemRoom : roomList)
                if (elemRoom.text().startsWith(room)) { // some rooms have a "Hörsaal" postfix
                    return new URL(BASE_URL + elemRoom.select("a").first().attr("href"));
                }
            throw new ServiceException("room not found");
        } catch (MalformedURLException e) {
            throw new ServiceException("room not found");
        }
    }

    @Override
    public BufferedImage getRoomPathImage(String room) throws ServiceException {
        URL roomURL = getRoomURL(room);
        try {
            Document tuwienRoomDoc = Jsoup.parse(roomURL, timeout);
            Element plan = tuwienRoomDoc.getElementsByAttributeValueContaining("href", "plaene/gif").first();
            return ImageIO.read(new URL(plan.absUrl("href")));
        } catch (IOException e) {
            throw new ServiceException("service not available", e);
        }
    }
}
