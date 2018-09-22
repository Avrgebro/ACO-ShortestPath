import java.util.ArrayList;

public class Ant {
    private Node current;
    private double dtraveled;
    private double ttraveled;
    private ArrayList<Path> paths;

    public Ant(Node start){
        this.setCurrent(start);
        this.dtraveled = 0;
        this.ttraveled = 0;
        paths = new ArrayList<>();
    }


    public Node getCurrent() {
        return current;
    }

    public void setCurrent(Node current) {
        this.current = current;
    }

    public double getDtraveled() {
        return dtraveled;
    }

    public void addDtraveled(double dtraveled) {
        this.dtraveled += dtraveled;
    }

    public double getTtraveled() {
        return ttraveled;
    }

    public void addTtraveled(double ttraveled) {
        this.ttraveled += ttraveled;
    }

    public void addTraveledPath(Path p){
        paths.add(p);
        this.dtraveled += p.getDistance();
        this.ttraveled += p.getTime();
    }

    public ArrayList<Path> getTraveledPaths(){
        return this.paths;
    }
}
