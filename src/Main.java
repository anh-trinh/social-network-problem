import algorithm.ConventionalMethod;
import algorithm.GenerateGraph;
import algorithm.HeuristicMethod;
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
//        GenerateGraph generateGraph = new GenerateGraph();
//        Graph graph = generateGraph.genNetwork(2, 10);
//
//        ConventionalMethod conventionalMethod = new ConventionalMethod(graph);
//        ArrayList<ArrayList<Vertex>> communities = conventionalMethod.findCommunities();
//        System.out.println("List of communities:");
//        printVerticesList(communities);
//        System.out.println(" -> number of communities: " + communities.size());

        // 4. Develop a heuristics algorithm to find as many communities as possible
        GenerateGraph generateGraph2 = new GenerateGraph();
        Graph graph2 = generateGraph2.genNetwork(2, 4);

        HeuristicMethod heuristicMethod = new HeuristicMethod(graph2);
        ArrayList<ArrayList<Vertex>> communities2 = heuristicMethod.findCommunities();
        System.out.println("List of communities by heuristic:");
        printVerticesList(communities2);
        System.out.println(" -> number of communities: " + communities2.size());
    }

    public static void printVerticesList(ArrayList<ArrayList<Vertex>> verticesList) {
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
}
