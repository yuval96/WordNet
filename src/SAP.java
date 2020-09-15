import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if (G==null) {
            throw new IllegalArgumentException("Digraph is Null");
        }

        // make defensive copy of G to make it immutable.
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        int[] results = ancestorPath(v, w);
        return results[0];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){

        int[] results = ancestorPath(v, w);
        return results[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        return ancestorPath(v, w)[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        return ancestorPath(v, w)[1];
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= digraph.V())
            throw new IllegalArgumentException("vertex is out of range");
    }

    private void validateVertex(Iterable<Integer> v) {
        for (int vertex: v){
            if (vertex < 0 || vertex >= digraph.V())
                throw new IllegalArgumentException("vertex is out of range");
        }

    }

    private int[] ancestorPath(int v, int w) {
        // check if v or w are out of bound
        validateVertex(v);
        validateVertex(w);

        // if v==w the length of shortest ancestral path is 0, and the common ancestor is v itself.
        if (v==w) {
            return new int[] {0, v};
        }

        // create bfs from v and w
        BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(digraph, w);

        // for each vertex, check distance from v and w. return shortest.
        int minDistance = -1;
        int commonAncestor = -1;
        int currDistance = 0;
        for (int i=0; i < digraph.V(); i++) {
            if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
                currDistance = vBFS.distTo(i) + wBFS.distTo(i);
                if (minDistance == -1 || minDistance > currDistance) {
                    minDistance = currDistance;
                    commonAncestor = i;
                }
            }
        }
        return new int[] {minDistance, commonAncestor};
    }

    private int[] ancestorPath(Iterable<Integer> v, Iterable<Integer> w) {
        // check if any vertex in v or w is out of bound
        validateVertex(v);
        validateVertex(w);

        // create bfs from v and w
        BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(digraph, w);

        // for each vertex, check distance from v and w. return shortest.
        int minDistance = -1;
        int commonAncestor = -1;
        int currDistance = 0;
        for (int i=0; i < digraph.V(); i++) {
            if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
                currDistance = vBFS.distTo(i) + wBFS.distTo(i);
                if (minDistance == -1 || minDistance > currDistance) {
                    minDistance = currDistance;
                    commonAncestor = i;
                }
            }
        }
        return new int[] {minDistance, commonAncestor};
    }

    // do unit testing of this class
//    public static void main(String[] args){
//        In in = new In(args[0]);
//        Digraph G = new Digraph(in);
//        SAP sap = new SAP(G);
//        while (!StdIn.isEmpty()) {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            int length   = sap.length(v, w);
//            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }
//    }
}
