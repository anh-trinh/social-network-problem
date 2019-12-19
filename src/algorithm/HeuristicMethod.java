package algorithm;

import user.Edge;
import user.Graph;
import user.Vertex;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class HeuristicMethod {

    private Graph graph;

    ArrayList<ArrayList<Vertex>> communities = new ArrayList<>();

    public HeuristicMethod(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public ArrayList<ArrayList<Vertex>> findCommunities() {
        long curTime = Instant.now().toEpochMilli();

        List<Vertex> graphVertices = graph.getVertices();
        List<List<Edge>> edges = graph.getEdges();
        graph.printGraph();

        int index = 0;
        while (calculateTotalMembersOfCommunity() < graphVertices.size()) {
            ArrayList<Vertex> community = new ArrayList<>();
            Vertex chosenVertex = graphVertices.get(index);
            List<Edge> edgeList = edges.get(chosenVertex.id());
            Vertex vertexWithMostEdge = getVertexWithMostEdge(edgeList);
            index = vertexWithMostEdge.id();
            System.out.println("---------------");
            System.out.println("chosenVertex.id(): "+chosenVertex.id());
            System.out.println("vertexWithMostEdge.id(): "+vertexWithMostEdge.id());
            System.out.println(chosenVertex.id() == vertexWithMostEdge.id());
            if (chosenVertex.id() == vertexWithMostEdge.id()) {
                edgeList = edges.get(edgeList.get(0).to().id());
            }
            community.add(edgeList.get(0).from());
            for (Edge edgeOfChosenVertex : edgeList) {
                Vertex memberVertex = edgeOfChosenVertex.to();
                community.add(memberVertex);
            }
            this.communities.add(community);
        }

        return this.communities;
    }

    private Vertex getVertexWithMostEdge(List<Edge> edgeList) {
        Vertex  vertexWithMostEdge = null;
        int mostNumberOfEdges = 0;
        List<List<Edge>> edges = graph.getEdges();
        for (Edge edge : edgeList) {
            int numberOfEdges = edges.get(edge.to().id()).size();
            if (numberOfEdges > mostNumberOfEdges) {
                mostNumberOfEdges = numberOfEdges;
                vertexWithMostEdge = edge.to();
            }
        }
        return vertexWithMostEdge;
    }

    private int calculateTotalMembersOfCommunity() {
        int sum = 0;
        for (ArrayList<Vertex> community : this.communities) {
            sum += community.size();
        }
        return sum;
    }
}
