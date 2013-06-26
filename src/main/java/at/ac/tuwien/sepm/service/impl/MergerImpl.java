package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * @author Markus MUTH
 */

public class MergerImpl {
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private ArrayList<MetaLVA> oldMetaLVAs = new ArrayList<>();
    private ArrayList<MetaLVA> newMetaLVAs = new ArrayList<>();
    private ArrayList<Module> oldModules = new ArrayList<>();
    private ArrayList<Module> newModules = new ArrayList<>();

    void reset() {
        oldMetaLVAs = new ArrayList<>();
        newMetaLVAs = new ArrayList<>();
        oldModules = new ArrayList<>();
        newModules = new ArrayList<>();
        logger.info("Merger reset");
    }

    void add(MetaLVA oldMetaLVA, MetaLVA newMetaLVA) {
        boolean lvaEquals = true;

        if(!MetaLVA.equalsForMerge(oldMetaLVA, newMetaLVA) || !lvaEquals){
            newMetaLVA.setId(oldMetaLVA.getId());
            oldMetaLVAs.add(oldMetaLVA);
            newMetaLVAs.add(newMetaLVA);
            logger.debug("new meta lva pair added.");
        }
    }

    void add(Module oldModule, Module newModule) {
        newModule.setId(oldModule.getId());
        oldModules.add(oldModule);
        newModules.add(newModule);
        logger.debug("new module pair added.");
    }

    public boolean metaLvaMergingNecessary() {
        return newMetaLVAs.size()!=0;
    }

    public boolean moduleMergingNecessary() {
        return newMetaLVAs.size()!=0;
    }

    public ArrayList<MetaLVA> getOldMetaLVAs() {
        return oldMetaLVAs;
    }

    public ArrayList<MetaLVA> getNewMetaLVAs() {
        return newMetaLVAs;
    }

    public ArrayList<Module> getOldModules() {
        return oldModules;
    }

    public ArrayList<Module> getNewModules() {
        return newModules;
    }
}
