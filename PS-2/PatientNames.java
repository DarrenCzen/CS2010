import java.util.*;
import java.io.*;

// write your matric number here: A0155600R
// write your name here: Darren Chin Jhun Pyng
// write list of collaborators here: CS2010 BST Demo and CS2010 Notes for Rotation Pseudo Code Algorithm.
// year 2018 hash code: wrMQ8UMcPU5q7F4UPNhT (do NOT delete this line)

class BSTVertex {
    public BSTVertex parent, left, right;
    public String key;
    public int height;
    public int size;

    BSTVertex(String v) {
        key = v;
        parent = left = right = null;
        size = 1;
        height = 0;
    }
}

class PatientNames {

    private BST maleList;
    private BST femaleList;

    public PatientNames() {
        maleList = new BST();
        femaleList = new BST();
    }

    void AddPatient(String patientName, int gender) {
        if (gender == 1) {
            maleList.insert(patientName);
        } else {
            femaleList.insert(patientName);
        }
    }

    void RemovePatient(String patientName) {
    }

    int Query(String START, String END, int gender) {
        int ans = 0;

        if (gender == 0 || gender == 1) {
            //finding the rank of the last person that starts with END
            ans += Math.abs(maleList.rank(END) - maleList.rank(START));
        }

        if (gender == 0 || gender == 2) {
            ans += Math.abs(femaleList.rank(END) - femaleList.rank(START));
        }

        return ans;
    }

    void run() throws Exception {
        // do not alter this method to avoid unnecessary errors with the automated
        // judging
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            if (command == 0) // end of input
                break;
            else if (command == 1) // AddPatient
                AddPatient(st.nextToken(), Integer.parseInt(st.nextToken()));
            else if (command == 2) // RemovePatient
                RemovePatient(st.nextToken());
            else // if (command == 3) // Query
                pr.println(Query(st.nextToken(), // START
                        st.nextToken(), // END
                        Integer.parseInt(st.nextToken()))); // GENDER
        }
        pr.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method to avoid unnecessary errors with the automated
        // judging
        PatientNames ps2 = new PatientNames();
        ps2.run();
    }
}

class BST {
    protected BSTVertex root;

    public BST() {
        root = null;
    }

    public int rank(String v) {
        return rank(root, v);
    }

    public int rank(BSTVertex T, String v) {
        // check whether Vertex is non-existent.
        if (T == null) {
            return 0; 
        }
        
        int compareValue = T.key.compareTo(v);
        // compare the current vertex first, if string is lexicographically larger or smaller
        int leftSize = T.left != null ? T.left.size : 0; 
        if (compareValue < 0) { // search to the right
            return leftSize + 1 + rank(T.right, v);
        } else if (compareValue > 0) { // search to the left
            return rank(T.left, v);
        } else {
            return leftSize + 1;
        }
    }

    // method called to insert a new key with value v into BST
    public void insert(String v) {
        root = insert(root, v);
    }

    // overloaded recursive method to perform insertion of new vertex into BST
    protected BSTVertex insert(BSTVertex T, String v) {
        if (T == null) {
            return new BSTVertex(v); // insertion point is found
        }

        if (T.key.compareTo(v) <= 0) { // search to the right
            T.right = insert(T.right, v);
            T.right.parent = T;
        } else { // search to the left
            T.left = insert(T.left, v);
            T.left.parent = T;
        }

        updateSize(T);
        updateHeight(T);

        int bF = calculateBalanceFactor(T);

        if(bF == 2 || bF == -2 ){
            rotateTree(T, bF);
        }
        
        return T; // return the updated BST
    }

    public int calculateBalanceFactor(BSTVertex T) {
        int leftNodeHeight = (T.left != null) ? T.left.height : -1;
        int rightNodeHeight = (T.right != null) ? T.right.height : -1;

        return leftNodeHeight - rightNodeHeight;
    }

    public void rotateTree(BSTVertex T, int bF) {
        // 4 Cases
        if(bF == 2){
            int leftBF = calculateBalanceFactor(T.left);
            
            if(leftBF <= 1 && leftBF >= 0){
                rotateRight(T);
            } else if (leftBF == -1) {
                rotateLeft(T.left);
                rotateRight(T);
            }
        } else {
            int rightBF = calculateBalanceFactor(T.right);
            
            if(rightBF <= 0 && rightBF >= -1){
                rotateLeft(T);
            } else if (rightBF == 1) {
                rotateRight(T.left);
                rotateLeft(T);
            }
        }
    }

    // pre-req: T.right != null
    public BSTVertex rotateLeft(BSTVertex T) {
        BSTVertex w = T.right;
        w.parent = T.parent;
        T.parent = w;
        T.right = w.left;
        
        if(w.left != null) w.left.parent = T;

        w.left = T;

        updateSize(T);
        updateHeight(T);
        updateSize(w);
        updateHeight(w);
        
        return w;
    }

    // pre-req: T.left != null
    public BSTVertex rotateRight(BSTVertex T) {
        BSTVertex w = T.left;
        w.parent = T.parent;
        T.parent = w;
        T.left = w.right;
        
        if(w.right != null) w.right.parent = T;

        w.right = T;
        
        updateSize(T);
        updateHeight(T);
        updateSize(w);
        updateHeight(w);

        return w;
    }

    // method called to search for a value v
    public String search(String v) {
        BSTVertex res = search(root, v);
        return res == null ? null : res.key;
    }

    // overloaded recursive method to perform search
    protected BSTVertex search(BSTVertex T, String v) {
        if (T == null)
            return null; // not found
        else if (T.key.equals(v))
            return T; // found
        else if (T.key.compareTo(v) <= 0)
            return search(T.right, v); // search to the right
        else
            return search(T.left, v); // search to the left
    }

    // public method called to find Minimum key value in BST
    public String findMin() {
        return findMin(root);
    }

    // overloadded recursive method to perform findMin
    protected String findMin(BSTVertex T) {
        if (T == null)
            throw new NoSuchElementException("BST is empty, no minimum");
        else if (T.left == null)
            return T.key; // this is the min
        else
            return findMin(T.left); // go to the left
    }

    // public method called to find Maximum key value in BST
    public String findMax() {
        return findMax(root);
    }

    // overloadded recursive method to perform findMax
    protected String findMax(BSTVertex T) {
        if (T == null)
            throw new NoSuchElementException("BST is empty, no maximum");
        else if (T.right == null)
            return T.key; // this is the max
        else
            return findMax(T.right); // go to the right
    }

    // public method to find successor to given value v in BST
    public String successor(String v) {
        BSTVertex vPos = search(root, v);
        return vPos == null ? null : successor(vPos);
    }

    // overloaded recursive method to find successor to for a given vertex T in BST
    protected String successor(BSTVertex T) {
        if (T.right != null) // this subtree has right subtree
            return findMin(T.right); // the successor is the minimum of right subtree
        else {
            BSTVertex par = T.parent;
            BSTVertex cur = T;
            // if par(ent) is not root and cur(rent) is its right children
            while ((par != null) && (cur == par.right)) {
                cur = par; // continue moving up
                par = cur.parent;
            }
            return par == null ? null : par.key; // this is the successor of T
        }
    }

    // public method to find predecessor to given value v in BST
    public String predecessor(String v) {
        BSTVertex vPos = search(root, v);
        return vPos == null ? null : predecessor(vPos);
    }

    // overloaded recursive method to find predecessor to for a given vertex T in
    // BST
    protected String predecessor(BSTVertex T) {
        if (T.left != null) // this subtree has left subtree
            return findMax(T.left); // the predecessor is the maximum of left subtree
        else {
            BSTVertex par = T.parent;
            BSTVertex cur = T;
            // if par(ent) is not root and cur(rent) is its left children
            while ((par != null) && (cur == par.left)) {
                cur = par; // continue moving up
                par = cur.parent;
            }
            return par == null ? null : par.key; // this is the successor of T
        }
    }

    // public method to delete a vertex containing key with value v from BST
    public void delete(String v) {
        root = delete(root, v);
    }

    // overloaded recursive method to perform deletion
    protected BSTVertex delete(BSTVertex T, String v) {
        if (T == null)
            return T; // cannot find the item to be deleted

        if (T.key.compareTo(v) <= 0) // search to the right
            T.right = delete(T.right, v);
        else if (T.key.compareTo(v) > 0) // search to the left
            T.left = delete(T.left, v);
        else { // this is the node to be deleted
            if (T.left == null && T.right == null) // this is a leaf
                T = null; 
            else if (T.left == null && T.right != null) { // only one child at right
                T.right.parent = T.parent;
                T = T.right; // bypass T
            } else if (T.left != null && T.right == null) { // only one child at left
                T.left.parent = T.parent;
                T = T.left; // bypass T
            } else { // has two children, find successor
                String successorV = successor(v);
                T.key = successorV; // replace this key with the successor's key
                T.right = delete(T.right, successorV); // delete the old successorV
            }
        }

        updateSize(T);
        updateHeight(T);

        return T; // return the updated BST
    }

    public void updateSize(BSTVertex T) {
        int leftNodeSize = (T.left != null) ? T.left.size : 0;
        int rightNodeSize = (T.right != null) ? T.right.size : 0;

        T.size = 1 + leftNodeSize + rightNodeSize;
    }
    public void updateHeight(BSTVertex T) {
        int leftNodeHeight = (T.left != null) ? T.left.height : -1;
        int rightNodeHeight = (T.right != null) ? T.right.height : -1;

        T.height = Math.max(leftNodeHeight, rightNodeHeight) + 1;
    }

    protected int getHeight(BSTVertex T) {
        if (T == null)
            return -1;
        else
            return Math.max(getHeight(T.left), getHeight(T.right)) + 1;
    }

    public int getHeight() {
        return getHeight(root);
    }
}
