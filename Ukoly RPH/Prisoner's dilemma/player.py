COOPERATE = True
DEFECT = False

class MyPlayer:
    """
    player decisions based on matrix values and opponent's moves 
    """
    def __init__(self, payoff_matrix, number_of_iterations = None):
        self.answer = None
        self.my_history = []
        self.opponent_history = []
        self.payoff = payoff_matrix
        if number_of_iterations == None:
            self.number_of_iterations = 20
        else:
            self.number_of_iterations = number_of_iterations
        self.current_iteration = 1
        
        
    def move(self):
        if self.current_iteration == 1:
            self.answer = COOPERATE
        elif self.current_iteration == 2:
            self.evaluate()
            self.answer = self.chosen_strategy
        else:
            self.evaluate()
            self.answer = self.chosen_strategy
        self.current_iteration += 1
        return self.answer

    def record_last_moves(self, my_last_move, opponent_last_move):
        self.my_history.append(my_last_move)
        self.opponent_history.append(opponent_last_move)
        if len(self.opponent_history) > 5:
            self.opponent_history.pop(0)
            self.my_history.pop(0)
    
    def evaluate(self):
        self.cooperate_value = self.payoff[0][0][0] + self.payoff[0][1][0]
        self.defect_value = self.payoff[1][1][0] + self.payoff[1][0][0]
        if self.defect_value - self.cooperate_value < 3 or COOPERATE in self.opponent_history:
            self.chosen_strategy = self.adjustedCopyCat()
        else:
            self.chosen_strategy = self.greedy()
    
    def adjustedCopyCat(self):
        if self.current_iteration == self.number_of_iterations - 1: #defects in the last round
            self.answer = DEFECT
        elif self.current_iteration % 7 == 0: #defects every 7th round
            self.answer = DEFECT
        elif self.current_iteration >= 3 and self.opponent_history.count(DEFECT) > 1: #respects opponent's mistake
            self.answer = self.my_history[2]
        elif self.current_iteration < 3:
            self.answer = self.opponent_history[self.current_iteration - 2] #copies opponent's move
        else:
            self.answer = self.opponent_history[1] #copies opponent's move in the first three iterations
        return self.answer
    
    def greedy(self): #always defects
        self.answer = DEFECT
        return self.answer