simulation
====

This project implements a cellular automata simulator.

Names: Chris Warren, Shruthi Kumar, Aneesh Gupta

### Timeline

Start Date: 01-25-2020

Finish Date: 02-09-2020

Hours Spent: 20+ hours per person.

### Primary Roles
1. Aneesh Gupta: Writing _Configuration_ part and package, reading in things from XML files, creating Grid objects and populating them based on this. Also helped with some visualization components like uploading new simulations, downloading current state, resetting them, and two-pass update for the Grid class.

2. Shruthi Kumar:

3. Chris Warren:

### Resources Used
1. Class and Lab code: [Spike Simulation](https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation)
2. StackOverflow and Oracle Java documentation
3. XMLWrite [example](https://examples.javacodegeeks.com/core-java/xml/parsers/documentbuilderfactory/create-xml-file-in-java-using-dom-parser-example/)



### Running the Program

#### Main class: 
`src/cellsociety/Main.java` or `src/cellsociety/view/Display.java` (both run the program)

#### Data files needed: 
XML Files housed in `simulation_team03/data`: These house the simulations, the error files, and the saved (downloaded) simulations. New simulations can be added here.

#### Features implemented:
- Configuration:


- Model:

- Visualization:



### Notes/Assumptions

Assumptions or Simplifications:
- Once a file is desired to be uploaded, you should not close the file browsing window.
- Cell shapes are inputs through XML files.
- For a simulation to run correctly, clean, and correct XML files must be passed in.


Interesting data files: 
- `simulation_team03/data/gameOfLifeLayout.xml`: Represents a preset states and layout file
- `simulation_team03/data/predator.xml`: Randomly generated states file
- Files under `simulation_team03/data/ErrorFiles/`, eg.:
  - `invalidDataType.xml`: Invalid data type, such as string instead of number
  - `allEmpty.xml`: Missing fields
  - `incorrectChoice.xml`: Choice of simulation is not in bounds, so default to Game of Life simulation.
- Files under `simulation_team03/data/savedFiles/`, eg.:
  - `GameOfLife_saved.xml`: represents a simulation state downloaded from between the program, allowing users to save the state of the simulation they are seeing on the screen.

Known Bugs:
- Not a bug, but closing the file explorer once it is opened can cause program to shut down. Therefore, you must always choose a file if you have opened the file explorer.


Extra credit:


### Impressions

