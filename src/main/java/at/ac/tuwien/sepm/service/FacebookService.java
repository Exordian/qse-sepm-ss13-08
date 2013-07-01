package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.MetaLVA;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jenglisch
 * Date: 01.07.13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public interface FacebookService {
    void authenticate();

    void postToWall(String text);

    void postLvasToWall(List<MetaLVA> metaLvas);
}
