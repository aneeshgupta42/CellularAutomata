##Refactoring Discussion
###Priorities
- Duplicated Methods 
    - Our duplicated methods were primarily in the Cell classes. This is because we needed to check the neighbors and the states
    of all the classes. Because all of the states for each simulation were different it seemed like there wasn't a way to combine
    the methods. Upon further analysis, we found that we could refactor the code in such a way that we just passed the state
    variable that we needed to check as a parameter to the method. Then all we had to do is create 1 method that would be in the
    Cell superclass and then call that method as necessary. We refactored the GameCell, FireCell, and PercolationCell classes 
    using this design. We made a few minor changes to SegregationCell and PredatorPreyCell using this design but there are 
    still lines of code that could be refactored in such a way that there isn't any duplication. 
- Color for states (javafx) 
    - Part of the design specifications states that we can't use javafx imports in our model or configuration components. This
    was an oversight on our part and we ended up using JavaFX colors in our Cell classes that are located in the Model component. 
    In order to fix this problem, we are going to use Strings to define the colors and then set the actual value of those colors in 
    the View component where the colors are displayed.  
- Hiding the implementation of the grid in the Cell classes
    - We correctly hid the implementation of the grid structure when we updated the grid. There are 2 other areas where we thought
    we hid the implementation, but we really did not. The first is located inside the MainView class where we display the grid. 
    In this displayGrid method, we define Point and use those as keys to get values in the HashMap. So the MainView class now
    knows that we used a HashMap (with Points as keys and Cells as values -- the Point object determine which row and col the Cell 
    is placed in). The second place is in the Cell classes. We thought that because we're not directly accessing the grid hashmap 
    and rather accessing it by reference through the grid (because we passed the hashmap as a parameter), this didn't violate 
    the encapsulation. But when we asked ourselves if the Cell classes knew that the data structure we used was a hashmap, we
    realized that we had been wrong. In order to fix this, we are going to declare a Grid object in each cell and then create 
    a getCell method in the grid class. The getCell method is going to take in a row and a column and then return the 
    corresponding cell. We're going to call this method on the Grid object that each cell will have. Thus, the Cell classes won't 
    know what type of structure the grid is. It will just have access to the cell at the given points.  
- Getting rid of all magic numbers
    - We have a lot of numbers that need to be changed into constants. This way, we're not hard coding values into our code.  
