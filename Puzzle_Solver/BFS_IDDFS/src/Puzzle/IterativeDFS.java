package Assign3p71;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import static Assign3p71.UninformedSearch.Contains;

public class IterativeDFS {
    public List<Node> Solution;
    public Boolean isTargetFound = false;
    public IterativeDFS(){

    }
    public void runIterativeSearch(Node root){
        Solution = new ArrayList<Node>();
        int depth = 0;
        while(!isTargetFound){
            System.out.println();
            dfs(root,depth);        // dfs call with depth = 0
            depth++;
        }
    }
    public Boolean checkStack(Stack<Node> a,Node b){
        Boolean result = false;
        for(int i=0;i<a.size(); i++){
            if(a.get(i).isSamePuzzle(b.puzzle)){
                result = true;
            }
        }
      return result;
    }
    public void dfs(Node root, int depth){
        Stack<Node> stack = new Stack<Node>();
        root.depthLevel = 0;
        stack.push(root);                     // pushing element into the stack

        //While stack is not empty, keep expaning and checking actualNode until depthLevel>=depth
        while(!stack.empty()){
            Node actualNode = stack.pop();
            if (actualNode.isSamePuzzle(root.puzzle)){
            }
            if((actualNode.puzzle.length==9 && actualNode.goalTestThreeByThree()) ||
                    (actualNode.puzzle.length==16 && actualNode.goalTestFourByFour())) {
                System.out.println("Success");
                this.isTargetFound = true;
                pathTrace(Solution,actualNode);
                printSolPath(Solution);
                return;
            }
            if(actualNode.depthLevel>=depth){
                continue;
            }
            // Node expansion to find children
            actualNode.expandNode();
            for(int i=0; i< actualNode.children.size(); i++) {
                if(!Contains(actualNode.adjList, actualNode) && !Contains(stack,actualNode))
                actualNode.addNeighbour(actualNode.children.get(i));
            }
            for(Node node : actualNode.adjList){
                node.depthLevel = actualNode.depthLevel+1;
                if(!checkStack(stack,node)){
                    stack.push(node);
                }
            }
        }
    }
    public void printSolPath(List<Node> sol){
        if (sol.size()>0) {
            Collections.reverse(sol);
            for (int i = 0; i < sol.size(); i++) {
                sol.get(i).printPuzzle();
            }
        }
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
