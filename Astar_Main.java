/**
 * Created by sam on 3/18/17.
 */

import java.util.*;
import java.lang.*;

public class Astar_Main {

    public static int row=15;//number of rows
    public static int col=15;//number of columns
    public static int [][] mainboard = new int [row][col]; //this will hold the main board
    public static Node [][] node = new Node[row][col];
    public static Node start;
    public static Node goal;

    public static void main(String[]args)
    {
        Astar_Driver As = new Astar_Driver();
        As.set10percent(mainboard);
        As.writeToNode(mainboard, node);

        start=As.getStart(node);//gets the start node
        goal=As.getGoal(node);//gets the goal node

        As.getHeuristic(node, goal);
        As.displayNode(node);

        As.displayHeuristic(node);
       // System.out.println(node[start.getRow()][start.getCol()].getH());

        start.setG(0);
        start.setH(node[start.getRow()][start.getCol()].getH());
        start.setF();

        //makes  a copy of the nodeboard to be used to display the path later
        Node [][] copy = new Node[row][col];

        for (int i=0;i<node.length;i++){
            for (int j=0;j<node[0].length;j++)
            {
                copy[i][j]=node[i][j];
            }
        }

        boolean continueSearching = true;

        //create an openList and a closedList array
        ArrayList<Node> openList = new ArrayList<>();//will be used to hold nodes to be visited
        ArrayList<Node> closedList = new ArrayList<>();//will be used to hold nodes already visited

        openList.add(start);//add the start node to the openlist
        System.out.println("Adding Node: " + start.toString()+" to the openList");

        while(continueSearching){
            //remove node from openList
            Node n = openList.remove(0);//remove the first element
            System.out.println("\nRemoving node " + n.toString()+" from openList\n");

            //check if goal has been reached
            if(n.equals(goal)){
                System.out.println("Goal reached!. Path has been found");
                System.out.println("This is the path: ");
                continueSearching = false;//if goal is reached boolean is set to false which exits the loop and stop searching

                while(!n.equals(start)){
                    System.out.print(n.getParent().toStringFinal());
                    n = n.getParent();//set the parent to n

                    copy[n.getRow()][n.getCol()].setType(8);
                }
            }else{//look for neighbors around node
                int Nrow = n.getRow();
                int Ncol = n.getCol();

                for(int i = Nrow - 1; i <= Nrow + 1; i++){
                    for(int j = Ncol - 1; j <= Ncol + 1; j++){
                        //traverses to look for neighbors
                        if((i >= 0 && i < row) && (j >= 0 && j < row) && (i != Nrow || j != Ncol) && (node[i][j].getType() != 1)){
                            Node newNode = new Node(i, j, 0);//this is the current node to get neighbors of
                            System.out.println("Getting neighbors: " + newNode.toString());
                            newNode.setParent(n);
                            int newG = 10;//this is if it is moving either vertical or horizontal it adds a 10 for the G value
                            if(Math.abs(i - Nrow) + Math.abs(j - Ncol) == 2){
                                newG = 14;//this is for diagonal it adds 14 to the G value
                            }

                            newNode.setG(n.getG() + newG);//this is the new G value
                            newNode.setH(node[start.getRow()][start.getCol()].getH());//assigns the heuristic value to the node
                            newNode.setF();//gets the new F value

                            if(As.checkinList(newNode, closedList) == null){//checks to see if node is in the closed list or not
                                Node sm = As.checkinList(newNode, openList);//checks the newNOde against the openlist to see if it is present
                                if(sm == null){
                                    openList.add(newNode);//if it not it adds it to it
                                    System.out.println("\nAdding Node : " + newNode.toString()+" to List\n");
                                }else{
                                    if(newNode.getG() < sm.getG()){//check the G value and if it is smaller
                                        sm.setG(newNode.getG());//it means that path is shorter
                                        sm.setParent(n);//so it repaths to that node and sets it as its new parent
                                    }}}
                        }}
                }As.sort(openList); closedList.add(n);
            }}
        System.out.println("This is a grid layout where \"8\" represents the path\n");
        As.displayNode(copy);//displasy the grid with the path.
    }
}
