## Simulation Questions
ccw43 ag468 sk593

* How does a Cell know about its neighbors? How can it update itself without effecting its neighbors update?
    * DFS recursion 
    * Each specific cell has to have its own state
* What relationship exists between a Cell and a simulation's rules?
    * the simulations rules end up changing that specific cell's state
* What is the grid? Does it have any behaviors? Who needs to know about it?
    * hold all the cells and makes sure that each cell has a neighbor. The grid itself does not have behaviors but 
    the cells do. The methods that update each cell need to know about the grid
* What information about a simulation needs to be the configuration file?
    * rules, starting state, current state, neighbor state
* How is the graphical view of the simulation updated after all the cells have been updated?
    * every state has its own image and it constantly updates as each of the states update as well

### Game Review 
-Bricks
 We can add polymorphism to bricks. There could be a main type of brick that is just destroyed when hit. 
  There there could be bricks that have power ups or that break into two. Each of these would extend the main Brick class.
  - destroyBrick()
  - hitBrick()
-Power ups
We can add polymorphism to the power ups by having a parent class that handles things like dropping the bricks. Each subclass could take care of applying that specific power up
    -dropPowerUp()
    -applyPowerUp()
-Levels
There would be a basic level that reads the levels and then sets them up. Each subsequent level could have its own characterstics that change as levels increase
    -switchLevels()
    -setUpLevel()
    -readFile()
 
###CRC Cards
* Cell Class
    * Responsibilities
        * updateState(); updates the state based on rules/neighbors
    * Dependencies
        * Game class to input rules
    
* Grid Classes (we'd have multiple subclasses of this to represent different simulation)
    * Responsibilities
        * makeGrid(); makes the grid that holds the cells
        * checkNeighborStates(); checks state of neighboring cells
        * checkCurrentState(); checks current state of cell
        * readRules(); reads the rules 
    * Dependencies
        * Cell class
        
* Game class (runs the program)
    * Responsibilities
        * start(); starts/sets up the simulation
        * step(); updates the state as time passes
        * switchGrid(); 
    * Dependencies
        * Cell class
        * Grid class
        
###Use Cases
Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
* Grid class: checkNeighborStates(), readRules()
* Cell Class: updateState()
Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
* Grid class: checkNeighborStates(), readRules()
* Cell Class: updateState()
Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
* Game class: step()
Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML file
* Grid class: readRules()
Switch simulations: use the GUI to change the current simulation from Game of Life to Wator
* Grid class: switchGrids()