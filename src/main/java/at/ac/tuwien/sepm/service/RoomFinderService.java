package at.ac.tuwien.sepm.service;

import java.awt.image.BufferedImage;
import java.net.URL;

public interface RoomFinderService {

    /**
     * get the path description page
     * @param room room name
     * @return url of room description page
     * @throws ServiceException if room not found or service not available
     */
    public URL getRoomURL(String room) throws ServiceException;

    /**
     * get the path description as image
     * @param room room name
     * @return image of the room path
     * @throws ServiceException if room not found or service not available
     */
    public BufferedImage getRoomPathImage(String room) throws ServiceException;
}
