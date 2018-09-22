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

    public ArrayList<Path> readPaths(String route, ArrayList<Node> cities){

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "-";

        ArrayList<Path> plans = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(route));
            while ((line = br.readLine()) != null) {

                String[] plan = line.split(cvsSplitBy);


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
}