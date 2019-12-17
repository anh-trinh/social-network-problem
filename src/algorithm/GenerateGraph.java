package algorithm;

import user.Graph;
import user.Vertex;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class GenerateGraph {

    private Random rand = new Random();

    public Graph genRandomNetwork(int nUsers, int nFriends) {
        Graph g = new Graph();
        nFriends = Math.min(nUsers - 1, nFriends);

        System.out.println(String.format("genRandomNetwork(nUsers = %d, nFriends = %d)", nUsers, nFriends));
        long curTime = Instant.now().toEpochMilli();

        // step 1: create vertices (users)
        IntStream.range(0, nUsers).forEach(i -> {
            g.addVertex(new Vertex(i));
            g.getEdges().add(new ArrayList<>());
        });
        System.out.println("  -> Create vertices takes: " + (Instant.now().toEpochMilli() - curTime) + " ms");

        // step 2: add edges (friends)
        curTime = Instant.now().toEpochMilli();
        for (Vertex v : g.getVertices()) {
            IntStream.range(0, nFriends).forEach(i -> {
                int idx = rand.nextInt(nUsers);
                while (idx == v.id()) {
                    idx = rand.nextInt(nUsers);
                }
                g.addEdge(v, g.getVertices().get(idx), 1.0);
            });
        }
        System.out.println("  -> Add edges takes: " + (Instant.now().toEpochMilli() - curTime) + " ms");
        return g;
    }

    public Graph genNetwork(int nCommunity, int nMember) {
        Graph g = new Graph();

        if (nMember >= 3) {
            System.out.println(String.format("createGraph(nCommunity = %d, nMember = %d)", nCommunity, nMember));
            long curTime = Instant.now().toEpochMilli();

            // step 1: add mandatory requirement (n communities, each has m members)
            IntStream.range(0, nCommunity).forEach(i -> {

                // create members
                IntStream.range(0, nMember).forEach(j -> {
                    Vertex v = new Vertex(i * nMember + j);
                    g.addVertex(v);
                    g.getEdges().add(new ArrayList<>());
                });

                // for each member just created, add connections to all other members in one community (create clique)
                for (int t = 0; t < nMember; t++) {
                    for (int t2 = 0; t2 < nMember; t2++) {
                        if (t != t2) {
                            g.addEdge(g.getVertices().get(i * nMember + t), g.getVertices().get(i * nMember + t2), 1.0);
                        }
                    }
                }
            });
            System.out.println("  -> Add links takes: " + (Instant.now().toEpochMilli() - curTime) + " ms");

            // step 2: add random links between those communities
            curTime = Instant.now().toEpochMilli();
            IntStream.range(0, nCommunity).forEach(i -> {
                int randomUser1 = rand.nextInt(i * nMember + nMember);
                int randomUser2 = rand.nextInt(nCommunity * nMember);

                // make sure a user can not connect itself
                if (randomUser1 != randomUser2) {
                    g.addEdge(g.getVertices().get(randomUser1),
                            g.getVertices().get(randomUser2), 1.0);
                }
            });

            System.out.println("  -> Add more links takes: " + (Instant.now().toEpochMilli() - curTime) + " ms");
        }
        else {
            System.out.println("Please enter at least 3 users");
        }

        return g;
    }
}
