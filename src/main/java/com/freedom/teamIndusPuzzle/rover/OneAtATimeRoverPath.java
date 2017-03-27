package com.freedom.teamIndusPuzzle.rover;

import com.freedom.teamIndusPuzzle.environment.Environment;
import com.freedom.teamIndusPuzzle.environment.ValidMove;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by nishant.kyal on 3/22/2017.
 */
public class OneAtATimeRoverPath implements Runnable, IRoverPath {

    private ArrayList<ValidMove> movesSoFar;
    private ArrayList<Point> visitedCells;
    private Point currentCoordinate;
    private int accumulatedPoints = 0;
    private boolean hasPassedOrigin = false;

    public OneAtATimeRoverPath() {}

    public OneAtATimeRoverPath(Point startCoordinates, int pointsSoFar, ArrayList<ValidMove> movesSoFar, ArrayList<Point> visitedCells, boolean hasPassedOrigin) {
        this.currentCoordinate = startCoordinates;
        this.accumulatedPoints = pointsSoFar;
        this.visitedCells = visitedCells;
        this.movesSoFar = movesSoFar;
        this.hasPassedOrigin = hasPassedOrigin;

        if (this.currentCoordinate.equals(Environment.LANDING_COORDINATE) && movesSoFar.size() != 0 )
        {
            if (this.accumulatedPoints > Environment.BEST_PATH_SO_FAR.getPoints())
            {
                StringBuilder listMoves = new StringBuilder();
                for (ValidMove move: this.movesSoFar)
                {
                    listMoves.append(move.toString());
                    listMoves.append("|");
                }

                StringBuilder visitedCellsString = new StringBuilder();
                for (Point cell: this.visitedCells)
                {
                    visitedCellsString.append(cell.x + "," + cell.y);
                    visitedCellsString.append("#");
                }

                Environment.BEST_PATH_SO_FAR = this;
                System.out.println(this.accumulatedPoints + " " + this.movesSoFar.size() + " " + listMoves.toString() + " " + visitedCellsString.toString());
                hasPassedOrigin = true;
                //run();
            }
        }
        else
            run();
    }

    public void run() {
        boolean hasNextPath = false;
        for (ValidMove possibleMove : ValidMove.values())
        {
            Move move = Environment.COMPUTED_MOVES.get(this.currentCoordinate.x + ":" + this.currentCoordinate.y + "-" + possibleMove.toString());

            if (move == null)
                continue;

            Point coordinateAfterMove = move.end;

            if (!visitedCells.contains(coordinateAfterMove)) {
                ArrayList<ValidMove> newMovesSofar = new ArrayList<ValidMove>(movesSoFar);
                ArrayList<Point> newVisitedCells = new ArrayList<Point>(visitedCells);

                newMovesSofar.add(possibleMove);
                newVisitedCells.add(move.end);


                /*try {
                    long averagePointsPerStep = this.accumulatedPoints/this.movesSoFar.size();
                    Thread.sleep(10/averagePointsPerStep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ArithmeticException e) {

                }*/

                Environment.executor.execute(new OneAtATimeRoverPath(move.end, move.points + this.accumulatedPoints, newMovesSofar, newVisitedCells, this.hasPassedOrigin));

                hasNextPath = true;

            }
        }

        /*if (!hasNextPath)
            System.out.println("End of path at: " + this.currentCoordinate.x + ", " + this.currentCoordinate.y);*/

    }


    public int getPoints() {
        return this.accumulatedPoints;
    }
}
