Rock, Paper Scissors practice exericse


* Because we have to consider the fact that there can be an infinite number of dependencies, we can make a class that
counts as any object that inherits a wiinning and losing condition that can be determined separately by a 2d array.
* The class can also have inputs for a new type  and allow the user to choose a proportional amount of things that the 
new object loses and wins to.
* Game class that handles which the actual action of the game i.e. the current score, who chooses what..
* Choices are represent a number, have 2 users choose a number in that range, and then have the game class compare which 
one wins, and this happens every turn
* Classes
    * Game Class
        * responsibilities
            * compares player choices
            * keeps score
            * determines a winner
            * start game
            * reads user interface and assigns a choice
        * interactions
            * user interface
            * destructor class   
    * Destructor Class
        * responsibilities
            * reads file input
            * makes data structure with win/lose conditioning for each weapon
            * compares 2 weapons to see which wins
        * interactions
            * game class
            * text file/user input