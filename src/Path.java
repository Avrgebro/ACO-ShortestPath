public class Path {

    private double distance;
    private double time;
    private Node ni;
    private Node nf;

    private double probability;

    public Path(double tiempo, Node ni, Node nf) {
        this.distance = 0;
        this.setTime(tiempo);
        this.setNi(ni);
        this.setNf(nf);
    }


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Node getNi() {
        return ni;
    }

    public void setNi(Node ni) {
        this.ni = ni;
    }

    public Node getNf() {
        return nf;
    }

    public void setNf(Node nf) {
        this.nf = nf;
    }

    public void setProbability(Double p){
        this.probability = p;
    }

    public double getProbability(){
        return this.probability;
    }
}
