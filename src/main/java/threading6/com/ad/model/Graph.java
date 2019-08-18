package threading6.com.ad.model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import threading6.com.ad.factory.ShapeFactory;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int VertexAmount;
    private List<Vertex> vertices=new ArrayList<>();
    private String name=this.toString();
    private List<Link> links=new ArrayList<>();
    private List<Label> labels=new ArrayList<>();
    private List<List<Integer>>connectionTable=new ArrayList<>();


    public Graph(int vertexAmount) {
        VertexAmount = vertexAmount;
        ShapeFactory shapeFactory = new ShapeFactory();
        for(int i=0;i<VertexAmount;i++) {
            this.vertices.add((Vertex)shapeFactory.getShape("VERTEX"));
            this.vertices.get(i).setName(Integer.toString(i));
        }
        for(int i=0;i<VertexAmount-1;i++){
            for(int j=0;j<VertexAmount;j++){
                this.links.add((Link)shapeFactory.getShape("LINK"));
            }
        }
    }
    public void fillConnectionTable(){
        connectionTable.clear();
        for(int i=0;i<vertices.size();i++){
            List<Integer> l=new ArrayList();
            for(int x=0;x<vertices.size();x++){
                l.add(999);
            }
            this.connectionTable.add(l);
        }
    }
    public void setVerticesPositions(){
        int vertexCounter=0;
        for(Vertex v:vertices){
            v.setPosition();
            v.setName(Integer.toString(vertexCounter));
            vertexCounter++;
            //System.out.println(v.getName());
        }
    }
    public void setLabelsPositions() {
        for(Link l:this.links){
            Label label=new Label();
            PositionXY labelXY=l.getMiddlePosition();
            label.setLayoutX(labelXY.getPositionX());
            label.setLayoutY(labelXY.getPositionY());
            label.setText(Integer.toString(l.getCost()));
            this.connectionTable.get(Integer.valueOf(l.getLeftConnection())).set(Integer.valueOf(l.getRightConnection()),l.getCost());
            this.connectionTable.get(Integer.valueOf(l.getRightConnection())).set(Integer.valueOf(l.getLeftConnection()),l.getCost());
            //this.connectionTable.get(period).set(verticesPointer,this.links.get(i).getCost());
           // this.connectionTable.get(verticesPointer).set(period,this.links.get(i).getCost());
            this.labels.add(label);
        }
    }
    public void setVertexLabels(){
        for(Vertex v:this.vertices){
            Label label=new Label();
            PositionXY labelXY=v.getPosition();
            label.setLayoutX(labelXY.getPositionX());
            label.setLayoutY(labelXY.getPositionY());
            label.setText(v.getName());
            label.setFont(new Font("Arial", 15));
            label.setTextFill(Color.RED);
            this.labels.add(label);
        }
    }
    public void setLinksPositions() {
        this.fillConnectionTable();
        //for(int j=0;j<this.getVertexAmount();j++){
        int verticesPointer=0;
        int period=0;
        for(int i=0;i<this.links.size();i++){
           if(verticesPointer==this.getVertexAmount()){
               verticesPointer=0;period+=1;
           }
           if(!this.vertices.get(period).getPosition().isTheSame(this.vertices.get(verticesPointer).getPosition())){
               this.links.get(i).setPosition(this.vertices.get(period).getPosition(),this.vertices.get(verticesPointer).getPosition());
               this.links.get(i).setLeftConnection(this.vertices.get(period).getName());
               this.links.get(i).setRightConnection(this.vertices.get(verticesPointer).getName());
              // System.out.println("I="+i+"Period"+period+"VerticesPointer"+verticesPointer);
           }
            verticesPointer+=1;

        }
        this.removeEmptyLinks();
        this.removeDuplicatedLinks();
        //System.out.println(this.connectionTabletoString());
    }


    public int getVertexAmount() {
        return VertexAmount;
    }

    public List<Link> getLinks() {
        return links;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<List<Integer>> getConnectionTable() {
        return connectionTable;
    }

    private void removeDuplicatedLinks() {

        List<Link> linksToRemove=new ArrayList<>();
        for(Link l:links){

            for(Link x:links){
                if((l.isDuplicated(x))&&!linksToRemove.contains(l)){
                    linksToRemove.add(x);
                }
            }

        }

        for(Link l:linksToRemove){
            this.links.remove(l);
        }

    }

    private void removeEmptyLinks(){
        List<Link> linksToRemove=new ArrayList<>();
        for(Link l:this.getLinks()){
            if(l.getLeftConnection()==null){
                linksToRemove.add(l);
            }
        }
        for(Link l:linksToRemove){
            this.links.remove(l);
        }
    }

    public void deleteVertices(){
        for(Vertex v:vertices){
            v.setPosition(new PositionXY(0,0));

        }
    }

    public void deleteLinks(){
        for(Link l:links){
            l.setPosition(new PositionXY(0,0),new PositionXY(0,0));
        }
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void connectionTableToString() {
        for(List l:connectionTable){
        System.out.println(l);
        }
    }

}

