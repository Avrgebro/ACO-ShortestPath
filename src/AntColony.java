import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AntColony {

    private int iter; //numero de iteraciones
    private int nAnts; //numero de hormigas
    private Node origin;
    private Node destination;
    private ArrayList<Ant> ants; //arreglo de nAnts hormigas
    private Network network; //colonia con nodos y caminos entre nodos
    //private HashMap<Path, Double> pheromonePerPath; //objeto que anade feromonas a los caminos entre nodos



    public AntColony(int iter, int nAnts){

        this.iter = iter;
        this.nAnts = nAnts;

        network = new Network();
        this.network.generateNetwork();
        System.out.println("Mundo generado");
        this.network.setProbs();
        System.out.println("probabilidades seteadas");


        //if(this.origin==null || this.destination==null) return;



    }


    /*Metodo para crear las hormigas pertenecientes a la colonia*/
    private void GenerateAnts(){

        for(int i = 0; i < nAnts; i++){
            Ant a = new Ant(this.origin);
            this.ants.add(a);
        }
    }

    public void Simulation(String origin, String destination, String h){

        this.origin = network.getNode(origin); // se setea el nodo inicial
        this.destination = network.getNode(destination); // se setea el destino

        ants = new ArrayList<>();
        GenerateAnts();

        for(int i = 0; i<this.iter; i++){
            System.out.println("iter " + i);
            int hor = 0;
            for(Ant a : this.ants){
                int stop = 0;
                while(!a.getCurrent().equals(this.destination)){
                    Node currnode = a.getCurrent();
                    Path selectedPath = getProbablePath(currnode);
                    a.setCurrent(selectedPath.getNf());
                    a.addTraveledPath(selectedPath);
                    stop++;
                    if(stop > 5000){
                        a.setCurrent(this.origin);
                        break;
                    }
                }

                hor++;
                System.out.println(hor);
            }

            HashMap<Path, Double> pheromonePerPath = new HashMap<>();
            double shortestTime = Double.MAX_VALUE;
            Ant fastestAnt = null;

            for(Ant ant : this.ants){
                double time = ant.getTtraveled();
                if(time < shortestTime){
                    fastestAnt = ant;
                    shortestTime = time;
                }

                for(Path path : ant.getTraveledPaths()){
                    if(pheromonePerPath.containsKey(path)){
                        pheromonePerPath.put(path, pheromonePerPath.get(path)+1.0/time);
                    }else{
                        pheromonePerPath.put(path, 1.0/time);
                    }

                }


            }

            for(Path path : fastestAnt.getTraveledPaths()){
                pheromonePerPath.put(path, pheromonePerPath.get(path)+3.0/shortestTime);
            }

            this.network.updateProbs(pheromonePerPath);

            resetAnts();


        }

        ArrayList<Node> shortestpath = new ArrayList<>();
        ArrayList<Path> followedpath = new ArrayList<>();
        shortestpath.add(this.origin);
        Node current = this.origin;
        double finaltime = 0.0;

        while(current != this.destination){
            //System.out.println(current.getName());
            Path mostprob = current.mostProbablePath();
            followedpath.add(mostprob);
            finaltime += mostprob.getTime();
            current = current.mostProbablePath().getNf();
            shortestpath.add(current);
        }

        for(Path p :followedpath){
            System.out.println(p.getNi().getName()+" "+p.getNf().getName()+" "+p.getHi()+"-"+p.getHf());
        }

        String hi = followedpath.get(0).getHi();
        double wait = network.flyTime(h, hi, 0, 0);


        System.out.println(finaltime);



    }

    private void resetAnts(){
        for(Ant a : this.ants){
            a.setCurrent(this.origin);
        }
    }

    private Path getProbablePath(Node n){
        Random random = new Random();
        int stop = 20;

        while(stop>0){
            double r = random.nextDouble();
            double counter = 0.0;
            for(Path path : n.getPaths()){
                counter += path.getProbability();
                if(r < counter){
                    return path;
                }

            }
            stop--;
        }
        return n.getPaths().get(0);

    }



}
