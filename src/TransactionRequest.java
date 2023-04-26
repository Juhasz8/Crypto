public class TransactionRequest implements Comparable<TransactionRequest>
{
    public User user;
    public String publicKey;
    public TransactionType type;
    public float tradeAmount;
    public float transactionFee;

    public TransactionRequest(User user, String publicKey, TransactionType type, float tradeAmount, float transactionFee)
    {
        this.user = user;
        this.publicKey = publicKey;
        this.type = type;
        this.tradeAmount = tradeAmount;
        this.transactionFee = transactionFee;
    }

    @Override
    public int compareTo(TransactionRequest request) {

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
