import algorithm.CountCommunity;
import user.Graph;
import user.Vertex;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // 1. Generate a random social network, represented as a graph.
        // create random graph with 50k user, each user has 1k friends
        //Graph.genRandomNetwork(50000, 1000);

        // 2. Generate a network with N communities, each has M members
        // create network with 5000 community, each has 100 users (500k users total)
        //Graph.genNetwork(5000, 100);

        CountCommunity countCommunity = new CountCommunity(2, 10);
        System.out.println("Number of communities: " + countCommunity.countNumberOfCommunity());

    }
}
