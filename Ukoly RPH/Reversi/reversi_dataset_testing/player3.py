import random

EMPTY = -1
BOARD_SIZE = 7


class MyPlayer:
    def __init__(self, my_color, opponent_color):
        self.name = 'martama1'
        self.my_color = my_color
        self.opp_color = opponent_color

    def move(self, board):
        self.board = board
        self.scanBoard()
        self.choosePosition()
        self.getMove()
        return self.next_move_position

    def scanBoard(self):  # checks for my positions on the board
        self.my_positions = []
        for i in range(len(self.board)):
            for j in range(len(self.board[i])):
                if self.board[i][j] == self.my_color:
                    self.my_positions.append((i, j))

    def getMaxElementIndex(self, arr):
        max_element = max(arr)
        max_element_index = 0
        for i in range(len(arr)):
            if arr[i] == max_element:
                max_element_index = i
                break
        return max_element_index

    def getMove(self):

        # gets the "chosen_region" position and determines the most valuable move from this region

        self.next_move_position = self.my_positions[self.chosen_position_index]
        self.row = self.next_move_position[0]
        self.column = self.next_move_position[1]

        print(f"{self.next_move_position}")  # test prints - DELETE!!
        print(f"row before correction {self.row}")
        print(f"column before correction {self.column}")
        # get move value of all the directions
        move_value = []
        print(f"row positive size: {self.rowPositiveSize()}")
        move_value.append(self.rowPositiveSize())
        move_value.append(self.rowNegativeSize())
        move_value.append(self.columnPositiveSize())
        move_value.append(self.columnNegativeSize())
        move_value.append(self.topLeftDiagonalSize())
        move_value.append(self.topRightDiagonalSize())
        move_value.append(self.bottomLeftDiagonalSize())
        move_value.append(self.bottomRightDiagonalSize())

        print(f"move values: {move_value}")

        # add the value of chosen move direction to the value of the initial point
        move_index = self.getMaxElementIndex(move_value)
        print(f"chosen move index: {move_index}")
        if move_index == 0:
            self.column += move_value[0]
        if move_index == 1:
            self.column -= move_value[1]
        if move_index == 2:
            self.row += move_value[2]
        if move_index == 3:
            self.row -= move_value[3]
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

    def rowOversize(self, pos):
        if self.row + pos > BOARD_SIZE:
            return True
        else:
            return False

    def columnOversize(self, pos):
        if self.column + pos > BOARD_SIZE:
            return True
        else:
            return False

    # picks a region from the "greatest_positons" array and stores it in the "chosen_region variable"
    def choosePosition(self):
        self.chosen_position_index = 0
        self.getPositionSize()
        self.chosen_position_index = random.choice(self.greatest_positons)
        print(f"{self.chosen_position_index}")

    # gets values of regions of positions listed in the array "my_positions"
    def getPositionSize(self):
        region_sizes = []
        self.greatest_positons = []
        for pos in range(len(self.my_positions)):
            self.row = self.my_positions[pos][0]
            self.column = self.my_positions[pos][1]
            self.positionMaxValue()
            region_sizes.append(self.size)
        max_value = max(region_sizes)
        for pos in range(len(region_sizes)):
            if region_sizes[pos] == max_value:
                self.greatest_positons.append(pos)
        print(f"greatest positions: {self.greatest_positons}")
    # functions below are used to calculate values of their respective regions

    def rowPositiveSize(self):
        size = 0
        for pos in range(self.column + 1, len(self.board[self.row])):
            position = self.board[self.row][pos]
            if position == self.opp_color:
                if pos < (len(self.board[self.row]) - 1):
                    size += 1
                else:
                    size = 0
                    break
            elif position == EMPTY and size > 0:
                size += 1
                break
            elif position == self.my_color:
                size = 0
                break
            else:
                size = 0
                break
        # print(f"row pos. s.:{size}")
        return size

    def rowNegativeSize(self):
        size = 0
        for pos in range(self.column, -1, -1):
            position = self.board[self.row][pos]
            if position == self.opp_color:
                if pos != 0:
                    size += 1
                else:
                    size = 0
                    break
            elif position == EMPTY and size > 0:
                size += 1
                break
            elif position == self.my_color and pos != 0:
                break
            else:
                size = 0
                break
        return size

    def columnPositiveSize(self):
        size = 0
        for pos in range(self.row + 1, BOARD_SIZE):
            position = self.board[pos][self.column]
            if position == self.opp_color:
                if pos < BOARD_SIZE - 1:
                    size += 1
                else:
                    size = 0
                    break
            elif position == EMPTY and size > 0:
                size += 1
                break
            elif position == self.my_color:
                size = 0
                break
            else:
                size = 0
                break
        return size

    def columnNegativeSize(self):
        size = 0
        for pos in range(self.row, -1, -1):
            position = self.board[pos][self.column]
            if position == self.opp_color:
                if pos > 0:
                    size += 1
                else:
                    size = 0
                    break
            elif position == EMPTY and size > 0:
                size += 1
                break
            elif position == self.my_color:
                size = 0
                break
            else:
                size = 0
                break
        return size

    def topLeftDiagonalSize(self):
        size = 0
        pos_row = self.row
        pos_column = self.column
        while pos_row > 0 and pos_column > 0:
            pos_row -= 1
            pos_column -= 1
            position = self.board[pos_row][pos_column]
            if position == self.opp_color:
                if pos_row > 0 and pos_column > 0:
                    size += 1
                else:
                    size = 0
                    break
            elif position == EMPTY and size > 0:
                size += 1
                break
            elif position == self.my_color:
                size = 0
                break
            else:
                size = 0
                break
        return size

    def topRightDiagonalSize(self):
        size = 0
        pos_row = self.row
        pos_column = self.column
        while pos_row > 0 and pos_column < BOARD_SIZE:
            pos_row -= 1
            pos_column += 1
            position = self.board[pos_row][pos_column]
            if position == self.opp_color:
                if pos_row > 0 and pos_column < BOARD_SIZE:
                    size += 1
                else:
                    size = 0
                    break
            elif position == EMPTY and size > 0:
                size += 1
                break
            elif position == self.my_color:
                size = 0
                break
            else:
                size = 0
                break
        return size

    def bottomLeftDiagonalSize(self):
        size = 0
        pos_row = self.row
        pos_column = self.column
        while pos_row < BOARD_SIZE and pos_column > 0:
            pos_row += 1
            pos_column -= 1
            position = self.board[pos_row][pos_column]
            if position == self.opp_color:
                if pos_row < BOARD_SIZE and pos_column > 0:
                    size += 1
                else:
                    size = 0
                    break
            elif position == EMPTY and size > 0:
                size += 1
                break
            elif position == self.my_color:
                size = 0
                break
            else:
                size = 0
                break
        return size

    def bottomRightDiagonalSize(self):
        size = 0
        pos_row = self.row
        pos_column = self.column
        while pos_row < BOARD_SIZE and pos_column < BOARD_SIZE:
            pos_row += 1
            pos_column += 1
            position = self.board[pos_row][pos_column]
            if position == self.opp_color:
                if pos_row < BOARD_SIZE and pos_column < BOARD_SIZE:
                    size += 1
                else:
                    size = 0
                    break
            elif position == EMPTY and size > 0:
                size += 1
                break
            elif position == self.my_color:
                size = 0
                break
            else:
                size = 0
                break
        return size

    def positionMaxValue(self):  # func to return the greatest value of the desired
        self.size = 0

        local_size = []
        local_size.append(self.rowPositiveSize())
        local_size.append(self.rowNegativeSize())
        local_size.append(self.columnPositiveSize())
        local_size.append(self.columnNegativeSize())
        local_size.append(self.topLeftDiagonalSize())
        local_size.append(self.topRightDiagonalSize())
        local_size.append(self.bottomLeftDiagonalSize())
        local_size.append(self.bottomRightDiagonalSize())

        self.size = max(local_size)
