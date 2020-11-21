package Assign3p71;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Puzzle {
    Scanner x = new Scanner(System.in);
    int[] puzzle;
    int size = 0;

        public Puzzle(){
            System.out.println("Enter the size of the board. (3 or 4)");
            size = x.nextInt();
            puzzle = new int[size*size];
            for (int i=0; i<size*size;i++) {
            System.out.println("Please enter the "+ (i+1) +" st/nd/rd/th "+ "number. (ROW-WISE)");
                puzzle[i] = x.nextInt();
            }

        }
    //BFS
    public static void runBFS(Node root){
        UninformedSearch ui = new UninformedSearch();
        List<Node> sol = ui.BFS(root);

        if (sol.size()>0) {
            Collections.reverse(sol);
            for (int i = 0; i < sol.size(); i++) {
                sol.get(i).printPuzzle();

            }
        }
        else{
            System.out.println("No Solution Found!");

        }
    }
    public static void options(Node root) {
        Scanner y = new Scanner(System.in);
        System.out.println("Please choose the Algorithm to be used");
        System.out.println("Press '1' for IDDFS");
        System.out.println("Press '2' for BFS");

        int choice = y.nextInt();

        switch (choice) {
            case 1:
                IterativeDFS god = new IterativeDFS();
                god.runIterativeSearch(root);
                break;
            case 2:
                runBFS(root);
                break;
            default:
                System.out.println();
                System.out.println("Please choose a valid option");
                options(root);


        }
    }



    public static void main(String[] args) {

        Puzzle mainPuzzle = new Puzzle();
        Node root = new Node(mainPuzzle.puzzle);
        options(root);
    }//main


}

