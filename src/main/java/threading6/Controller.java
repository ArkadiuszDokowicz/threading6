package threading6;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Controller {

    private List<HamiltonCycle> allCycles=new ArrayList<>();
    private List<List<Integer>> allCyclesAsLists=new ArrayList<>();
    private int threadCounter;
    private Graph graph;
    private int vertexCounter;
    private final int firstVertex=1;
    private List<List<Integer>>noVisualConnectionTable=new ArrayList<>();
    @FXML
    AnchorPane GraphArea;
    @FXML
    TextArea VerticesTextArea;
    @FXML
    TextArea ThreadsTextArea;
    @FXML
    Button startButton;
    @FXML
    CheckBox CheckVisualization;

    public void setThreads(){
        threadCounter=Integer.valueOf(this.ThreadsTextArea.getText());
    }
    public void ClearTextVerticesArea(){
        this.VerticesTextArea.clear();
    }

    public void ClearTextThreadsArea(){
        this.ThreadsTextArea.clear();
    }

    public void startAlgorithm(){
        Randomizer rand=new Randomizer();
        List<Integer>cycle=new ArrayList<>();
        cycle.add(this.firstVertex);
        while(cycle.size()!=this.vertexCounter){
            Integer vertex=rand.getRandomInt(0,this.vertexCounter);
            if(!cycle.contains(vertex)){
                cycle.add(vertex);
            }
        }
        cycle.remove(0);
        allCyclesAsLists=this.listPermutations(cycle);
        for(List l:allCyclesAsLists){
            List<Integer>toAdd=l;
            toAdd.add(0,this.firstVertex);
            allCycles.add(new HamiltonCycle(l));
        }
        int cost;
        for(HamiltonCycle hc:this.allCycles){
            for(int i=0;i<hc.getCycle().size()-1;i++){
                cost=this.noVisualConnectionTable.get(hc.getCycle().get(i)).get(hc.getCycle().get(i+1));
                hc.addToCost(cost);
            }
        }
        Collections.sort(this.allCycles);
        System.out.println(allCycles.get(0));

        if(this.CheckVisualization.isSelected()){
            ThreadPoolExecutor algorithms = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCounter);
            ThreadsOptimalizator tho=new ThreadsOptimalizator(threadCounter);
            for (int i = 0; i < this.threadCounter; i++) {
                algorithms.submit(new OrderCrossoverAlg(vertexCounter, 50, this.firstVertex, this.noVisualConnectionTable,tho));
            }
            algorithms.shutdown();

        }
    /* TODO:adapt to new arguments of crossoverAlg
        else {
            ThreadPoolExecutor algorithms = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCounter);

            for (int i = 0; i < this.threadCounter; i++) {
                algorithms.submit(new OrderCrossoverAlg(graph.getVertices().size(), 50, 1, graph.getConnectionTable()));
            }
            algorithms.shutdown();
            //this.graph.connectionTableToString();
        }
        */
    }

    public void WriteGraph() throws Exception{

        if(this.CheckVisualization.isSelected()){
            this.vertexCounter=Integer.valueOf(this.VerticesTextArea.getText());
            this.fillConnectionTable();
            this.randomConnectionCosts();
            System.out.println(this.noVisualConnectionTable);
        }
        else {
            if (this.graph != null) {
                this.graph.deleteLinks();
                this.graph.deleteVertices();
                this.GraphArea.getChildren().clear();
            }
            this.graph = new Graph(Integer.valueOf(this.VerticesTextArea.getText()));
            this.graph.setVerticesPositions();
            this.graph.setLinksPositions();
            this.graph.setLabelsPositions();
            this.graph.setVertexLabels();
            GraphInitialize();
        }
        }




    private void VerticesInitialize(){
        for(Vertex v:this.graph.getVertices()){
            this.GraphArea.getChildren().add(v.getCircle());
        }
    }
    private void LinksInitialize(){
        for(Link link:this.graph.getLinks()){
            this.GraphArea.getChildren().add(link.getLine());
        }
    }

    public void GraphInitialize() {
        this.VerticesInitialize();
        this.LinksInitialize();
        this.LabelInitialize();

    }

    private void LabelInitialize() {
        for(Label l:this.graph.getLabels()){
            this.GraphArea.getChildren().add(l);
        }
    }
    private void fillConnectionTable(){
        noVisualConnectionTable.clear();
        for(int i=0;i<this.vertexCounter;i++){
            List<Integer> l=new ArrayList();
            for(int x=0;x<vertexCounter;x++){
                l.add(999);
            }
            this.noVisualConnectionTable.add(l);
        }
    }
    private void randomConnectionCosts() {
        for(int i=0;i<this.vertexCounter;i++){
            List<Integer> table=this.noVisualConnectionTable.get(i);
            for(int x=0;x<vertexCounter;x++){
                if(x!=i){
                    table.set(x,new Randomizer().getRandomInt(1,10));
                }
            }
        }
    }

    public  List<List<Integer>> listPermutations(List<Integer> list) {


        if (list.size() == 0) {
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            result.add(new ArrayList<Integer>());
            return result;
        }

        List<List<Integer>> returnMe = new ArrayList<List<Integer>>();

        Integer firstElement = list.remove(0);

        List<List<Integer>> recursiveReturn = listPermutations(list);
        for (List<Integer> li : recursiveReturn) {

            for (int index = 0; index <= li.size(); index++) {
                List<Integer> temp = new ArrayList<Integer>(li);
                temp.add(index, firstElement);
                returnMe.add(temp);
            }

        }
        return returnMe;
    }
}
