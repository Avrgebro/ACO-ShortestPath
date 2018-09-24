import java.util.ArrayList;

class ACOdriver {

    public static void main (String[] args) throws java.lang.Exception
    {
        AntColony solution = new AntColony(4, 200);


        CSVReader r = new CSVReader();
        ArrayList<String[]> parcels = r.readParcels("/home/jose/Downloads/parcels.txt");

        System.out.println("Lectura de parcelas completa");

        for(String[] p : parcels){
            if(p[3].equals("LSZB")) continue;
            solution.Simulation("SKBO", p[3], p[2]);
            solution.resetColony();
        }

        //solution.Simulation("SPIM", "SABE", "23:34");
        //solution.resetColony();

    }
}