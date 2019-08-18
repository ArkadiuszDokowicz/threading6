package threading6.com.ad.factory;

import threading6.com.ad.model.Link;
import threading6.com.ad.model.Shape;
import threading6.com.ad.model.Vertex;

public class ShapeFactory  {
    public Shape getShape(String shapeType){
        if(shapeType == null){
            return null;
        }
        if(shapeType.equalsIgnoreCase("LINK")){
            return new Link();

        } else if(shapeType.equalsIgnoreCase("VERTEX")){
            return new Vertex();

        }
        return null;
    }

}
