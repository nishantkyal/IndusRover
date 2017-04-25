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
public class DontRepeatPreviousPathFinder implements Runnable, IPathFinder {

    private Path path;
    private ValidMove suggestedMove;

    private static FileOutputStream allResultsOutputStream;

    public DontRepeatPreviousPathFinder() {
        this(new Path());
    }

    public DontRepeatPreviousPathFinder(Path path) {
        this(path, null);
    }

    public DontRepeatPreviousPathFinder(Path path, ValidMove suggestedMove) {
        this.path = path;
        this.suggestedMove = suggestedMove;

        if (this.path.getCurrentCoordinate().equals(Environment.LANDING_COORDINATE) && this.path.moves.length() != 0 )
        {
            // Dump
            try {
                if (DontRepeatPreviousPathFinder.allResultsOutputStream == null)
                    DontRepeatPreviousPathFinder.allResultsOutputStream = new FileOutputStream(Environment.ALL_OUTPUT_FILE_PATH);
                DontRepeatPreviousPathFinder.allResultsOutputStream.write((this.path.toString() + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (this.path.points > Environment.BEST_PATH_SO_FAR.points)
            {
                Environment.BEST_PATH_SO_FAR = this.path;

                try {
                    FileOutputStream bestOutputFileStream = new FileOutputStream(Environment.BEST_OUTPUT_FILE_PATH);
                    bestOutputFileStream.write((this.path.toString() + "\n\n").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(this.path.toString());
            }
        }
        else
            Environment.executor.execute(this);

    }

    public void run() {
        ArrayList<Move> possibleMoves = new ArrayList<Move>();

        for (ValidMove possibleMove : ValidMove.values()) {
            if ((this.suggestedMove != null && !this.suggestedMove.equals(possibleMove))) {
                continue;
            }

            Move move = Environment.COMPUTED_MOVES.get(this.path.getCurrentCoordinate().x + ":" + this.path.getCurrentCoordinate().y + "-" + possibleMove.toString());

            if (move == null) {
                continue;
            }

            Point coordinateAfterMove = move.end;
            if (!this.path.coordinates.contains(coordinateAfterMove)) {
                possibleMoves.add(move);
            }
        }

        Collections.sort(possibleMoves, new Comparator<Move>() {
            public int compare(Move m1, Move m2) {
                return m2.points - m1.points;
            }
        });

        int branchingFactor = Math.min((int) (Math.floor(this.path.moves.length()/5) + 1), Environment.MAX_BRANCHING);

        for (int i = 0; i < Math.min(branchingFactor, possibleMoves.size()); i++)
        {
            Move move = possibleMoves.get(i);
            ArrayList<Point> newVisitedCells = new ArrayList<Point>(this.path.coordinates);

            newVisitedCells.add(move.end);

            Path newBranchedPath = new Path();
            newBranchedPath.points = move.points + this.path.points;
            newBranchedPath.coordinates = newVisitedCells;
            newBranchedPath.moves = this.path.moves + "##" + move.move.toString();
            newBranchedPath.movesIndices = this.path.movesIndices + move.moveIndex;

            new DontRepeatPreviousPathFinder(newBranchedPath);
        }


    }

}
