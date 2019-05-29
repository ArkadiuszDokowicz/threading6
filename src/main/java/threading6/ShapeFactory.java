package threading6;

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
