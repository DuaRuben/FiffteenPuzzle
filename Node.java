package fifteenpuzzle;

public class Node implements Comparable<Node> {
	public int[][] board;
	public int SIZE = -1;
	public Pair emptySpace = new Pair(-1,-1);
	public int manhhattanheuristic = -1;
	public Node parent = null;
	public String move="";
	public int hashCode =0;
	public int closestheuristic =-1;
	public int euclideanheuristic = -1;
	
	//Constructors
	public Node(int size) {
		SIZE = size;
		board = new int[size][size];
	}
	public Node(Node n) {
		SIZE = n.getSize();
		board = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++) {
				board[i][j] = n.getBoard()[i][j];
				if(board[i][j] == 0) {
					this.emptySpace = new Pair(i,j);
				}
			}
	}
	
	//getters
	public Node getParent() {
		return this.parent;
	}
	public String getMove() {
		return move;
	}
	public int getHeuristic() {
		return manhhattanheuristic;
	}

	public int getSize() {
		return SIZE;
	}

	public Pair getEmptySpace() {
		return emptySpace;
	}
	public int[][] getBoard() {
		return board;
	}
	
	//setters
	public void setParent(Node p) {
		this.parent =p;
	}
	public void setMove(String m) {
		this.move = m;
	}

	// @Override hashcode method to generate hashcode of given object
	public void setHashCode() {
		int num=0;
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				if(i==j)
					num=num*10+board[i][j];
				
				else if(i+j==SIZE-1)
					num=num*10+board[i][j];
			}
		}
		//num =  num/99999989;
		this.hashCode = num;

	}

	public void setManhhattanHeuristic(int m) {
		this.manhhattanheuristic = m;
	}
	public void setEuclideanHeuristic(int e) {
		this.euclideanheuristic = e;
	}
	public void setClosestHeuristic(int h) {
		this.closestheuristic = h;
	}
	
	//Functions
	public void makeMove(String s) {
		if (s.equals("L")) {
			int temp = board[emptySpace.i][emptySpace.j - 1];
			this.setMove(temp+" "+"R");
			board[emptySpace.i][emptySpace.j - 1] = 0;
			board[emptySpace.i][emptySpace.j] = temp;
			this.emptySpace.j=this.emptySpace.j-1;
		} else if (s.equals("R")) {
			int temp = board[emptySpace.i][emptySpace.j + 1];
			this.setMove(temp+" "+"L");
			board[emptySpace.i][emptySpace.j + 1] = 0;
			board[emptySpace.i][emptySpace.j] = temp;
			this.emptySpace.j = this.emptySpace.j+1;
		} else if (s.equals("U")) {
			int temp = board[emptySpace.i - 1][emptySpace.j];
			this.setMove(temp+" "+"D");
			board[emptySpace.i - 1][emptySpace.j] = 0;
			board[emptySpace.i][emptySpace.j] = temp;
			this.emptySpace.i = this.emptySpace.i-1;
		} else if (s.equals("D")) {
			int temp = board[emptySpace.i + 1][emptySpace.j];
			this.setMove(temp+" "+"U");
			board[emptySpace.i + 1][emptySpace.j] = 0;
			board[emptySpace.i][emptySpace.j] = temp;
			this.emptySpace.i = this.emptySpace.i+1;
		}
	}
	
	public Pair findCoord(int tile) {
		int i = 0, j = 0;
		for (i = 0; i < SIZE; i++)
			for (j = 0; j < SIZE; j++)
				if (board[i][j] == tile) {
					Pair p = new Pair(i, j);
					return p;
				}
		return null;
	}

	public boolean isLegal(String s) { // if move isLegal and move != lastMove, then enter new board into priority
								// queue
		if (s.equals("L")) {
			if (emptySpace.j - 1 < 0)
				return false;
			return true;
		} else if (s.equals("R")) {
			if (emptySpace.j + 1 > SIZE - 1)
				return false;
			return true;
		} else if (s.equals("U")) {
			if (emptySpace.i - 1 < 0)
				return false;
			return true;
		} else if (s.equals("D")) {
			if (emptySpace.i + 1 > SIZE - 1)
				return false;
			return true;
		} else
			return false;
	}

	public int calculateMisplacedTiles() {
		int mTiles = 0;
		int value = 1;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] == 0)
					continue;
				if (board[i][j] != value)
					mTiles++;
				value++;
			}
		}
		return mTiles;
	}

	public boolean isSolved() {
//		if (row == -1) {
			int value = 1;
			for (int i = 0; i < SIZE; i++)
				for (int j = 0; j < SIZE; j++) {
					if (board[i][j] != value && board[i][j] != 0)
						return false;
					value++;
				}
			return true;
		}
//		else {
//			int value = 1 + row*SIZE;
//			for (int i = 0; i <= row; i++) {
//				for (int j = 0; j < SIZE; j++) {
//					if (board[i][j] != value)
//						return false;
//					value++;
//				}
//			}
//			return true;
//		}
//	}

	@Override
	public int compareTo(Node otherNode) {
		// TODO Auto-generated method stub
		if(this.SIZE<=6) {
			if (this.euclideanheuristic < otherNode.euclideanheuristic)
				return -1;
			else if (otherNode.euclideanheuristic < this.euclideanheuristic)
				return 1;
			return 0;
		}
		else {
			if (this.euclideanheuristic < otherNode.euclideanheuristic)
				return -1;
			else if (otherNode.euclideanheuristic < this.euclideanheuristic)
				return 1;
			else {
				if (this.closestheuristic < otherNode.closestheuristic)
					return -1;
				else if (otherNode.closestheuristic < this.closestheuristic)
					return 1;
				else {
					if (this.manhhattanheuristic < otherNode.manhhattanheuristic)
						return -1;
					else if (otherNode.manhhattanheuristic < this.manhhattanheuristic)
						return 1;
					else
						return 0;
				}
			}
		}
	}
	public boolean equalTo(Node n) {

		if (this.hashCode != n.hashCode)
			return false;

		if (this == n)
			return true;
		else {
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					if (board[i][j] != n.getBoard()[i][j])
						return false;
				}
			}
			return true;
		}
	}
	public void print() {
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				System.out.print(board[i][j]+" ");
			}
			System.out.println(" ");
		}
	}
}
