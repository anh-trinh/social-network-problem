package user;

public class Edge {
    private Vertex from;
    private Vertex to;
    private double weight;

    public Edge(Vertex from, Vertex to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Vertex from() {
        return from;
    }

    public Vertex to() {
        return to;
    }

    public double weight() {
        return weight;
    }
}
