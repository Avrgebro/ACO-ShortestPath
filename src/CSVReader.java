import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {


    public CSVReader(){

    }

    public ArrayList<Node> readCities(String route){

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        ArrayList<Node> cities = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(route));
            while ((line = br.readLine()) != null) {

                String[] airport = line.split(cvsSplitBy);

                String name = airport[2];
                String cont = airport[6];
                int gmt = Integer.parseInt(airport[5]);
                double x = Double.parseDouble(airport[0]);
                double y = Double.parseDouble(airport[1]);

                Node n = new Node(name, cont, gmt, x, y);
                cities.add(n);

            }

            return  cities;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return cities;

    }

    public ArrayList<String[]> readPaths(String route){

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "-";

        ArrayList<String[]> plans = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(route));
            while ((line = br.readLine()) != null) {

                String[] plan = line.split(cvsSplitBy);

                plans.add(plan);

            }

            return  plans;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return plans;
    }

    public ArrayList<String[]> readParcels(String route){

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "-";

        ArrayList<String[]> parcels = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(route));
            while ((line = br.readLine()) != null) {

                String[] plan = line.split(cvsSplitBy);

                parcels.add(plan);

            }

            return  parcels;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return parcels;
    }
}
