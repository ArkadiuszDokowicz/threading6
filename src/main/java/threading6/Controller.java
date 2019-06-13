package threading6;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Controller {

    private int threadCounter;
    private Graph graph;
    private int vertexCounter;
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
        if(this.CheckVisualization.isSelected()){
            ThreadPoolExecutor algorithms = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCounter);
            ThreadsOptimalizator tho=new ThreadsOptimalizator(threadCounter);
            for (int i = 0; i < this.threadCounter; i++) {
                algorithms.submit(new OrderCrossoverAlg(vertexCounter, 50, 1, this.noVisualConnectionTable,tho));
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
}
