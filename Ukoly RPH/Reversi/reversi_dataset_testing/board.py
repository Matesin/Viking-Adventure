
BLACK = 1
WHITE = 1
EMPTY = -1

class Board:
    def __init__(self, rows, columns) -> None:
        self.rows = rows
        self.columns = columns
    def initializeBoard(self):
        board = []
        for row in range(self.rows):
            for column in range(self.columns):
                if row == (self.rows - 1)/2 and column == (self.columns -1 )/2:
                    board.append(BLACK)
                elif row == (self.rows + 1)/2 and column == (self.columns -1 )/2:
                    board.append(WHITE)
                elif row == (self.rows - 1)/2 and column == (self.columns +1 )/2:
                    board.append(WHITE)
                elif row == (self.rows + 1)/2 and column == (self.columns+1 )/2:
                    board.append(BLACK)
                else:
                    board.append(EMPTY)
        return board