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

        //there only two kinds of vertex in this graph, one is members of communities and one is linked-vertex to link community
        List<Vertex> graphVertices = graph.getVertices();
        List<List<Edge>> edges = graph.getEdges();
        //graph.printGraph();
        int index = 0;
        while (calculateTotalMembersOfCommunity() < graphVertices.size()) {
            ArrayList<Vertex> community = new ArrayList<>();

            // choose a first vertex to check and get its connected edges
            Vertex chosenVertex = graphVertices.get(index);
            List<Edge> edgeList = edges.get(chosenVertex.id());

            // find out the vertex with the most number of connected edges, this could be a linked-vertex to the another community, and return the edge
            Edge vertexWithMostEdge = getVertexWithMostEdge(edgeList, index);

            // the vertex not in community will next chosen vertex to find community
            if (vertexWithMostEdge.from().id() == chosenVertex.id() || vertexWithMostEdge.to().id() == chosenVertex.id()) {
                edgeList = edges.get(vertexWithMostEdge.to().id());
                vertexWithMostEdge = getVertexWithMostEdge(edgeList, index);
            }
            index = vertexWithMostEdge.to().id();
            if (index == chosenVertex.id() || index == chosenVertex.id()) {
                if (index < graphVertices.size() / 2){
                    index += edges.get(vertexWithMostEdge.from().id()).size() + 1;
                }
                else {
                    index -= edges.get(vertexWithMostEdge.from().id()).size() + 1;
                }
            }


            community.add(edgeList.get(0).from());

            // add all vertex if it has the same number of connected edges
            for (Edge edgeOfChosenVertex : edgeList) {
                Vertex memberVertex = edgeOfChosenVertex.to();
                community.add(memberVertex);
            }
            this.communities.add(community);
        }
        System.out.println("  -> Find community by heuristic takes: " + (Instant.now().toEpochMilli() - curTime) + " ms");
        return this.communities;
    }

    private Edge getVertexWithMostEdge(List<Edge> edgeList, int index) {

        //find out the vertex with the most number of edges
        Vertex vertexWithMostEdge = edgeList.get(0).from();
        int mostNumberOfEdges = edgeList.size();
        int minNumberOfEdges = edgeList.size();
        List<List<Edge>> edges = graph.getEdges();
        for (Edge edge : edgeList) {
            int numberOfEdges = edges.get(edge.to().id()).size();
            if (numberOfEdges >= mostNumberOfEdges && !isExistInCommunities(edge.to()) && edge.to().id() != index) {
                mostNumberOfEdges = numberOfEdges;
                vertexWithMostEdge = edge.to();
            }
            else {
                minNumberOfEdges = numberOfEdges;
            }
        }

        // from above find out the edge contains a vertex which not a member of community
        List<Edge> edgeListOfVertexWithMostEdge = edges.get(vertexWithMostEdge.id());
        Edge edgeOfVertexWithMostEdge = edgeListOfVertexWithMostEdge.get(0);

        for (Edge considerdEdgeOfVertexWithMostEdge : edgeListOfVertexWithMostEdge) {
            if (minNumberOfEdges < edges.get(considerdEdgeOfVertexWithMostEdge.to().id()).size()
                    && !isExistInCommunities(considerdEdgeOfVertexWithMostEdge.to())) {
                edgeOfVertexWithMostEdge = considerdEdgeOfVertexWithMostEdge;
            }
        }
        return edgeOfVertexWithMostEdge;
    }

    private boolean isExistInCommunities(Vertex vertex) {
        for (ArrayList<Vertex> community : this.communities) {
            for (Vertex eachVertex : community) {
                if (eachVertex.id() == vertex.id()) {
                    return true;
                }
            }
        }

        return false;
    }

    private int calculateTotalMembersOfCommunity() {
        int sum = 0;
        for (ArrayList<Vertex> community : this.communities) {
            sum += community.size();
        }
        return sum;
    }
}
