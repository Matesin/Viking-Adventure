
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
        self.destinations_dict = {}
    
    def pick_move(self):
        """
        Picks a move from the best positions
        """
        self.chosen_position = random.choice(self.best_destinations) #randomly chooses one of the best positions
        self.next_move = self.chosen_position[0]
    def check_position(self, pos):
        """
        Checks the position for possible moves
        """
        for i in range(-1, 2):
                for j in range(-1, 2):
                    self.evaluate_position(pos, i, j)

    def find_moves(self):
        for pos in self.my_positions:
            self.check_position(pos)
        self.best_destinations = self.pick_best_from_dict(self.destinations_dict) #list of tuples of the best positions and their regions

    def pick_best_from_dict(self, dict):
        """
        Picks the best position from the dictionary
        """
        best = max(dict.values())
        best_items = [k for k in dict.items() if k[1] == best or k[1] == best-1]
        print(best_items)
        return best_items

    def evaluate_position(self, pos, row_diff, col_diff):
        """
        Evaluates the position for possible moves in the given direction
        :param pos: position to be evaluated

        :param row_diff: direction to check in rows
        :param col_diff: direction to check in columns
        """
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
        else:
            pass
