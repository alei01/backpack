/*
public class Knapsack  {

    double[] profit;
    double[] weight;
    double[] take;

    public Knapsack(int n) {

        profit = new double[n];
        weight = new double[n];
        take = new double[n];
        for (int i = 0; i < n; i++) {
            profit[i] = (int) Math.round(Math.random() * 96 + 44);
            weight[i] = (int) Math.round(Math.random() * 89 + 15);
        }
    }

    public void unitPriceOrder() {
        for (int i = 0; i < profit.length; i++) {
            for (int j = 1; j < (profit.length - i); j++) {
                double x=profit[j - 1] / weight[j - 1];
                double y=profit[j] / weight[j];
                if (x <=y) {
                    double temp = profit[j - 1];
                    profit[j - 1] = profit[j];
                    profit[j] = temp;

                    double temp1 = weight[j - 1];
                    weight[j - 1] = weight[j];
                    weight[j] = temp1;
                }
            }
        }
    }

    public void Knapsack(int m) {
        unitPriceOrder();
        int j;
        for (j = 0; j < profit.length; j++) {
            take[j] = 0;
        }
        double total = m;
        for (j = 0; j < profit.length; j++) {
            if (weight[j] <= total) {
                take[j] = 1.00;
                total = total - weight[j];
            } else {
                break;// to exit the for-loop
            }
        }
        if (j < profit.length) {
            take[j] = (double)(total / weight[j]);
        }
    }

    public void print() {

        System.out.println("item" + " |  " + "profit" + "  |   " + "weight" +
                "   |     " + "Unit Price" + "      |" + "  Take weight");
        for (int n = 0; n < profit.length; n++) {
            System.out.println(n + "\t" + profit[n] + "\t" + weight[n] + "\t"
                    + (profit[n] / weight[n]) + "\t" + take[n]);
        }
    }

    public static void main(String args[]) {
        Knapsack G = new Knapsack(10);
        System.out.println("Optimal soluation to knapsack instance "
                + "with values given as follows : m=35");
        G.Knapsack(35);
        G.print();
        System.out.println("=======+============+=======+============+="
                + "===========");
        System.out.println("Optimal soluation to knapsack instance with "
                + "values given as follows : m=120");
        G.Knapsack(120);
        G.print();
    }
}*/

import java.util.Scanner;

/*************************************************************************
 *  Compilation:  javac Knapsack.java
 *  Execution:    java Knapsack N W
 *
 *  Generates an instance of the 0/1 knapsack problem with N items
 *  and maximum weight W and solves it in time and space proportional
 *  to N * W using dynamic programming.
 *
 *  For testing, the inputs are generated at random with weights between 0
 *  and W, and profits between 0 and 1000.
 *
 *  %  java Knapsack 6 2000
 *  item    profit  weight  take
 *  1       874     580     true
 *  2       620     1616    false
 *  3       345     1906    false
 *  4       369     1942    false
 *  5       360     50      true
 *  6       470     294     true
 *
 *************************************************************************/

public class Knapsack {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int numberOfItems = scanner.nextInt();
        int maxBackpackWeight = scanner.nextInt();

       /* int numberOfItems = Integer.parseInt(args[0]);   // number of items
        int maxBackpackWeight = Integer.parseInt(args[1]);   // maximum weight of knapsack*/

        int[] profit = new int[numberOfItems+1];
        int[] weight = new int[numberOfItems+1];

        // generate random instance, items 1..numberOfItems
        for (int n = 1; n <= numberOfItems; n++) {
            profit[n] = (int) (Math.random() * 1000);
            weight[n] = (int) (Math.random() * maxBackpackWeight);
        }

        // opt[n][w] = max profit of packing items 1..n with weight limit w
        // sol[n][w] = does opt solution to pack items 1..n with weight limit w include item n?
        int[][] opt = new int[numberOfItems+1][maxBackpackWeight+1];
        boolean[][] sol = new boolean[numberOfItems+1][maxBackpackWeight+1];

        for (int n = 1; n <= numberOfItems; n++) {
            for (int w = 1; w <= maxBackpackWeight; w++) {

                // don't take item n
                int option1 = opt[n-1][w];

                // take item n
                int option2 = Integer.MIN_VALUE;
                if (weight[n] <= w) option2 = profit[n] + opt[n-1][w-weight[n]];

                // select better of two options
                opt[n][w] = Math.max(option1, option2);
                sol[n][w] = (option2 > option1);
            }
        }

        // determine which items to take
        boolean[] take = new boolean[numberOfItems+1];
        for (int n = numberOfItems, w = maxBackpackWeight; n > 0; n--) {
            if (sol[n][w]) { take[n] = true;  w = w - weight[n]; }
            else           { take[n] = false;                    }
        }

        // print results
        System.out.println("item" + "\t" + "profit" + "\t" + "weight" + "\t" + "take");
        for (int n = 1; n <= numberOfItems; n++) {
            System.out.println(n + "\t" + profit[n] + "\t" + weight[n] + "\t" + take[n]);
        }
    }
}

