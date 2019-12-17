package algorithm;

import user.Edge;
import user.Graph;
import user.Vertex;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ConventionalMethod {

    private Graph graph;

    public ConventionalMethod(Graph graph) {
        setGraph(graph);
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public ArrayList<ArrayList<Vertex>> findCommunities() {
        long curTime = Instant.now().toEpochMilli();

        ArrayList<ArrayList<Vertex>> communities = new ArrayList<>();

        ArrayList<ArrayList<Vertex>> allSubVertices = generateSubVertices();
        for (ArrayList<Vertex> subVertices : allSubVertices) {
            if (isCompleteGraph(subVertices)) {
                communities.add(subVertices);
            }
        }
        System.out.println(" -> Find all communities takes: " + (Instant.now().toEpochMilli() - curTime) + " ms");
        return communities;
    }

    public int countNumberOfMember() {
        List<List<Edge>> edgeList = getGraph().getEdges();
        int numberOfMember = edgeList.size() > 0 ? edgeList.get(0).size() + 1 : 0;
        for (List<Edge> edges : edgeList) {
            if (edges.size() < numberOfMember) {
                numberOfMember = edges.size() + 1;
            }
        }

        return numberOfMember;
    }

    public void printVerticesList(ArrayList<ArrayList<Vertex>> verticesList) {
        for (ArrayList<Vertex> vertices : verticesList) {
            System.out.print("(");
            int finalIndex = vertices.size() - 1;
            for (int i = 0; i < finalIndex; i++) {
                System.out.print(vertices.get(i).id());
                System.out.print(",");
            }
            System.out.print(vertices.get(finalIndex).id());
            System.out.println(")");
        }
    }

    private ArrayList<ArrayList<Vertex>> generateSubVertices() {
        ArrayList<ArrayList<Vertex>> allSubVertices = new ArrayList<>();
        List<Vertex> graphVertices = graph.getVertices();

        int numberOfMember = countNumberOfMember();
        for (int i = 0; i <= graphVertices.size() - numberOfMember; i++) {
            for (int k = i + 1; k <= graphVertices.size() - numberOfMember + 1; k++) {
                ArrayList<Vertex> smallGroupVertices = new ArrayList<>();
                smallGroupVertices.add(graphVertices.get(i));
                for (int j = 0; j < numberOfMember - 1; j++) {
                    smallGroupVertices.add(graphVertices.get(k + j));
                }
                allSubVertices.add(smallGroupVertices);
            }
        }

        return allSubVertices;
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
