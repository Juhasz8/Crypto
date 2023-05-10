import java.security.PublicKey;

public class TransactionMatch
{
    //public User fromUser;
    //public User toUser;
    public String fromPublicKey;
    public String toPublicKey;
    public float amount;

    public TransactionMatch(String fromPublicKey, String toPublicKey, float amount)
    {
        //this.fromUser = fromUser;
        //this.toUser = toUser;
        this.fromPublicKey = fromPublicKey;
        this.toPublicKey = toPublicKey;
        this.amount = amount;
    }
}
