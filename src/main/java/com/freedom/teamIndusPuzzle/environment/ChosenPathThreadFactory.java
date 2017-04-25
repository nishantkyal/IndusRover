package com.freedom.teamIndusPuzzle.environment;

import com.freedom.teamIndusPuzzle.rover.BackTrackingPathFinder;

import java.util.concurrent.ThreadFactory;

/**
 * Created by nishant.kyal on 4/5/2017.
 */
public class ChosenPathThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable r) {
        return new Thread(r, String.valueOf(((BackTrackingPathFinder) r).initialMove));
    }
}
