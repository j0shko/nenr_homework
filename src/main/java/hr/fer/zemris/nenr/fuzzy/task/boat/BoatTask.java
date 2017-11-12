package hr.fer.zemris.nenr.fuzzy.task.boat;

import hr.fer.zemris.nenr.fuzzy.system.COADefuzzifier;
import hr.fer.zemris.nenr.fuzzy.system.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.system.FuzzySystem;

import java.util.Scanner;

public class BoatTask {

    public static void main(String[] args) {

        Defuzzifier def = new COADefuzzifier();

        FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
        FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);

        Scanner inScanner = new Scanner(System.in);
        while(true) {
            String line = inScanner.nextLine();
            if (line.equals("KRAJ")) break;
            Inputs in = parseInputs(line);

            int A = fsAkcel.conclude(in.L, in.D, in.LK, in.DK, in.V, in.S);
            int K = fsKormilo.conclude(in.L, in.D, in.LK, in.DK, in.V, in.S);

            System.out.println(A + " " + K);
            System.out.flush();
        }
    }

    static class Inputs {
        int L;
        int D;
        int LK;
        int DK;
        int V;
        int S;
    }

    private static Inputs parseInputs(String line) {
        String[] parts = line.split(" ");
        Inputs i = new Inputs();
        i.L = Integer.parseInt(parts[0]);
        i.D = Integer.parseInt(parts[1]);
        i.LK = Integer.parseInt(parts[2]);
        i.DK = Integer.parseInt(parts[3]);
        i.V = Integer.parseInt(parts[4]);
        i.S = Integer.parseInt(parts[5]);

        return i;
    }
}
