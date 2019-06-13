package threading6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ThreadsOptimalizator {
    private List<HamiltonCycle> parents= new ArrayList<>();
    private final int parentsSize=20;
    private volatile int threadConnected=0;
    private final int threadCounter;
    private Boolean readyToGo=false;
    public ThreadsOptimalizator(int threads) {
        threadCounter=threads;
    }



    public synchronized List<HamiltonCycle> expentance(List<HamiltonCycle>threadParents){
        System.out.println("parents switched");
        this.parents.addAll(threadParents);
        Collections.sort(this.parents);
        List<HamiltonCycle> toReturn= new ArrayList<>();
        for(int i=0;i<parentsSize;i++){
            toReturn.add(parents.get(i));
        }
        return toReturn;
    }


}
