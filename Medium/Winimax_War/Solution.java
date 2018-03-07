import java.util.*;
import java.io.*;

class Solution {
    private final static String LOG = "Log > ";
    private final static String PLAYER_ONE = "1 ";
    private final static String PLAYER_TWO = "2 ";
    private static ArrayList<Integer> cardsOnePlayer = new ArrayList<>();
    private static ArrayList<Integer> cardsTwoPlayer = new ArrayList<>();

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
        
        System.err.println(LOG + "1st Number: " + numOneCards);
        System.err.println(LOG + "2nd Number: " + numTwoCards);
        
        while(!isGameOver){
            List<Integer> warListOne = new ArrayList<>();
            List<Integer> warListTwo = new ArrayList<>();
            
            System.err.println(LOG + "1st Cards: " + cardsOnePlayer);
            System.err.println(LOG + "2nd Cards: " + cardsTwoPlayer);
            
            int oneCard = cardsOnePlayer.remove(0);
            int twoCard = cardsTwoPlayer.remove(0);
            
            if (oneCard > twoCard){
                cardsOnePlayer.add(oneCard);
                cardsOnePlayer.add(twoCard);
            }
            else if (oneCard < twoCard){
                cardsTwoPlayer.add(oneCard);
                cardsTwoPlayer.add(twoCard);
            }
            else {
                System.err.println(LOG + "Going to WAR!");
                warListOne.add(oneCard);
                warListTwo.add(twoCard);
                isGameOver = goToWar(cardsOnePlayer, cardsTwoPlayer, warListOne, warListTwo);
            }
            gameTurns++;
            
            if (cardsOnePlayer.isEmpty()){
                winner = PLAYER_TWO;
                isGameOver = true;
            }
            else if (cardsTwoPlayer.isEmpty()){
                winner = PLAYER_ONE;
                isGameOver = true;
            }
        }
    
    if (winner!="PAT")
        System.out.println(winner + gameTurns);
        else
        System.out.println(winner);
    }
    
    private static boolean goToWar(ArrayList<Integer> cardsOnePlayer, ArrayList<Integer> cardsTwoPlayer, 
                                    List<Integer> warListOne, List<Integer> warListTwo){
        
        if (cardsOnePlayer.size() < 4 || cardsTwoPlayer.size() < 4){
            return true;
        }
        else{
            List<Integer> warOneCards = new ArrayList<>(cardsOnePlayer.subList(0,4));
            List<Integer> warTwoCards = new ArrayList<>(cardsTwoPlayer.subList(0,4));
            
            System.err.println(LOG + "1st WAR Cards: " + warOneCards);
            System.err.println(LOG + "2nd WAR Cards: " + warTwoCards);
            
            int oneCard = cardsOnePlayer.get(3);
            int twoCard = cardsTwoPlayer.get(3);
            
            cardsOnePlayer.subList(0,4).clear();
            cardsTwoPlayer.subList(0,4).clear();
            
            warListOne.addAll(warOneCards);
            warListTwo.addAll(warTwoCards);
            
            if (oneCard > twoCard){
                cardsOnePlayer.addAll(warListOne);
                cardsOnePlayer.addAll(warListTwo);
                return false;
            }
            else if (oneCard < twoCard){
                cardsTwoPlayer.addAll(warListOne);
                cardsTwoPlayer.addAll(warListTwo);
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
