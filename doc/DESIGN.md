# Simulation Design Final
### Aneesh Gupta, Chris Warren, Shruthi Kumar
####ag468, ccw43, sk593 

## Team Roles and Responsibilities

 * Team Member #1: Aneesh Gupta
    - Configuration and parsing:
        - XMLReader, XMLWriter, GridCreator, Game class, XML Exception classes
        - Helped with Grid class two-pass update logic, constructing the Grid.
        - UI elements such as Reset, Upload and Download simulations functionality through front end.
 * Team Member #2: Shruthi Kumar
    - Backend Side:
        - Cell classes and subclasses (Cell, GameCell, PercolationCell, RPSCell, SegregationCell, PredatorPreyCell, FireCell, SugarScapeCell)
        - Grid class
        - Neighbor shape class and subclasses (Neighbor, SquareNeighbor, HexagonNeighbor, TriangleNeighbor)
    - Front end  
        - Neighborhood UI display to display different shapes (MainView)

 * Team Member #3: Christopher Warren
    * Frontend/UI Side
    * Config Panel Class
        * Making the graph and parameter inputs
    * Mainview Class
        * Displaying the graph and having it interact depending on the actions happening in the toolbar.
    * Toolbar class
        * Functions of all the Buttons
    * Helped with th logic of displaying different shapes of simulations



## Design goals

#### What Features are Easy to Add
* Displaying other simulations are easy to add.
    * To do this, the developer would have to create a new cell subclass and implement the rules for it. Then this option would need to have a corresponding XML file, and it would need to be added as an option to the Grid and Toolbar classes. 
* Adding neighborhood shapes 
    * You would have to add another neighbor subclassfor the shape you want to implement. Then you would have to set the row and column deltas based on how many neighbors a cell of that shape would have. Then the new shape would need to be added as an option in the XML files and the cell classes.   
* Changing the parameters of the simulations to new configurations is simple by the design of our code.
    * Either upload your own file in the correct format or change one of the existing files 

* Adding in new parameters to read in from XML source files is easy to add.
    * In XML reader, add code that reads in the input from the new desired attributes and tags, and stores it. Then in Game, add space for this in terms of new instance variables.

* Adding new buttons that have specific functionality in the toolbar and configuration panel
    * In the Toolbar or ConfigPanel Class, you can add a button that will be horizonatally or vertically aligned and then create a method that specifically has the function of that button and use it to update the simulation through the private instance variables that were set.
    
* Adding neighborhood arrangements
    * The neighbor classes would have to be updated so the row/column deltas reflect the correct placement of neighbors rather than the standard ones that would check the top, bottom, left, right, etc.

## High-level Design

#### Core Classes
- The core classes are XMLReader, Game, Grid, Cell, Neighbor, Display, MainView, Toolbar. 
    - *XMLReader*: This can take in an XML file, read, and store its components. It also provides error and exception handling, for garbage input cases such as missing/incorrect data, out of bounds data, incorrect file type, and so on.
    - *Game*: This acts like an intermediary datastructure, between the XML file contents and the Grid object. It holds and constructs an object, holding in all the different type of paramteters for different simulations.
    - *XMLWriter*: This writes out XML files taking in the current simulation grid as an input. It creates a string-based layout of the current states of all the cells in the grid, the paramteres of the grid, and functionality to write these down to XML files.
    - *Grid*: Create Grid object from this data and updates the grid cell by cell. This class hides the structure of the grid we used. It allows other classes to access the cells in the grid while still hiding the implementation of the grid. 
    - *Cell*(s): Abstract cell class that defines methods to be overridden (updateCell, setCellColor) or implements methods that all child Cells use (getNeighbor, checkState, etc). The subclasses all override the updateCell method based on the rules of the corresponding simulation. The subclasses also contain private helper methods that are used to implement rules. 
    - *Neighbor*: This implements methods for the different neighborhood shapes (square, triangle, hexagon). The superclass has methods that checks the neighbors and updates counts or boolean values based on simulation needs. The subclasses each set the row and column deltas on the neighborhood so that neighbors can be correctly checked based on the shape (ie square has 8 neighbors, triangle has 12, etc)
    - *Display*: Creates the scene where everything is dislayed, includes the MainView Class and opens up a window to upload a simulation.
    - *MainView*: BorderPane that holds the Toolbar class, Grid display and configuration panel. 
    - *ToolBar*: Hbox that holds most of the buttons that interacted with the graph. the Buttons included Play, Stop, Step, Reset, Upload Sim, and Save Sim. Included a slider that also affected the speed in which the simulation ran. There was also a dropdown box to switch between simulations and a timer that displayed the elapsed time that was dependent on the speed of the simulation. It also included all the functionality so that these features correctly affected the simulation.


## Assumptions that Affect the Design

#### Features Affected by Assumptions
* The simulation would not run unless a valid file was chosen from the new window that was displayed upon the start of the program.
* The parameter to change the cell shapes was in the XML file, not in the UI where a user can manually change it.
* The program was made to implement error handling for corrupted XML data, such as the following examples
* The ability for our grid to be able to 


## New Features HowTo

#### Easy to Add Features
- Adding new simulations 
- Adding more parameters to read in from XML
- Adding new shapes
- Adding different neighborhood arrangements (you would have to change the row/col deltas in the neighbor classes)

#### Other Features not yet Done
Things to be implemented:
#### Simulation
- Allow any different arrangements of neighbors instead of assuming it will always be the total possible number of neighbors
    - This would likely be handled in the neighborhood class when you check the neighbors. To do this, we would have to update where the bounds of the neighbors are (so rather than checking if the row/col exists in the grid, we would have the row and column numbers wrap around if they went over the width/height of the grid).
- Allow any different kind of grid edge types
    - This would also be implemented in the neighbor and grid classes. The Grid class would keep track of the type of edge it has and keep updating as the simulation went along. The Neighbor class would also have to change because the bounds would no longer stop at the edge of the grid (they would keep going in the case of an infinite grid or they would wrap around in the case of a toroidal grid).

#### Configuration
- Some more Data checks can be added, like checking if the states assigned in the layout are within the simulations's bounds (but this can be done later as well, when populating the grid.)

- States can be assigned according to the layout specified in data file, or randomly. We can add feature to do this based on a probability distribution.

- Can add more styling features such as color, boundary, etc.

#### Visualization
* Display a graph of stats about the populations of all of the "kinds" of cells over the time of the simulation
    * We already have a graph displayed, we just need to record the amounts of cells in different states and pass it to the grid
* Allow users to interact with the simulation dynamically to create or change a state at a grid location
    * This would be handled using an eventHandler that detected the coordinate of a specific cell and depending on the simulation ran,  a click of the mouse will change it to another state which updates the grid and then the display once the next iteration happens.
* Allow users to run multiple simulations at the same time so they can compare the results side by side (i.e., do not use tabs like a browser).
    * Adding another simulation to the Borderpane and then switching which simulation the button action affects.



