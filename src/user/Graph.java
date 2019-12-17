package user;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Graph {
	// adjacency list of edges
	private List<List<Edge>> edges;

	// vector of vertices in
	private List<Vertex> vertices;

	private static Random rand = new Random();

	public Graph() {
		edges = new ArrayList<>();
		vertices = new ArrayList<>();
	}

	public List<List<Edge>> getEdges() {
		return edges;
	}

	public void setEdges(List<List<Edge>> edges) {
		this.edges = edges;
	}

	public List<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
	}

	public void addVertex(Vertex v) {
		vertices.add(v);
	}

	public void addEdge(Vertex from, Vertex to, double weight) {
		for (Edge e : edges.get(from.id())) {
			if (e.from() == from && e.to() == to || e.from() == to && e.to() == from) {
				return; // already has an edge, return
			}
		}

		edges.get(from.id()).add(new Edge(from, to, weight));
		edges.get(to.id()).add(new Edge(to, from, weight));
	}

	public void printGraph() {
		for (List<Edge> vList : edges) {
			if (vList.size() > 0) {
				System.out.print("Vertex ID: " + vList.get(0).from().id() + " Edge: ");
				for (Edge edge : vList) {
					System.out.print(String.format(" (%d, %d)", edge.from().id(), edge.to().id()));
				}
				System.out.println();
			}
		}
	}

	public static Graph genRandomNetwork(int nUsers, int nFriends) {
		nFriends = Math.min(nUsers - 1, nFriends);

		System.out.println(String.format("genRandomNetwork(nUsers = %d, nFriends = %d)", nUsers, nFriends));
		long curTime = Instant.now().toEpochMilli();
		Graph g = new Graph();

		// step 1: create vertices (users)
		IntStream.range(0, nUsers).forEach(i -> {
			g.addVertex(new Vertex(i));
			g.edges.add(new ArrayList<>());
		});
		System.out.println("  -> Create vertices takes: " + (Instant.now().toEpochMilli() - curTime) + " ms");

		// step 2: add edges (friends)
		curTime = Instant.now().toEpochMilli();
		for (Vertex v : g.vertices) {
			IntStream.range(0, nFriends).forEach(i -> {
				int idx = rand.nextInt(nUsers);
				while (idx == v.id()) {
					idx = rand.nextInt(nUsers);
				}
				g.addEdge(v, g.vertices.get(idx), 1.0);
			});
		}
		System.out.println("  -> Add edges takes: " + (Instant.now().toEpochMilli() - curTime) + " ms");
		return g;
	}

	public static Graph genNetwork(int nCommunity, int nMember) {
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
					g.edges.add(new ArrayList<>());
				});

				// for each member just created, add connections to all other members in one community (create clique)
				for (int t = 0; t < nMember; t++) {
					for (int t2 = 0; t2 < nMember; t2++) {
						if (t != t2) {
							g.addEdge(g.vertices.get(i * nMember + t), g.vertices.get(i * nMember + t2), 1.0);
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
					g.addEdge(g.vertices.get(randomUser1),
							g.vertices.get(randomUser2), 1.0);
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
