
import random

EMPTY = -1
BOARD_SIZE = 8

class MyPlayer:
    def __init__(self, my_color, opponent_color):
        self.name = 'martama1'
        self.my_color = my_color
        self.opp_color = opponent_color

    def move(self, board):
        self.board = board
        self.scanBoard()
        self.chooseRegion()
        self.getValidMoves()
        return self.next_move_position
    
    def scanBoard(self): #checks for my positions on the board
        self.my_positions = []
        for i in range (len(self.board)):
            for j in range (len(self.board[i])):
                if self.board[i][j] == self.my_color:
                    self.my_positions.append((i,j))
    
    def getMaxElement(self, target_arr): #func to get the maximal element of an array (instead of max() func)
        max = target_arr[0]
        for el in target_arr:
            if target_arr[el] > max:
                max = target_arr[el]
        return max
    
    def getValidMoves(self):

        #gets the "chosen_region" position and determines the most valuable move from this region

        self.next_move_position = self.my_positions[self.chosen_region_index]
        self.row = self.next_move_position[0]  
        self.column = self.next_move_position[1]

        print(f"{self.next_move_position}") #test prints - DELETE!!
        print(f"{self.row}")
        print(f"{self.column}")
        #get move value of all the directions
        move_value = []
        print(f"{self.rowPositiveSize}")
        move_value.append(self.rowPositiveSize())
        move_value.append(self.rowNegativeSize())
        move_value.append(self.columnPositiveSize())
        move_value.append(self.columnNegativeSize())
        move_value.append(self.topLeftDiagonalSize())
        move_value.append(self.topRightDiagonalSize())
        move_value.append(self.bottomLeftDiagonalSize())
        move_value.append(self.bottomRightDiagonalSize())

        print(f"move values: {move_value}")

        for el in move_value:
            el += 1

        move_index = self.getMaxElement(move_value) #add the value of chosen move direction to the value of the initial point
        print(f"chosen move index: {move_index}")
        if move_index == 0:
            self.row += move_value[0]
        if move_index == 1:
            self.row -= move_value[1]
        if move_index == 2:
            self.column += move_value[2]
        if move_index == 3:
            self.column -= move_value[3]
        if move_index == 4:
            self.row -= move_value[4]
            self.column -= move_value[4]
        if move_index == 5:
            self.row -= move_value[5]
            self.column += move_value[5]
        if move_index == 6:
            self.row += move_value[6]
            self.column -= move_value[6]
        if move_index == 7:
            self.row += move_value[7]
            self.column += move_value[7]
        print(f"chosen row:{self.row}, chosen column:{self.column}")
        self.next_move_position = (self.row, self.column)

    def chooseRegion(self): #picks a region from the "greatest_regions" array and stores it in the "chosen_region variable"
        self.chosen_region_index = 0
        self.getGreatestRegionSize()
        self.chosen_region_index = random.choice(self.greatest_regions)
        print(f"{self.chosen_region_index}")

    def getGreatestRegionSize(self): #gets values of regions of positions listed in the array "my_positions"
        region_sizes = []
        self.greatest_regions = []
        for pos in range(len(self.my_positions)):
            self.row = self.my_positions[pos][0]
            self.column = self.my_positions[pos][1] 
            self.regionSize()
            region_sizes.append(self.size)
        print(f"region sizes:{region_sizes}")
        max = self.getMaxElement(region_sizes)
        for pos in range(len(region_sizes)):
            if region_sizes[pos] == max:
                self.greatest_regions.append(pos)
    #functions below are used to calculate values of their respective regions 
    def rowPositiveSize(self):
        size = 0
        for pos in range(BOARD_SIZE - self.column):
            if self.board[self.row][self.column + pos] == self.opp_color:
                if pos < len(self.board[self.row]):
                    size += 1
            elif self.board[self.row][self.column + pos] == EMPTY:
                break
            else: 
                size = 0
                break
        print(f"row pos. s.:{size}")
        return size
    def rowNegativeSize(self):
        size = 0
        for i in range(self.column, -1, -1):
            if self.board[self.row][self.column - i] == self.opp_color:
                if i < self.column:
                    size += 1
            elif self.board[self.row][self.column - i] == EMPTY:
                break
            else:
                break
        return size

    def columnPositiveSize(self):
        size = 0
        for pos in range(0, len(self.board) - self.row):
            if self.board[self.row + pos][self.column] == self.opp_color:
                if pos >= 0:
                    size += 1
            elif self.board[self.row + pos][self.column] == EMPTY:
                break
            else:
                size = 0
                break
        return size
    def columnNegativeSize(self):
        size = 0
        for pos in range(self.row, -1, -1):
            if self.board[self.row - pos][self.column] == self.opp_color:
                if pos > 0:
                    size += 1
            elif self.board[self.row - pos][self.column] == EMPTY:
                break
            else:
                size = 0
                break
        return size
    def topLeftDiagonalSize(self):
        size = 0
        for pos in range(self.row):
            if self.board[self.row - pos][self.column - pos] == self.opp_color:
                if pos > 0 and pos <= self.column:
                    size += 1
            elif self.board[self.row - pos][self.column - pos] == EMPTY:
                break
            else:
                size = 0
                break
        return size
    def topRightDiagonalSize(self):
        size = 0
        for pos in range(self.row):
            if self.board[self.row - pos][self.column + pos] == self.opp_color:
                if pos > 0:
                    size += 1
            elif self.board[self.row - pos][self.column + pos] == EMPTY:
                break
            else:
                size = 0
                break
        return size
    def bottomLeftDiagonalSize(self):
        size = 0
        for pos in range(len(self.board) - self.row):
            if self.board[self.row + pos][self.column - pos] == self.opp_color:
                if pos > 0 and pos <= self.column:
                    size += 1
            elif self.board[self.row + pos][self.column - pos] == EMPTY:
                break
            else:
                size = 0
                break
        return size
    def bottomRightDiagonalSize(self):
        size = 0
        for pos in range(len(self.board) - self.row):
            if self.board[self.row + pos][self.column + pos] == self.opp_color:
                if pos > 0:
                    size += 1
            elif self.board[self.row + pos][self.column + pos] == EMPTY:
                break
            else:
                size = 0
                break
        return size
    
    def diagonalSize(self):
        size = 0
        size = self.topLeftDiagonalSize() + self.topRightDiagonalSize() + self.bottomLeftDiagonalSize() + self.bottomRightDiagonalSize()
        return size
    
    def regionSize(self): #simplifying func to return value of the whole region of a point
        self.size = 0
        self.size = self.diagonalSize() + self.columnPositiveSize() + self.columnNegativeSize() + self.rowPositiveSize() + self.rowNegativeSize()
