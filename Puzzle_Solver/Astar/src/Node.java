class Node {
    private int[][] grid;
    private int g;  // cost
    private int h;  // heuristic distance
    public Node parent;
    String shift;


    Node(int[][] grid, int g, int h) {
        this.grid = grid;
        this.g = g;
        this.h = h;
    }

    Node (Node node){

    }

    int[][] getPuzzle() {
        return grid;
    }
    int getG() {
        return g;
    }
    int getH() {
        return h;
    }
    int getF_n() {
        return (g + h);
    }
}

