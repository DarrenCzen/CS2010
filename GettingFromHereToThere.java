import java.util.*;
import java.io.*;

// write your matric number here: A0155600R
// write your name here: Darren Chin Jhun Pyng
// write list of collaborators here: CS2010 Notes, MiniMax algorithm on CP3 Pg 143, 159 (Floyd Warshall)
// year 2018 hash code: c3HL7hbuMVasJh2TEnnf (do NOT delete this line) <-- change this

class GettingFromHereToThere {
  private int V; // number of vertices in the graph (number of rooms in the building)
  private Vector<Vector<IntegerPair>> AdjList; // the weighted graph/effort rating of each corridor is

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  // --------------------------------------------
  private static ArrayList<Boolean> taken;
  private static PriorityQueue<IntegerTriple> pq;
  private Vector<Vector<IntegerPair>> mstList;
  private int[] visited;
  private int[][] weightMatrix;
  // --------------------------------------------

  public GettingFromHereToThere() {
  }
  
  // This method builds the MST and does traversal from 10 different node
  void PreProcess() {
    taken = new ArrayList<Boolean>();
    taken.addAll(Collections.nCopies(V, false));
    pq = new PriorityQueue<IntegerTriple>();
    mstList = new Vector<Vector<IntegerPair>>();
    for (int i = 0; i < V; i++) {
      mstList.add(new Vector<IntegerPair>());
    }

    process(0);

    while (!pq.isEmpty()) {
      IntegerTriple front = pq.poll();
      if (!taken.get(front.second())) {
        // vertex not connected yet so add the edge to the MST
        int lastVisited = front.third();
        mstList.get(lastVisited).add(new IntegerPair(front.second(), front.first()));
        mstList.get(front.second()).add(new IntegerPair(lastVisited, front.first()));
        process(front.second());
      }
      // else do nothing
    }
    
    // for D, must traverse from 10 different sources and store answers/parents
    // this allows easier access to ans.
    int currentV = (V >  10) ? 10 : V;
    weightMatrix = new int[currentV][V];

    for(int j = 0; j < currentV; j++){
      visited = new int[V];
      DFSrec(j, j, 0);
    }
  }

  int Query(int source, int destination) {
    // return stored answers from DFS traversal of 10 diff. vertices
    return weightMatrix[source][destination];
  }

  // You can add extra function if needed
  // --------------------------------------------
  private void process(int vtx) {
    taken.set(vtx, true);
    for (int j = 0; j < AdjList.get(vtx).size(); j++) {
      IntegerPair v = AdjList.get(vtx).get(j);
      if (!taken.get(v.first())) {
        pq.offer(new IntegerTriple(v.second(), v.first(), vtx)); // Sort Q by weight then by adjacent vertex
      }
      // else do nothing
    }
  }

  void DFSrec(int source, int vertex, int weight) {
    visited[vertex] = 1;
    for (int i = 0; i < mstList.get(vertex).size(); i++) {
      IntegerPair next = mstList.get(vertex).get(i);
      int nextVertex = next.first();
      int nextWeight = next.second();
      
      if (visited[nextVertex] == 0) {
        int newWeight = (weight < nextWeight) ? nextWeight : weight;
        weightMatrix[source][nextVertex] = newWeight;
        DFSrec(source, nextVertex, newWeight);
      }
    }
  }
  // --------------------------------------------

  void run() throws Exception {
    // do not alter this method
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
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (corridor) weight (effort rating) is stored here
        }
      }

      PreProcess(); // you may want to use this function or leave it empty if you do not need it

      int Q = sc.nextInt();
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt()));
      pr.println(); // separate the answer between two different graphs
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    GettingFromHereToThere ps4 = new GettingFromHereToThere();
    ps4.run();
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