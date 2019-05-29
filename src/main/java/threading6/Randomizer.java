package threading6;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
    public int getRandomInt(Integer start,Integer end){
        int intToReturn=ThreadLocalRandom.current().nextInt(start,end);
        return intToReturn;
    }
}
