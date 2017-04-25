package com.freedom.teamIndusPuzzle.rover;

import com.freedom.teamIndusPuzzle.entities.Move;
import com.freedom.teamIndusPuzzle.entities.Path;
import com.freedom.teamIndusPuzzle.environment.Environment;
import com.freedom.teamIndusPuzzle.environment.ValidMove;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nishant.kyal on 3/22/2017.
 */
public class MaxStepsPathFinder implements Runnable, IPathFinder {

    private Path path;
    private ValidMove suggestedMove;
    private ArrayList<String> alreadyTakenPaths = new ArrayList<String>();

    private static FileOutputStream allResultsOutputStream;

    public MaxStepsPathFinder() {
        this(new Path());
    }

    public MaxStepsPathFinder(ValidMove suggestedMove) {
        this(new Path(), suggestedMove);
    }

    public MaxStepsPathFinder(Path path) {
        this(path, null);
    }

    public MaxStepsPathFinder(Path path, ValidMove suggestedMove) {
        this.path = path;
        this.suggestedMove = suggestedMove;

        if (this.path.movesIndices.length() == 20 && !this.alreadyTakenPaths.contains(this.path.movesIndices))
        {
            this.alreadyTakenPaths.add(this.path.movesIndices);

            try {
                if (MaxStepsPathFinder.allResultsOutputStream == null)
                    MaxStepsPathFinder.allResultsOutputStream = new FileOutputStream(Environment.ALL_OUTPUT_FILE_PATH);
                MaxStepsPathFinder.allResultsOutputStream.write((this.path.toString() + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
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

            Move move = Environment.COMPUTED_MOVES.get(this.path.getCurrentCoordinate().x + ":" + this.path.getCurrentCoordinate().y + "-" + possibleMove.toString());

            if (move == null)
                continue;

            Point coordinateAfterMove = move.end;
            if (!this.path.coordinates.contains(coordinateAfterMove)) {
                possibleMoves.add(move);
            }
        }

        for (int i = 0; i < possibleMoves.size(); i++)
        {
            Move move = possibleMoves.get(i);
            ArrayList<Point> newVisitedCells = new ArrayList<Point>(this.path.coordinates);

            newVisitedCells.add(move.end);

            Path newBranchedPath = new Path();
            newBranchedPath.points = move.points + this.path.points;
            newBranchedPath.coordinates = newVisitedCells;
            newBranchedPath.moves = this.path.moves + "##" + move.move.toString();
            newBranchedPath.movesIndices = this.path.movesIndices + move.moveIndex;

            new MaxStepsPathFinder(newBranchedPath);
        }

    }

}
