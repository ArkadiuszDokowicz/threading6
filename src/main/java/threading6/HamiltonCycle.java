package threading6;

import java.util.ArrayList;
import java.util.List;

public class HamiltonCycle implements Comparable<HamiltonCycle>  {
    private List<Integer> cycle;
    private Integer cost=0;

    public Integer getCost() {
        return cost;
    }

    public void addToCost(int value){
        this.cost=this.cost+value;
    }
    public HamiltonCycle(List<Integer> cycle) {
        this.cycle = cycle;
    }

    public List<Integer> getCycle() {
        return cycle;
    }

    public void setCycle(List<Integer> cycle) {
        this.cycle = cycle;
    }

    @Override
    public String toString() {
        return "HamiltonCycle{" +
                "cycle=" + cycle +
                ", cost=" + cost +
                "}\n";
    }

    @Override
    public int compareTo(HamiltonCycle o) {
        return this.cost.compareTo(o.cost);
    }
}
