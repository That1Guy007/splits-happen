package com.EnlightenIT;

import java.util.Scanner;

/*
 * Author:      Carlos Hernandez
 * Date:        12/7/2018
 * Description: Bowling logic, accepts a string which represents the frames of a bowling game and outputs the total score.
 *
 */

public class Main {

    //Constants
    private static final char MISS = '-';
    private static final char STRIKE = 'X';
    private static final char SPARE = '/';

    /*
    Method: strike
    Args:   firstRoll: the next roll after the strike
            secondRoll: the second roll after the strike
    Return: The integer sum of the strike frame.
     */

    private static int strike(char firstRoll, char secondRoll){
        int sum = 10;
        if(firstRoll != MISS) {
            if (firstRoll == STRIKE) {
                sum += 10;
            } else {
                sum += Character.getNumericValue(firstRoll);
            }
        }
        if(secondRoll != MISS){
            if(secondRoll == STRIKE || secondRoll == SPARE ){
                if(secondRoll == STRIKE){
                    sum += 10;
                }
                else{
                    sum += (10 - Character.getNumericValue(firstRoll));
                }
            }
            else{
                sum += Character.getNumericValue(secondRoll);
            }
        }
        return sum;
    }

    /*
    Method: spare
    Args:   nextRoll: the next roll after the spare
    Return: The integer sum of the spare frame.
     */

    private static int spare(char nextRoll){
        int sum = 10;

        if(nextRoll == STRIKE){
            sum += 10;
        }
        else if(Character.isDigit(nextRoll)){
            sum += Character.getNumericValue(nextRoll);
        }
        return sum;
    }

    /*
    Method: missFrame
    Args:   nextRoll: the next roll after the miss roll
    Return: The integer sum of the miss frame.
     */

    private static int missFrame(char nextRoll){
        int frameSum = 0;
        if (Character.isDigit(nextRoll)){
            frameSum += Character.getNumericValue(nextRoll);
        }
        return frameSum;
    }

    /*
    Method: valueFrame
    Args:   currentRoll: the initial roll of the frame
            nextRoll: the second roll of the frame
    Return: The integer sum of the value frame.
     */

    private static int valueFrame(char currentRoll, char nextRoll){
        int frameSum = 0;
        if( Character.isDigit(nextRoll)){
            frameSum += Character.getNumericValue(currentRoll) + Character.getNumericValue(nextRoll) ;
        }
        else if( nextRoll == MISS){
            frameSum += Character.getNumericValue(currentRoll);
        }
        return frameSum;
    }

    /*
    Method: calcFinalScore
    Args:   frames: string of all of the frames, passed from user input.
    Return: void, outputs the final score to the screen.
     */

    private static void calcFinalScore(String frames){
        int answer = 0;
        
        for(int frameIndex = 0; frameIndex < frames.length(); frameIndex++){
            if(frames.charAt(frameIndex) == MISS || Character.isDigit(frames.charAt(frameIndex)) ){
                if(frames.charAt(frameIndex) == MISS){
                    if(frames.charAt(frameIndex + 1) == SPARE) {
                        answer += spare(frames.charAt(frameIndex + 2));
                    }
                    else{
                        answer += missFrame(frames.charAt(frameIndex +1));
                    }
                }
                else{
                    if( frames.charAt(frameIndex + 1) == SPARE) {
                        answer += spare( frames.charAt((frameIndex + 2)));
                    }
                    else{
                        answer += valueFrame(frames.charAt(frameIndex), frames.charAt((frameIndex + 1)));
                    }
                }
                if(frameIndex +3 >= frames.length()){
                    frameIndex = frames.length();
                }
                else
                    frameIndex++;
            }
            else{
                answer += strike(frames.charAt(frameIndex + 1), frames.charAt(frameIndex + 2));

                if(frameIndex +3 >= frames.length()){
                    frameIndex = frames.length();
                }
            }
        }
        System.out.println("Final Score: " + answer);
    }

    /*
    Method: main
    Args:   command line args
     */

    public static void main(String[] args) {
	    Scanner keyIn = new Scanner(System.in);
        String data;
	    System.out.println("Input the rolls as a single string (ex. X-1-5621/XXX9-18-): 'q' to exit");

	    while(true) {
            data = keyIn.nextLine();
            if (data.equals("q") || data.equals("Q")) {
                System.exit(0);
            }
            else{
                calcFinalScore(data);
            }
        }
    }
}
