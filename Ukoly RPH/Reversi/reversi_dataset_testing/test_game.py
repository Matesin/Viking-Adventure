from player import BOARD_SIZE, MyPlayer
from board import BLACK, Board

BOARD_SIZE = 8
WHITE = 1
BLACK = 0

if __name__ == '__main__':
    board_init = Board(BOARD_SIZE, BOARD_SIZE)
    player = MyPlayer(WHITE, BLACK)
    board = []
    board = board_init.initializeBoard()
    player.move(board)
    