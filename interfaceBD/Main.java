package interfaceBD;

import dataBase.ConnectionClass;

public class Main {
    public static void main(String[] args) {
        LogIn test=new LogIn(new ConnectionClass("proiectbd4"));
        test.go();
    }
}
