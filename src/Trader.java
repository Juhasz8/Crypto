public class Trader extends User
{

    public Trader()
    {
        super();
    }

    public void BuyingRequest(float amount)
    {
        TransactionRequest request = new TransactionRequest(this, publicKeyString, TransactionType.BUY, amount, 0);
        try
        {
            Network.getInstance().ProcessRequest(request);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

}
