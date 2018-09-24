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

        /*ArrayList<Path> aux = this.network.getPaths();
        for(Path p : aux){
            if(p.getNi().getName().equals(origin) && p.getNf().getName().equals(destination)){
                p.setProbability(0.2);
            }
        }*/

        ants = new ArrayList<>();
        GenerateAnts();

        for(int i = 0; i<this.iter; i++){
            //System.out.println("iter " + i);
            int hor = 0;
            for(Ant a : this.ants){
                int stop = 0;
                Path prevp = null;
                while(!a.getCurrent().equals(this.destination)){
                    Node currnode = a.getCurrent();
                    Path selectedPath = getProbablePath(currnode);
                    double layover = 0.0;
                    if(prevp != null){
                        layover = this.network.flyTime(prevp.getHf(), selectedPath.getHi(), 0, 0);
                    }
                    a.setCurrent(selectedPath.getNf());
                    a.addTraveledPath(selectedPath);
                    a.addTtraveled(layover);
                    /*stop++;
                    if(stop > 5000){
                        a.setCurrent(this.origin);
                        break;
                    }*/
                }

                hor++;
                //System.out.println(hor);
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
                pheromonePerPath.put(path, pheromonePerPath.get(path)+150.0/shortestTime);
            }

            this.network.updateProbs(pheromonePerPath);

            resetAnts();


        }

        ArrayList<Node> shortestpath = new ArrayList<>();
        ArrayList<Path> followedpath = new ArrayList<>();
        shortestpath.add(this.origin);
        Node current = this.origin;
        double finaltime = 0.0;
        Node prev = null;

        while(current != this.destination){
            //System.out.println(current.getName());
            Path mostprob = current.mostProbablePath();
            if(shortestpath.contains(mostprob.getNf())){
                mostprob.setProbability(0.0);
                mostprob = current.mostProbablePath();
            }
            followedpath.add(mostprob);
            finaltime += mostprob.getTime();
            prev = current;
            current = current.mostProbablePath().getNf();
            shortestpath.add(current);
        }

        for(Path p :followedpath){
            System.out.println(p.getNi().getName()+" "+p.getNf().getName()+" "+p.getHi()+"-"+p.getHf());
        }

        String hi = followedpath.get(0).getHi();
        double wait = network.flyTime(h, hi, 0, 0);

        finaltime += wait;

        System.out.println(wait + " " + h);


        int conti = this.origin.getCont().equals(this.destination.getCont()) ? 1 : 0;
        double th = 0;
        if(conti == 1){
            th = 24.0;
        } else{
            th = 48.0;
        }

        if((finaltime/60) <= th){
            System.out.println("Se puede realizar el envio - tiempo: " + finaltime/60 + " / " + finaltime );
        } else {
            System.out.println("No se puede realizar el envio - tiempo: " + finaltime/60+ " / " + finaltime );
        }

        System.out.println("--------------------");



    }

    private void resetAnts(){
        for(Ant a : this.ants){
            a.setCurrent(this.origin);
            a.clearAnt();
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

    public void resetColony(){
        this.network.setProbs();
        this.resetAnts();

    }



}
