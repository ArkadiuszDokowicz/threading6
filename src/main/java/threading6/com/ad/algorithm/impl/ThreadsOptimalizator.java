package threading6.com.ad.algorithm.impl;

import threading6.com.ad.model.HamiltonCycle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadsOptimalizator {
    private List<HamiltonCycle> parents= new ArrayList<>();
    private final int parentsSize=20;
    private volatile int threadConnected=0;
    private final int threadCounter;
    private Boolean readyToGo=false;
    private PrintWriter out;
    public ThreadsOptimalizator(int threads, PrintWriter out) {
        threadCounter=threads;
        this.out=out;
    }


    //TODO
    //this class should return best result for every part


    public synchronized List<HamiltonCycle> expentance(List<HamiltonCycle>threadParents){

        this.parents.addAll(threadParents);
        Collections.sort(this.parents);
        List<HamiltonCycle> toReturn= new ArrayList<>();

        for(int i=0;i<parentsSize;i++){
            toReturn.add(parents.get(i));
        }

            //TODO return it to json file
        out.println("threads: "+threadCounter+" "+"cost: "+toReturn.get(0).getCost());
        return toReturn;
    }


}
