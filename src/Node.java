import java.util.ArrayList;

public class Node {

    private String name;
    private String cont;
    private int gmt;
    private double x;
    private double y;
    private ArrayList<Path> paths;

    public Node(String name, String cont, int gmt, double x, double y) {
        this.setName(name);
        this.cont = cont;
        this.gmt = gmt;
        this.setX(x);
        this.setY(y);
        this.paths = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void addPath(Path p){
        this.paths.add(p);
    }

    public ArrayList<Path> getPaths(){
        return this.paths;
    }

    public Path mostProbablePath(){
        Path mostprob = this.paths.get(0);

        for(int i = 1; i<this.paths.size(); i++){
            Path aux = this.paths.get(i);
            if(aux.getProbability() > mostprob.getProbability()){
                mostprob = aux;
            }
        }

        return mostprob;
    }

    public int getGmt(){
        return this.gmt;
    }
}
