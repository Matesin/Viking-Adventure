
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
    
    def move(self, board) -> tuple:
        """
        Returns the next move of the player

        :param board: current state of the board
        :return: next move of the player
        """
        self.board = board
        self.scan_board()
        self.find_moves()
        self.pick_move()
        return self.next_move
    
    def scan_board(self):
        """
        Returns the list of the player's positions on the board
        """
        self.my_positions = []
        for i in range(len(self.board)):
            for j in range(len(self.board[i])):
                if self.board[i][j] == self.my_color:
                    self.my_positions.append((i, j))
        self.pos_dict = {}
        self.destinations_dict = {}
    
    def pick_move(self):
        """
        Picks a move from the best positions
        """
        self.chosen_position = random.choice(self.best_positions) #randomly chooses one of the best positions
        regions = self.chosen_position[1] #regions is a list of the sizes of the regions
        best_region_index = self.arr_max_index(regions) - 1 #index of the best region - serves as the row_diff param
        pos = self.chosen_position[0]
        row = self.chosen_position[0][0]
        column = self.chosen_position[0][1]
        size = 0
        differences = [0, 0]
        for col_diff in range (-1, 2):
            local_size = self.evaluate_position(pos, best_region_index, col_diff)
            if local_size > size:
                size = local_size
                differences[0], differences[1] = (best_region_index, col_diff) #initialized for better readability
        self.next_move = (row + size * differences[0], column + size * differences[1])

    def check_position(self, pos): #returns the size of the region
        region_size = []
        for i in range(-1, 2):
                row_diff = i
                if i == -1: 
                    local_region_size = []
                    for j in range(-1, 2):
                        col_diff = j
                        local_region_size.append(self.evaluate_position(pos, row_diff, col_diff))
                        #print(f"local region size for {pos} -1:{local_region_size}")
                    region_size.append(sum(local_region_size))
                elif i == 0:
                    local_region_size = []
                    for j in range(-1, 2):
                        col_diff = j
                        local_region_size.append(self.evaluate_position(pos, row_diff, col_diff))
                        #print(f"local region size for {pos} 0:{local_region_size}")
                    region_size.append(sum(local_region_size))
                elif i == 1:
                    local_region_size = []
                    for j in range(-1, 2):
                        col_diff = j
                        local_region_size.append(self.evaluate_position(pos, row_diff, col_diff))
                        #print(f"local region size for {pos} 1:{local_region_size}")
                    region_size.append(sum(local_region_size))
                #print(f"region size for {pos}:{region_size}")
        return region_size

    def find_moves(self):
        for pos in self.my_positions:
            self.pos_dict[pos] = self.check_position(pos)
        self.best_positions = ([val for val in self.pos_dict.items() if val[1] == max(self.pos_dict.values())]) #list of tuples of the best positions and their regions
        #print(f"best positions: {self.best_positions}")

    def evaluate_position(self, pos, row_diff, col_diff):
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
        if size > 0:
            if (row, column) in self.destinations_dict:
                self.destinations_dict[(row, column)] += size
            else:
                self.destinations_dict[(row, column)] = size
        return size
    def arr_max_index(self, arr):
        maximum = max(arr)
        for i in range(len(arr)):
            if arr[i] == maximum:
                return i
    