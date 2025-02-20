package edu.eci.arsw.blueprints.filter.filterTypes;

import edu.eci.arsw.blueprints.filter.BluePrintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubsamplingFilter implements BluePrintFilter {
    @Override
    public Blueprint filter(Blueprint blueprint) {
        List<Point> originalPoints = blueprint.getPoints();
        List<Point> subsamplingPoints = new ArrayList<>();

        for (int i = 0; i < originalPoints.size(); i++) {
            if (i % 2 == 0) {
                subsamplingPoints.add(originalPoints.get(i));
            }
        }

        blueprint.setPoints(subsamplingPoints);
        return blueprint;
    }
}
