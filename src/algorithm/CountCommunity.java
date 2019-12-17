package algorithm;

import user.Edge;
import user.Graph;
import user.Vertex;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CountCommunity {

    private Graph graph;

    public CountCommunity(int nCommunity, int nMember) {
        setGraph(Graph.genNetwork(nCommunity, nMember));
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public int countNumberOfCommunity() {
        int numberOfCommunity = 0;
        ArrayList<ArrayList<Vertex>> allSubVertices = generateSubVertices();
        printSubVertices(allSubVertices);
        for (ArrayList<Vertex> subVertices : allSubVertices) {
            if (isCompleteGraph(subVertices)) {
                numberOfCommunity++;
            };
        }
        return numberOfCommunity;
    }

    private ArrayList<ArrayList<Vertex>> generateSubVertices() {
        ArrayList<ArrayList<Vertex>> allSubVertices = new ArrayList<>();
        List<Vertex> graphVertices = graph.getVertices();

        // generate sub vertices group with size from 3 vertices to all number of vertices
        for (int subVericesSize = 3; subVericesSize != graphVertices.size() + 1; subVericesSize++) {
            for (int i = 0; i <= graphVertices.size() - subVericesSize; i++) {
                for (int k = i + 1; k <= graphVertices.size() - subVericesSize + 1; k++) {
                    ArrayList<Vertex> smallGroupVertices = new ArrayList<>();
                    smallGroupVertices.add(graphVertices.get(i));
                    for (int j = 0; j < subVericesSize - 1; j++) {
                        smallGroupVertices.add(graphVertices.get(k + j));
                    }
                    allSubVertices.add(smallGroupVertices);
                }
            }
        }

        return allSubVertices;
    }

    public void printSubVertices(ArrayList<ArrayList<Vertex>> subVertices) {
        for (ArrayList<Vertex> vertices : subVertices) {
            System.out.print("(");
            for (Vertex vertex : vertices) {
                System.out.print(vertex.id());
                System.out.print(",");
            }
            System.out.println(")");
        }
    }

    private boolean isCompleteGraph(ArrayList<Vertex> subVertices) {
        System.out.println("-----------------------------------");
        List<List<Edge>> edges = this.graph.getEdges();
        int counter = 0;

        for (Vertex chosenVertex : subVertices) {
            counter++;
            List<Edge> adjacencyEdgesOfChosenVertex = edges.get(chosenVertex.id());
            for (Vertex consideredVertex : subVertices) {
                if (chosenVertex.id() != consideredVertex.id()) {
                    for (Edge adjacencyEdge : adjacencyEdgesOfChosenVertex) {
                        if (consideredVertex.id() == adjacencyEdge.to().id()) {
                            counter++;
                        }
                    }
                }
            }

            System.out.println("counter: "+counter);
            System.out.println("subVertices.size(): "+subVertices.size());
            if (counter < subVertices.size() || counter % subVertices.size() != 0) {
                System.out.println("return false");
                return false;
            }
        }
        System.out.println("return true");
        return true;
    }
}
