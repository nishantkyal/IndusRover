package com.freedom.teamIndusPuzzle.entities;

import com.freedom.teamIndusPuzzle.environment.Environment;
import com.freedom.teamIndusPuzzle.environment.ValidMove;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nishant.kyal on 3/27/2017.
 */
public class Path {
    public String moves = "";
    public String movesIndices = "";
    public ArrayList<Point> coordinates = new ArrayList<Point>();
    public int points = 0;
    public ValidMove lastEvaluatedMove;

    public Path() {

    }

    /*public Path(String outputString) {
        String[] split = outputString.split("\\s");

        this.points = Integer.parseInt(split[0]);

        String[] movesSplit = split[2].split("##");
        for (String moveString: movesSplit) {
            this.moves.add(ValidMove.valueOf(moveString));
        }

        String[] coordinatesSplit = split[3].split("##");
        for (String coordinateString: coordinatesSplit) {
            String[] coordinateSplit = coordinateString.split(",");
            int x = Integer.parseInt(coordinateSplit[0]);
            int y = Integer.parseInt(coordinateSplit[1]);

            this.coordinates.add(new Point(x, y));
        }

        this.movesIndices = split[4];
    }
*/

    public Path(String outputString) {
        String[] split = outputString.split("\\s");

        this.points = Integer.parseInt(split[0]);


        ArrayList<ValidMove> movesList = new ArrayList<ValidMove>(Arrays.asList(ValidMove.values()));

        this.moves = split[2];

        String[] coordinatesSplit = split[3].split("##");
        for (String coordinateString : coordinatesSplit) {
            try {
                String[] coordinateSplit = coordinateString.split(",");
                int x = Integer.parseInt(coordinateSplit[0]);
                int y = Integer.parseInt(coordinateSplit[1]);

                this.coordinates.add(new Point(x, y));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.movesIndices = split[4];

        /*
        Point currentCoordinate = Environment.LANDING_COORDINATE;
        for (char moveIndex:movesIndices.toCharArray()) {
            ValidMove takenMove = movesList.get(Integer.parseInt(String.valueOf(moveIndex), 16));
            Move move = Environment.COMPUTED_MOVES.get(currentCoordinate.x + ":" + currentCoordinate.y + "-" + takenMove.toString());
            this.moves.add(takenMove);
            try {
                this.coordinates.add(move.end);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            currentCoordinate = move.end;
        }*/
    }

    public Path(String moves, ArrayList<Point> coordinates, String moveIndex, int points) {
        this.moves = moves;
        this.coordinates = coordinates;
        this.points = points;
        this.movesIndices = moveIndex;
    }

    public Point getCurrentCoordinate() {
        try {
            return coordinates.get(coordinates.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            return Environment.LANDING_COORDINATE;
        }
    }

    public String toString() {
        StringBuilder outputString = new StringBuilder();

        outputString.append(this.points);
        outputString.append(" ");

        outputString.append(this.moves.split("##").length);
        outputString.append(" ");


        outputString.append(this.moves);
        outputString.append(" ");

        for (Point coordinate : this.coordinates) {
            outputString.append(coordinate.x + "," + coordinate.y);
            outputString.append("##");
        }
        outputString.append(" ");

        //outputString.append(movesIndices);

        return outputString.toString();
    }

    public void addMove(Move move) {
        this.coordinates.add(move.end);
        this.points += move.points;
        this.moves += "##" + move.move.toString();
        lastEvaluatedMove = null;
    }

    public Move popMove() {
        int indexOfLastButOneDelimiter = moves.lastIndexOf("##");
        ValidMove lastMove = ValidMove.valueOf(moves.substring(indexOfLastButOneDelimiter + 2));
        try {
            coordinates.remove(coordinates.size() - 1);
            points -= Environment.COMPUTED_MOVES.get(getCurrentCoordinate().x + ":" + getCurrentCoordinate().y + "-" + lastMove.toString()).points;
            moves = moves.substring(0, indexOfLastButOneDelimiter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        lastEvaluatedMove = lastMove;
        return Environment.COMPUTED_MOVES.get(getCurrentCoordinate().x + ":" + getCurrentCoordinate().y + "-" + lastMove);
    }
}
