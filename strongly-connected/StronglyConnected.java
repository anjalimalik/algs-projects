import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

// APIs used from algs4.jar
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.TarjanSCC;

public class StronglyConnected {
    
    //class for input undirected graph
    static class undirGraph {
        int V;
        int E;
        int maxV;
        ArrayList<Integer>[] adjList;
        ArrayList<ArrayList<Integer>> edges;
        
        // constructor
        undirGraph(int V, int E) {
            this.V = V;
            this.E = E;
            this.maxV = 0;
            
            adjList = new ArrayList[V];
            edges = new ArrayList<ArrayList<Integer>>();
            
            for(int i = 0; i < V ; i++){
                adjList[i] = new ArrayList<Integer>();
            }
        }
        
        // Add edge to an undirected graph
        void addEdge(undirGraph graph, int v, int w) {
            if(v != w){
                graph.adjList[v].add(w);
                graph.adjList[w].add(v);
            } else {
                //corner case - SELF LOOPS
                graph.adjList[v].add(w);
            }
            
        }
        
        //Using non-recursive DFS to make directed graph out of undirected input graph
        Digraph DFS4DirectedGraph(undirGraph g, int maxv){
            
            Stack<Integer> stack = new Stack<Integer>();
            stack.push(maxv); //push highest numbered vertex
            
            Digraph gDir = new Digraph(g.V);
            
            while(!stack.empty()){
                int v = stack.peek();
                if(g.adjList[v].size() != 0){
                    int w = g.adjList[v].get(0);    //first neighbor in sorted adjacency list
                    if(v != w) {
                        //remove from both lists so that the edge is not repeated
                        g.adjList[v].remove(0);
                        g.adjList[w].remove(g.adjList[w].indexOf(v));
                    } else {
                        //corner case - SELF LOOPS
                        g.adjList[v].remove(0); //in case of self-loops, remove only once
                    }
                    
                    // add to edges and make directed graph
                    ArrayList<Integer> l = new ArrayList<Integer>();
                    l.add(v);
                    l.add(w);
                    edges.add(l);
                    //System.out.println(v + " " + w);
                    gDir.addEdge(v, w);
                    
                    //push if not in stack
                    if(!stack.contains(w)) {
                        stack.push(w);
                    }
                } else {
                    stack.pop(); //backtrack if no more vertex to visit in adjacency list
                }
            }
            return gDir;
        }
        
        // sorts list in increasing order
        void sortadjLists(undirGraph graph) {
            for(int v = 0; v < graph.V; v++){
                graph.maxV = Math.max(v, graph.maxV); //get vertex to start dfs with
                Collections.sort(graph.adjList[v]);
            }
        }
        
        // print all edges
        void printEdges(undirGraph g){
            for(int i = 0; i < g.edges.size(); i++){
                StdOut.println(g.edges.get(i).get(0) + " " + g.edges.get(i).get(1));
            }
        }
    }
    
    public static void main(String[] args) {
        
        // Step 1: get undirected input graph, make adjacency lists and sort them
        int V = StdIn.readInt();
        int E = StdIn.readInt();
        
        undirGraph inputg = new undirGraph(V, E);
        while(!StdIn.isEmpty()){
            inputg.addEdge(inputg, StdIn.readInt(), StdIn.readInt());
        }
        inputg.sortadjLists(inputg);
        
        // Step 2: using dfs convert undirected graph to directed graph
        Digraph g = inputg.DFS4DirectedGraph(inputg, inputg.maxV);
        
        // Step 3: determine if it is strongly connected or not, and
        // print minimum number of edges needed to make it strongly connected.
        ArrayList<ArrayList<Integer>> sccs = new ArrayList<ArrayList<Integer>>();
        
        // USING TARJAN SCC API for getting Strongly Connected Components
        TarjanSCC scc = new TarjanSCC(g);
        // number of connected components
        int m = scc.count();
        
        if(m == 1){
            // Strongly connected, therefore, 0 directed edges needed
            StdOut.println(0);
            inputg.printEdges(inputg);
            return;
        }
        
        // compute list of vertices in each strong component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < g.V(); v++) {
            components[scc.id(v)].enqueue(v);
        }
        
        // print results
        for (int i = 0; i < m; i++) {
            ArrayList<Integer> cc = new ArrayList<Integer>();
            for (int v : components[i]) {
                //StdOut.print(v + " ");
                cc.add(v);
            }
            sccs.add(cc);
            //StdOut.println();
        }
        
        // GET MINIMUM NUMBER OF DIRECTED EDGES REQUIRED TO MAKE STRONGLY CONNECTED
        int inzero = 0,  outzero = 0;
        boolean check = true;
        
        for(int i = 0; i < m; i++){
            ArrayList<Integer> cc = new ArrayList<Integer>();
            cc = sccs.get(i);
            for(int j = 0; j < g.V(); j++){
                for(int v: g.adj(j)){
                    if(cc.contains(j)){
                    } else {
                        if(cc.contains(v)){
                            check = false;
                        }
                    }
                }
            }
            if(!check){
                check = true;
            } else {
                inzero++;
            }
            
            for(int j = 0; j < cc.size(); j++){
                for(int v : g.adj(cc.get(j))){
                    if(cc.contains(v)){
                        //check = true;
                    } else { check = false; }
                }
            }
            
            if(!check){
                check = true;
            } else {
                outzero++;
            }
        }
        
        StdOut.println(Math.max(inzero, outzero));
        inputg.printEdges(inputg);
        return;
    }
}
