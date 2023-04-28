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


        Miner miner1 = new Miner(20, 50);
        System.out.println("name: " + miner1.name);
        miner1.IncreaseWallet(25);
        miner1.RequestToSell(10, 2);

        System.out.println("----------------------------------------------");

        Trader trader1 = new Trader();
        System.out.println("name: " + trader1.name);
        trader1.IncreaseWallet(10);
        trader1.RequestToBuy(35);

        System.out.println("----------------------------------------------");

        Trader trader2 = new Trader();
        System.out.println("name: " + trader2.name);
        trader2.IncreaseWallet(500);
        trader2.RequestToSell(100, 4);



        // for later:
        // random name generator
        //

    }
}