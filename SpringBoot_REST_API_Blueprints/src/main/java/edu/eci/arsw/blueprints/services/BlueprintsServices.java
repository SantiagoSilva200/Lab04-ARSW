/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.eci.arsw.blueprints.filter.BluePrintFilter;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {
   
    @Autowired
    BlueprintsPersistence bpp;

    @Autowired
    private BluePrintFilter filter;
    
    public void addNewBlueprint(Blueprint bp){
        try {
            bpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException e) {
            e.printStackTrace();
        }
    }

    public Set<Blueprint> getAllBlueprints() throws BlueprintPersistenceException {
        Set<Blueprint> blueprints = bpp.getAllBluePrints();
        Set<Blueprint> filteredBlueprints = blueprints.stream().map(filter::filter).collect(Collectors.toSet());

        for (Blueprint bp : filteredBlueprints) {
            System.out.println("🔹 Blueprint filtrado: " + bp.getPoints());
        }

        return filteredBlueprints;
    }


    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException{
        return filter.filter(bpp.getBlueprint(author, name));
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException, BlueprintPersistenceException {
        Set<Blueprint> blueprints = bpp.getBlueprintsByAuthor(author);
        return blueprints.stream().map(filter::filter).collect(Collectors.toSet());
    }
    
}
