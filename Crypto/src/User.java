import java.security.*;
import java.util.Random;
import java.util.Base64;

public class User
{
    public String publicKey;
    private String privateKey;

    //the amount of Puff you own
    private int wallet = 0;

    public User()
    {
        try
        {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            //Initializing the KeyPairGenerator
            keyPairGen.initialize(512);

            //Generating the pair of keys
            KeyPair pair = keyPairGen.generateKeyPair();

            //Getting the private key from the key pair
            PrivateKey privKey = pair.getPrivate();

            //Getting the public key from the key pair
            PublicKey publKey = pair.getPublic();

            Base64.Encoder encoder = Base64.getEncoder();

            //System.out.println("Keys generated -> public: " + encoder.encodeToString(publicKey.getEncoded()) + " size: "+ encoder.encodeToString(publicKey.getEncoded()).length() + " private: " + encoder.encodeToString(privKey.getEncoded()) );
            publicKey = encoder.encodeToString(publKey.getEncoded());
            privateKey = encoder.encodeToString(privKey.getEncoded());
        }
        catch (Exception e)
        {
            System.out.println("Keys couldnt be created: " + e);
        }
    }

    //this is called whenever the transaction is verified
    public void IncreaseWallet(int amount)
    {
        wallet += amount;
    }

    public int GetWallet()
    {
        return wallet;
    }

    public void SellRequest(int amount)
    {
        if(wallet < amount)
            return;

        TransactionRequest request = new TransactionRequest(publicKey, TransactionType.SELL, amount);
        try
        {
            DataManager.getInstance().ProcessRequest(request);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
