//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: AVLtree
// Files: AVLtree.java Question.java
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

/** The class represents a AVL Tree with Question nodes. 
 * @author teamA6 CS400
 */
public class AVLtree  {

  private Question root;
  
  ArrayList<Question>[] questionArray;
  /**Insert a new question node to the AVL tree. 
   * @param newNode
   */
  public void insert(Question newNode) {
    Question needToSetRoot = null;
    
    if (root == null) {
      root = newNode;
      return;
    } 
    
    needToSetRoot = insertHelper(newNode, root);
    
    if(needToSetRoot != null) {
      root = needToSetRoot;
    }
  }
  /**Getter method to get the private field root of type Question. 
   * @return root of Question tree
   */
  public Question getRoot() {
    return root;
  }
  /**Helper method to assist with inserting new Question node to the tree.
   * @param newNode
   * @param currNode
   * @return Question to be inserted 
   */
  public Question insertHelper(Question newNode, Question currNode) {
    int imbalance;
    Question needToSetParentNode = null;
    
    if(newNode.difficulty > currNode.difficulty) { //compare difficult for insertion
    	//if greater on right, else on left 
      if(currNode.right == null) {
        currNode.right = newNode;
      } else {
        needToSetParentNode = insertHelper(newNode, currNode.right);
        if(needToSetParentNode != null) {
          currNode.right = needToSetParentNode;
        }
      }
    } else {
      if(currNode.left == null) {
        currNode.left = newNode;
      } else {
        needToSetParentNode = insertHelper(newNode, currNode.left);
        if(needToSetParentNode != null) {
          currNode.left = needToSetParentNode;
        }
      }
    }
    
    
      updateHeight(currNode);
   
    needToSetParentNode = null;
    
    imbalance = height(currNode.left) - height(currNode.right); //calculates balance factor and takes action if imbalance

    if(imbalance > 1) {
      if(height(currNode.left.right) > height(currNode.left.left)) { //left rotate
        currNode.left = leftRotate(currNode.left);
      }
      needToSetParentNode = rightRotate(currNode);
    }
    if(imbalance < -1) {
      if(height(currNode.right.right) < height(currNode.right.left)) { //right rotate
        currNode.right = rightRotate(currNode.right);
      }
      needToSetParentNode = leftRotate(currNode);
    }  
    
    return needToSetParentNode;
  }
  /**Performs an AVL left Rotation around currNode or current Question.
   * @param currNode or currentQuestion
   * @return node of type Question
   */
  public Question leftRotate(Question currNode) {
    Question childNode = currNode.right;
    Question tempNode = childNode.left;
    
    childNode.left = currNode;
    currNode.right = tempNode;
    
    updateHeight(currNode);
    updateHeight(childNode);
    
    return childNode;
  }
  /**Performs an AVL right Rotation around currNode or current Question.
   * @param currNode or currentQuestion
   * @return node of type Question
   */
  public Question rightRotate(Question currNode) {
    Question childNode = currNode.left;
    Question tempNode = childNode.right;
    
    childNode.right = currNode;
    currNode.left = tempNode;
    
    updateHeight(currNode);
    updateHeight(childNode);
    
    return childNode;
  }
  /**Method to calculate the height of the tree of currNode specified by finding larger of two trees. 
   * @param node
   */
  public void updateHeight(Question node) {
    if(height(node.left) > height(node.right)) {
      node.height = height(node.left) + 1;
    } else {
      node.height = height(node.right) + 1;
    }
  }
  /**Gets the height of tree specified by currNode.
   * @param node 
   */
  public int height(Question node) {
    if (node == null) {
      return 0;
    } else {
      return node.height;
    }
  }
  
}  