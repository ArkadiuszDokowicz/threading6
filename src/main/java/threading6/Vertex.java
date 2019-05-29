package threading6;

import javafx.geometry.Pos;
import javafx.scene.shape.Circle;

public class Vertex implements Shape {
    private Circle circle;
    private String name;

    public Vertex() {
        this.circle = new Circle();
        this.name = this.toString();
    }

    public String getName() {
        return name;
    }


    @Override
    public void setPosition() {
        //TEST
        this.circle.setRadius(5);
        this.circle.setLayoutX(new Randomizer().getRandomInt(10,700));
        this.circle.setLayoutY(new Randomizer().getRandomInt(10,550));

    }

    @Override
    public void setPosition(PositionXY p) {
        this.circle.setLayoutX(p.getPositionX());
        this.circle.setLayoutY(p.getPositionY());
    }

    @Override
    public void setPosition(PositionXY p1, PositionXY p2) {

    }


    @Override
    public void setColor() {

    }

    @Override
    public PositionXY getPosition() {
        PositionXY position=null;
        position=new PositionXY(circle.getLayoutX(),circle.getLayoutY());
       // System.out.println();
        return  position;

    }


    public Circle getCircle() {
        return circle;
    }

    public void setName(String name) {
    this.name=name;
    }
}
