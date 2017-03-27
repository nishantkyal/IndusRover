package com.freedom.teamIndusPuzzle.rover;

import com.freedom.teamIndusPuzzle.environment.Environment;
import com.freedom.teamIndusPuzzle.environment.ValidMove;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by nishant.kyal on 3/24/2017.
 */
public class MoveTest {

    @org.junit.Test
    public void perform() throws Exception {
        System.out.println("huha");
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                for (ValidMove validMove : ValidMove.values()) {
                    Move move = new Move(new Point(x, y), validMove);
                    System.out.println(move.points);
                }
            }
        }
        assert (true);
    }

}