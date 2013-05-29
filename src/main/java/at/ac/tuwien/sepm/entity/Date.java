package at.ac.tuwien.sepm.entity;

import at.ac.tuwien.sepm.service.TimeFrame;
import org.joda.time.DateTime;

/**
 * @author Markus MUTH
 */
public interface Date {
    public String getName();
    public TimeFrame getTime();
    public DateTime getStart();
    public DateTime getStop();
}