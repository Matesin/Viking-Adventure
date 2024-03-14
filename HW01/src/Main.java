import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double first_num, second_num, result;
        int operation, decimal_point;
        char sign;

        Scanner input = new Scanner(System.in);
        System.out.println("Vyber operaci (1-soucet, 2-rozdil, 3-soucin, 4-podil):");
        operation = input.nextInt();
        switch (operation){
            case 1:
                Addition add = new Addition();
                result = add.calculate();
                first_num = add.getFirstNumber();
                second_num = add.getSecondNumber();
                sign = add.getSign();
                break;
            case 2:
                Subtraction sub = new Subtraction();
                result = sub.calculate();
                first_num = sub.getFirstNumber();
                second_num = sub.getSecondNumber();
                sign = sub.getSign();
                break;
            case 3:
                Multiplication mul = new Multiplication();
                result = mul.calculate();
                first_num = mul.getFirstNumber();
                second_num = mul.getSecondNumber();
                sign = mul.getSign();
                break;
            case 4:
                Division div = new Division();
                result = div.calculate();
                first_num = div.getFirstNumber();
                second_num = div.getSecondNumber();
                sign = div.getSign();
                break;
            default:
                throw new RuntimeException("Chybna volba!\n");
        }
        decimal_point = getDecimalPoint();
        printResult(first_num, second_num, result, decimal_point, sign);
    }
    private static int getDecimalPoint(){
        int decimal_spaces;
        Scanner input = new Scanner(System.in);
        System.out.println("Zadej pocet desetinnych mist: ");
        decimal_spaces = input.nextInt();
        if (decimal_spaces < 0){
            throw new RuntimeException("Chyba - musi byt zadane kladne cislo!\n");
        }
        return decimal_spaces;
    }
    private static void printResult(double x, double y, double res, int decimals, char sign){
        System.out.println(getFormattedNumber(x, decimals)
                + sign
                + getFormattedNumber(y, decimals)
                + '='
                + getFormattedNumber(res, decimals));
    }
    private static String getFormattedNumber(double num, int decimals){
        String format = "%." + decimals + "f";
        return String.format(format, num);
    }
}