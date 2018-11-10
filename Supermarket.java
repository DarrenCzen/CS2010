import java.util.*;
import java.io.*;

// write list of collaborators here:
// year 2018 hash code: 8A2sCvBuVXdWFrYXe63U (do NOT delete this line)

class Supermarket {
    private int N; // number of items in the supermarket. V = N+1
    private int K; // the number of items that Prof. Chong Ket Fah has to buy
    private int[] shoppingList; // indices of items that Prof. Chong Ket Fah has to buy
    private int[][] T; // the complete weighted graph that measures the direct wheeling time to go from
                       // one point to another point in seconds

    // --------------------------------------------
    private boolean[] visited;
    private int minCost;
    private ArrayList<Integer> path;
    // --------------------------------------------

    public Supermarket() {
    }

    int Query() {
        // You have to report the quickest shopping time that is measured
        // since Prof. Chong Ket Fah enters the supermarket (vertex 0),
        // completes the task of buying K items in that supermarket,
        // then reaches the cashier of the supermarket (back to vertex 0).
        visited = new boolean[N+1];
        Arrays.fill(visited, false);
        path = new ArrayList<Integer>();
        minCost = Integer.MAX_VALUE;

        // Calculates the shortest distance between different vertices using Floyd Warshall
        for (int k = 0; k < N+1; k++) {
            for (int i = 0; i < N+1; i++) {
                for (int j = 0; j < N+1; j++) {
                    T[i][j] = Math.min(T[i][j], T[i][k] + T[k][j]);
                }
            }    
        }
        backtracking(0);

        return minCost;
    }

    // You can add extra function if needed
    // --------------------------------------------
    private void backtracking(int u) {
        path.add(u); // we append vertex u to current path
        visited[u] = true; // to avoid cycle

        // check if all vertices are visited
        boolean all_visited = true;
        for (int v = 0; v < shoppingList.length; v++){
            int itemNumber = shoppingList[v];
            if (!visited[itemNumber]) {
                all_visited = false;
            }
        }

        // all V vertices have been visited, compute tour cost
        if (all_visited) {
            int cost = 0;
            for (int i = path.size() - 1; i >= 1; i--) {
                cost += T[path.get(i)][path.get(i - 1)];
            }

            cost += T[path.get(path.size() - 1)][path.get(0)];
            minCost = Math.min(minCost, cost);
        } else {
            for (int v = 0; v < shoppingList.length; v++) {
                int itemNumber = shoppingList[v];
                if (!visited[itemNumber]) {
                    // Traverse to next item in the list.
                    backtracking(itemNumber);
                }
            }
        }

        visited[u] = false;
        path.remove(path.size() - 1); // remove last item
    }
    // --------------------------------------------

    void run() throws Exception {
        // do not alter this method to standardize the I/O speed (this is already very
        // fast)
        IntegerScanner sc = new IntegerScanner(System.in);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int TC = sc.nextInt(); // there will be several test cases
        while (TC-- > 0) {
            // read the information of the complete graph with N+1 vertices
            N = sc.nextInt();
            K = sc.nextInt(); // K is the number of items to be bought

            shoppingList = new int[K];
            for (int i = 0; i < K; i++)
                shoppingList[i] = sc.nextInt();

            T = new int[N + 1][N + 1];
            for (int i = 0; i <= N; i++)
                for (int j = 0; j <= N; j++)
                    T[i][j] = sc.nextInt();

            pw.println(Query());
        }

        pw.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method
        Supermarket ps6 = new Supermarket();
        ps6.run();
    }
}

class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
    BufferedInputStream bis;

    IntegerScanner(InputStream is) {
        bis = new BufferedInputStream(is, 1000000);
    }

    public int nextInt() {
        int result = 0;
        try {
            int cur = bis.read();
            if (cur == -1)
                return -1;

            while ((cur < 48 || cur > 57) && cur != 45) {
                cur = bis.read();
            }

            boolean negate = false;
            if (cur == 45) {
                negate = true;
                cur = bis.read();
            }

            while (cur >= 48 && cur <= 57) {
                result = result * 10 + (cur - 48);
                cur = bis.read();
            }

            if (negate) {
                return -result;
            }
            return result;
        } catch (IOException ioe) {
            return -1;
        }
    }
}