import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        JFrame frame = new JFrame("CryptoApp");
        frame.setContentPane(new Window().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        System.out.println("----------------------------------------------");


        Miner Alfred = new Miner(20, 50);
        Alfred.IncreaseWallet(25);
        Alfred.SellRequest(10, 2);

        System.out.println("----------------------------------------------");

        Trader Jack = new Trader();
        Jack.IncreaseWallet(10);
        Jack.BuyingRequest(35);

        System.out.println("----------------------------------------------");

        Trader Bob = new Trader();
        Bob.IncreaseWallet(500);
        Bob.SellRequest(100, 4);



    }
}