import java.util.*;
import java.io.*;
import java.math.*;
import java.util.LinkedList;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {
    private final static String LOG = "Log > ";
    private final static String PLAYER_ONE = "1 ";
    private final static String PLAYER_TWO = "2 ";
    private static LinkedList<Integer> cardsOnePlayer = new LinkedList<>();
    private static LinkedList<Integer> cardsTwoPlayer = new LinkedList<>();

    public static void main(String args[]) {
        boolean isGameOver = false;
        int gameTurns = 0;
        String winner = "PAT";

        Scanner input = new Scanner(System.in);
        
        int numOneCards = input.nextInt(); // the number of cards for player 1
        
        for (int i = 0; i < numOneCards; i++) {
            cardsOnePlayer.add (keepOnlyNumber(input.next())); // the n cards of player 1
        }
        
        int numTwoCards = input.nextInt(); // the number of cards for player 2
        
        for (int i = 0; i < numTwoCards; i++) {
            cardsTwoPlayer.add (keepOnlyNumber(input.next())); // the m cards of player 2
        }
        
        System.err.println(LOG + "1st Player Cards: " + cardsOnePlayer);
        System.err.println(LOG + "2nd Player Cards: " + cardsTwoPlayer);

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        List<Integer> warListOne = new ArrayList<>();
        List<Integer> warListTwo = new ArrayList<>();
        
        while(!isGameOver){
            int oneCard = cardsOnePlayer.get(0);
            int twoCard = cardsTwoPlayer.get(0);
            
            cardsOnePlayer.remove(0);
            cardsTwoPlayer.remove(0);
                
            if (oneCard > twoCard){
                cardsOnePlayer.add(oneCard);
                cardsOnePlayer.add(twoCard);
            }
            else if (oneCard < twoCard){
                cardsTwoPlayer.add(twoCard);
                cardsTwoPlayer.add(oneCard);
            }
            else {
                System.err.println(LOG + "Going to WAR!");
                
                warListOne.add(oneCard);
                warListTwo.add(twoCard);
                isGameOver = goToWar(cardsOnePlayer, cardsTwoPlayer, warListOne, warListTwo);
            }
            gameTurns++;
            
            System.err.println(LOG + "1st Player Cards: " + cardsOnePlayer);
            System.err.println(LOG + "2nd Player Cards: " + cardsTwoPlayer);
            
            if (cardsOnePlayer.isEmpty()){
                winner = PLAYER_TWO;
                isGameOver = true;
            }
            else if (cardsTwoPlayer.isEmpty()){
                winner = PLAYER_ONE;
                isGameOver = true;
            }
        }
    
        System.out.println(winner + gameTurns);
    }
    
    private static boolean goToWar(LinkedList<Integer> cardsOnePlayer, LinkedList<Integer> cardsTwoPlayer, 
                                    List<Integer> warListOne, List<Integer> warListTwo){
        int scoreOne = 0;
        int scoreTwo = 0;
        int turn = 0;
        
        if (cardsOnePlayer.size() < 3 || cardsTwoPlayer.size() < 3){
            return true;
        }
        else{
            List<Integer> warOneCards = new ArrayList<>(cardsOnePlayer.subList(0,3));
            List<Integer> warTwoCards = new ArrayList<>(cardsTwoPlayer.subList(0,3));
            
            System.err.println(LOG + "1st WAR Cards: " + warOneCards);
            System.err.println(LOG + "2nd WAR Cards: " + warTwoCards);
            
            while (turn < 3){
                if (warOneCards.get(turn) > warTwoCards.get(turn)){
                    scoreOne++;
                }
                else if (warOneCards.get(turn) < warTwoCards.get(turn)){
                    scoreTwo++;
                }
                turn++;
            }
            cardsOnePlayer.subList(0,3).clear();
            cardsTwoPlayer.subList(0,3).clear();
            
            warListOne.addAll(warOneCards);
            warListTwo.addAll(warTwoCards);
            
            if (scoreOne > scoreTwo){
                cardsOnePlayer.addAll(warListOne);
                cardsOnePlayer.addAll(warListTwo);
                return false;
            }
            else if (scoreOne < scoreTwo){
                cardsTwoPlayer.addAll(warListTwo);
                cardsTwoPlayer.addAll(warListOne);
                return false;
            }
            else{
                return goToWar(cardsOnePlayer, cardsTwoPlayer, warListOne, warListTwo);
                }
        }
    }
    
    private static int keepOnlyNumber(String cardValue){
        // Discard the letter, since it's useless
        cardValue = cardValue.substring(0, cardValue.length() - 1);
        
        if (cardValue.matches("\\d+")){
            return Integer.parseInt(cardValue);
        }
        else{
            return convertAlphasToNums(cardValue.charAt(0));
        }
    }
    
    private static int convertAlphasToNums(char alphaValue){
        switch(alphaValue){
            case 'A':
                return 14;
            case 'K':
                return 13;
            case 'Q':
                return 12;
            case 'J':
                return 11;
            default:
                return 0;
        }
    }
    
}
