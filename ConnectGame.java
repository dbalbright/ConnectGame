
/**
 * Write a description of class ConnectGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ConnectGame
{

    final private char PLAYER1_CHAR = 'X';
    final private char PLAYER2_CHAR = 'O';
    final private char EMPTY_CHAR = '_';
    final private char BAR_HORIZ = '=';
    final private String BAR_VERT = "|";
    
    /*
     * We need to set an absolute minimum size of 3 because mathematically, if a game has 2 columns then player 1
     * wins by default on turn 2. And with 1 column, a tie is the only possible outcome!
     */
    final private int MIN_COL_SIZE = 3;

    // instance variables - replace the example below with your own
    private int[][] board;
    private int numToConnect;

    private final int ROWS;
    private final int COLS;

    // Player 2 always goes first.
    private int currentPlayer = 1;

    private int roundCounter = 0;

    /**
     * Constructor for objects of class ConnectGame
     */
    public ConnectGame( int rows, int cols, int numToConnect )
    {
        buildNumToConnect( numToConnect );
        buildBoard( rows, cols, numToConnect );

        this.ROWS = rows;
        this.COLS = cols;
    }

    /**
     * Method for toggling the current player's turn held by currentPlayer
     */
    public void togglePlayer()
    {
        if ( currentPlayer == 1 && roundCounter > 0 ) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
            roundCounter++;
        }
    }

    public int getCurrentPlayer()
    {
        return currentPlayer;
    }

    public int getRoundCount()
    {
        return roundCounter;
    }

    public boolean dropPiece( int col )
    {
        col--; // Shift numbers down by one because Java arrays start at 0 instead of 1.
        
        for ( int r = 0; r < board.length; r++ )
        {
            if ( board[r][col] != 0 ) {
                return false;
            } else if ( r == board.length - 1 || ( board[r][col] == 0 && board[r+1][col] != 0 ) ) {
                board[r][col] = currentPlayer;
                return true;
            }
        }

        return false;
    }

    public void drawBoard()
    {
        // Clears the screen
        System.out.print('\f');

        for ( int r = 0; r < board.length; r++ )
        {
            System.out.print(BAR_VERT);
            for ( int c = 0; c < board[r].length; c++ )
            {
                if ( board[r][c] == 1 ) {
                    System.out.print(PLAYER1_CHAR + BAR_VERT);
                } else if ( board[r][c] == 2 ) {
                    System.out.print(PLAYER2_CHAR + BAR_VERT);
                } else {
                    System.out.print(EMPTY_CHAR + BAR_VERT);
                }

                if ( c == board[r].length - 1 ) {
                    System.out.print("\n");
                }
            }
        }

        System.out.print("\n\n");
    }

    /**
     * Checks whether a win condition has been met.
     */
    public boolean checkWin()
    {
        if ( checkWinDownDiag() || checkWinUpDiag() || checkWinHoriz() || checkWinVert() ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkTie()
    {
        for ( int c = 0; c < board[0].length; c++ )
        {
            if ( board[0][c] == 0 ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Constructor method to set the consecutive number of cells that are occupied by the same player
     * in order to satisfy the win condition
     */
    private void buildNumToConnect( int numToConnect )
    {
        if ( numToConnect <= 1 ) {
            throw new IllegalArgumentException("Number to connect must be greater than 1. Given: " + numToConnect);
        }
        this.numToConnect = numToConnect;
    }

    /**
     * Constructor method for board size with exception check for minimum board size of both rows and columns
     */
    private void buildBoard( int rows, int columns, int numToConnect )
    {
        if ( ( rows < numToConnect && columns < numToConnect ) || columns < MIN_COL_SIZE ) {
            throw new IllegalArgumentException("Rows and columns must be at least " + numToConnect + " units. Columns must be at least " + MIN_COL_SIZE + ".");
        }
        board = new int[rows][columns];
    }

    private boolean checkWinDownDiag()
    {
        for ( int row = 0; row <= board.length - numToConnect; row++ )
        {
            for ( int col = 0; col <= board[row].length - numToConnect; col++ )
            {
                for ( int r = row, c = col; r < row + numToConnect && c < col + numToConnect; r++, c++ )
                {
                    if ( board[r][c] != currentPlayer ) {
                        break;
                    } else if ( c == col + numToConnect - 1 ) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkWinUpDiag()
    {
        for ( int row = numToConnect - 1; row < board.length; row++ )
        {
            for ( int col = 0; col <= board[row].length - numToConnect; col++ )
            {
                for ( int r = row, c = col; r >= row - numToConnect && c < col + numToConnect; r--, c++ )
                {
                    if ( board[r][c] != currentPlayer ) {
                        break;
                    } else if ( c == col + numToConnect - 1 ) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkWinHoriz()
    {
        int consecCount = 0;

        for ( int r = 0; r < board.length; r++ )
        {
            consecCount = 0;
            for ( int c = 0; c < board[r].length; c++ )
            {
                if ( board[r][c] == currentPlayer ) {
                    consecCount++;
                    if ( consecCount == numToConnect ) {
                        return true;
                    }
                } else {
                    consecCount = 0;
                }
            }
        }

        return false;
    }

    private boolean checkWinVert()
    {
        int consecCount = 0;

        for ( int c = 0; c < board[0].length; c++ )
        {
            consecCount = 0;
            for ( int r = 0; r < board.length; r++ )
            {
                if ( board[r][c] == currentPlayer ) {
                    consecCount++;
                    if ( consecCount == numToConnect ) {
                        return true;
                    }
                } else {
                    consecCount = 0;
                }
            }
        }

        return false;
    }
}
