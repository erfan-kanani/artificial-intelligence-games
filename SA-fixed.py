from datetime import datetime
import random, math


class Board:
    def __init__(self, queen_count=8):
        self.queen_count = queen_count
        self.reset()

    def reset(self):
        self.queens = [-1 for i in range(0, self.queen_count)]

        for i in range(0, self.queen_count):
            self.queens[i] = random.randint(0, self.queen_count - 1)


    @staticmethod
    def calculateCostWithQueens(queens):
        threat = 0
        queen_count = len(queens)

        for queen in range(0, queen_count):
            for next_queen in range(queen+1, queen_count):
                if queens[queen] == queens[next_queen] or abs(queen - next_queen) == abs(queens[queen] - queens[next_queen]):
                    threat += 1

        return threat

    @staticmethod
    def toString(queens):
        board_string = ""

        for row, col in enumerate(queens):
            board_string += "(%s, %s)\n" % (row, col)

        return board_string

    def __str__(self):
        board_string = ""

        for row, col in enumerate(self.queens):
            board_string += "(%s, %s)\n" % (row, col)

        return board_string

class SimulatedAnnealing:
    def __init__(self, board):
        self.elapsedTime = 0
        self.board = board
        self.temperature = 20
        self.startTime = datetime.now()


    def run(self):
        board = self.board
        board_queens = self.board.queens
        solutionFound = False

        for k in range(0,self.temperature):
            
            successor_queens = board.queens[:]
            randomColumn = random.randint(0, board.queen_count-1)
            randomRow = random.randint(0,board.queen_count-1)
            while randomRow == successor_queens[randomColumn] :
                 randomRow = random.randint(0,board.queen_count-1)

            successor_queens[randomColumn] = randomRow
            dw = Board.calculateCostWithQueens(successor_queens) - Board.calculateCostWithQueens(board_queens)
            exp = math.e ** (dw/self.temperature)
            print(exp)
            if dw>0 :
                board_queens = successor_queens
                self.temperature -= 1

            if dw<0 :
                self.temperature -= 1
                if random.uniform(0,1) < exp :
                    board_queens = successor_queens


            if Board.calculateCostWithQueens(board_queens) == 0:
                print("Solution:")
                print(Board.toString(board_queens))
                self.elapsedTime = self.getElapsedTime()
                print("Success, Elapsed Time: %sms" % (str(self.elapsedTime)))
                solutionFound = True
                break

        if solutionFound == False:
            self.elapsedTime = self.getElapsedTime()
            print("Unsuccessful, Elapsed Time: %sms" % (str(self.elapsedTime)))

        return self.elapsedTime

    def getElapsedTime(self):
        endTime = datetime.now()
        elapsedTime = (endTime - self.startTime).microseconds / 1000
        return elapsedTime


if __name__ == '__main__':
    board = Board()
    print("Board:")
    print(board)
    SimulatedAnnealing(board).run()

