package algorithm;

import user.Edge;
import user.Graph;
import user.Vertex;

import java.util.ArrayList;
import java.util.List;

public class CountCommunity {

    private Graph graph;

    public CountCommunity(int nCommunity, int nMember) {
        GenerateGraph generateGraph = new GenerateGraph();
        Graph graph = generateGraph.genNetwork(nCommunity, nMember);
        setGraph(graph);
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public int countNumberOfCommunity() {
        int numberOfCommunity = getCommunityVertices().size();
        return numberOfCommunity;
    }

    public ArrayList<ArrayList<Vertex>> getCommunityVertices() {
        ArrayList<ArrayList<Vertex>> communityVertices = new ArrayList<>();
        ArrayList<ArrayList<Vertex>> completeGraphVertices = filterCompleteGraphVertices();

        while (completeGraphVertices.size() != 0) {
            ArrayList<Vertex> chosenVertices = completeGraphVertices.get(0);
            for (int i = 1; i < completeGraphVertices.size(); i++) {
                ArrayList<Vertex> consideredVertices = completeGraphVertices.get(i);
                if (consideredVertices.size() > chosenVertices.size()) {
                    if (consideredVertices.containsAll(chosenVertices)) {
                        communityVertices.add(consideredVertices);
                        completeGraphVertices.remove(i);
                    }
                }
                else {
                    if (chosenVertices.containsAll(consideredVertices)) {
                        communityVertices.add(chosenVertices);
                        completeGraphVertices.remove(i);
                    }
                }
            }
            completeGraphVertices.remove(0);
        }
        return communityVertices;
    }

    private ArrayList<ArrayList<Vertex>> filterCompleteGraphVertices() {
        ArrayList<ArrayList<Vertex>> completeGraphVertices = new ArrayList<>();
        ArrayList<ArrayList<Vertex>> allSubVertices = generateSubVertices();
        for (ArrayList<Vertex> subVertices : allSubVertices) {
            boolean isCompleteGraph = isCompleteGraph(subVertices);
            if (isCompleteGraph) {
                completeGraphVertices.add(subVertices);
            };
        }
        return completeGraphVertices;
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

        List<List<Edge>> edges = this.graph.getEdges();

        for (Vertex chosenVertex : subVertices) {
            int counter = 1;
            List<Edge> adjacencyEdgesOfChosenVertex = edges.get(chosenVertex.id());
            for (Vertex consideredVertex : subVertices) {
                if (chosenVertex.id() != consideredVertex.id()) {
                    for (Edge adjacencyEdge : adjacencyEdgesOfChosenVertex) {
                        if (consideredVertex.id() == adjacencyEdge.to().id()) {
                            counter++;
                            break;
                        }
                    }
                }
            }

            if (counter != subVertices.size()) {
                return false;
            }
        }
        return true;
    }
}
