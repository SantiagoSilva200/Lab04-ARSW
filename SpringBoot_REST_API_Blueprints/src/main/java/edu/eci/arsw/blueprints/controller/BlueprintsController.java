package edu.eci.arsw.blueprints.controller;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/blueprints")
public class BlueprintsController {

    @Autowired
    private BlueprintsServices blueprintServices;


    @PostMapping
    public ResponseEntity<?> addNewBlueprint(@RequestBody Blueprint blueprint) {
        try {
            blueprintServices.addNewBlueprint(blueprint);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al agregar el blueprint: " + e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<?> getAllBlueprints() throws BlueprintPersistenceException {
        Set<Blueprint> blueprints = blueprintServices.getAllBlueprints();
        if (blueprints == null || blueprints.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(blueprints);
    }


    @GetMapping("/{author}/{name}")
    public ResponseEntity<?> getBlueprint(@PathVariable String author, @PathVariable String name) {
        try {
            Blueprint blueprint = blueprintServices.getBlueprint(author, name);
            return ResponseEntity.ok(blueprint);
        } catch (BlueprintNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) {
        try {
            Set<Blueprint> blueprints = blueprintServices.getBlueprintsByAuthor(author);
            return ResponseEntity.ok(blueprints);
        } catch (BlueprintNotFoundException | BlueprintPersistenceException e) {
            return ResponseEntity.status(404).body("No se encontraron blueprints para el autor: " + author);
        }
    }
}
