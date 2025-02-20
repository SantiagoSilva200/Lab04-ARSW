package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filter.BluePrintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class BluePrintServiceTest {

    @InjectMocks
    private BlueprintsServices services;

    @Mock
    private BlueprintsPersistence mockPersistence;

    @Mock
    private BluePrintFilter mockFilter;

    @Before
    public void setUp() {

    }

    @Test
    public void testAddNewBlueprint() throws BlueprintPersistenceException, BlueprintNotFoundException {
        Blueprint bp = new Blueprint("Santiago", "Proyecto1", new Point[]{new Point(1, 1), new Point(2, 2)});

        services.addNewBlueprint(bp);
        Mockito.verify(mockPersistence, Mockito.times(1)).saveBlueprint(bp);
    }

    @Test
    public void testGetBlueprintsByAuthor() throws BlueprintPersistenceException, BlueprintNotFoundException {
        Set<Blueprint> blueprints = new HashSet<>();
        Blueprint bp1 = new Blueprint("Santiago", "Plano1", new Point[]{new Point(1, 1), new Point(2, 2)});
        Blueprint bp2 = new Blueprint("Santiago", "Plano2", new Point[]{new Point(3, 3), new Point(4, 4)});
        blueprints.add(bp1);
        blueprints.add(bp2);

        Mockito.when(mockPersistence.getBlueprintsByAuthor("Santiago")).thenReturn(blueprints);
        Mockito.when(mockFilter.filter(bp1)).thenReturn(bp1);
        Mockito.when(mockFilter.filter(bp2)).thenReturn(bp2);

        Set<Blueprint> result = services.getBlueprintsByAuthor("Santiago");

        assertEquals(2, result.size());
        assertTrue(result.contains(bp1));
        assertTrue(result.contains(bp2));
    }

    @Test
    public void testGetAllBlueprints() throws BlueprintPersistenceException {
        Set<Blueprint> blueprints = new HashSet<>();
        Blueprint bp1 = new Blueprint("Autor1", "bp1", new Point[]{new Point(5, 5), new Point(6, 6)});
        Blueprint bp2 = new Blueprint("Autor2", "bp2", new Point[]{new Point(7, 7), new Point(8, 8)});
        blueprints.add(bp1);
        blueprints.add(bp2);

        Mockito.when(mockPersistence.getAllBluePrints()).thenReturn(blueprints);
        Mockito.when(mockFilter.filter(bp1)).thenReturn(bp1);
        Mockito.when(mockFilter.filter(bp2)).thenReturn(bp2);

        Set<Blueprint> result = services.getAllBlueprints();

        assertEquals(2, result.size());
    }
}
