package com.freedom.teamIndusPuzzle.rover;

import com.freedom.teamIndusPuzzle.entities.Move;
import com.freedom.teamIndusPuzzle.entities.Path;
import com.freedom.teamIndusPuzzle.environment.Environment;
import com.freedom.teamIndusPuzzle.environment.ValidMove;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by nishant.kyal on 3/22/2017.
 */
public class ChooseTopPathFinder implements IPathFinder {

    public static ConcurrentHashMap<Integer, AtomicInteger> PATH_TRACKER = new ConcurrentHashMap<Integer, AtomicInteger>();
    private static ValidMove SUGGESTED_MOVE;
    private static int COUNTER = 0;

    private static FileOutputStream allResultsOutputStream;

    public ChooseTopPathFinder() {
        this(new Path());
    }

    public ChooseTopPathFinder(ValidMove suggestedMove) {
        this(new Path(), suggestedMove);
    }

    public ChooseTopPathFinder(Path path) {
        this(path, null);
    }

    public ChooseTopPathFinder(Path path, ValidMove suggestedMove) {
        ChooseTopPathFinder.run(path, suggestedMove);
    }

    public static void run(Path path, ValidMove suggestedMove) {
        ChooseTopPathFinder.SUGGESTED_MOVE = suggestedMove;

        try {
            PATH_TRACKER.get(path.movesIndices.length()/10).incrementAndGet();
        } catch (NullPointerException e) {
            PATH_TRACKER.put(path.movesIndices.length()/10, new AtomicInteger(1));
        }

        ArrayList<Move> possibleMoves;

        /*possibleMoves = new ArrayList<Move>();
        for (ValidMove possibleMove : ValidMove.values()) {
            if ((suggestedMove != null && !suggestedMove.equals(possibleMove)))
                continue;

            Move move = Environment.COMPUTED_MOVES.get(path.getCurrentCoordinate().x + ":" + path.getCurrentCoordinate().y + "-" + possibleMove.toString());

            if (move == null)
                continue;

            Point coordinateAfterMove = move.end;
            if (!path.coordinates.contains(coordinateAfterMove)) {
                possibleMoves.add(move);
            }
        }*/

        possibleMoves = (ArrayList<Move>) Environment.INDEXED_COMPUTED_MOVES.get(path.getCurrentCoordinate().x + "-" + path.getCurrentCoordinate().y).clone();

        Iterator<Move> it = possibleMoves.iterator();
        while( it.hasNext() ) {
            Move foo = it.next();
            boolean matchesSuggestedMove = suggestedMove == null || foo.move.equals(suggestedMove);
            boolean alreadyVisitedCell = path.coordinates.contains(foo.end);
            if(!matchesSuggestedMove || alreadyVisitedCell)
                it.remove();
        }

        int branchingFactor = Math.min(Environment.MAX_BRANCHING, possibleMoves.size());

        //branchingFactor = Math.min((int) (Math.floor(path.movesIndices.length()/5) + 1), Environment.MAX_BRANCHING);

        /*if (Math.random() > 1)
            branchingFactor = 4;*/

        int counter = 0;
        while (counter < branchingFactor)
        {
            Move move = possibleMoves.get(possibleMoves.size() - counter - 1);
            ArrayList<Point> newVisitedCells = new ArrayList<Point>(path.coordinates);

            newVisitedCells.add(move.end);

            Path newBranchedPath = new Path();
            newBranchedPath.points = move.points + path.points;
            newBranchedPath.coordinates = newVisitedCells;
            newBranchedPath.moves = path.moves + "##" + move.move.toString();
            newBranchedPath.movesIndices = path.movesIndices + move.moveIndex;

            if (newBranchedPath.getCurrentCoordinate().equals(Environment.LANDING_COORDINATE) && newBranchedPath.movesIndices.length() != 0 )
            {
            /*// Dump
            try {
                if (ChooseTopPathFinder.allResultsOutputStream == null)
                    ChooseTopPathFinder.allResultsOutputStream = new FileOutputStream(Environment.ALL_OUTPUT_FILE_PATH);
                ChooseTopPathFinder.allResultsOutputStream.write((this.path.toString() + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }*/

                if (newBranchedPath.points > Environment.BEST_PATH_SO_FAR.points)
                {
                    Environment.BEST_PATH_SO_FAR = newBranchedPath;

                    try {
                        FileOutputStream bestOutputFileStream = new FileOutputStream(Environment.BEST_OUTPUT_FILE_PATH);
                        bestOutputFileStream.write((newBranchedPath.toString()).getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println(newBranchedPath.toString());
                }
            }
            else {
            /*if (this.path.points > Environment.BEST_PATH_SO_FAR.points)
                System.out.println("Propective: " + this.path.toString());*/
                if (COUNTER % 1000000 == 0)
                {
                    //System.out.println(new Date().toString() + " " + ChooseTopPathFinder.SUGGESTED_MOVE.toString() + " " + PATH_TRACKER.toString());
                }
                COUNTER++;
                ChooseTopPathFinder.run(newBranchedPath, null);
            }

            counter++;

        }

    }

}
