//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Question
// Files: Question.java
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

import java.util.ArrayList;
import java.util.Random;

/**
 * Class question holds the data related to a single question
 * @author kevin
 *
 */
public class Question {
	private String question;
	private String topic;
	private String imageFile;
	private String[][] answerArray;
	private ArrayList<Question> questionArray;
	public Question left;
	public Question right;
	public int height;
	public double difficulty;
	
	/**
	 * Public constructor
	 * @param question
	 * @param topic
	 * @param imageFile
	 * @param answerArray
	 */
	public Question(String question, String topic, String imageFile, String[][] answerArray) {
		this.question = question;
		this.topic = topic;
		this.imageFile = imageFile;
		this.answerArray = answerArray;
		//Random rnd = new Random();
		this.difficulty = Math.random();
	}
	
	/**
	 * Quesiton getter
	 * @return
	 */
	public String getQ() {
		return this.question;
	}
	
	/**
	 * topic getter
	 * @return
	 */
	public String getTopic() {
		return this.topic;
	}
	
	/**
	 * Image file path getter
	 * @return
	 */
	public String getImageFile() {
		return this.imageFile;
	}
	
	/**
	 * Answer array getter
	 * @return
	 */
	public String[][] getAnswerArray() {
		return this.answerArray;
	}
	
	/**
	 * Set topic
	 * @param topic
	 */

	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * Get Successors of current question node.
	 */
 
  public ArrayList<Question> getSuccessors(Question node) {
	  this.questionArray.add(node.left);
	  this.questionArray.add(node.right);
		return questionArray;
	}
}