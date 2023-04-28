public class TransactionRequest
{
    public float tradeAmount;

    public TransactionRequest(float tradeAmount)
    {
        this.tradeAmount = tradeAmount;
    }

}

class BuyingRequest extends TransactionRequest
{
    public Trader trader;

    public BuyingRequest(Trader trader, float tradeAmount)
    {
        super(tradeAmount);
        this.trader = trader;
    }

}

class SellingRequest extends TransactionRequest implements Comparable<SellingRequest>
{
    public User user;
    public float transactionFee;

    public SellingRequest(User user, float tradeAmount, float transactionFee)
    {
        super(tradeAmount);
        this.user = user;
        this.transactionFee = transactionFee;
    }

    @Override
    public int compareTo(SellingRequest request)
    {

        if (request.transactionFee > this.transactionFee)
        {
            return 1;
        }
        else if (request.transactionFee < this.transactionFee)
        {
            return -1;
        }

        return 0;
    }
}
