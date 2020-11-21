import java.util.*;
import static java.lang.System.exit;

class puzzle {

    public static void main(String[] args) {
        new puzzle();
    }

    private int puzzleSize;
    private int[][] goalState;

    private static PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparing(Node::getF_n));
    private static ArrayList<Node> visited = new ArrayList<>();  // previously explored states
    public List<Node> pathToSolution = new ArrayList<Node>();
    ArrayList<Node> arr = new ArrayList<>();
    ArrayList<Node> children = new ArrayList<Node>();

    private int[][] goalStateThreeByThree = {{1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}};
    private int[][] getGoalStateFourByFour = {{1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}};

    private static int expandNode = 0;


    puzzle() {

        int returnVal = -1;
        Scanner scanner = new Scanner(System.in);
        Scanner m = new Scanner(System.in);
        System.out.println("Enter puzzle size. (3 or 4)");
        int choice = m.nextInt();
        if (choice == 3) {
            puzzleSize = 3;
            goalState = goalStateThreeByThree.clone();
        } else if (choice == 4) {
            puzzleSize = 4;
            goalState = getGoalStateFourByFour.clone();
        } else {
            System.out.println("Please input either 3 or 4");
        }

        if (true) {
            System.out.println("Enter problem puzzle with space in between and press enter to fill in the next row");
            int[][] root = new int[puzzleSize][puzzleSize];
            for (int i = 0; i < puzzleSize; i++) {
                String[] row;
                String line = scanner.nextLine();
                if (line == null) {
                    System.err.println("Invalid Input!");
                }
                row = line.split(" ");
                for (int j = 0; j < puzzleSize; j++)
                    root[i][j] = Integer.parseInt(row[j]);      //Converting string to int
            }
            Node rootProb = new Node(root,0,0);
            System.out.println("Problem");
            printPuzzle(root);
            returnVal = AstarSeacrh(rootProb);

        } else {
            System.err.println("Please enter a valid choice: '1' or '2'");
            exit(1);
        }
        if (returnVal == 0) {
            System.out.println("Solved!\n");

            printSolPath(pathToSolution);

            System.out.println("Number of nodes expanded: " + expandNode);
        } else if (returnVal == -1) {
            System.out.println("Error: Given input has no solution!");
        }
    }

    private int AstarSeacrh(Node root) {
        queue.add(root);
        visited.add(root);
        while (true) {
            if (queue.isEmpty()) return -1;
            Node tempNodeState = queue.peek();
            int[][] tempNode = tempNodeState.getPuzzle();
            int[][] topNode = new int[root.getPuzzle().length][];
            for (int i = 0; i < root.getPuzzle().length; i++)
                topNode[i] = tempNode[i].clone();
            Node topNodeState = new Node(topNode, tempNodeState.getG(), tempNodeState.getH());
            queue.remove();
            if (isSamePuzzle(topNodeState.getPuzzle(), goalState)) {
                pathTrace(pathToSolution,tempNodeState);
                return 0;
            } else {  // keep expanding
                children = getChildren(topNodeState);
                expandNode++;

                for (Node child : children) {
                    if (!contains(child)) {  // if unique state
                        queue.add(child);
                        visited.add(child);
                        child.parent = tempNodeState;
                    }


                }
            }
        }

    }
    private ArrayList<Node> getChildren(Node currNode) {
        int[][] currPuzzle = currNode.getPuzzle();
        int g_n = currNode.getG();
        int coordinate_X = -1;
        int coordinate_Y = -1;
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                if (currPuzzle[i][j] == 0) {
                    coordinate_X = i;
                    coordinate_Y = j;
                }
            }
        }

        int h_n = 0;

        for (int i = 0; i < puzzleSize; i++)
            for (int j = 0; j < puzzleSize; j++) {
                int target = currPuzzle[i][j];
                for (int k = 0; k < puzzleSize; k++)
                    for (int l = 0; l < puzzleSize; l++) {
                        if (goalState[k][l] == target) {
                            h_n += Math.abs(k - i) + Math.abs(l - j);
                        }
                    }
            }
        if (coordinate_X - 1 >= 0) {   // UP
            int[][] currentPuzzle = new int[currPuzzle.length][];
            for (int i = 0; i < currPuzzle.length; i++)
                currentPuzzle[i] = currPuzzle[i].clone();
            int varHolder = currentPuzzle[coordinate_X][coordinate_Y];
            currentPuzzle[coordinate_X][coordinate_Y] = currentPuzzle[coordinate_X - 1][coordinate_Y];
            currentPuzzle[coordinate_X - 1][coordinate_Y] = varHolder;
            Node child = new Node(currentPuzzle, g_n + 1, h_n);
            child.parent = currNode;
            arr.add(child);
            child.shift = "UP";
        }
        if (coordinate_X + 1 < puzzleSize) {    // DOWN
            // Clone currPuzzle to currentPuzzle
            int[][] currentPuzzle = new int[currPuzzle.length][];
            for (int i = 0; i < currPuzzle.length; i++)
                currentPuzzle[i] = currPuzzle[i].clone();

            int temp = currentPuzzle[coordinate_X][coordinate_Y];
            currentPuzzle[coordinate_X][coordinate_Y] = currentPuzzle[coordinate_X + 1][coordinate_Y];
            currentPuzzle[coordinate_X + 1][coordinate_Y] = temp;
            Node child = new Node(currentPuzzle, g_n + 1, h_n);
            child.parent = currNode;
            arr.add(child);
            child.shift = "DOWN";
        }
        if (coordinate_Y - 1 >= 0) {   // LEFT
            int[][] currentPuzzle = new int[currPuzzle.length][];
            for (int i = 0; i < currPuzzle.length; i++)
                currentPuzzle[i] = currPuzzle[i].clone();

            int temp = currentPuzzle[coordinate_X][coordinate_Y];
            currentPuzzle[coordinate_X][coordinate_Y] = currentPuzzle[coordinate_X][coordinate_Y - 1];
            currentPuzzle[coordinate_X][coordinate_Y - 1] = temp;
            Node child = new Node(currentPuzzle, g_n + 1, h_n);
            child.parent = currNode;
            arr.add(child);
            child.shift = "LEFT";
        }
        if (coordinate_Y + 1 < puzzleSize) {  // RIGHT
            int[][] currentPuzzle = new int[currPuzzle.length][];
            for (int i = 0; i < currPuzzle.length; i++)
                currentPuzzle[i] = currPuzzle[i].clone();

            int temp = currentPuzzle[coordinate_X][coordinate_Y];
            currentPuzzle[coordinate_X][coordinate_Y] = currentPuzzle[coordinate_X][coordinate_Y + 1];
            currentPuzzle[coordinate_X][coordinate_Y + 1] = temp;
            Node child = new Node(currentPuzzle, g_n + 1, h_n);
            child.parent = currNode;
            arr.add(child);
            child.shift = "RIGHT";
        }
        return arr;
    }


    private void printPuzzle(int[][] actualPuzzle) {
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                System.out.print(actualPuzzle[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isSamePuzzle(int[][] puzzle1, int[][] puzzle2) {
        for (int i = 0; i < puzzleSize; i++)
            for (int j = 0; j < puzzleSize; j++)
                if (puzzle1[i][j] != puzzle2[i][j])
                    return false;
        return true;
    }

    public static void pathTrace(List<Node> path, Node n){
        Node current = n;
        path.add(current);
        while(current.parent != null){
            current= current.parent;
            path.add(current);
        }

    }

    public void printSolPath(List<Node> sol){
        if (sol.size()>0) {
            Collections.reverse(sol);
            for (int i = 0; i < sol.size(); i++) {
                System.out.println("SHIFT "+sol.get(i).shift);
                printPuzzle(sol.get(i).getPuzzle());

            }
        }

    }

    private boolean contains(Node Node) {
        int[][] child = Node.getPuzzle();
        for (Node node2 : visited) {
            int[][] temp = node2.getPuzzle();
            boolean identical = true;
            for (int i = 0; i < puzzleSize; i++)
                for (int j = 0; j < puzzleSize; j++)
                    if (temp[i][j] != child[i][j])
                        identical = false;
            if (identical)
                return true;
        }
        return false;
    }
}