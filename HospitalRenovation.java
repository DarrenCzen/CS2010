import java.util.*;
import java.io.*;

// write list of collaborators here:
// year 2018 hash code: xrVYbth32e6GM6jXHLKb (do NOT delete this line) <- generate a new hash code

class HospitalRenovation {
    private int V; // number of vertices in the graph (number of rooms in the hospital)
    private ArrayList<ArrayList<Integer>> AdjList; // the graph (the hospital)
    private int[] RatingScore; // the weight of each vertex (rating score of each room)
    private ArrayList<Integer> criticalList; // list of critical rooms

    // if needed, declare a private data structure here that
    // is accessible to all methods in this class
    private int[] visited;
    private int[] parent;

    public HospitalRenovation() {

    }

    // You have to report the rating score of the critical room (vertex)
    // with the lowest rating score in this hospital
    // or report -1 if that hospital has no critical room
    int Query() {

        criticalList = new ArrayList<>();
        for(int i = 0; i < V; i++){
            intializeVP();
            visited[i] = 1;
            int numComponents = 0;

            for(int j = 0; j < V; j++){
                if(visited[j] == 0){
                    numComponents += 1;
                    DFSrec(j);
                }
            }
            
            if (numComponents > 1){
                criticalList.add(i);
            }
        }
        // Check whether critical room exist
        // a critical room is one when the exits is blocked, it cannot go to other paths
        // return room no else return -1
        if(!criticalList.isEmpty()){
            int mostImportant = 1000000;
            for(int j = 0; j < criticalList.size(); j++){
                int criticalVertex = criticalList.get(j).intValue();
                if(RatingScore[criticalVertex] < mostImportant){
                    mostImportant = RatingScore[criticalVertex];
                }
            }
            return mostImportant;
        }

        return -1;
    }

    // You can add extra function if needed
    // --------------------------------------------
    void DFSrec(int vertex){
        visited[vertex] = 1;
        for(int i = 0; i < AdjList.get(vertex).size(); i++){
            int nextVertex = AdjList.get(vertex).get(i).intValue();
            if(visited[nextVertex] == 0){
                parent[nextVertex] = vertex;
                DFSrec(nextVertex);
            }
        }
    }

    void intializeVP(){
        visited = new int[V];
        parent = new int[V];

        for(int i = 0; i < V; i++){
            visited[i] = 0;
            parent[i] = -1;
        }
    }
    // --------------------------------------------

    void run() throws Exception {
        // for this PS3, you can alter this method as you see fit

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int TC = Integer.parseInt(br.readLine()); // there will be several test cases
        while (TC-- > 0) {
            br.readLine(); // ignore dummy blank line
            V = Integer.parseInt(br.readLine());

            StringTokenizer st = new StringTokenizer(br.readLine());

            // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the
            // V-th index
            RatingScore = new int[V];
            for (int i = 0; i < V; i++) {
                RatingScore[i] = Integer.parseInt(st.nextToken());
            }

            // clear the graph and read in a new graph as Adjacency Matrix
            AdjList = new ArrayList<ArrayList<Integer>>();

            for (int i = 0; i < V; i++) {
                st = new StringTokenizer(br.readLine());
                int k = Integer.parseInt(st.nextToken());

                AdjList.add(new ArrayList<Integer>());
                while (k-- > 0) {
                    int j = Integer.parseInt(st.nextToken());
                    AdjList.get(i).add(new Integer(j));
                }
            }

            pr.println(Query());
        }
        pr.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method
        HospitalRenovation ps3 = new HospitalRenovation();
        ps3.run();
    }
}
