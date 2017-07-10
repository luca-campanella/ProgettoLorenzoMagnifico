This repository contains the code for the computer version of the board game "Lorenzo il Magnifico" by cranio creations.
It was created as a univesity project.
Authors:
Amadelli Federico: federico.amadelli@mail.polimi.it
Artoni Alessandro: alessandro1.artoni@mail.polimi.it
Campanella Luca: luca.campanella@mail.polimi.it

To build and run the repository please follow the following instructions, with maven installed:
- inside the main folder of the repository run 'mvn package' to create the executable jar
- run 'cd target' to place yourself in the target directory where you should now find the file: LM8.jar
- to run the server type: 'java -cp LM8.jar it.polimi.ingsw.server.ServerMain'
- to run the client (run multiple times) type: 'java -cp LM8.jar it.polimi.ingsw.client.ClientMainClass'
