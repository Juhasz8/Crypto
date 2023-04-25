public class TransactionRequest
{
    public String publicKey;
    public TransactionType type;
    public int amount;

    public TransactionRequest(String publicKey, TransactionType type, int amount)
    {
        this.publicKey = publicKey;
        this.type = type;
        this.amount = amount;
    }
}
