AI games 
in this project we implement 3 AI games 
1)maze problem
2)n-queen problem 
4)tic-tac-toe

1-Maze problem using A star algorithm 
in this problem we want to find a path from initial state to goal state with the A star algorithm 
we set the walls around the cells of the maze board and we want the algorithm to reach the goal state 
if there is any path from initial state to goal the algorithm will find the optimal path because
the A star algorithm is complete and cause the heuristic function is admissible it will find the optimal path

2- n-queen problem using Simulated Annealing
there are n queen that we want to put in the n*n board with no two queens threat each other 
the initial state of our board generate randomly 
we have a method called find cost that calculate the pairs of queens that threat each other
cause we use simulated annealing algorithm we may not found the solution
in SA algorithm we use tempreture deltaE and the probability to move or not 
in each move random column will be choosed and based on that random column random row will be generated 
the cost of the nextState will be calculated and also does the current state
we calculate deltaE => deltaE = cost of next state - cost of current state
if deltaE becomes greater than zero means that next state is better than current than current state and the move accurs
if the deltaE becoms zero means the current and next state are equal
if deltaE becomes less than zero we calculate the probability => probability = e ** (deltaE/Tempreture)
then we generate random number between 0 and 1 and if the random number less or equal to probability we take the action and make the move
and in each iterate the tempreture subtracted by 1

3-tic-tac-toe alpha-beta pruning
we play against our ai with the alpha beta pruning algorithm 
the ai is the maximizer and we are minimizer 
we cant win against our ai but if we make a mistake our ai wins





