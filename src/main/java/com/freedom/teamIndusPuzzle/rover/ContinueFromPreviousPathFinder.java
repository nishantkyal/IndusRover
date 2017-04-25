package com.freedom.teamIndusPuzzle.rover;

import com.freedom.teamIndusPuzzle.entities.Path;
import com.freedom.teamIndusPuzzle.environment.Environment;
import com.freedom.teamIndusPuzzle.environment.ValidMove;

import java.awt.*;
import java.io.*;

/**
 * Created by nishant.kyal on 3/22/2017.
 */
public class ContinueFromPreviousPathFinder {

    public ContinueFromPreviousPathFinder() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Environment.BEST_OUTPUT_FILE_PATH));
            String line = br.readLine();
            init(new Path(line));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ContinueFromPreviousPathFinder(Path path) {
        init(path);
    }

    private void init(Path path) {

        Environment.MAX_BRANCHING = 32;

        for (int i = 0; i < Environment.NUM_MOVES_TO_POP; i++) {
            path.popMove();
        }

        new BackTrackingPathFinder(path).run();

    }
}
