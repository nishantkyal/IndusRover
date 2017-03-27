package com.freedom.teamIndusPuzzle;

import com.freedom.teamIndusPuzzle.environment.Environment;
import com.freedom.teamIndusPuzzle.environment.ValidMove;
import com.freedom.teamIndusPuzzle.rover.BruteForceRoverPath;
import com.freedom.teamIndusPuzzle.rover.OneAtATimeRoverPath;

import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        new BruteForceRoverPath(Environment.LANDING_COORDINATE, 0, new ArrayList<ValidMove>(), new ArrayList<String>(), null);

        /*for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                for (ValidMove validMove : ValidMove.values()) {
                    Move move = new Move(new Point(x, y), validMove);
                    if (move.points > -10)
                        System.out.println("put(\"" + x + ":" + y + "-" + validMove.toString() + "\", new Move(new Point(" + x + ", " + y + "), ValidMove." + validMove.toString() + ", " + move.points + ", new Point(" + move.end.x + ", " + move.end.y + ")));");
                }
            }
        }*/
    }
}
