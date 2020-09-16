//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Main
// Files: Main.java JSONParsing.java AVLtree.java QuizAttemp.java
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
// import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

/**
 * 
 * Main class
 *
 */
public class Main extends Application {

	  Scene menu; // The main menu scene
	  Scene makeAquiz; // The make a quiz scene
	  Scene makeQuizName; // Scene in which user decides a quiz's name
	  Scene takeAquiz; // Scene where user decides which quiz to take
	  Scene deleteQuiz; // Scene where user decides which quiz to delete
	  Scene quizHistory; // Scene where user decides which quiz's scoreboard to see

	  static List<String> quizNames = new ArrayList<String>(); // string names of each quiz
	  static List<String> quizTopics = new ArrayList<String>(); // topic names for each quiz
	  static List<Integer> quizScores = new ArrayList<Integer>(); // quiz scores for single attempt
	  static List<Integer> questionsAsked = new ArrayList<Integer>();
	  static List<Integer> quizAttempts = new ArrayList<Integer>(); // attempts for each quiz
	  // Array list of array lists. Each element is an arraylist of questions that have the same topic
	  static List<List<Question>> questionBank = new ArrayList<List<Question>>();
	  static List<String> topicList = new ArrayList<String>(); // String list of topics
	  static String currentQuiz; // stores what quiz is currently being taken

	  // List used to keep track of a quiz's attempts and scores for each attempt
	  static List<List<QuizAttempt>> quizStats = new ArrayList<List<QuizAttempt>>();
  /**
   * Sorts the question bank received from the json parser into the question bank lists of lists.
   * Each element represents a different topic and at that element is the list of questions with
   * said topic.
   * 
   * @param filename json file to be imported
   * @param primaryStage
   */
  private boolean sortQuestions(String filename, Stage primaryStage) {

    boolean importSuccess = true;
    JSONParsing parser = new JSONParsing();

    // if any exception is thrown while trying to parse a file, import is a failure
    try {

      parser.parseFile(filename);

    } catch (FileNotFoundException e) {

      System.out.println("FIlenotFound");
      importSuccess = false;
      primaryStage.setScene(createFail(primaryStage));

    } catch (IOException e) {

      System.out.println("IO Exception");
      importSuccess = false;
      primaryStage.setScene(createFail(primaryStage));

    } catch (ParseException e) {

      System.out.println("Parsing exception");
      importSuccess = false;
      primaryStage.setScene(createFail(primaryStage));
    }

    catch (Exception e) {

      System.out.println("EXCEPTION THROWN");
      System.out.println(e.getStackTrace());
      e.printStackTrace();
      importSuccess = false;
      primaryStage.setScene(createFail(primaryStage));

    }

    // if import was a success, questions with their respective data are obtained
    if (importSuccess) {

      List<Question> questionList = parser.getQuestions();

      for (int i = 0; i < questionList.size(); i++) {
        String topic = questionList.get(i).getTopic();

        if (!topicList.contains(topic)) {

          int topicIndex = topicList.size();
          topicList.add(topic);
          questionBank.add(new ArrayList<Question>());
          questionBank.get(topicIndex).add(questionList.get(i));

        } else {

          int topicIndex = topicList.indexOf(topic);
          questionBank.get(topicIndex).add(questionList.get(i));
        }
      }

      primaryStage.setScene(createSuccess(primaryStage));

      return true;

    } else {

      return false;
    }

  }

  /**
   * The following method creates a scene that indicates if user was able to succesfully import
   * questions from a file.
   * 
   * @param primaryStage The primary stage
   * @return The scene
   */
  private Scene createSuccess(Stage primaryStage) {

    Scene scene;
    Label success = new Label("Success!");
    Button menuButton = createButton("MENU");
    menuButton.setOnAction(e -> primaryStage.setScene(menu));
    BorderPane bordPane = new BorderPane();

    BorderPane.setAlignment(success, Pos.CENTER);
    BorderPane.setAlignment(menuButton, Pos.CENTER);
    bordPane.setCenter(success);
    bordPane.setBottom(menuButton);

    scene = new Scene(bordPane, 1000, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    return scene;
  }

  /**
   * The following method creates a scene that indicates if the user was not able to import
   * questions successfully.
   * 
   * @param primaryStage The primary stage
   * @return The scene
   */
  private Scene createFail(Stage primaryStage) {
    
    Scene scene;
    Label fail = new Label("Failed to import!");
    Button menuButton = createButton("MENU");
    menuButton.setOnAction(e -> primaryStage.setScene(menu));
    BorderPane bordPane = new BorderPane();
    
    BorderPane.setAlignment(fail, Pos.CENTER);
    BorderPane.setAlignment(menuButton, Pos.CENTER);
    bordPane.setCenter(fail);
    bordPane.setBottom(menuButton);

    scene = new Scene(bordPane, 1000, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    
    return scene;
  }

  /**
   * The following method creates the main menu scene, setting up its respective buttons
   * 
   */
  @Override
  public void start(Stage primaryStage) {
    
    try {
      
      // Buttons for the main menu     
      BorderPane root = new BorderPane();

      Button quizHist = createButton("Quiz History");

      Label menuTitle = new Label("A.V.L. Quiz");

      Button makeQuiz = createButton("Make New Quiz");

      Button takeQuiz = createButton("Take a Quiz");

      Button deleteQuiz = createButton("Delete Quiz");

      Button importQuestions = createButton("Import Questions");

      Button addQuestion = createButton("Add a Question");

      Button exit = createButton("Exit");

      Button stats = createButton("Stats");

      // button handling
      makeQuiz.setOnAction(e -> primaryStage.setScene(createQuizName(primaryStage)));
      takeQuiz.setOnAction(e -> primaryStage.setScene(createTakeQuizScene(primaryStage)));
      quizHist.setOnAction(e -> primaryStage.setScene(createQuizHistoryScene(primaryStage)));
      deleteQuiz.setOnAction(e -> primaryStage.setScene(createDeleteQuizScene(primaryStage)));
      importQuestions.setOnAction(e -> primaryStage.setScene(createMakeQuizScene(primaryStage)));
      addQuestion.setOnAction(e -> primaryStage.setScene(createAddQuestionScene(primaryStage)));
      exit.setOnAction(e -> primaryStage.setScene(exitProgram(primaryStage)));
      stats.setOnAction(e -> stats());

      VBox vbox = new VBox();
      vbox.getChildren().addAll(importQuestions, addQuestion, makeQuiz, takeQuiz, quizHist,
          deleteQuiz, exit);
      
      vbox.setSpacing(40); // button spacing
      vbox.setAlignment(Pos.CENTER);

      BorderPane.setAlignment(menuTitle, Pos.CENTER);
      BorderPane.setAlignment(stats, Pos.CENTER);

      root.setTop(menuTitle);
      root.setCenter(vbox);
      primaryStage.setTitle("Team A6 CS400 AVL Program");
 
      // image of main menu background
      String path =
          "https://www.globalconnections.org.uk/sites/newgc.localhost/files/styles/1160w/public"
              + "/images/general/test-yourself.png?itok=Baf5U8vR";
      
      Image image = new Image(path);
      ImageView newImage = new ImageView(image);
      newImage.setFitHeight(270);
      newImage.setFitWidth(320);
      
      BorderPane.setAlignment(menuTitle, Pos.TOP_CENTER);
      root.setLeft(newImage);
      BorderPane.setAlignment(newImage, Pos.CENTER_LEFT);

      menu = new Scene(root, 1000, 800);

      //CSS settings
      menu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      primaryStage.setScene(menu);
      primaryStage.show();
      
    } catch (Exception e) {
      
      e.printStackTrace();
    }
  }
  
  /**
   * Scene for adding a user question
   * @param primaryStage
   * @return
   */
  private Scene createAddQuestionScene(Stage primaryStage) {
	  Scene scene;
	  BorderPane brPane = new BorderPane();
	  Button menuButton = createButton("MENU");
	  menuButton.setOnAction(e -> primaryStage.setScene(menu));
	  Label questionLabel = new Label("Enter a question");
	  TextField questionField = new TextField();
	  Label topicLabel = new Label("Enter the topic");
	  TextField topicField = new TextField();
	  // 5 answers
	  Label answer1Label = new Label("Enter answer 1");
	  TextField ans1Field = new TextField();
	  Label answer2Label = new Label("Enter answer 2");
	  TextField ans2Field = new TextField();
	  Label answer3Label = new Label("Enter answer 3");
	  TextField ans3Field = new TextField();
	  Label answer4Label = new Label("Enter answer 4");
	  TextField ans4Field = new TextField();
	  Label answer5Label = new Label("Enter answer 5");
	  TextField ans5Field = new TextField();
	  // Combo box for correct answer
	  ComboBox<Integer> correctCombo = new ComboBox<Integer>();
	  correctCombo.getItems().addAll(1,2,3,4,5);
	  correctCombo.setMaxWidth(700);
	  correctCombo.setPromptText("Choose *CORRECT* answer");
	  // button for finishing
	  Button finish = createButton("Add Question");
	  // Layout
	  VBox qbox = new VBox();
	  qbox.getChildren().addAll(questionLabel, questionField);
	  VBox topbox = new VBox();
	  topbox.getChildren().addAll(topicLabel, topicField);
	  HBox hbox = new HBox();
	  hbox.getChildren().addAll(qbox, topbox);
	  hbox.setSpacing(20);
	  VBox ansVbox = new VBox();
	  ansVbox.getChildren().addAll(answer1Label, ans1Field, answer2Label, ans2Field, answer3Label, ans3Field, answer4Label,
			  ans4Field, answer5Label, ans5Field);
	  
	  VBox addMenu = new VBox();
	  addMenu.getChildren().addAll(menuButton, finish);
	  addMenu.setAlignment(Pos.CENTER);
	  addMenu.setSpacing(20);
	  
	  brPane.setTop(hbox);
	  brPane.setCenter(ansVbox);
	  brPane.setBottom(correctCombo);
	  brPane.setRight(addMenu);
	  
	  //IMPORTANT ACTION
	  finish.setOnAction(e -> {JSONParsing obj = new JSONParsing(); if 
	  			(sortQuestions(obj.makeSingleJSONQuestion(questionField.getText(),
			  topicField.getText(), ans1Field.getText(), ans2Field.getText(), ans3Field.getText(),
			  ans4Field.getText(), ans5Field.getText(), correctCombo.getValue()), primaryStage)) 
	  		{primaryStage.setScene(menu);}});
	  			
	  //
	  
	  BorderPane.setAlignment(correctCombo, Pos.CENTER);
	  hbox.setAlignment(Pos.CENTER);
	  ansVbox.setAlignment(Pos.CENTER);
	  scene = new Scene(brPane, 1000, 800);
	  scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	  return scene;
  }

  private void stats() {
    System.out.print("Quiz names: ");
    for (int i = 0; i < quizNames.size(); i++) {
      System.out.print(quizNames.get(i) + " ");
    }
    System.out.println("");
    System.out.print("Associated quiz topics: ");
    for (int i = 0; i < quizTopics.size(); i++) {
      System.out.print(quizTopics.get(i) + " ");
    }
    System.out.println("");
    System.out.print("Quiz attempts: ");
    for (int i = 0; i < quizAttempts.size(); i++) {
      System.out.print(quizAttempts.get(i) + " ");
    }
    System.out.println("");
  }
  
  /**
   * Create button method
   * @param name
   * @return
   */
  private Button createButton(String name) {
    Button button = new Button(name);
    return button;
  }

  /**
   * The following method creates and returns a scene displaying the different quizzes that were
   * created in order to view their respective scores
   * 
   * @param primaryStage The primary stage
   * @return The scene made
   */
  private Scene createQuizHistoryScene(Stage primaryStage) {

    VBox quizHistBox = new VBox();

    // for every quiz, an appropriate button leading to its scores is created
    for (int i = 0; i < Main.quizNames.size(); i++) {

      Button button = createButton(Main.quizNames.get(i));
      quizHistBox.getChildren().add(button);

      button.setOnAction(
          e -> primaryStage.setScene(createQuizHistoryChartScene(button.getText(), primaryStage)));
    }

    // if no quiz has been made, descriptive label is showcased
    if (Main.quizNames.isEmpty()) {
      
      quizHistBox.getChildren().add(new Label("No quiz made!"));
    }

    Scene scene; // The scene to be returned

    BorderPane quizHistoryPane = new BorderPane();
    Label quizHistoryLabel = new Label("Select Quiz History");

    Button menuFromQuizHist = createButton("go back to menu");

    menuFromQuizHist.setOnAction(e -> primaryStage.setScene(menu));

    quizHistoryPane.setTop(quizHistoryLabel);
    quizHistoryPane.setBottom(menuFromQuizHist);
    quizHistoryPane.setCenter(quizHistBox);

    quizHistBox.setAlignment(Pos.CENTER);

    quizHistoryPane.setAlignment(quizHistBox, Pos.CENTER);
    quizHistoryPane.setAlignment(menuFromQuizHist, Pos.CENTER);
    quizHistoryPane.setAlignment(quizHistoryLabel, Pos.CENTER);

    quizHistBox.setSpacing(40.0);

    scene = new Scene(quizHistoryPane, 1000, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    return scene;
  }

  /**
   * The following method creates and returns a scene that displays the scores for a specific quiz
   * depending on what attempt
   * 
   * @param quizName The specific quiz's name
   * @param primaryStage The primary stage
   * @return A scene depicting the quiz scores
   */
  private Scene createQuizHistoryChartScene(String quizName, Stage primaryStage) {

    Scene scene; // The scene to be returned

    BorderPane historyChartPane = new BorderPane();
    Label labelHistChart = new Label("Quiz Scoreboard " + quizName);

    Button menuFromQuizHistChart = createButton("go back to menu");
    menuFromQuizHistChart.setOnAction(e -> primaryStage.setScene(menu));

    VBox historyChart = new VBox(); // Box where labels indicating scores are put

    historyChartPane.setTop(labelHistChart);
    historyChartPane.setCenter(historyChart);
    historyChartPane.setBottom(menuFromQuizHistChart);

    historyChartPane.setAlignment(labelHistChart, Pos.CENTER);

    int index = quizNames.indexOf(quizName); // index of the location of a particular quiz on data
                                             // structures

    int numAttempts = quizStats.get(index).size(); // number of attempts done on a quiz

    // for all the attempts on this quiz, the appropriate information regarding scores are displayed
    for (int i = 0; i < numAttempts; ++i) {

      // number of questions on a specific quiz attempt
      int questionNumber = quizStats.get(index).get(i).getNumberQuestions();
      // number of questions answered correctly on a specific quiz attempt
      int correct = quizStats.get(index).get(i).getCorrect();

      historyChart.getChildren()
          .add(new Label("For attempt " + (i + 1) + ", # questions: " + questionNumber
              + ", # of correct: " + correct + ", # of incorrect: " + (questionNumber - correct)));
    }

    scene = new Scene(historyChartPane, 1000, 600);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }
  /**
   * Prompts the user to select a quiz to be deleted
   * 
   * @param primaryStage The primary Stage
   * @return current scene
   */
  private Scene createDeleteQuizScene(Stage primaryStage) {

    Scene scene;

    Button exit2 = createButton("MENU");
    BorderPane deleteMenu = new BorderPane();
    Label deleteTitle = new Label("Delete a Quiz");
    ComboBox<String> comboBox = new ComboBox<String>();
    // creates items in the combo box for active quizzes
    for (int i = 0; i < Main.quizNames.size(); i++) {
      comboBox.getItems().add(Main.quizNames.get(i));
    }

    if (Main.quizNames.isEmpty()) {
      comboBox.setPromptText("No quizzes to delete!");
    } else {
      comboBox.setPromptText("Quiz for deletion");
    }


    deleteMenu.setCenter(comboBox);
    deleteMenu.setTop(deleteTitle);
    deleteMenu.setAlignment(deleteTitle, Pos.TOP_CENTER);
    exit2.setOnAction(e -> primaryStage.setScene(menu));

    Button delete = createButton("Delete Quiz");
    delete.setOnAction(e -> {
      deleteQuiz(comboBox.getValue());
      primaryStage.setScene(menu);
    });
    HBox newBox = new HBox();

    newBox.setSpacing(390);
    newBox.setPadding(new Insets(15, 20, 20, 10));
    newBox.getChildren().addAll(exit2, delete);
    deleteMenu.setBottom(newBox);

    scene = new Scene(deleteMenu, 1000, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    return scene;
  }
  /**
   * An error scene that is used to tell the user that they cannot make a quiz without first importing or making questions
   * 
   * @param primaryStage The primary stage
   * @return current scene
   */
  private Scene noImportYet(Stage primaryStage) {
    Scene scene;
    BorderPane bordPane = new BorderPane();
    Label message = new Label("Cannot make quiz since no imported questions yet");
    Button menuButton = createButton("MENU");
    menuButton.setOnAction(e -> primaryStage.setScene(menu));
    Button importButton = createButton("Import Questions");
    importButton.setOnAction(e -> primaryStage.setScene(createMakeQuizScene(primaryStage)));
    HBox hbox = new HBox();
    hbox.getChildren().addAll(menuButton, importButton);
    BorderPane.setAlignment(message, Pos.CENTER);
    BorderPane.setAlignment(hbox, Pos.CENTER);
    hbox.setAlignment(Pos.CENTER);
    hbox.setSpacing(50);

    bordPane.setCenter(message);
    bordPane.setBottom(hbox);
    scene = new Scene(bordPane, 1000, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }
  /**
   * Creates scene that prompts the user for a json file to be imported
   * 
   * 
   * @param primaryStage The primary Stage
   * @return current scene
   */
  private Scene createMakeQuizScene(Stage primaryStage) {
    // KEVIN////
    Scene scene;
    Button importButton = createButton("Import");

    TextField jsonField = new TextField();
    jsonField.setMaxWidth(400);
    Label importLabel = new Label("Enter JSON filename");
    

    importButton.setOnAction(e -> sortQuestions(jsonField.getText(), primaryStage)); //sorts the questions parsing them 
    BorderPane importPane = new BorderPane();
    VBox vbox = new VBox();
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(30);
    vbox.getChildren().addAll(importLabel, jsonField, importButton);
    importPane.setCenter(vbox);

    scene = new Scene(importPane, 1000, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    return scene;
  }
  /**
   * 
   * @param primaryStage
   * @return
   */
  private Scene createQuizName(Stage primaryStage) {
    if (questionBank.size() == 0) {
      return noImportYet(primaryStage);
    }
    Scene scene;
    BorderPane borderpane = new BorderPane();
    Label enterName = new Label("Enter quiz name");
    //Label enterRemind = new Label("***Hit enter after inputing name***");
    Button makeQuiz = createButton("Make Quiz");
    VBox vbox = new VBox();
    TextField txtfield = new TextField();
    vbox.getChildren().addAll(txtfield);
    vbox.setAlignment(Pos.CENTER);
    txtfield.setMaxWidth(400);
    //txtfield.setOnAction(e -> addNameandScoreField(txtfield.getText()));
    makeQuiz.setOnAction(e -> {addNameandScoreField(txtfield.getText());
    primaryStage.setScene(createTopicChoiceScene(primaryStage));}); // CHANGE
    BorderPane.setAlignment(enterName, Pos.CENTER);
    BorderPane.setAlignment(txtfield, Pos.CENTER);
    BorderPane.setAlignment(makeQuiz, Pos.CENTER);
    borderpane.setCenter(vbox);
    borderpane.setTop(enterName);
    borderpane.setBottom(makeQuiz);

    scene = new Scene(borderpane, 1000, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }
  /**
   * Updates information of certain fields
   * 
   * @param quizName name of quiz
   */
  private void addNameandScoreField(String quizName) {
    quizNames.add(quizName);
    quizScores.add(new Integer(0));
    quizAttempts.add(new Integer(0));
    questionsAsked.add(new Integer(0));
    quizStats.add(new ArrayList<QuizAttempt>());
  }
  /**
   * Creates a selection scene with buttons for the user to select the topic of the quiz they just created
   * 
   * @param primaryStage The primary Stage
   * @return current scene
   */
  private Scene createTopicChoiceScene(Stage primaryStage) {
    BorderPane borderpane = new BorderPane();
    Scene scene;
    //Button menuButton = createButton("MENU");
    Label chooseTopic = new Label("Please choose a topic for the quiz");
    //menuButton.setOnAction(e -> primaryStage.setScene(menu));
    VBox vbox = new VBox();
    
    //Creates a button for each topic, these buttons return the user to the main menu once pressed
    for (int i = 0; i < Main.topicList.size(); i++) {
      Button button = createButton(Main.topicList.get(i));
      button.setOnAction(e -> {
        Main.quizTopics.add(button.getText());
        primaryStage.setScene(menu);
      });
      vbox.getChildren().add(button);
    }
    
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(20);
    //BorderPane.setAlignment(menuButton, Pos.CENTER);
    BorderPane.setAlignment(chooseTopic, Pos.CENTER);
    borderpane.setCenter(vbox);
    //borderpane.setBottom(menuButton);
    borderpane.setTop(chooseTopic);

    scene = new Scene(borderpane, 1000, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  /**
   * Creates the AVL tree with question nodes that represents the quiz
   * 
   * @param quizName name of quiz being taken
   * @return root of question AVL Tree
   */
  private Question createAVLTree(String quizName) {
	  currentQuiz = quizName; // sets current quiz
	  int topicIndex = Main.quizNames.indexOf(quizName); //keeps track of selected topic
	  String topic = Main.quizTopics.get(topicIndex);
	  quizAttempts.set(topicIndex, quizAttempts.get(topicIndex) + 1); // increase quiz attempts
	  List<Question> tempQuestionList = new ArrayList<Question>();
	  AVLtree quiz = new AVLtree();
	  int index;
	  
	  //populates tempQuesitonList with questions of specified topic
	  for(index = 0; index < questionBank.size(); index++) {
	    if(questionBank.get(index).get(0).getTopic().equals(topic)) {
	      tempQuestionList = questionBank.get(index);
	    }
	  }
	  
	  //sets question fields to null in case they were previously set, then inserts a question node into the tree
	  for(index = 0; index < tempQuestionList.size(); ++index) {
	    tempQuestionList.get(index).height = 1;
	    if(tempQuestionList.get(index).right != null) {
	      tempQuestionList.get(index).right = null;
	    }
	    if(tempQuestionList.get(index).left != null) {
	      tempQuestionList.get(index).left = null;
	    }
	    quiz.insert(tempQuestionList.get(index));
	  }
	  
	  return quiz.getRoot();
	  
	  
	}
  /*
   * Creates the Take A Quiz scene that prompts the user as to which quiz they would like to take
   * 
   * @param primaryStage The primary stage
   */
  private Scene createTakeQuizScene(Stage primaryStage) {

    Scene scene; // scene to be returned
    Button beginQuiz = createButton("Begin Quiz");
    BorderPane startAquiz = new BorderPane();
    ComboBox<String> myComboBox = new ComboBox<String>();
    Button menuButton = createButton("MENU");
    menuButton.setOnAction(e -> primaryStage.setScene(menu)); //creates a button to return to the main menu

    //fills ComboBox with quiz names
    for (int i = 0; i < Main.quizNames.size(); i++) { 
      myComboBox.getItems().add(Main.quizNames.get(i));
    }

    //sets correct prompt
    if (Main.quizNames.isEmpty()) {
      myComboBox.setPromptText("No quizzes to take!");
    } else {
      myComboBox.setPromptText("Select Quiz to take");
    }

    //Makes an AVL tree and then passes that tree to a method which will create the first question scene and set that to the stage
    beginQuiz.setOnAction(e -> primaryStage
        .setScene(questionScene(createAVLTree(myComboBox.getValue()), primaryStage)));


    //formatting
    Label selectionLabel = new Label("Quiz Selection");
    startAquiz.setTop(selectionLabel);
    startAquiz.setCenter(myComboBox);
    startAquiz.setRight(beginQuiz);
    BorderPane.setAlignment(beginQuiz, Pos.CENTER);

    startAquiz.setBottom(menuButton);
    startAquiz.setAlignment(selectionLabel, Pos.TOP_CENTER);
    // startAquiz.setAlignment(startQuiz, Pos.BOTTOM_CENTER);



    scene = new Scene(startAquiz, 1000, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }


  /**
   * The following method creates and returns a scene that displays a question and provides its
   * possible answer choices
   * 
   * @param ques A specific question to be answered
   * @param primaryStage The primary stage
   * @return The scene displaying the question with possible choices
   */
  private Scene questionScene(Question ques, Stage primaryStage) {

    int indexQuiz = quizNames.indexOf(currentQuiz);
    questionsAsked.set(indexQuiz, questionsAsked.get(indexQuiz) + 1); // saves the data of the
                                                                      // current question asked

    Scene scene; // The scene to be returned

    Button goMenu = createButton("menu");
    goMenu.setOnAction(e -> primaryStage.setScene(menu));

    Label label = new Label("Answer Questions");
    Label question = new Label(ques.getQ());

    BorderPane pane = new BorderPane();
    pane.setTop(label);
    label.setAlignment(Pos.CENTER);

    VBox box = new VBox();

    box.getChildren().addAll(question);

    String[][] answers = ques.getAnswerArray(); // array containing information indicating which
                                                // choices are correct or incorrect

    List<Button> list = new ArrayList<Button>();

    // a button is created for every possible choice presented by the question
    for (int i = 0; i < answers.length; ++i) {

      Button but = createButton(answers[i][1]);

      box.getChildren().addAll(but);
      list.add(but);
    }

    // for every choice presented by the question, if it is correct, when clicking on it the user is
    // redirected to a scene indicating the correct choice was made, else to a scene indicating
    // incorrectness
    for (int j = 0; j < answers.length; ++j) {

      Button click = list.get(j);

      if (answers[j][0].equals("T")) {

        click.setOnAction(e -> primaryStage.setScene(Correct(ques, primaryStage)));

      } else {

        click.setOnAction(e -> primaryStage.setScene(Incorrect(ques, primaryStage)));
      }
    }

    pane.setBottom(box);
    pane.setLeft(goMenu);
    box.setAlignment(Pos.CENTER);

    try {

      // image will be added to the center of pane, if no image is contained by the question, it
      // isn't

      Image image = new Image(ques.getImageFile());
      ImageView imageView = new ImageView(image);

      imageView.setFitHeight(450);
      imageView.setFitWidth(450);

      imageView.setPreserveRatio(true);

      pane.setCenter(imageView);

      scene = new Scene(pane, 1000, 800);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      return scene;

    } catch (Exception e) {

      scene = new Scene(pane, 1000, 800);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      return scene;
    }
  }

  /**
   * The following method returns a scene that indicates the user's choice was a correct answer
   * 
   * @param ques The specific question
   * @param primaryStage The primary stage
   * @return The scene depicting an answer's correctness
   */
  private Scene Correct(Question ques, Stage primaryStage) {

    int indexQuiz = quizNames.indexOf(currentQuiz);
    quizScores.set(indexQuiz, quizScores.get(indexQuiz) + 1); // saves data for correct answer

    Scene scene; // The scene to be returned

    BorderPane pane = new BorderPane();
    Label label = new Label("CORRECT!");
    Button nextQuestion = createButton("next question");

    pane.setCenter(label);
    pane.setBottom(nextQuestion);

    scene = new Scene(pane, 1000, 800);

    Question next = ques.right; // next question would be one of more difficulty, so to the right in
                                // AVL tree

    // if there are no more questions of greater difficulty on the tree path, quiz ends
    if (next == null) {

      nextQuestion.setOnAction(e -> primaryStage.setScene(finalQuestion(primaryStage)));

    } else {

      nextQuestion.setOnAction(e -> primaryStage.setScene(questionScene(next, primaryStage)));
    }

    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  /**
   * The following method returns a scene that indicates the user's choice was an incorrect answer
   * 
   * @param ques The specific question
   * @param primaryStage The primary stage
   * @return The scene depicting an answer's incorrectness
   */
  private Scene Incorrect(Question ques, Stage primaryStage) {
	ques.difficulty++;
    Scene scene; // The returned scene

    BorderPane pane = new BorderPane();
    Label label = new Label("INCORRECT!");
    Button nextQuestion = createButton("next question");

    pane.setCenter(label);
    pane.setBottom(nextQuestion);

    scene = new Scene(pane, 1000, 800);

    Question next = ques.left; // next question would be one of less difficulty, so to the left in
                               // AVL tree

    // if there are no more questions of lesser difficulty on tree path, quiz ends
    if (next == null) {

      nextQuestion.setOnAction(e -> primaryStage.setScene(finalQuestion(primaryStage)));

    } else {

      nextQuestion.setOnAction(e -> primaryStage.setScene(questionScene(next, primaryStage)));
    }

    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }
  
  /**
   * The following method creates and returns a scene that conveys that the quiz is over, and
   * informs the user of their performance
   * 
   * @param primaryStage The primary stage
   * @return The scene depicting quiz results
   */
  private Scene finalQuestion(Stage primaryStage) {

    int index = quizNames.indexOf(currentQuiz);

    Scene scene; // The scene to be returned
    BorderPane pane = new BorderPane();
    Label label = new Label("Quiz Finished!");

    pane.setTop(label);
    pane.setAlignment(label, Pos.CENTER);

    int indexQuiz = quizNames.indexOf(currentQuiz);
    int intScore = quizScores.get(indexQuiz);
    String strScore = String.valueOf(intScore); // Number of correct answers

    Label score = new Label("Your score was: " + strScore + " / " + questionsAsked.get(indexQuiz));

    QuizAttempt attempt =
        new QuizAttempt(quizAttempts.get(indexQuiz), questionsAsked.get(indexQuiz), intScore);

    quizStats.get(indexQuiz).add(attempt);

    quizScores.set(indexQuiz, 0); // reset score for next attempt of quiz
    questionsAsked.set(indexQuiz, 0); // reset questions asked
    Button goBack = createButton("Go Back To Menu");

    VBox box = new VBox();
    box.getChildren().addAll(score, goBack);

    pane.setCenter(box);
    box.setAlignment(Pos.CENTER);

    goBack.setOnAction(e -> primaryStage.setScene(menu)); //sends user back to the menu

    scene = new Scene(pane, 1000, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    return scene;
  }
  
  /**
   * The following method creates and returns a scene that prompts the user to exit the program,
   * giving them the choice to save their changes
   * 
   * @param primaryStage The primary stage
   * @return The scene
   */
  private Scene exitProgram(Stage primaryStage) {

    Scene scene; // The scene to be returned

    Label label = new Label("Exit Program");

    BorderPane pane = new BorderPane();
    BorderPane.setAlignment(label, Pos.CENTER);

    Button save = createButton("save and exit"); // Button that saves the user's changes and exits
    Button noSave = createButton("exit without saving"); // Button that just exits programs

    VBox box = new VBox();

    box.getChildren().addAll(save, noSave);
    box.setAlignment(Pos.CENTER);
    box.setSpacing(30);

    pane.setCenter(box);
    pane.setTop(label);
    label.setAlignment(Pos.CENTER);

    noSave.setOnAction(e -> goodbye(primaryStage)); // the stage only closes, no changes are saved
    save.setOnAction(e -> primaryStage.setScene(exitSaving(primaryStage))); // goes to exit and save scene

    scene = new Scene(pane, 1000,800);
    
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    return scene;
  }

  /**
   * The following method saves the altered question bank into a file created by the user, and then
   * the program proceeds to close
   * 
   * @param primaryStage The primary stage
   * @return The according scene
   */
  private Scene exitSaving(Stage primaryStage) {

    Scene scene; // The scene to be returned 

    JSONParsing parser = new JSONParsing(); 

    BorderPane pane = new BorderPane();

    Label label =
        new Label("Enter name of file you wish to create (excluding .json from the name): ");

    TextField text = new TextField();

    Button exitSave = createButton("Save");

    pane.setTop(label);
    pane.setCenter(text);
    pane.setBottom(exitSave);

    exitSave.setAlignment(Pos.CENTER);

  

    // when user presses button, question bank is saved onto created file, then programs exits
    exitSave.setOnAction(e -> {
      String fileName = text.getText();
      parser.writeFile(questionBank, fileName);
      goodbye(primaryStage);
    });

    scene = new Scene(pane, 1000, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    return scene;
  }
  
  /**
   * The following method presents a good bye message to user for 2 seconds before closing program
   * 
   * @param primaryStage The primary Stage
   * @return
   */
  private void goodbye(Stage primaryStage) {
    
    long time = System.currentTimeMillis(); // current time in milliseconds
    long end = time + 2000; // time two seconds later
    
    BorderPane pane = new BorderPane();
    Label label = new Label("GoodBye!!!");
    
    pane.setCenter(label);
    
    Scene scene = new Scene(pane,800,600);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    
    primaryStage.setScene(scene);
    
    while (time < end) {
      
      time = System.currentTimeMillis();
    }
      
    primaryStage.close(); // after 2 seconds stage closes
  }

  /**
   * The following method deletes a specific quiz's data from relevant data structures
   * 
   * @param quizName The name of a specific quiz
   */
  private void deleteQuiz(String quizName) {
    
    if (quizNames.contains(quizName)) {
      
      int index = quizNames.indexOf(quizName);
      
      quizNames.remove(index); // remove name
      quizTopics.remove(index); // remove topic linked to that quiz. From the array list
      quizScores.remove(index); // remove score
      quizAttempts.remove(index); // remove attempt #
      questionsAsked.remove(index); // remove questions asked person
    }

  }

  public static void main(String[] args) {
    launch(args);
  }

}
