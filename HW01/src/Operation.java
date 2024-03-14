import java.util.Scanner;

abstract class Operation {
    int decimal_spaces;
    Scanner input = new Scanner(System.in);
    double first_num, second_num;
    abstract double readFirstNumber();
    abstract double readSecondNumber();
    abstract double calculate();
    public double getFirstNumber(){
        return first_num;
    }
    public double getSecondNumber(){
        return second_num;
    }


}
