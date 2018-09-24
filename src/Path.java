public class Path {

    private double distance;
    private double time;
    private Node ni;
    private Node nf;
    private String hi;
    private String hf;

    private double probability;

    public Path(String hi, String hf, double time, Node ni, Node nf) {
        this.distance = 0;
        this.setTime(time);
        this.setNi(ni);
        this.setNf(nf);
        this.hi = hi;
        this.hf = hf;
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

    public String getHi(){
        return this.hi;
    }

    public String getHf(){
        return this.hf;
    }
}
