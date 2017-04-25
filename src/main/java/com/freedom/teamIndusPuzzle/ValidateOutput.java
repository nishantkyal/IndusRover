package com.freedom.teamIndusPuzzle;

import com.freedom.teamIndusPuzzle.environment.Environment;

import java.awt.*;

/**
 * Created by nishant.kyal on 4/2/2017.
 */
public class ValidateOutput {
    public static void main(String args[]) throws Exception {
        String outputString = "##NWW##NNW##SSW##EEN##NNW##SSW##SSE##EEN##NNW##SSW##NNW##EEN##SSE##NNE##EES##EEN##SSW##NNW##SSW##SSE##SSW##SSE##EEN##NNW##NNE##NNW##WWN##SSE##NNE##SSE##SSW##WWN##SSE##SSE##WWS##NNW##NNW##SSW##SSE##WWN##EEN##NNW##NNW##EEN##SSE##SSE##SSE##EEN##SSW##WWN##NNW##EES##NNW##WWS##SSE##WWS##NNW##WSS##NEE##NWW##ESS##NEE##NNW##NEE".replace("##", "");

        Point currentCoordinate = Environment.LANDING_COORDINATE;
        int index = 0;
        int points = 0;

        for (char move:outputString.toCharArray()) {
            if (String.valueOf(move).equals("N")) {
                currentCoordinate.translate(0, -1);
            } else if (String.valueOf(move).equals("S")) {
                currentCoordinate.translate(0, 1);
            } else if (String.valueOf(move).equals("E")) {
                currentCoordinate.translate(1, 0);
            } else if (String.valueOf(move).equals("W")) {
                currentCoordinate.translate(-1, 0);
            }

            index++;
            points += Environment.LUNAR_MAP[currentCoordinate.y][currentCoordinate.x];

            if (Environment.FORBIDDEN_CELLS.contains(currentCoordinate))
            {
                throw new Exception("Landed on forbidden cell while moving " + move + " at index " + index);
            }
        }

        System.out.println(points);
    }
}
