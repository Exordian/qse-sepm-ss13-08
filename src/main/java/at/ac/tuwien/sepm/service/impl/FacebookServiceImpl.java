package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.FacebookService;
import at.ac.tuwien.sepm.service.PropertyService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacebookServiceImpl implements FacebookService {
    private static Logger log = Logger.getLogger(FacebookServiceImpl.class);

    @Autowired
    private PropertyService propertyService;

    private FacebookClient facebookClient;

    @Override
    public void authenticate()  {
        facebookClient = new DefaultFacebookClient(propertyService.getProperty("facebook.key"));
    }

    @Override
    public void postToWall(String text) {
        if(facebookClient == null)
            this.authenticate();
        log.info("post to facebook: "+text);
        facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", text));
    }

    @Override
    public void postLvasToWall(List<MetaLVA> metaLvas) {
        String text = "Mein derzeitiger Semesterplan beinhält: \n";
        for(MetaLVA lva: metaLvas) {
            text += lva.getName() + "\n";
        }
        postToWall(text);
    }

}
