public class Multiplication extends Operation{
    double readFirstNumber() {
        System.out.println("Zadej cinitel: ");
        first_num = input.nextDouble();
        return first_num;
    }

    double readSecondNumber() {
        System.out.println("Zadej cinitel: ");
        second_num = input.nextDouble();
        return second_num;
    }
    double calculate() {
        first_num = readFirstNumber();
        second_num = readSecondNumber();
        return first_num * second_num;
    }
    char getSign() {
        return '*';
    }
}