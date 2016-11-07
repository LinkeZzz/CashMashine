import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class Cashmashine {

    private Map<Integer, Integer> cash;

    public Cashmashine() {
        this.cash = new TreeMap<Integer, Integer>(Collections.reverseOrder());
        cash.put(1, 0);
        cash.put(3, 0);
        cash.put(5, 0);
        cash.put(10, 0);
        cash.put(25, 0);
        cash.put(50, 0);
        cash.put(100, 0);
        cash.put(500, 0);
        cash.put(1000, 0);
        cash.put(5000, 0);
    }

    private int helpCheck(int cash_val, int val) {
        /**
         * helpful method for get
         * checks possibility and amount of returnig each currency
         */
        int temp = val / cash_val;
        if (temp != 0) {
            if (this.cash.get(cash_val)>0) {
                if (this.cash.get(cash_val) < temp) {
                    val -= this.cash.get(cash_val) * cash_val;
                    System.out.print(cash_val + " = " + this.cash.get(cash_val) + ", ");
                    this.cash.put(cash_val, 0);
                } else {
                    val -= temp * cash_val;
                    System.out.print(cash_val + " = " + temp + ", ");
                    this.cash.put(cash_val, this.cash.get(cash_val) - temp);
                }
            }
        }
        return val;
    }

    public void get(int val) {
        /**method returns max possible currency
         * for example if in debt it is one 100 coin and hundred 1 coins
         * one 100 coin will be returned
        */
        int temp=val;
        for (Map.Entry<Integer, Integer> en : this.cash.entrySet())
            temp = helpCheck(en.getKey(), temp);
        if (temp == 0)
            System.out.print("всего"+val);
        else {
            System.out.println("всего "+(val-temp));
            System.out.println("без " + temp);
        }

    }

    public void put(int val, int count) {
        if (this.cash.containsKey(val)) {
            this.cash.put(val, this.cash.get(val) + count);
        } else
            System.out.println("wrong input cash format");
    }

    public void dump() {
        //output each currency count
        for (Map.Entry<Integer, Integer> en : this.cash.entrySet())
            System.out.format("%d-%d  ", en.getKey(), en.getValue());
    }

    public void state() {
        // return total debt
        int sum = 0;
        for (Map.Entry<Integer, Integer> en : this.cash.entrySet())
            sum += en.getKey() * en.getValue();
        System.out.println(sum);
    }
}

public class Terminal {
    public static void main(String args[]) {

        boolean flag = false;
        Cashmashine cm = new Cashmashine();
        while (!flag) {
            System.out.print("\n>");
            Scanner terminalInput = new Scanner(System.in);

            //read input
            String input = terminalInput.nextLine();
            String delims = "[ ]+";
            String[] tokens = input.split(delims);

            // cash currency
            int val = 0;
            // cash count
            int count = 0;
            switch (tokens[0]) {
                case "put":
                    val = Integer.parseInt(tokens[1]);
                    count = Integer.parseInt(tokens[2]);
                    cm.put(val, count);
                    cm.state();
                    break;
                case "get":
                    val = Integer.parseInt(tokens[1]);
                    cm.get(val);
                    break;
                case "dump":
                    cm.dump();
                    break;
                case "state":
                    cm.state();
                    break;
                case "quit":
                    flag = true;
                    break;
                default:
                    break;
            }
        }
    }
}