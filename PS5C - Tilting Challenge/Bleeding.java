import java.util.*;
import java.io.*;

// write list of collaborators here: Henry Ang
// year 2018 hash code: psJ6yCZMN7uwQv79EtpQ (do NOT delete this line)

class Bleeding {
    private int V; // number of vertices in the graph (number of junctions in Singapore map)
    private int Q; // number of queries
    private Vector<Vector<IntegerPair>> AdjList; // the weighted graph (the Singapore map), the length of each edge
                                                 // (road) is stored here too, as the weight of edge

    // if needed, declare a private data structure here that
    // is accessible to all methods in this class
    // --------------------------------------------
    private int[][] distMatrix;
    private PriorityQueue<IntegerTriple> pq;
    public static final int MAX_LIMIT = 1000000000;
    // --------------------------------------------

    public Bleeding() {
    }

    void PreProcess() {
    }

    int Query(int s, int t, int k) {
        int min = MAX_LIMIT;
        distMatrix = new int[k][V];

        for (int i = 0; i < k; i++) {
            Arrays.fill(distMatrix[i], MAX_LIMIT);
        }

        distMatrix[0][s] = 0;
        pq = new PriorityQueue<IntegerTriple>();
        pq.offer(new IntegerTriple(0, s, 0));

        while (!pq.isEmpty()) {
            IntegerTriple current = pq.poll();
            int currentVert = current.second().intValue();
            int currentHops = current.third().intValue();
            int nextHop = 1 + currentHops;
            if (k > nextHop && current.first().intValue() == distMatrix[currentHops][currentVert]) {
                for (int j = 0; j < AdjList.get(currentVert).size(); j++) {
                    IntegerPair next = AdjList.get(currentVert).get(j);
                    int nextVertex = next.first().intValue();
                    int weight = next.second().intValue();           
                    if (distMatrix[nextHop][nextVertex] > distMatrix[currentHops][currentVert] + weight) {
                        distMatrix[nextHop][nextVertex] = distMatrix[currentHops][currentVert] + weight;
                        pq.offer(new IntegerTriple(distMatrix[nextHop][nextVertex], nextVertex, nextHop));
                    }
                }
            }
            if(currentVert == t){
                min = distMatrix[currentHops][t];
                break;
            }
        }
        return (min == MAX_LIMIT) ? -1 : min;
    }

    void run() throws Exception {
        IntegerScanner sc = new IntegerScanner(System.in);
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int TC = sc.nextInt();
        while (TC-- > 0) {
            V = sc.nextInt();
            AdjList = new Vector<Vector<IntegerPair>>();
            for (int i = 0; i < V; i++) {
                AdjList.add(new Vector<IntegerPair>());

                int k = sc.nextInt();
                while (k-- > 0) {
                    int j = sc.nextInt(), w = sc.nextInt();
                    AdjList.get(i).add(new IntegerPair(j, w));
                }
            }

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

class IntegerTriple implements Comparable<IntegerTriple> {
    Integer _first, _second, _third;

    public IntegerTriple(Integer f, Integer s, Integer t) {
        _first = f;
        _second = s;
        _third = t;
    }

    public int compareTo(IntegerTriple o) {
        if (!this.first().equals(o.first()))
            return this.first() - o.first();
        else if (!this.second().equals(o.second()))
            return this.second() - o.second();
        else
            return this.third() - o.third();
    }

    Integer first() {
        return _first;
    }

    Integer second() {
        return _second;
    }

    Integer third() {
        return _third;
    }
}
