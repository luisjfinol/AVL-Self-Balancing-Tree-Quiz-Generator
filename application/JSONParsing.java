//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: JSONParsing
// Files: JSONParsing.java Question.java
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

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
/*
 * A class for handling the parsing and writing of JSON files
 * Any time a question is added, either through a JSON file or user input, this class creates 
 * program usable Questions. This class also takes either singular questions made by users, or it 
 * takes the entire questionsBank and turns it into a JSON file that can be read by the program
 */
public class JSONParsing {
	private List<Question> questionBank; // holds all of the read in questions
	
	/**
	 * Public constructor
	 * Initialize question bank arrayList
	 */
	public JSONParsing() {
		this.questionBank = new ArrayList<Question>();
	}
	
	/**
	 * Parses the json file, gets the questions and inserts them into the
	 * questionBank arrayList
	 * @param filename
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public void parseFile(String filename) throws FileNotFoundException, IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader(filename));
    	JSONObject jo = (JSONObject) obj;//creates JSON object
    	JSONArray questions = (JSONArray) jo.get("questionArray"); //gets questionArray from JSON file
    	
    	
    	for (int i = 0; i < questions.size(); i++) { //iterates through question to get other Question components
    		JSONObject question = (JSONObject) questions.get(i);
    		// get question, topic and image file path
    		String questionText = (String) question.get("questionText"); 
    		String topic = (String) question.get("topic");
    		String imageFile = (String) question.get("image");
    		//
    		JSONArray answers = (JSONArray) question.get("choiceArray"); //iterates through answers array to get answers components
    		//Each answer array is (KV) pair of each 
    		
    		String[][] answerArray = new String[answers.size()][2]; // array for question class
    		for (int p = 0; p < answers.size(); p++) {
    			JSONObject specificAns = (JSONObject) answers.get(p);
    			answerArray[p][0] = (String) specificAns.get("isCorrect");
    			answerArray[p][1] = (String) specificAns.get("choice");

    		}
    		Question newQuestion = new Question(questionText, topic, imageFile, answerArray);
    		this.questionBank.add(newQuestion); //
    	}
	}
	/*
	 * Takes a parameters that describe a question, formats them correctly, and turns it into a
	 * JSONObject which gets written into a file
	 */
	@SuppressWarnings("unchecked")
	  public String makeSingleJSONQuestion(String userQuestion, String userTopic, String ans1, String ans2, 
		    String ans3, String ans4, String ans5, Integer correctNum) {
		  
		  JSONObject thatsRatchet = new JSONObject();
		  JSONArray questionBank = new JSONArray();
		  JSONObject questionNode = new JSONObject();
		  String fileName;
		  int quesLength = userQuestion.length();
		  
		  if(quesLength >= 10) {
		  	fileName = userQuestion.substring(0,10) + ".json";
			} else {
			fileName = userQuestion + ".json";
	}	  
		  questionNode.put("meta-data","unused");
		  
		  questionNode.put("questionText", userQuestion);
		  
		  questionNode.put("topic", userTopic);
		  
		  questionNode.put("image","none");
		  
		  JSONObject answer1 = new JSONObject();
		  if(correctNum == 1) {
		    answer1.put("isCorrect","T");
		  } else {
		    answer1.put("isCorrect","F");
		  }
		  answer1.put("choice",ans1);
		  
		  JSONObject answer2 = new JSONObject();
		  if(correctNum == 2) {
	        answer2.put("isCorrect","T");
	      } else {
	        answer2.put("isCorrect","F");
	      }
	      answer2.put("choice",ans2);
	      
	      JSONObject answer3 = new JSONObject();
	      if(correctNum == 3) {
	        answer3.put("isCorrect","T");
	      } else {
	        answer3.put("isCorrect","F");
	      }
	      answer3.put("choice",ans3);
	      
	      JSONObject answer4 = new JSONObject();
	      if(correctNum == 4) {
	        answer4.put("isCorrect","T");
	      } else {
	        answer4.put("isCorrect","F");
	      }
	      answer4.put("choice",ans4);
	      
	      JSONObject answer5 = new JSONObject();
	      if(correctNum == 5) {
	        answer5.put("isCorrect","T");
	      } else {
	        answer5.put("isCorrect","F");
	      }
	      answer5.put("choice",ans5);
	      
		  JSONArray choiceArray = new JSONArray();
		  choiceArray.add(answer1);
		  choiceArray.add(answer2);
		  choiceArray.add(answer3);
		  choiceArray.add(answer4);
		  choiceArray.add(answer5);
		  
		  questionNode.put("choiceArray",choiceArray);
		  questionBank.add(questionNode);
		  thatsRatchet.put("questionArray", questionBank);
		  
		  
		  try {
			  FileWriter file = new FileWriter(fileName);
			  file.write(thatsRatchet.toJSONString());
			  file.flush();
			  file.close();
		  } catch (IOException e) {
			  e.printStackTrace();
		}
		  
		  
		  
		  return fileName;
		}
	/*
     * Takes parameters that describe a question, formats them correctly, and turns it into a
     * JSONObject
     */
	@SuppressWarnings("unchecked")
	  public JSONObject makeJSONQuestion(String userQuestion, String userTopic, String ans1, String ans2, 
		    String ans3, String ans4, String ans5, Integer correctNum) {
		  	  
		  JSONObject questionNode = new JSONObject();
		  
		  questionNode.put("meta-data","unused");
		  
		  questionNode.put("questionText", userQuestion);
		  
		  questionNode.put("topic", userTopic);
		  
		  questionNode.put("image","none");
		  
		  
		  
		  JSONObject answer1 = new JSONObject();
		  if(correctNum == 1) {
		    answer1.put("isCorrect","T");
		  } else {
		    answer1.put("isCorrect","F");
		  }
		  answer1.put("choice",ans1);
		  
		  JSONObject answer2 = new JSONObject();
		  if(correctNum == 2) {
	        answer2.put("isCorrect","T");
	      } else {
	        answer2.put("isCorrect","F");
	      }
	      answer2.put("choice",ans2);
	      
	      JSONObject answer3 = new JSONObject();
	      if(correctNum == 3) {
	        answer3.put("isCorrect","T");
	      } else {
	        answer3.put("isCorrect","F");
	      }
	      answer3.put("choice",ans3);
	      
	      JSONObject answer4 = new JSONObject();
	      if(correctNum == 4) {
	        answer4.put("isCorrect","T");
	      } else {
	        answer4.put("isCorrect","F");
	      }
	      answer4.put("choice",ans4);
	      
	      JSONObject answer5 = new JSONObject();
	      if(correctNum == 5) {
	        answer5.put("isCorrect","T");
	      } else {
	        answer5.put("isCorrect","F");
	      }
	      answer5.put("choice",ans5);
	      
		  JSONArray choiceArray = new JSONArray();
		  choiceArray.add(answer1);
		  choiceArray.add(answer2);
		  choiceArray.add(answer3);
		  choiceArray.add(answer4);
		  choiceArray.add(answer5);
		  
		  questionNode.put("choiceArray",choiceArray);
		 
		  
		  return questionNode;
		}
	
	/*
	 * Takes the questionBank and creates A JSON file that contains all the questions currently in
	 * the quiz program
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void writeFile(List<List<Question>> questions, String fileName) {
		  
		  int index;
		  int dindex;
		  int trindex;
		  List<Question> tempList;
		  String[][] tempAnswersArray;
		  String tempAnswer1;
		  String tempAnswer2;
		  String tempAnswer3;
		  String tempAnswer4;
		  String tempAnswer5;
		  int CorrectNumber = -1;
		  JSONArray questionBank = new JSONArray();       //JSON placeholder
		  JSONObject stillKindaRatchet = new JSONObject();//JSON placeholder
		  
		  //double nested for loop needs to enumerate through outer and inner ArrayLists of the
		  //question bank as well as loop through the answer array of each question
		  
		  for (trindex = 0; trindex < questions.size(); ++trindex) {
		    tempList = questions.get(trindex);
	    	  for(index = 0; index < tempList.size(); ++index) {
	    	    tempAnswersArray = tempList.get(index).getAnswerArray();
	    	    tempAnswer1 = tempAnswersArray[0][1];
	    	    tempAnswer2 = tempAnswersArray[1][1];
	    	    tempAnswer3 = tempAnswersArray[2][1];
	    	    tempAnswer4 = tempAnswersArray[3][1];
	    	    tempAnswer5 = tempAnswersArray[4][1];
	    	    for (dindex = 0; dindex < 5; ++dindex) {
	    	      if(tempAnswersArray[dindex][0].equals("T")) {
	    	        CorrectNumber = dindex + 1;
	    	      }
	    	    }
	    	    questionBank.add(makeJSONQuestion(tempList.get(index).getQ(), 
	    	        tempList.get(index).getTopic(), tempAnswer1, tempAnswer2, tempAnswer3, tempAnswer4,
	    	            tempAnswer5, CorrectNumber));
	    	        
	    	  }
		  }  
		  
		  stillKindaRatchet.put("questionArray",questionBank);
		  try {
		    FileWriter file = new FileWriter("" + fileName + ".json");
		    file.write(stillKindaRatchet.toJSONString());
		    file.flush();
		    file.close();
		  } catch (IOException e) {
		    e.printStackTrace();
		  }
		}
	/*
	 * getter for questionBank field	
	 */
	public List<Question> getQuestions() {
		return this.questionBank;
	}
	
	
	

}
