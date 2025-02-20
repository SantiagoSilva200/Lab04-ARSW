package edu.eci.arsw.blueprints.filter.filterTypes;

import edu.eci.arsw.blueprints.filter.BluePrintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class RedundancyFilter implements BluePrintFilter {
    @Override
    public Blueprint filter(Blueprint blueprint) {
        List<Point> filteredPoints = new ArrayList<>();
        List<Point> originalPoints = blueprint.getPoints();

        if (!originalPoints.isEmpty()) {
            filteredPoints.add(originalPoints.get(0));
            for (int i = 1; i < originalPoints.size(); i++) {
                Point current = originalPoints.get(i);
                Point lastAdded = filteredPoints.get(filteredPoints.size() - 1);

                if (current.getX() != lastAdded.getX() || current.getY() != lastAdded.getY()) {
                    filteredPoints.add(current);
                }
            }
        }

        blueprint.setPoints(filteredPoints);
        return blueprint;
    }
}
