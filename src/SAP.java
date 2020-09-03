import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {

    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if (G==Null) {
            throw new IllegalArgumentException("Digraph is Null");
        }

        // make defensive copy of G to make it immutable.
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        // check if v or w are out of bound
        validateVertex(v);
        validateVertex(w);

        // create bfs from v and w
        BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(digraph, w);

        // for each vertex, check distance from v and w. return shortest.
        int minDistance = -1;
        int currDistance = 0;
        for (int i=0; i < digraph.V(); i++) {
            if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
                currDistance = vBFS.distTo(i) + wBFS.distTo(i);
                if (minDistance == -1 || minDistance > currDistance) {
                    minDistance = currDistance;
                }
            }
        }
        return minDistance;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){

    }

    // do unit testing of this class
    public static void main(String[] args){

    }

    private void validateVertex(int v) {
        if (v < 0 || v >= digraph.V())
            throw new IllegalArgumentException("vertex is out of range");
    }
}
