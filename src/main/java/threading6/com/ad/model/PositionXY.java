package threading6.com.ad.model;

public class PositionXY {
    private double positionX;
    private double positionY;

    public PositionXY(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }


    @Override
    public String toString() {
        return "PositionXY{" +
                "positionX=" + positionX +
                ", positionY=" + positionY +
                '}';
    }


    public Boolean isTheSame(PositionXY o) {
        if((this.positionX==o.positionX)&&(this.positionY==o.positionY)){
            return true;
        }
        return false;
    }
}
