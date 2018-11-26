import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Set;

interface Graph{
    
    void addEdge(int v0, int v1);
    
    List<Integer>getVertices_Adj(int v);//We are defining interface graph here
}

class AdjSet implements Graph {

    List<Node> verticeList = new ArrayList<>();

    private int NumVertices = 0;//Defining Class and methods for AdjacencySet

    
    public AdjSet(int NumVertices) {
        this.NumVertices = NumVertices;
        for (int i = 0; i < NumVertices; i++) {
            verticeList.add(new Node(i));//We are creating AdjacencySet here
        }
    }

    public void addEdge(int v0, int v1) {
        if (v0 >= NumVertices || v0 < 0 || v1 >= NumVertices || v1 < 0) {
            throw new IllegalArgumentException("Vertex number does not exist: " + v0 + ", " + v1);
        }

        verticeList.get(v0).addEdge(v1);//An Edge is added to the Adjacency Set
    }

    public List<Integer> getVertices_Adj(int v) {
        if (v >= NumVertices || v < 0) {
            throw new IllegalArgumentException("Vertex number does not exist: " + v);
        }

        return verticeList.get(v).getVertices_Adj();//A list of Edges is created and Adjacent vertices are added
    }

    
    public int getVertexNum(int v){

        return verticeList.get(v).getNodeNumber();//We are getting vertex Number
    }


   class Node {

        private int NodeNumber;
        private Set<Integer> adjacencySet = new HashSet<>();

        public Node(int NodeNumber) {
            this.NodeNumber = NodeNumber;//A Node is created
        }

        public int getNodeNumber() {
            return NodeNumber;
        }

        public void addEdge(int NodeNumber) {
            adjacencySet.add(NodeNumber);
        }

        public List<Integer> getVertices_Adj() {
            List<Integer> sortedList = new ArrayList<>(adjacencySet);
            Collections.sort(sortedList);
            return sortedList;
        }
    }
}
