# The_Coach_Timer





## Setup Instructions:
Taking into consideration that you are already familiar with Android Applications and Git.

### First way:

1) Download the Code from GitHub by pressing the (Code > download zip)
2) Extract the downloaded Project

### Second way:

1) Open git Bash in the file you want to have the project in
2) Write in the terminal:
 	git clone https://github.com/mouss39/TheCoachTimer.git

for better info check: https://docs.github.com/en/github/creating-cloning-and-archiving-repositories/cloning-a-repository


### THEN:

3) Open Android Studio or any other IDE
4) File > Open > choose the downloaded project
5) run the application an emulator or your own android phone.

If you are using your phone make sure that it is in developer mode.


## This Application consist of 5 Activities:
1)Main Activity: lists the list of players retrieved from the API using Retrofit and listed using a recycler View.
                  the coach can then select a player in order to move to the next activity.
                  
2)Input Activity: The Coach can then choose the distance of the training  session.


3)Session Activity: Contains the main work of the application, the stop watch which can start the training session, tag every lap ,and end the training.
                    After each lap:- Recycler View will be updated, a row is added to the database about the lap info and then showed on the screen,
                                   - Stats will be calculated and showed ( average speed, peak speed, average time per lap....
                                   
                   Room database was used in order to store data.
                   
                   
4)Leaderboard Activity: Contains all the sessions done, sorted based on 2 values (peak speed , number of laps). Also  2 additional buttons in order to export the results into a CSV file and another button that takes us to the CSVFileImports.

5) CSVFileImports Activity: Imports all the CSVFiles and show there contents.
                        
                 






