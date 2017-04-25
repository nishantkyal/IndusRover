package com.freedom.teamIndusPuzzle.rover;

import com.freedom.teamIndusPuzzle.entities.Move;
import com.freedom.teamIndusPuzzle.entities.Path;
import com.freedom.teamIndusPuzzle.environment.Environment;
import com.freedom.teamIndusPuzzle.environment.ValidMove;

/**
 * Created by nishant.kyal on 4/5/2017.
 */
public class BackTrackingPathFinder implements Runnable {

    private boolean reverse = false;
    private Path path;
    public ValidMove initialMove;

    public BackTrackingPathFinder(Path path) {
        this(path, false);
    }

    public BackTrackingPathFinder(Path path, boolean reverse) {
        this.reverse = reverse;
        this.path = path;
        initialMove = path.lastEvaluatedMove;
    }

    public void run() {
        int maxPoints = 0;
        try {
            while(!endOfWorld(path))
            {
                traverse(path);
                if (path.getCurrentCoordinate().equals(Environment.LANDING_COORDINATE) && path.points > maxPoints)
                {
                    System.out.println("SUCCESS: " + path);
                    maxPoints = path.points;
                }
                path.popMove();
            }
        } catch (StackOverflowError e) {
            e.printStackTrace();
        }
    }

    private Path traverse(Path path) {

        // Try going further by picking first ValidMove in the list
        int indexOfMoveToEvaluate = 0;
        boolean foundNextPath = false;

        if (path.lastEvaluatedMove != null) {
            indexOfMoveToEvaluate = ValidMove.ORDERED_MOVES.indexOf(path.lastEvaluatedMove) + 1;
        }

        // Figure out traverse feasible branch, provided I know where I was
        while (indexOfMoveToEvaluate < 16) {
            Move possibleNextMove = Environment.COMPUTED_MOVES.get(path.getCurrentCoordinate().x + ":" + path.getCurrentCoordinate().y + "-" + ValidMove.ORDERED_MOVES.get(indexOfMoveToEvaluate).toString());
            if (possibleNextMove != null && !path.coordinates.contains(possibleNextMove.end)) {
                path.addMove(possibleNextMove);
                foundNextPath = true;
                break;
            }
            indexOfMoveToEvaluate++;
        }

        // If taking that move lands on LandingCorrdinate, log and step back
        // Else continue down that path
        // If no path, step back
        if (foundNextPath) {
            if (path.getCurrentCoordinate().equals(Environment.LANDING_COORDINATE))
                return path;
            else
                return traverse(path);

        } else {
            //System.out.println("DEAD END at " + path.getCurrentCoordinate().x + "," + path.getCurrentCoordinate().y);
            return path;
        }
    }

    private boolean endOfWorld(Path path) {
        return path.coordinates.size() == 0 && ValidMove.ORDERED_MOVES.get(15).equals(path.lastEvaluatedMove);
    }

}
