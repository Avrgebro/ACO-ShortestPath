import java.util.ArrayList;
import java.util.HashMap;

public class Network {

    private ArrayList<Node> nodes;
    private ArrayList<Path> paths;


    public Network(){
        this.nodes = new ArrayList<>();
        this.paths = new ArrayList<>();
    }

    public void AddNode(Node n){
        this.nodes.add(n);
    }

    public void AddPath(Path p){
        this.paths.add(p);
    }

    public ArrayList<Node> getNodes(){
        return this.nodes;
    }

    public ArrayList<Path> getPaths(){
        return this.paths;
    }

    public Node getNode(String name){
        for(Node n : this.nodes){
            if(n.getName().equals(name)) return n;
        }

        return null;
    }

    public void generateNetwork(){
        Node n1 = new Node("SBBR", "America del Sur", -3, -15.8267, -47.9218);
        Node n2 = new Node("SPIM", "America del Sur", -5, -12.0464, -77.0428);
        Node n3 = new Node("EDDI", "Europa", 2, 52.52, 13.405);
        Node n4 = new Node("LEMD", "Europa", 2, 40.4168, -3.7038);

        n1.addPath(new Path(240, n1, n2));
        n1.addPath(new Path(720, n1, n3));
        n1.addPath(new Path(610, n1, n4));

        n2.addPath(new Path(180, n2, n1));
        //n2.addPath(new Path(600, n2, n3));
        n2.addPath(new Path(700, n2, n4));

        n3.addPath(new Path(710, n3, n1));
        n3.addPath(new Path(610, n3, n2));
        n3.addPath(new Path(150, n3, n4));

        n4.addPath(new Path(700, n4, n1));
        n4.addPath(new Path(680, n4, n2));
        n4.addPath(new Path(120, n4, n3));

        this.nodes.add(n1);
        this.nodes.add(n2);
        this.nodes.add(n3);
        this.nodes.add(n4);
    }

    public void setProbs(){
        for(Node n : this.nodes){
            ArrayList<Path> ps = n.getPaths();
            for(Path p : ps){
                p.setProbability(1.0/ps.size());
            }
        }
    }

    public void updateProbs(HashMap<Path, Double> pher){

        for(Node n : this.nodes){
            ArrayList<Path> pat = n.getPaths();
            double total = 0.0;
            for(Path p : pat){
                if(pher.get(p) != null){
                    total += pher.get(p);
                }
            }

            for(Path p : pat){
                if(pher.get(p) != null){
                    double newprob = (p.getProbability() * pher.get(p))/total;
                    p.setProbability(newprob);
                }
            }

        }
    }


}
