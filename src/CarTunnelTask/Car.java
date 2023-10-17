package CarTunnelTask;

public class Car implements Comparable {

    private int id;
    private long time;

    public Car(int id, long time) {
        this.id = id;
        this.time = time;
    }
    public Car(int id) {
        this.id = id;
        this.time = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", time=" + time +
                '}';
    }

 


    @Override
    public int compareTo(Object o) {
        long compTime = ((Car)o).getTime();
        return (int)getTime() - (int)compTime;
    }
}
