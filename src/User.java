import java.security.*;
import java.util.Base64;

public class User
{
    //these three could be in its own wallet class
    public String publicKeyString;
    private String privateKeyString;

    public PublicKey publicKey;
    private PrivateKey privateKey;

    //the amount of Puff you own
    private double wallet = 0;

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
            privateKey = pair.getPrivate();

            //Getting the public key from the key pair
            publicKey = pair.getPublic();

            Base64.Encoder encoder = Base64.getEncoder();

            //System.out.println("Keys generated -> public: " + encoder.encodeToString(publicKey.getEncoded()) + " size: "+ encoder.encodeToString(publicKey.getEncoded()).length() + " private: " + encoder.encodeToString(privKey.getEncoded()) );
            publicKeyString = encoder.encodeToString(publicKey.getEncoded());
            privateKeyString = encoder.encodeToString(privateKey.getEncoded());

            Network.getInstance().JoinNetwork(this);
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

    public double GetWallet()
    {
        return wallet;
    }

    public void SellRequest(float amount, float fee)
    {
        if(wallet < amount)
            return;

        TransactionRequest request = new TransactionRequest(this, publicKeyString, TransactionType.SELL, amount, fee);
        try
        {
            Network.getInstance().ProcessRequest(request);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void Sign(String buyerPublicKey, byte[] message)
    {
        try
        {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);

            signature.update(message);

            //byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));
            byte[] digitalSignature = signature.sign();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
