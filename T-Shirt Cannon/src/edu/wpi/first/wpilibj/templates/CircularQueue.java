//Queue to use for t-shirt cannon, written in java 4
import java.util.ArrayList;

public class CircularQueue {

    ArrayList queue = new ArrayList();

    public void addData(Object data){
        queue.add(data);
    }

    public Object get(){
        Object result;
        result = queue.remove(0);
        queue.add(result);
        return result;
    }

    public int getSize(){
        return queue.size();
    }
}
