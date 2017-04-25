package com.freedom.teamIndusPuzzle;

import com.freedom.teamIndusPuzzle.entities.Move;
import com.freedom.teamIndusPuzzle.entities.Path;
import com.freedom.teamIndusPuzzle.environment.Environment;
import com.freedom.teamIndusPuzzle.environment.ValidMove;
import com.freedom.teamIndusPuzzle.rover.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //new DontRepeatPreviousPathFinder();

        try {
            new ChooseTopPathFinder(ValidMove.valueOf(args[0]));
        } catch (Exception e) {
            new ChooseTopPathFinder();
        }

        /*for (int i = 0; i < 16; i++)
        {
            Path path = new Path();
            path.coordinates = new ArrayList<Point>() {{
                    add(new Point(3, 6));
                    add(new Point(2,4));
            }};
            path.moves = "##WWS##NNW";

            //path.lastEvaluatedMove = ValidMove.ORDERED_MOVES.get(i);
            Environment.executor.execute(new BackTrackingPathFinder(path));
        }*/

        //new ContinueFromPreviousPathFinder();

        /*for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                int movesIndices = 0;
                for (ValidMove validMove : ValidMove.values()) {
                    Move move = new Move(new Point(x, y), validMove);
                    if (move.points > -10)
                        System.out.println("put(\"" + x + ":" + y + "-" + validMove.toString() + "\", new Move(new Point(" + x + ", " + y + "), ValidMove." + validMove.toString() + ", " + move.points + ", new Point(" + move.end.x + ", " + move.end.y + "), " + movesIndices + "));");
                    movesIndices++;
                }
            }
        }*/


    }
}
