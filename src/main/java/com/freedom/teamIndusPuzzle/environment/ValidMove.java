package com.freedom.teamIndusPuzzle.environment;

import java.awt.*;

/**
 * Created by nishant.kyal on 3/21/2017.
 */
public enum ValidMove {
    NNW(new Point[]{new Point(0, -1), new Point(0, -1), new Point(-1, 0)}),
    NNE(new Point[]{new Point(0, -1), new Point(0, -1), new Point(1, 0)}),
    SSE(new Point[]{new Point(0, 1), new Point(0, 1), new Point(1, 0)}),
    SSW(new Point[]{new Point(0, 1), new Point(0, 1), new Point(-1, 0)}),
    EEN(new Point[]{new Point(1, 0), new Point(1, 0), new Point(0, -1)}),
    EES(new Point[]{new Point(1, 0), new Point(1, 0), new Point(0, 1)}),
    WWN(new Point[]{new Point(-1, 0), new Point(-1, 0), new Point(0, -1)}),
    WWS(new Point[]{new Point(-1, 0), new Point(-1, 0), new Point(0, 1)}),
    NEE(new Point[]{new Point(0, -1), new Point(1, 0), new Point(1, 0)}),
    NWW(new Point[]{new Point(0, -1), new Point(-1, 0), new Point(-1, 0)}),
    SEE(new Point[]{new Point(0, 1), new Point(1, 0), new Point(1, 0)}),
    SWW(new Point[]{new Point(0, 1), new Point(-1, 0), new Point(-1, 0)}),
    ENN(new Point[]{new Point(1, 0), new Point(0, -1), new Point(0, -1)}),
    ESS(new Point[]{new Point(1, 0), new Point(0, 1), new Point(0, 1)}),
    WNN(new Point[]{new Point(-1, 0), new Point(0, -1), new Point(0, -1)}),
    WSS(new Point[]{new Point(-1, 0), new Point(0, 1), new Point(0, 1)});

    private final Point[] move;

    ValidMove(Point[] move) {
        this.move = move;
    }

    public Point[] getMove() {
        return move;
    }
}
