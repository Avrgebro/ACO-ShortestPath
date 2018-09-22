class ACOdriver {

    public static void main (String[] args) throws java.lang.Exception
    {
        AntColony solution = new AntColony(3, 20, "SPIM", "EDDI");

        solution.Simulation();

    }
}