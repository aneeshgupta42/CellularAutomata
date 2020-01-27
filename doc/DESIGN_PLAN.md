# Simulation Design Plan
### Team Number: 3
### Names: Chris Warren, Shruthi Kumar, Aneesh Gupta

## Introduction
The aim of this group project is to write a program using OpenJFX that animates 2D Cellular Automata simulations (such as the four simulations described). In a CA, the cells in the simulation comprise of their state and rules for interacting with their neighbors. In the beginning, parameters like the number of rows and columns in the grid, and the rules that dictate how cells are modified, are set. The CA runs by modifiying the state of the cells based on their current state and the state of their neighbours.

The different simulations to be implemented are:  game of life; percolation; segregation; predator-prey; and fire.

## Overview
We plan on having our program read in an XML file that determines the starting configurartion and all the initial parameters needed for that specific simulation. The design implemenatation that we looked at was using a data structure (most likely going to be a 2D array) to run the simulation. The array would be populated based on the information in the XML file that is read.

Our program will then be split into 5 major classes: 

- Grid Class:
We will have a grid class that serves as a basic grid, that will be able to hold and alter the Cell objects within based on the simulation we are doing. This grid will hold a certain x rows and y columns with each individual cell. The grid class will be the class that runs the simulation by updating cells and allowing them to change state. The grid is set up by calling on the Configuration class that reads in the XML file. The Grid class would likely be an abtract class that holds the basic functions that each simulation will have. It would have step function that would call the update function for each cell as time passes. As a result, it would be dependent on the Cell class because it needs to call the Cell class update function. For each game implemented in the CA simulation, we'd create a separate subclass that implements this parent Grid class. This class would read the data from the XML file and then applies it to the cells, using the Cell class' setter methods.
Methods: `step()` , `populateGridCells()` , `getNeighbors()` , `updateGrid()`

- Configuration Class:
Our initial thought is that the Grid class is going to have the most functionality out of all the components. So in order to simplify the Grid class so that it only handles running and updating the simulation, we will also have a Configuration class that sets up the grid. This class will read from the XMLFile and set up how many rows/columns the grid needs to have. Thus it will also need to get the data from the XML file that the user will choose in the Display class. This class would also contain specification on how the data in the XML file would be read and stored.  
Methods: `readXMLFile()`

- Cell Class:
The first is the Cell class that will define the states that each Cell object in the grid will have. The states will be updated in an update method based on the rules that are specified in the XML file. This method will need to be able to access the data of the neighbording cells in the grid so that updates to the current state can be made based on these. Comprises of setter and getter methods that change the state, and other parameters. 
Methods: `getState()`, `setState()` , `updateCell()` , `applyRules()`  

- Display Class: 
Different GUI elements, such as displaying the grid, time, speed up, stop simulation. Also features to read in configuration files, change the simulation type. The text displayed here will also be read in through resource files. The text displayed in this class will be from resource files. This class would need information from both the Cell and the Grid class. The Cell class would hold information on the current state of the Cell and its corresponding display picture. The Display class will need this picture in order to show in the GUI which state the Cell is currently on. It will also depend on the Grid class because it needs access to how the Grid is set up (ie how many rows and columns) so that the user can track the simulation.
Methods: `displayGrid()` , `displayInfo()` , `startSimulation()` , `stopSimulation` , `changeSimulationSpeed`

- Main Class:
This class would start and run the program. It displays an intial `startScreen()` scene, which comprises of the starting splash screen, and choice of simulation. Going on, it would have dependencies on all the other classes. It would call on the Grid class to start and run through the simulation, and it would call on the display class to implement the user interface. Apart from that it wouldn't have any other major functionalities.  
Methods: `startScreen` , `start()`

## User Interface
The user will choose from a dropdown menu specifying what game simulationthey want to play. Once clicked, the display screen that displays the grid will show. The user then can click a play button which starts the simulation, and they can pause it any time by clicking a pause button. There will also be a reset button which allows the user to go back to time 0. Once the play button is clicked, a timer will be displayed which shows the user elapsed time. We also well have a fastforward and rewind button.


The grid will be displayed similarly to this: ![](https://i.imgur.com/uUwoojg.png)

The interactive buttons will be on either the top or bottom of the grid that the user can click in order to interact with the grid: ![](https://i.imgur.com/TEbgTG5.png)

The player can choose from a selection of simulations from a dropdown menu to the side of the interactive buttons like this: ![](https://i.imgur.com/wH8Jm7N.png)

Some of the possible errors that would popup is if the user tried to upload a wrongly formatted XML file which prompts the user to re-upload the file.




## Design Details

1. Main Class

   1. `startScreen()`: This method brings in the starting splash screen for the program, with the choice to select a starting simulation. It will call the `readXMLfile()` method from the Configuration class
   2. `start()`: This method then is called when a button is pressed in the start screen, and goes to the display screen with all the other methods (stop, start, new simulation, etc.)

2. Configuration Class:

    1. `readXMLfile (String simulation)`: This method reads in a specific XML file depending on the simulation option inputted, and sets the different instance variables (such as row, height) and other configuration parameters.

3. Grid Class:
    
    1. `populateGridCells(int rows, int columns)`: Responsible for populating the grid with Cell objects, configured in the way that is specified by the Configuration class.
    2. `step()`: This method is responsible for the time keeping, and updating like the display paramters. It calls `updateGrid()`, and this provides the mechanism for checking and updating the cells in the grid.
    4. `updateGrid(Grid object)`: This searches through the grid and implements the logic for changing the state of a cell based on current state and state of neighbours. It will have to go through the grid in two passes, one to get current states, and then to update the cells in the next pass.
    5. `getNeighbors(int rows, int columns, int x, int y)`: Used for getting the neighbours of a particular cell, specified by the coordinates passed in. These will differ for cells in the edges, and cells in the center.
    6. `getImageRoot()`: Using this, we can get the image root containing all the images of the cells, etc., and pass this on to the Display class.

4. Cell Class:
 
    1. `updateCell()`: transition between current state of cell and next state. We can have instance variables for current state, and next state.
    2. `getState()`: getter method for a cell object, that returns its currentState` 
    3. `setState()`: update the value of the nextState variable, also update the image
    5. `applyRules()`: temporary method, that we might use in case we want different simulations to be implemented through cells, and not through the grid.
    6. `getImage()`: get the image of the cell associated with the state, which can be added to the Grid root.

5. Display Class:
    
    1. `displayGrid()`: Displays the grid, after it gets the root from the Grid class
    2. `displayInfo()`: Displays secondary information such as seconds passes, which Simulation we're running, and so on. 
    3. `startSimulation()`: Start the simulation (now `step()` from Grid becomes active.)
    4. `stopSimulation()`: Pause the animation.
    5. `changeSimulationSpeed()`: Increase the rate of step, and checking and updating the cells.
    6. `changeSimulation()`: Upload a new XML file, and enact a new simulation, with different rules.


## Design Considerations
1. Abstract Cell class vs Abstract Grid class 
    Pros of abstract cell:
    * Having an abstract cell class would allow all the cell update methods to be in the Cell class. This way, the Grid doesn't manipulate the Cell objects--the Cell class would be the only class that's updating Cell objects.  
    * Can create different update methods for the cells based on rules
    
    Cons of abstract cell:
    * Getting and accessing the neighboring cells is much harder because we will have to pass them through as parameters to the update method. 
    
    Pros of abstract grid: 
    * Can access neighbors easily
    * Still allowing cells to handle changing their state -- this class will just set/apply the rules to the Cell update method

    Cons of abstract grid: 
    
    * Will have a greater dependency on the Cell class because it will be applying the rules to the cells 
    
    Final Decision: We ended up choosing to have an abstract grid class. This will make it easy to access the neighboring cells and their states so that we can apply the rules to the cells. We also figured that since the rules of each simulation are different, the update method will just apply these rules to the cell. So the Cell class would still be able to handle the cell updating, it would just get its rules from elsewhere. 

2. Abstract Grid class
    Pros:
    * More flexibility: each simulation will have its own rules so we can create multiple grids based on these rules.
    * Implements inheritance hierarchies so each subclass/simulation can have its own method implementation

    Cons:
    * If you want to create a new simulation, you have to add a subclas

3. Main Class and Display Class
    Pro:
    
    * Main class would create implementations of the grids. This way you can run multiple different simulations
    
    Cons:
     * We will have separate methods for starting the program and starting the simulation. This may be a little redundant because they could be combined into one. 

4. Configuration Class
    Pros: 
    * Separates the setting up of the grid and the changing of the cells in the grid
    * Helps with readability of code
    * Can have one implementation of setting up the grid for all simulations
    
    Cons: 
    * Adds another dependency to the Grid class

#### Components
The components we have are the Main class, Grid class (and its subclasses for each simulation), Configuration class, Cell class, and the Display class. 

#### Use Cases

1. Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)

    1. Grid class updateGrid(Grid object) to run through the grid and update the cells
    2. Grid class getNeigbors(int rows, int columns, int x, int y) and then check the state of each of those cells to see if they are dead or not and then call updatecell() to change the state of the cell
    3. Cell class updateCell() and apply the rules of the current simulation to the cell and change its state
    4. Cell class

2. Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing). 

    1. Grid class updateGrid(Grid object) to run through the grid and update the cells
    2. Grid class getNeighbors(...) and if there are no neighbors on a specific side of the cell, then

3. Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically

    1. updategrid() in the grid class which would be in the step method. This would 

4. Set a simulation parameter: set the value of a global configuration parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire

    1. 

5. Switch simulations: load a new simulation from an XML file, stopping the current running simulation, say Segregation, and starting the newly loaded simulation, say Wator

    1. changeSimulation(): Load a new XML file from 
    2. readXMLfile("Wator"): Since the Wator option was selected, the file corresponding to Wator will be read in.
    





## Team Responsibilities
#### High Level Plan

#### Individual Responsibilities

 * Team Member #1 - Chris Warren

 * Team Member #2 - Shruthi Kumar

 * Team Member #3 - Aneesh Gupta

