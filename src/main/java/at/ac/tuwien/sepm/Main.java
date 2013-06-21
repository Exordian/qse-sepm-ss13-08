package at.ac.tuwien.sepm;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // Log4J
        //BasicConfigurator.configure();
        PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties"));

        final ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ctx.getBean("mainFrame");
            }
        });
    }

}
