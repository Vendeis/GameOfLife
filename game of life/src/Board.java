import java.util.Random;

public class Board {
    private char[][] boardStart;
    private char[][] boardUpdate;
    private int size;
    private int seed;

    public Board(int size, int seed) {
        this.size = size;
        this.seed = seed;
        boardStart = new char[size][size];
        boardUpdate = new char[size][size];
        generate(boardStart);
    }

    private void generate(char[][] boardStart) {
        Random rd = new Random(seed);
        boolean test;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                test = rd.nextBoolean();
                if (test)
                    this.boardStart[i][j] = 'O';
                else
                    this.boardStart[i][j] = ' ';
            }
        }
    }

    public void playOnce(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                int spr = checkNeighbor(i,j);
                boardUpdate[i][j] = updateCell(spr,i,j);
            }
        }
        displayState(boardUpdate);
        updateStatus();


    }

    private void updateStatus() {
        for(int i=0; i<size; i++){
            for(int j=0; j<size;j++){
                boardStart[i][j] = boardUpdate[i][j];
            }
        }
    }

    public char updateCell(int spr,int i,int j) {
        if(spr < 2) {
            boardUpdate[i][j] = ' ';
            return ' ';
        }
        else if(spr == 2){
            if(boardStart[i][j] == 'O') {
                boardUpdate[i][j] = 'O';
                return 'O';
            }
            else if(boardStart[i][j] == ' ') {
                boardUpdate[i][j] = ' ';
                return ' ';
            }
        }
        else if(spr == 3){
            boardUpdate[i][j] = 'O';
            return 'O';
        }
        else if(spr > 3){
            boardUpdate[i][j] = ' ';
            return ' ';
        }
        return ' ';
    }

    public int checkNeighbor(int a, int b){
        int temp = 0;
        if(isCorner(a,b)){
            if(a == 0 && b == 0){
                for(int i=0; i<=1; i++){
                    for(int j=0; j<=1; j++){
                        if(isO(i,j))
                            temp++;
                    }
                }
            }
            else if (a == 0 && b == size-1){
                for(int i = 0; i<=1; i++){
                    for(int j = size-2; j <= size-1; j++){
                        if(isO(i,j))
                            temp++;
                    }
                }
            }
            else if(a == size-1 && b == 0){
                for(int i = size-2; i <= size-1; i++){
                    for(int j = 0; j<=1; j++){
                        if(isO(i,j))
                            temp++;
                    }
                }
            }
            else if(a == size-1 && b == size-1){
                for(int i = size-2; i <= size-1; i++){
                    for(int j = size-2; j <= size-1; j++){
                        if(isO(i,j))
                            temp++;
                    }
                }
            }
        }
        else if(a == 0 && b != 0){
            for(int i = 0; i<=1; i++){
                for(int j = b-1; j<=b+1; j++){
                    if(isO(i,j))
                        temp++;
                }
            }
        }
        else if(b == 0 && a != 0){
            for(int j = a-1; j <= a+1; j++){
                for(int i = 0; i <= 1; i++){
                    if(isO(j,i))
                        temp++;
                }
            }
        }
        else{
            for(int i = a-1; i <= a+1; i++){
                for(int j = b-1; j <= b+1; j++){
                    if(i == size || j == size){
                        continue;
                    }
                    if(isO(i,j)){
                        temp++;
                    }
                }
            }
        }
        if(isO(a, b)){
            temp--;
        }
        return temp;
    }

    private Boolean isO(int x, int y){
            return boardStart[x][y] == 'O';
    }
    private Boolean isCorner(int x, int y){

            return ((x == 0 && y == 0) || (x == 0 && y == size-1)
                    || (x == size-1 && y == 0) || (x == size-1 && y == size-1));


    }
    public void displayState(char[][] board){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public char[][] getBoardStart() {
        return boardStart;
    }

    public char[][] getBoardUpdate() {
        return boardUpdate;
    }
}
