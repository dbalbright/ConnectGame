
/**
 * Write a description of class ConnectGameClient here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Scanner;

public class ConnectGameClient
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        final int MIN_SIZE = 4;
        final int MAX_SIZE = 10;

        int again;

        int rows, cols, numToConnect;
        int dropCol;

        do
        {
            // Clears the screen
            System.out.print('\f');

            do
            {
                System.out.println("Enter number of game pieces that must be connected in order to win.");
                numToConnect = input.nextInt();

                if ( numToConnect <= 1 ) {
                    System.out.print("The number of pieces to connect must be greater than 1");
                }
            } while ( numToConnect <= 1 );

            do
            {
                System.out.println("Enter the number of rows and columns for the game separated by a space.");
                System.out.println("Rows and columns must both be between " + MIN_SIZE + " and " + MAX_SIZE + ".");
                rows = input.nextInt();
                cols = input.nextInt();
            }
            while ( rows < MIN_SIZE || rows > MAX_SIZE || cols < MIN_SIZE || cols > MAX_SIZE );

            ConnectGame connect4 = new ConnectGame( rows, cols, numToConnect );
            while ( !( connect4.checkWin() || connect4.checkTie() ) )
            {
                connect4.togglePlayer();

                connect4.drawBoard();

                System.out.println("It is round " + connect4.getRoundCount() + " and player " + connect4.getCurrentPlayer() + "'s turn.");

                do
                {
                    System.out.println("Player " + connect4.getCurrentPlayer() +
                        ", enter the column in which to drop your piece. ( 1 - " +  cols + " )");
                    dropCol = input.nextInt();
                }
                while ( ( dropCol < 1 || dropCol > cols ) || !connect4.dropPiece( dropCol ) );
            }

            connect4.drawBoard();

            if ( connect4.checkWin() ) {
                System.out.println("Player " + connect4.getCurrentPlayer() + " has won the game in " + connect4.getRoundCount() + " rounds!");
            } else if ( connect4.checkTie() ) {
                System.out.println("After a long grueling match of " + connect4.getRoundCount() + " rounds, both players begrudgingly accept that neither can win.");
                System.out.println("They both leave the battlefield bloodied and bruised. The only winner today was misery...");
            } else {
                System.out.println("Something must have gone wrong.");
            }

            System.out.print("\n");
            System.out.println("Would you like to play again? ( 1 = yes, any other number = no )");
            again = input.nextInt();
        }
        while ( again == 1 );
    }
}
