package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.service.RoomFinderService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.awt.image.BufferedImage;
import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class WegweiserServiceTest {
    
    @Autowired
    @Qualifier("wegweiserService")
    RoomFinderService roomFinderService;

    @Test
    public void testGetValidRoomURL() throws Exception {
        URL roomUrl = roomFinderService.getRoomURL("HS 18 Czuber Hörsaal");
        Assert.notNull(roomUrl);
    }

    @Test(expected = ServiceException.class)
    public void testGetInvalidRoomURL() throws Exception {
        roomFinderService.getRoomURL("ASDF");
    }

    @Test(expected = ServiceException.class)
    public void testGetNullRoomURL() throws Exception {
        URL roomUrl = roomFinderService.getRoomURL(null);
    }

    @Test
    public void testGetValidRoomPathImage() throws Exception {
        BufferedImage image = roomFinderService.getRoomPathImage("HS 18 Czuber Hörsaal");
        Assert.notNull(image);
    }

    @Test(expected = ServiceException.class)
    public void testGetInvalidRoomPathImage() throws Exception {
        roomFinderService.getRoomPathImage("ASDF");
    }

    @Test(expected = ServiceException.class)
    public void testGetNullRoomPathImage() throws Exception {
        roomFinderService.getRoomPathImage(null);
    }

}
