package cz.cvut.fel.pjv;

import java.util.*;

public class BruteForceAttacker extends Thief {
    char[] passwordChars;
    int passwordSize;
    char[] password;
    int generationSize;
    int passwordIndex;
    @Override
    public void breakPassword(int sizeOfPassword) {
        this.passwordSize = sizeOfPassword;
        this.password = new char[passwordSize];
        this.passwordChars = getCharacters();
        generatePassword(passwordSize, "");
    }
    public void generatePassword(int passwordSize, String password){
        if (passwordSize == 0){
             tryOpen(password.toCharArray());
             return;
        }
        for(char c : passwordChars){
            if (isOpened()) break;
            String newPassword = password + c;
            generatePassword(passwordSize - 1, newPassword);
        }
    }
}
