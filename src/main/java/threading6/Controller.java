package threading6;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Controller {

    private int threadCounter;
    private Graph graph;

    @FXML
    AnchorPane GraphArea;
    @FXML
    TextArea VerticesTextArea;
    @FXML
    TextArea ThreadsTextArea;
    @FXML
    Button startButton;


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
        ThreadPoolExecutor algorithms= (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCounter);

        for(int i=0;i<this.threadCounter;i++){
            algorithms.submit(new OrderCrossoverAlg(graph.getVertices().size(),50,1,graph.getConnectionTable()));
        }
        algorithms.shutdown();
       //this.graph.connectionTableToString();

    }

    public void WriteGraph() throws Exception{

        if(this.graph!=null){
            this.graph.deleteLinks();
            this.graph.deleteVertices();
            this.GraphArea.getChildren().clear();
        }
        this.graph=new Graph(Integer.valueOf(this.VerticesTextArea.getText()));
        this.graph.setVerticesPositions();
        this.graph.setLinksPositions();
        this.graph.setLabelsPositions();
        this.graph.setVertexLabels();
        GraphInitialize();
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
}
