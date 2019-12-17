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
}
