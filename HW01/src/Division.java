public class Division extends Operation{
    double readFirstNumber() {
        System.out.println("Zadej delenec: ");
        first_num = input.nextDouble();
        return first_num;
    }

    double readSecondNumber() {
        System.out.println("Zadej delitel: ");
        second_num = input.nextDouble();
        if (second_num == 0){
            throw new RuntimeException("Pokus o deleni nulou!");
        }
        return second_num;
    }
    double calculate() {
        first_num = readFirstNumber();
        second_num = readSecondNumber();
        return first_num / second_num;
}
    char getSign() {
        return '/';
    }
}
