package threading6.com.ad.model;

import javafx.scene.shape.Line;

public class Link implements Shape {

    private Line line;
    private int cost;
    private String leftConnection;
    private String rightConnection;
    public Link() {
        this.line = new Line();
        this.cost = new Randomizer().getRandomInt(1, 10);
    }

    public String getLeftConnection() {
        return leftConnection;
    }

    public String getRightConnection() {
        return rightConnection;
    }

    @Override
    public void setPosition() {

    }

    @Override
    public void setPosition(PositionXY p) {

    }

    public int getCost() {
        return cost;
    }

    public void setLeftConnection(String leftConnection) {
        this.leftConnection = leftConnection;
    }

    public void setRightConnection(String rightConnection) {
        this.rightConnection = rightConnection;
    }

    @Override
    public void setPosition(PositionXY start, PositionXY end) {
        this.line.setStartX(start.getPositionX());
        this.line.setStartY(start.getPositionY());
        this.line.setEndX(end.getPositionX());
        this.line.setEndY(end.getPositionY());
    }

    public PositionXY getMiddlePosition(){
        double midX,midY;
        midX=(this.line.getStartX()+this.line.getEndX())/2;
        midY=(this.line.getStartY()+this.line.getEndY())/2;
        return new PositionXY(midX,midY);
    }
    @Override
    public void setColor() {

    }

    @Override
    public PositionXY getPosition() {
        PositionXY position=null;
        position=new PositionXY(this.line.getStartX(),this.line.getStartY());
        return position;
    }

    public Line getLine() {
        return line;
    }

    public Boolean isDuplicated(Link argument){
        if((this.leftConnection==argument.rightConnection)&&(this.rightConnection==argument.leftConnection)){
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "Link{" +
                "line=" + line +
                ", cost=" + cost +
                ", leftConnection='" + leftConnection + '\'' +
                ", rightConnection='" + rightConnection + '\'' +
                '}';
    }
}
