//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: A.V.L Quiz
// Files: QuizAttempt.java
// Course: COMP SCI 400, Spring 2019
//
// Author: Alex Kisch, Kevin O'Connor, Luis Finol, Arshad Habib
// Lecturer's Name: Debra Deppeler
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: NONE
// Partner Email: NONE
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

package application;

/**
 * The following class is designed to store information for a particular quiz attempt
 * 
 * @author ATeam6
 *
 */
public class QuizAttempt {

  private int attemptNumber; // specific attempt of a quiz taken

  private int numCorrect; // number of questions answered correctly in that attempt

  private int numQuestions; // number of questions answered on that attempt

  /**
   * Constructor assigning relevant values
   * 
   * @param attempt
   * @param questions
   * @param correct
   */
  public QuizAttempt(int attempt, int questions, int correct) {

    attemptNumber = attempt;

    numCorrect = correct;

    numQuestions = questions;
  }

  /**
   * Getter method for number of attempts
   * 
   * @return
   */
  public int getAttempt() {

    return attemptNumber;
  }

  /**
   * Getter method for number of correct
   * 
   * @return
   */
  public int getCorrect() {

    return numCorrect;
  }

  /**
   * Getter method for number of questions
   * 
   * @return
   */
  public int getNumberQuestions() {

    return numQuestions;
  }

  /**
   * Getter method for attempt number
   * 
   * @return
   */
  public int getAttemptNumber() {

    return attemptNumber;
  }
}
