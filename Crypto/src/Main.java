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

        System.out.println(Cryptography.sha256("Apple"));
        User Alfred = new User();
        System.out.println("----------------------------------------------");
        User Allice = new User();
        try {
            Thread.sleep(2000);

        }
        catch (Exception e)
        {

        }

        System.out.println("----------------------------------------------");
        User Allice3 = new User();


    }
}