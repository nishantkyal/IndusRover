package com.freedom.teamIndusPuzzle.rover;

import com.freedom.teamIndusPuzzle.environment.Environment;
import com.freedom.teamIndusPuzzle.environment.ValidMove;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nishant.kyal on 3/21/2017.
 */
public class Move {

    public Point start;
    public ValidMove move;
    public int points = 0;
    public Point end;

    public Move(Point start, ValidMove move, int points, Point end) {
        this.start = start;
        this.move = move;
        this.points = points;
        this.end = end;
    }

    public Move(Point start, ValidMove move) {
        this.start = start;
        this.move = move;

        List<Point> transforms = Arrays.asList(this.move.getMove());
        Iterator<Point> it = transforms.iterator();

        Point currPoint = (Point) this.start.clone();

        for (Point transform: transforms) {
            currPoint.translate(transform.x, transform.y);
            int tilePoints = 0;
            try {
                tilePoints = Environment.LUNAR_MAP[currPoint.y][currPoint.x];
            } catch (IndexOutOfBoundsException e) {
                tilePoints = -100;
            }
            points += tilePoints;
        }

        this.end = currPoint;

        if (this.end == null)
            throw new NullPointerException();
    }
}
