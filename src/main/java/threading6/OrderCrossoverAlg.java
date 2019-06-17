package threading6;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class OrderCrossoverAlg implements travellingSalesmanAlgorithm,Runnable {

    private HashMap<List<Integer>,Boolean>wasteCollector=new HashMap<>();
    private String name;
    private final int verticesSize;
    private int population;
    private List<HamiltonCycle> parents= new ArrayList<>();
    private List<HamiltonCycle> wastedCycles= new ArrayList<>();
    private List<HamiltonCycle> fullGeneration=new ArrayList<>();
    private int firstVertex;
    private final int parentsSize=20;
    private List<List<Integer>>connectionTable=new ArrayList<>();
    private ThreadsOptimalizator tho;
    public OrderCrossoverAlg(String  name, int size, int population, int firstVertex, List<List<Integer>> connections, ThreadsOptimalizator thopt) {
       // this.vertices = vertices;
       // this.links = links;
        this.name=name;
        this.population=population;
        this.firstVertex=firstVertex;
        verticesSize = size;
        this.connectionTable=connections;
        this.tho=thopt;
    }

    @Override
    public void firstGeneration() {
        Randomizer rand=new Randomizer();
        for(int i=0;i<population;i++){
            List<Integer> hamiltonCycle=new ArrayList<>();
            hamiltonCycle.add(this.firstVertex);
            while(hamiltonCycle.size()!=verticesSize){
                Integer vertex=rand.getRandomInt(0,this.verticesSize);
                if(!hamiltonCycle.contains(vertex)){
                    hamiltonCycle.add(vertex);
                }
            }
            //System.out.println(hamiltonCycle);
            this.fullGeneration.add(new HamiltonCycle(hamiltonCycle));
        }
    }

    @Override
    public void selection() {
       // System.out.println("selection");
        this.parents.clear();
        int cost;
        for(HamiltonCycle hc:this.fullGeneration){
           for(int i=0;i<hc.getCycle().size()-1;i++){
               cost=this.connectionTable.get(hc.getCycle().get(i)).get(hc.getCycle().get(i+1));
               hc.addToCost(cost);
           }
        }
        Collections.sort(this.fullGeneration);

        for(int i = 0; i<parentsSize; i++){
            this.parents.add(fullGeneration.get(i));
        }
        for(int i=parentsSize;i<this.population;i++){
            this.wasteCollector.putIfAbsent(fullGeneration.get(i).getCycle(),true);
            this.wastedCycles.add(fullGeneration.get(i));
        }
    }
    public int roulette(int roulette){ //this function heavily depends on ParentsSize value
        int ReturnIndex=0;
        if(roulette>260){
            return 1;
        }
        if(roulette>210){
            return new Randomizer().getRandomInt(2,5);
        }
        if(roulette>160){
            return new Randomizer().getRandomInt(6,10);
        }
        if(roulette>140){
            return 10;
        }
        if(roulette>110){
            return 11;
        }
        if(roulette>90){
            return 12;
        }
        if(roulette>70){
            return 13;
        }
        if(roulette>50){
            return 14;
        }
        if(roulette>30){
            return 15;
        }
        if(roulette>10){
            return 16;
        }
        else return new Randomizer().getRandomInt(17,20);


    }
    @Override
    public void crossover() {
       // System.out.println("crossover");
        Randomizer rand=new Randomizer();
        for(int i=parents.size();i<fullGeneration.size();i++){

            int rand1=roulette(rand.getRandomInt(0,360));
            int rand2=roulette(rand.getRandomInt(0,360));
            List<Integer> genotype1 = parents.get(rand1).getCycle();
            List<Integer> genotype2;
            List<Integer> child=new ArrayList<>();

            child.add(firstVertex);
            for(int j=1;j<genotype1.size();j++){
                child.add(null);
            }

            while (rand1==rand2)
            {
               // System.out.println("crossover while");
                 rand2=rand.getRandomInt(0, parents.size()-1);

            }
            genotype2=parents.get(rand2).getCycle();
            Integer genotypeSize=genotype1.size();
            genotypeSize=Math.round(genotypeSize/5);

            for(int y=genotypeSize;y<genotypeSize*4;y++){
                child.set(y,genotype1.get(y));
            }
            int positionGenotype2Counter;
            int acc=0;
            for(int gen=0;gen<child.size();gen++){
                int position=(gen+genotypeSize*4)%child.size();
             ///   System.out.println(position);
                positionGenotype2Counter=position;
                if(child.get(position)==null){
                    while(child.contains(genotype2.get(positionGenotype2Counter))){
                        positionGenotype2Counter++;
                        positionGenotype2Counter=positionGenotype2Counter%child.size();
                       // System.out.println("crossover while2");
                      //  System.out.println("Genotype1: "+genotype1+"Genotype2: "+genotype2+"Child: "+child);
                    }
                    child.set(position,genotype2.get(positionGenotype2Counter));
                }

            }
            fullGeneration.set(i,new HamiltonCycle(child));
        }

        ///System.out.println("Parents:\n"+this.parents.toString());
    }

    public List<HamiltonCycle> getFullGeneration() {
        return fullGeneration;
    }

    @Override
    public void mutation() {
      // System.out.println("mutation");
       for(int i=0;i<fullGeneration.size();i++){
           if(new Randomizer().getRandomInt(0,100)>80){
               List<Integer>cycle=fullGeneration.get(i).getCycle();
               while(this.wasteCollector.containsKey(cycle)){
               Integer rand1=new Randomizer().getRandomInt(1,fullGeneration.get(i).getCycle().size()-2);
               Collections.swap(cycle,rand1,rand1+1);
               fullGeneration.get(i).setCycle(cycle);
               }
           }
           else if(new Randomizer().getRandomInt(0,100)>80){
            Integer rand2=new Randomizer().getRandomInt(1,fullGeneration.get(i).getCycle().size()-1);
               List<Integer>cycle=fullGeneration.get(i).getCycle();
               List<Integer> nonReverse=new ArrayList<>();
               List<Integer> reversed=new ArrayList<>();
               List<Integer> toReturn=new ArrayList<>();
               for(int j=0;j<rand2;j++){
                   nonReverse.add(cycle.get(j));
               }
               for(int j=rand2;j<cycle.size();j++){
                   reversed.add(cycle.get(j));
               }
               Collections.reverse(reversed);
               toReturn.addAll(nonReverse);
               toReturn.addAll(reversed);
               fullGeneration.get(i).setCycle(toReturn);
           }
       }
    }

    @Override
    public String toString() {
        return "OrderCrossoverAlg{" +
                "fullGeneration=" + fullGeneration +
        '}';
    }

    @Override
    public void run() {
        this.firstGeneration();
        this.selection();
        FileWriter file = null;
        try {

            JSONArray testsList = new JSONArray();
            file = new FileWriter(this.name+".json");
            long start = System.nanoTime();
            for(int i=0;i<200;i++) {
                if(i%5==0){
                    this.wasteCollector.clear();
                }
                if(i%50==0){
                    this.parents=tho.expentance(this.parents);
                }
                this.crossover();
                this.mutation();
                this.selection();


                JSONObject logDetails = new JSONObject();
                logDetails.put("method:","Genetic");

                long elapsedTime = (System.nanoTime() - start);
                //System.out.println(elapsedTime);
                logDetails.put("endTime",elapsedTime);
                logDetails.put("best Cycle",this.parents.get(0).getCycle());
                logDetails.put("best Cycle cost",this.parents.get(0).getCost());
                JSONObject logObject = new JSONObject();
                logObject.put("test",logDetails);
                testsList.put(logObject);

            }

            file.write(testsList.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }



        //System.out.println(this.getFullGeneration().toString());
    }
}
