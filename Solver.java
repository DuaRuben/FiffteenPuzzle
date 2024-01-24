import fifteenpuzzle.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
public class Solver {
	
	static int SIZE = 0;  
	static HashMap<Integer, LinkedList<Node>> ClosedSet;
    static Node start;
    static Node goal;
    
    static Pair findCoord(int tile, int [][] board) {
        int i = 0, j = 0;
        for (i = 0; i < SIZE; i++)
            for (j = 0; j < SIZE; j++)
                if (board[i][j] == tile) {
                	Pair p = new Pair(i,j);
                	return p;
                }
        return null;
    }

    public static void goalBoard(){
        int value = 1;
		goal = new Node(SIZE);
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				if(i==SIZE-1 && j==SIZE-1) {
					goal.getBoard()[i][j] =0;
					break;
				}
				else
					goal.getBoard()[i][j] = value;
				value++;
			}
		}
    }

	public static void heuristic(Node u, Node goal) {
		if (u.getBoard() == null || goal.getBoard() == null)
			return;
		int manhhattan = 0;
		int euclidean = 0;
		int distance =0;
		int closest =10000;
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				if (u.getBoard()[i][j]!=0) {
					Pair p = findCoord(u.getBoard()[i][j],goal.getBoard());
					int x = Math.abs(i-p.i);
					int y = Math.abs(j-p.j);
					euclidean+=Math.sqrt(x*x+y*y);
					if(SIZE>6) {
						manhhattan+=x+y;
					}

				}
			}
		}
		u.setEuclideanHeuristic(euclidean);
		u.setClosestHeuristic(closest);
		u.setManhhattanHeuristic(manhhattan);
	}
	private static void addToMap(Node v) {
        if (ClosedSet.containsKey(v.hashCode))
        {
            LinkedList<Node> temp = ClosedSet.get(v.hashCode); //should save memory here
            for (int i =0; i < temp.size(); i++)
            {
                if (temp.get(i).equalTo(v))
                    return;
            }
            ClosedSet.get(v.hashCode).add(v);
        }
        else {
            LinkedList<Node> temp = new LinkedList<Node>();
            temp.add(v);
            ClosedSet.put(v.hashCode, temp);
        }
    }

    private static boolean setContains(HashMap<Integer, LinkedList<Node>> temp, Node n, PriorityQueue<Node> q) {
        if (temp.containsKey(n.hashCode))
        {
            LinkedList<Node> t = temp.get(n.hashCode);
            for (int i = 0; i < t.size(); i++) {
                if (t.get(i).equalTo(n)) {
                        return true;
                    }
                }    
            }
        return false;
    }

	public static Node algorithm() {
		
		int count = 0;
		PriorityQueue<Node> q = new PriorityQueue<Node>();
		ClosedSet = new HashMap<Integer, LinkedList<Node>>(); 
		q.add(start);
		if(start.isSolved())
			return start;
		while(!q.isEmpty()) {
			Node v = q.poll();
			String dir ="LRUD";
			for(int i=0;i<4;i++) {
				String move = ""+dir.charAt(i);
				if(v.isLegal(move)) {
					Node u = new Node(v);
					u.makeMove(move);
					u.setHashCode();
					heuristic(u,goal);
					if(u.isSolved()) {
						u.setParent(v);
						return u;
					}
					else {
						boolean flag = setContains(ClosedSet,u,q);
						if(!flag)
							q.add(u);
						u.setParent(v);
					}
				}
			
			}
			addToMap(v);
		}
		return null;
	}
	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("number of arguments: " + args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}

		if (args.length < 2) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}
		
		// TODO
		File input = new File(args[0]);
		if(!input.exists())
			throw new FileNotFoundException("File does not exists.");
		
		else
			System.out.println("File Found");
		
		Scanner reader = new Scanner(input);
		int x = 0;
		int y = 0;
		SIZE = reader.nextLine().charAt(0) - 48;
		start = new Node(SIZE);
		
		System.out.println(SIZE);
		while (reader.hasNext()) {
			String s = reader.nextLine();
			int num = 0;
			for (int i = 0; i < SIZE*3; i++) {
				if (i != (2+3*(y))) {
					if (s.charAt(i) >= 48 && s.charAt(i) <= 57) {
						num = num * 10 + (s.charAt(i) - 48);
					}
					else if(s.charAt(i)!=' ') {
						reader.close();
						break;
					}
				} else {
					start.getBoard()[x][y++] = num;
					num = 0;
				}
			}
			x++;
			y = 0;
		}
		reader.close();
		goalBoard();
		start.emptySpace = findCoord(0,start.getBoard());
		heuristic(start,goal);
	
		Node last = new Node(SIZE);
		last = algorithm();
		last.print();

		if (last == null)
			System.out.println("Not Successfull");
		File output = new File(args[1]);

		PrintWriter write = new PrintWriter(output);
		ArrayList<String> path = new ArrayList<String>();
		while (last.getParent() != null) {
			path.add(last.getMove());
			last = last.getParent();
		}

		for (int i = path.size() - 1; i >= 0; i--)
			write.println(path.get(i));
		write.close();
		System.out.println("DONE");
	}
}
