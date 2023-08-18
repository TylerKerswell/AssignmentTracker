# Aassignment tracker

## Function

For my personal project in **CPSC 210**, I will write a program to track due assignments for shcool assignments.
This program will allow the user to:
- keep track of school assignments
- check to see what assignment is due next
- keep track of how long each assignment should take to complete
- keep trach of which courses have which assignments due

this program will help users *stay on top of school* by showing them what they need to work on first,
and if theres any big assignments due soon.

## Reasoning

Because every class that I have gives multiple assignments each week, its hard to keep track of them all.
I think this program will allow me to stay on top of things better to school.



## User stories

- As a user, I want to be able to add an assignment to the list of due assignments
- As a user, I want to be able to know what assignment is due next
- As a user, I want to be able to know how long each assingnment is going to take to complete
- As a user, I want to be able to see which assignments I have due for a specific class
- As a user, I want to be able to save my assignments to a file
- As a user, I want to be able to load my assignments from a file

## Instructions for grader

- You can generate the first required event related to adding Xs to a Y by navigating to the "ADD" tab in the top right
and inputting the required information, then clicking the "SUBMIT" button.
- You can generate the second required event related to adding Xs to a Y by changing the information in the input fields
and then clicking the "SUBMIT" button again
- You can locate my visual component by clicking the button in the bottom right 
that says: "Click me for a map"
- You can save the state of my application by clicking on the "SAVE" tab in the top right and inputting the name of the file to save to,
then clicking the save button.
- You can reload the state of my application by clicking on the "LOAD" tab in the top right and inputting a file name to
load from, then clicking the load button.



## Phase 4: Task 2

Sample of events logged: \
Logs made: \
Assignment "WebWork" has been added. \
Assignment "Lab 7" has been added. \
Assignment "Homework 10" has been added. \
Assignment "Reading assignment 9" has been added.\
Assignment "Written assignment 2" has been added.\
Assignment "Tutorial 8" has been added.



## Phase 4: Task 3

Some changes I would make:\
If I had more time, I would probably change my program to have lower coupling and better cohesion.\
My gui, commands, and main classes are each responsible for multiple things
which reduces cohesion, I would rather seperate each general procedure in a single class and then make calls to that class
depending on what i wanted to do. (adding to a list, completing, loading, saving). Mostly because I implemented two different
ways of doing those things in my program. 