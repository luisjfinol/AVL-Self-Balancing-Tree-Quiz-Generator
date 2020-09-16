README

Course: cs400
Semester: Spring 2019
Project name: A.V.L (Assisted Variable Learning) Quiz
Team Members:
1. Kevin O'Connor, Lec 002, kevinoco97@gmail.com
2. Alex Kisch, Lec 002, akisch@wisc.edu
3. Arshad Habib, Lec 002, ahabib3@wisc.edu
4. Luis Finol, Lec 002, lfinol@wisc.edu

Notes or comments to the grader:

- We followed our own design when making this project. We made an A.V.L structured quiz.

- When a question is answered incorrectly, its difficulty field is increased. This is done
in such a way that the tree (which represents the quiz) is rebalanced to have easier questions 
(with lower difficuties) on the left and harder questions (with higher difficulties) on the right.
Not every question will be able to be answered on a particular quiz attempt, only a specific 
path of questions will be answered depending on whether the user is answering the questions correctly or 
incorrectly (going to left branches with the easier questions if the user answers a previous question 
incorrectly, and going to right branches with harder questions if the user answers a previous question 
correctly). 

- Our scenes were very cluttered, so we decided not to allow the user to provide images when adding a question.

- When giving the user the option to save added questions to file, we are creating a new file with all the 
questions, rather than overwritting an old one.

- When saving questions to a new json file, when we open the file in our own text editors, the whole body of the file
appears in one line, however the file is STILL functional. It can be imported to the quiz generator. 
