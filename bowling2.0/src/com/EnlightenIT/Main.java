package com.EnlightenIT;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class Main {

    //Constants
    private static final char MISS = '-';
    private static final char STRIKE = 'X';
    private static final char SPARE = '/';

    /*
    Class:  Frame
    Vars:   firstRoll: first roll of the frame
            secondRoll: second roll of the frame
            strike:     if the frame is a strike
            spare:      if the frame is a spare
     */
    public static class Frame{
        private int firstRoll;
        private int secondRoll;
        private boolean strike;
        private boolean spare;

        Frame(char roll_1, char roll_2){
            this.firstRoll = (roll_1 == STRIKE ? 10 :
                    (roll_1 == MISS ? 0 : Character.getNumericValue(roll_1) ) );
            this.secondRoll = (roll_2 == MISS ? 0 :
                    ( roll_2 == SPARE ? 10 -firstRoll : (roll_2 == STRIKE ? 10 : Character.getNumericValue(roll_2) ) ) );
            if(firstRoll + secondRoll == 10 && firstRoll != 10){
                spare = true;
            }
        }

        int getFirstRoll() {
            return firstRoll;
        }

        int getSecondRoll() {
            return secondRoll;
        }
        void isStrike(){
            strike = true;
        }

        boolean getStrike() {
            return strike;
        }
        boolean getSpare(){
            return spare;
        }
    }

    /*
    Method: formatData
    Args:   bowlingData: formated data
            data:        string from user input
    Return: returns an ArrayList of formated data, the frames of the game
    Description: Formats the string of user input to make things easier to read.
     */
    private static ArrayList<Frame> formatData(ArrayList<Frame> bowlingData, String data){
        bowlingData.clear();
        for(int i = 0; i < data.length(); i++){
            if(data.charAt(i) == STRIKE){
                Frame newObj = new Frame(data.charAt(i), MISS);
                newObj.isStrike();
                bowlingData.add(newObj);
                if(i +3 >= data.length()) {
                    bowlingData.add(new Frame(data.charAt(i +1), data.charAt(i +2)));
                    i = data.length();
                }
            }
            else{
                bowlingData.add(new Frame(data.charAt(i), data.charAt(i + 1)));

                if(i +3 >= data.length() && data.charAt(i + 1) == SPARE) {
                    bowlingData.add(new Frame(data.charAt(i +2), MISS));
                    i = data.length();
                }

                i++;
            }
        }
        return bowlingData;
    }

    /*
    Method: calcFinalScore
    Args:   bowlingFrames: contains the frames of the bowling game
    Description: Calculates the final score of the game and outputs it to the console.
     */

    private static void calcFinalScore(ArrayList<Frame> bowlingFrames){
        ListIterator<Frame> frames = bowlingFrames.listIterator();
        int answer = 0;
        for(int frameIndex = 0; frameIndex < 10; frameIndex++){
            Frame frame = frames.next();
            if(frame.getStrike()) {
                Frame nextFrame = frames.next();
                if(nextFrame.getSpare() || nextFrame.getStrike()) {
                    if (nextFrame.getStrike()) {
                        Frame lastFrame = frames.next();
                        answer += lastFrame.getFirstRoll() + nextFrame.getFirstRoll() + frame.getFirstRoll();//Change to the get methods
                        frames.previous();
                        frames.previous();
                    }
                    else{
                        answer += nextFrame.getFirstRoll() + nextFrame.getSecondRoll() + frame.getFirstRoll();
                        frames.previous();
                    }
                }
                else{
                    answer += nextFrame.getFirstRoll() + nextFrame.getSecondRoll() + frame.getFirstRoll();
                    frames.previous();
                }
            }
            else if(frame.getSpare()){
                Frame nextFrame = frames.next();
                answer += frame.getFirstRoll() + frame.getSecondRoll() + nextFrame.getFirstRoll();
                frames.previous();
            }
            else{
                answer += frame.getFirstRoll() + frame.getSecondRoll();
            }
        }
        System.out.println(answer);
    }

    public static void main(String[] args) {
        Scanner keyIn = new Scanner(System.in);
        String data;
        ArrayList<Frame> bowlingData = new ArrayList<Frame>();
        System.out.println("Input the rolls as a single string (ex. X-1-5621/XXX9-18-): 'q' to exit");

        while(true) {
            data = keyIn.nextLine();
            if (data.equals("q") || data.equals("Q")) {
                System.exit(0);
            }
            else{
                bowlingData = formatData(bowlingData, data);
                calcFinalScore(bowlingData);
            }
        }
    }
}
