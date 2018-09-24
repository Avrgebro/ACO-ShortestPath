import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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


        CSVReader reader = new CSVReader();

        this.nodes = reader.readCities("/home/jose/Downloads/aeropuertos.csv");
        ArrayList<String[]> paths = reader.readPaths("/home/jose/Downloads/planes_vuelo.txt");

        System.out.println("Termine de leer");
        System.out.println(this.nodes.size());
        System.out.println(paths.size());

        for(String[] p : paths){
            //obtengo los nodos para ese camino
            Node n1 = this.getNode(p[0]);
            Node n2 = this.getNode(p[1]);
            // genero el tiempo necesario de vuelo considerando huso horario

            double time = this.flyTime(p[2], p[3], n1.getGmt(), n2.getGmt());
            //

            Path npath = new Path(p[2], p[3], time, n1, n2);

            //this.paths.add(npath);
            n1.addPath(npath);
        }

    }

    public double flyTime(String h1, String h2, int u1, int u2){

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date t1 = null;
        Date t2 = null;
        try {
            t1 = formatter.parse(h1);
            t2 = formatter.parse(h2);
        } catch (ParseException e){

        }

        long p = 0;

        if(t1.after(t2)){
            Calendar c = Calendar.getInstance();
            c.setTime(t2);
            c.add(Calendar.DATE, 1);
            t2 = c.getTime();

            long minutes = t2.getTime() - t1.getTime();
            p = TimeUnit.MILLISECONDS.toMinutes(minutes);


        } else {
            long minutes = t2.getTime() - t1.getTime();
            p = TimeUnit.MILLISECONDS.toMinutes(minutes);
        }

        double r = (double) p;
        r -= (double) (u1 + u2);

        return r;
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
