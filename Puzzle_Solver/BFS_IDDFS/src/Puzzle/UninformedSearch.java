package Assign3p71;

import java.util.ArrayList;
import java.util.List;

public class UninformedSearch {
    int checkPuzzles =0;                            //Initialising number of puzzles checked
    public UninformedSearch() {

    }   //This function expands the root node and applies Breadth First Search to return a
    // list to the solution if it exists.
    public List<Node> BFS(Node root){
        List<Node> pathToSolution = new ArrayList<Node>();  //Stores the path from root (problem) to the leaf (solution)
        List<Node> openList = new ArrayList<Node>();        //Open list for Nodes still to be processes.
        List<Node> closedList = new ArrayList<Node>();      // Closed list for already checked nodes.

        openList.add(root);                 // Adding the root node to the open list.
        boolean goalFound = false;          // Setting goalFound to false.

        // Loops runs until the openList is empty AND goalFound has been turned TRUE.
        while(openList.size() > 0 && !goalFound){
            Node curr = openList.get(0);            // Grabbing the first element into Node curr.
            closedList.add(curr);                   // Adding curr to closed list.
            openList.remove(0);               // Removing curr from openList.

            curr.expandNode();                      // Expanding Node curr LEFT,RIGHT,UP,DOWN

            for(int i=0; i< curr.children.size(); i++){     // Looping through each neighbour of parent
                Node currentChild = curr.children.get(i);
                checkPuzzles++;
                // Checking child/neighbour with the solution
                if((currentChild.puzzle.length==9 && currentChild.goalTestThreeByThree()) ||
                        (currentChild.puzzle.length==16 && currentChild.goalTestFourByFour())){
                    System.out.println();
                    System.out.println("SUCCESS!, found atleast one solution");
                    System.out.println("Checking the rest in the queue...");
                    goalFound = true;
                    //Trace to root
                     pathTrace(pathToSolution,currentChild);
                }
                if(!Contains(openList,currentChild) && !Contains(closedList,currentChild)){
                    openList.add(currentChild);
                }
            }
        }
        return pathToSolution;
    }
    public static boolean Contains(List<Node> list, Node c){
        boolean contains = false;
        for(int i =0; i< list.size(); i++){
            if(list.get(i).isSamePuzzle(c.puzzle)){
                contains = true;
            }
        }
        return contains;
    }
    public static void pathTrace(List<Node> path, Node n){
        Node current = n;
        path.add(current);
        while(current.parent != null){
            current= current.parent;
            path.add(current);
        }
    }
}


