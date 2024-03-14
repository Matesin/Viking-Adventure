import random

EMPTY = -1
BOARD_SIZE = 7

class MyPlayer:
    """
    Player checks all of his positions for moves and chooses the best one
    """
    def __init__(self, my_color, opponent_color) -> None:
        self.name = 'martama1'
        self.my_color = my_color
        self.opp_color = opponent_color
    
    def move(self, board):
        self.board = board
        self.next_move = None
        self.scanBoard()
        self.findMoves()
        self.pickMove()
        return self.next_move
    def scanBoard(self):  # checks for my positions on the board
        self.my_positions = []
        for i in range(len(self.board)):
            for j in range(len(self.board[i])):
                if self.board[i][j] == self.my_color:
                    self.my_positions.append((i, j))
        self.pos_dict = {}
    
    def pickMove(self):
        row = self.chosen_position[0][0]
        column = self.chosen_position[0][1]
        value = self.chosen_position[1]
        pos = self.chosen_position[0]
        for i in range(-1, 2):
                row_diff = i
                if i == -1: 
                    for j in range(-1, 2):
                        col_diff = j
                        if self.evaluatePosition(pos, row_diff, col_diff) == value:
                            self.next_move = (row + value * i, column + value * j)
                            break
                elif i == 0:
                    for j in range(-1, 2):
                        col_diff = j
                        if self.evaluatePosition(pos, row_diff, col_diff) == value:
                            self.next_move = (row + value * i, column + value * j)
                            break
                elif i == 1:
                    for j in range(-1, 2):
                        col_diff = j
                        if self.evaluatePosition(pos, row_diff, col_diff) == value:
                            self.next_move = (row + value * i, column + value * j)
                            break

    def checkPosition(self, pos):
        size = 0
        for i in range(-1, 2):
                row_diff = i
                if i == -1: 
                    local_size = []
                    for j in range(-1, 2):
                        col_diff = j
                        local_size.append(self.evaluatePosition(pos, row_diff, col_diff))
                        #print(f"local size for {pos} -1:{local_size}")
                    size = max(local_size)
                elif i == 0:
                    local_size = []
                    for j in range(-1, 2):
                        col_diff = j
                        local_size.append(self.evaluatePosition(pos, row_diff, col_diff))
                        #print(f"local size for {pos} 0:{local_size}")
                    if max(local_size) > size:
                        size = max(local_size)
                elif i == 1:
                    local_size = []
                    for j in range(-1, 2):
                        col_diff = j
                        local_size.append(self.evaluatePosition(pos, row_diff, col_diff))
                        #print(f"local size for {pos} 1:{local_size}")
                    if max(local_size) > size:
                        size = max(local_size)
        return size

    def findMoves(self):
        for pos in self.my_positions:
            self.pos_dict[pos] = self.checkPosition(pos)
        self.best_positions = ([kv for kv in self.pos_dict.items() if kv[1] == max(self.pos_dict.values())])
        #print(self.best_positions)
        self.chosen_position = random.choice(self.best_positions)
        #print(self.chosen_position)

    def evaluatePosition(self, pos, row_diff, col_diff):
        row = pos[0] + row_diff
        column = pos[1] + col_diff
        if row < 0 or row > BOARD_SIZE or column < 0 or column > BOARD_SIZE:
            return 0
        size = 0
        while (self.board[row][column] == self.opp_color):
            size += 1
            row += row_diff
            column += col_diff
            if row < 0 or row > BOARD_SIZE or column < 0 or column > BOARD_SIZE:
                return 0
        if self.board[row][column] == self.my_color:
            size = 0
        if self.board[row][column] == EMPTY and size > 0: 
            size += 1
        else:
            size = 0
        return size