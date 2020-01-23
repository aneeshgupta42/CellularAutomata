## Simulation Questions
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
We can add polymorphism to bricks. There could be a main type of brick that is just destroyed when hit. 
 There there could be bricks that have power ups or that break into two. Each of these would extend the main Brick class.
 