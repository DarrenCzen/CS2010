import java.util.*;
import java.io.*;

// write list of collaborators here:
// year 2018 hash code: psJ6yCZMN7uwQv79EtpQ (do NOT delete this line)

class Bleeding {
    private int V; // number of vertices in the graph (number of junctions in Singapore map)
    private int Q; // number of queries
    private Vector<Vector<IntegerPair>> AdjList; // the weighted graph (the Singapore map), the length of each edge
                                                 // (road) is stored here too, as the weight of edge

    // if needed, declare a private data structure here that
    // is accessible to all methods in this class
    // --------------------------------------------
    private int[][] weightArray;
    private int[] visited;
    // --------------------------------------------

    public Bleeding() {
    }

    public void PreProcess() {
        weightArray = new int[V][V];

        for (int j = 0; j < V; j++) {
            for(int i = 0; i < V ; i ++){
                weightArray[j][i] = 100000000;
            }
            weightArray[j][j] = 0;
            visited = new int[V];
            DFSrec(j, j);

            for(int i = 0; i < V ; i ++){
                if(weightArray[j][i] == 100000000) {
                    weightArray[j][i] = -1;
                }
            }
        }
    }

    int Query(int s, int t, int k) {
        // You have to report the shortest path from Ket Fah's current position s
        // to reach the chosen hospital t, output -1 if t is not reachable from s
        // with one catch: this path cannot use more than k vertices
        return weightArray[s][t];
    }

    // You can add extra function if needed
    // --------------------------------------------
    void DFSrec(int j, int vertex) {
        visited[vertex] = 1;
        for (int i = 0; i < AdjList.get(vertex).size(); i++) {
            IntegerPair next = AdjList.get(vertex).get(i);
            int nextVertex = next.first();
            int nextWeight = next.second();

            if (visited[nextVertex] == 0) {
                if (weightArray[j][nextVertex] > weightArray[j][vertex] + nextWeight){ // if SP can be shortened
                    weightArray[j][nextVertex] = weightArray[j][vertex] + nextWeight;
                }
                DFSrec(j, nextVertex);
            }
        }
    }

    // --------------------------------------------

    void run() throws Exception {
        // you can alter this method if you need to do so
        IntegerScanner sc = new IntegerScanner(System.in);
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int TC = sc.nextInt(); // there will be several test cases
        while (TC-- > 0) {
            V = sc.nextInt();

            // clear the graph and read in a new graph as Adjacency List
            AdjList = new Vector<Vector<IntegerPair>>();
            for (int i = 0; i < V; i++) {
                AdjList.add(new Vector<IntegerPair>());

                int k = sc.nextInt();
                while (k-- > 0) {
                    int j = sc.nextInt(), w = sc.nextInt();
                    AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (in minutes) is stored here
                }
            }

            PreProcess(); // optional

            Q = sc.nextInt();
            while (Q-- > 0)
                pr.println(Query(sc.nextInt(), sc.nextInt(), sc.nextInt()));

            if (TC > 0)
                pr.println();
        }

        pr.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method
        Bleeding ps5 = new Bleeding();
        ps5.run();
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

class IntegerPair implements Comparable<IntegerPair> {
    Integer _first, _second;

    public IntegerPair(Integer f, Integer s) {
        _first = f;
        _second = s;
    }

    public int compareTo(IntegerPair o) {
        if (!this.first().equals(o.first()))
            return this.first() - o.first();
        else
            return this.second() - o.second();
    }

    Integer first() {
        return _first;
    }

    Integer second() {
        return _second;
    }
}
