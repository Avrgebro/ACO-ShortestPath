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



    public AntColony(int iter, int nAnts, String origin, String destination){

        this.iter = iter;
        this.nAnts = nAnts;

        network = new Network();
        this.network.generateNetwork();
        this.network.setProbs();


        this.origin = network.getNode(origin); // se setea el nodo inicial
        this.destination = network.getNode(destination); // se setea el destino

        if(this.origin==null || this.destination==null) return;

        ants = new ArrayList<>();
        GenerateAnts();

    }


    /*Metodo para crear las hormigas pertenecientes a la colonia*/
    private void GenerateAnts(){

        for(int i = 0; i < nAnts; i++){
            Ant a = new Ant(this.origin);
            this.ants.add(a);
        }
    }

    public void Simulation(){
        for(int i = 0; i<this.iter; i++){

            for(Ant a : this.ants){
                while(!a.getCurrent().equals(this.destination)){
                    Node currnode = a.getCurrent();
                    Path selectedPath = getProbablePath(currnode);
                    a.setCurrent(selectedPath.getNf());
                    a.addTraveledPath(selectedPath);

                }
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
                pheromonePerPath.put(path, pheromonePerPath.get(path)+2.0/shortestTime);
            }

            this.network.updateProbs(pheromonePerPath);

            resetAnts();


        }

        ArrayList<Node> shortestpath = new ArrayList<>();
        shortestpath.add(origin);
        Node current = origin;
        double finaltime = 0.0;

        while(current != destination){
            finaltime += current.mostProbablePath().getTime();
            current = current.mostProbablePath().getNf();
            shortestpath.add(current);
        }

        for(Node n : shortestpath){
            System.out.println(n.getName());
        }
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
