simulation
====

This project implements a cellular automata simulator.

Language: Java. Frameworks: OpenJFX

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
  1. Reads in XML files. There are two options: either you select easy-access, default files, or open up your file explorer and upload one of your own choice.
  2. Catches errors and exceptions in XML files, and does not let bad XML files to be fed in (for egs. go to `data/ErrorFiles`):
     1. Empty/Missing fields: `allEmpty.xml`
     2. Incorrect datatype: `invalidDataType.xml`
     3. Negative values/values out of bounds: `negativeValues.xml`
     4. Out of bounds: Sets default values.
  3. Allow simulation's initial configuration to be set by:
     1. A list of specific locations and states specified in file.
     2. Completely randomly based on a total number of locations to occupy
     3. You can load in saved simulations.
  4. Allow users to save the simulation's current state in an XML file by clicking a button.
     1. These files are saved under `data/savedFiles`.
     2. Contain parameters such as name, author, size, shape.
     3. Also contain an exact map of the saved simulation's cells states.
     4. You can load in these to save and later resume a simulation.
  5. You can specify characteristics of the simulation through such files, eg.:
     1. Size of grid.
     2. Shape of cells to be used.
     3. Type of simulation to be run.
     4. States of specific cells.
     


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
- SugarScape simulation not completely robust when it comes to update logic.

Extra credit:
- Writing to XML file the current state of the program.
- Being able to load in specific states and locations for cells through configuration.
- Error and exception handling.


### Impressions

