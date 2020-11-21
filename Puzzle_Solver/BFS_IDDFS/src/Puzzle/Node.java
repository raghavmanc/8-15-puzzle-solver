package Assign3p71;

import java.util.ArrayList;
import java.util.List;

import static Assign3p71.UninformedSearch.Contains;

public class Node {
    public List<Node> children;
    public List<Node> adjList;
    public Node parent;
    public int x = 0;
    public int[] puzzle;
    public int col;
    public String shift;
    public int depthLevel;

    public Node(int[] p){
        depthLevel = 0;
        children = new ArrayList<Node>();
        adjList = new ArrayList<Node>();
        puzzle = new int[p.length];
        col = (int) Math.sqrt(p.length);
        setPuzzle(p);
    }

    // Just setting the puzzle after the constructor call
    public void setPuzzle(int[] p){
        for (int i=0; i<p.length; i++){
            puzzle[i] = p[i];

        }
    }

    public boolean goalTestThreeByThree(){
        boolean isGoal = true;
        int solution[] = {1,2,3,4,5,6,7,8,0};
        for(int i=0;i< solution.length; i++){
            if(solution[i] != puzzle[i]){
                isGoal = false;
                return isGoal;
            }
        }

        return isGoal;
    }
    public boolean goalTestFourByFour(){
        boolean isGoal = true;
        int solution[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
        for(int i=0;i< solution.length; i++){
            if(solution[i] != puzzle[i]){
                isGoal = false;
                return isGoal;
            }
        }

        return isGoal;
    }
    // Expanding node after figuring the position of the blank tile
    public void expandNode(){
        for(int i=0; i<puzzle.length;i++){
            if(puzzle[i] == 0){
                x = i;
            }
        }
        moveToDown(puzzle,x);
        moveToLeft(puzzle,x);
        moveToRight(puzzle,x);
        moveToUp(puzzle,x);
    }

    // Check if we can move RIGHT
    public void moveToRight(int[] p, int i){
        if(i%col < col-1){
            int possMove[] = new int[9];
            possMove = p.clone();
            int temp = possMove[i+1];
            possMove[i+1] = possMove[i];
            possMove[i] = temp;

            Node child = new Node(possMove);

            children.add(child);
            child.parent = this;
            child.shift = "Shifted RIGHT";
        }

    }
    // Check if we can move LEFT
    public void moveToLeft(int[] p, int i){
        if(i%col > 0){
            int possMove[] = new int[puzzle.length];
            possMove = p.clone();
            int temp = possMove[i-1];
            possMove[i-1] = possMove[i];
            possMove[i] = temp;

            Node child = new Node(possMove);
            children.add(child);
            child.parent = this;
            child.shift = "Shifted LEFT";
        }

    }
    // Check if we can move UP
    public void moveToUp(int[] p, int i){
        if(i-col >= 0){
            int possMove[] = new int[puzzle.length];
            possMove = p.clone();
            int temp = possMove[i-col];
            possMove[i-col] = possMove[i];
            possMove[i] = temp;

            Node child = new Node(possMove);
            children.add(child);
            child.parent = this;
            child.shift = "Shifted UP";
        }

    }
    // Check if we can move DOWN
    public void moveToDown(int[] p, int i){
        if(i+col < puzzle.length){
            int possMove[] = new int[puzzle.length];
            possMove = p.clone();
            int temp = possMove[i+col];
            possMove[i+col] = possMove[i];
            possMove[i] = temp;

            Node child = new Node(possMove);
            //MAJOR CHANGE FOR BFS
            if(!Contains(children,child)) {
                children.add(child);
            }
            child.parent = this;
            child.shift = "Shifted DOWN";
        }

    }

    public void printPuzzle(){
        System.out.println();
        int m =0;
        if(this.shift==null){
            System.out.println("Problem Puzzle");
        }
        else {
            System.out.println(this.shift);
        }
        for (int i=0;i<Math.sqrt(puzzle.length);i++){
            for(int j =0; j<Math.sqrt(puzzle.length);j++){
                System.out.print(puzzle[m] + " ");
                m++;
            }
            System.out.println();
        }

    }
    public boolean isSamePuzzle(int[] p){
        boolean samePuzzle = true;
        for (int i= 0; i<p.length; i++){
            if(puzzle[i] != p[i]){
                samePuzzle = false;
                return samePuzzle;
            }
        }
        return samePuzzle;
    }
    public void addNeighbour(Node node){
            this.adjList.add(node);
        }
    }




