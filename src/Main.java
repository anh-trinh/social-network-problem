import algorithm.ConventionalMethod;
import algorithm.GenerateGraph;
import user.Graph;
import user.Vertex;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // 1. Generate a random social network, represented as a graph.
        // create random graph with 50k user, each user has 1k friends
        //Graph graph1 = new Graph();
        //graph1.genRandomNetwork(50000, 1000);

        // 2. Generate a network with N communities, each has M members
        // create network with 5000 community, each has 100 users (500k users total)
        //Graph graph2 = new Graph();
        //graph2.genNetwork(5000, 100);

        // 3. Develop an algorithm to find all of communities
        GenerateGraph generateGraph = new GenerateGraph();
        Graph graph = generateGraph.genNetwork(30, 100);

        ConventionalMethod conventionalMethod = new ConventionalMethod(graph);
        ArrayList<ArrayList<Vertex>> communities = conventionalMethod.findCommunities();
        System.out.println("List of communities:");
        conventionalMethod.printVerticesList(communities);
        System.out.println(" -> number of communities: " + communities.size());

    }
}
