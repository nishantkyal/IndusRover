package com.freedom.teamIndusPuzzle.rover;

import com.freedom.teamIndusPuzzle.environment.Environment;
import com.freedom.teamIndusPuzzle.environment.ValidMove;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nishant.kyal on 3/22/2017.
 */
public class BruteForceRoverPath implements Runnable, IRoverPath {

    private ArrayList<ValidMove> movesSoFar;
    private ArrayList<String> visitedCells;
    private Point currentCoordinate;
    private int accumulatedPoints = 0;
    private ValidMove suggestedMove;

    public BruteForceRoverPath() {}

    public BruteForceRoverPath(Point startCoordinates, int pointsSoFar, ArrayList<ValidMove> movesSoFar, ArrayList<String> visitedCells, ValidMove suggestedMove) {
        this.currentCoordinate = startCoordinates;
        this.accumulatedPoints = pointsSoFar;
        this.visitedCells = visitedCells;
        this.movesSoFar = movesSoFar;
        this.suggestedMove = suggestedMove;

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
                for (String cell: this.visitedCells)
                {
                    visitedCellsString.append(cell);
                    visitedCellsString.append("#");
                }

                Environment.BEST_PATH_SO_FAR = this;
                System.out.println(this.accumulatedPoints + " " + this.movesSoFar.size() + " " + listMoves.toString() + " " + visitedCellsString.toString());
            }
        }
        else
            Environment.executor.execute(this);

    }

    public void run() {

        ArrayList<Move> possibleMoves = new ArrayList<Move>();

        for (ValidMove possibleMove : ValidMove.values()) {
            if ((this.suggestedMove != null && !this.suggestedMove.equals(possibleMove)))
                continue;

            Move move = Environment.COMPUTED_MOVES.get(this.currentCoordinate.x + ":" + this.currentCoordinate.y + "-" + possibleMove.toString());

            if (move == null)
                continue;

            Point coordinateAfterMove = move.end;
            if (!visitedCells.contains(coordinateAfterMove.x + "," + coordinateAfterMove.y)) {
                possibleMoves.add(move);
            }
        }

        Collections.sort(possibleMoves, new Comparator<Move>() {
            public int compare(Move m1, Move m2) {
                return m2.points - m1.points;
            }
        });

        for (int i = 0; i < Math.min(Environment.MAX_BRANCHING, possibleMoves.size()); i++)
        {
            Move move = possibleMoves.get(i);
            ArrayList<ValidMove> newMovesSofar = new ArrayList<ValidMove>(movesSoFar);
            ArrayList<String> newVisitedCells = new ArrayList<String>(visitedCells);

            newMovesSofar.add(move.move);
            newVisitedCells.add(move.end.x + "," + move.end.y);

            new BruteForceRoverPath(move.end, move.points + this.accumulatedPoints, newMovesSofar, newVisitedCells, null);
        }
    }

    public int getPoints() {
        return this.accumulatedPoints;
    }
}
