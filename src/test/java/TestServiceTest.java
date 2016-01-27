import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by LChlebda on 2016-01-26.
 */
public class TestServiceTest {
    Bank bank1, bank2;

    @Before
    public void initBanks () {
        bank1 = new Bank ();
        bank1.setId (1);
        bank1.setName ("My Bank");
        Client client = new Client ();
        client.setName ("Ivan Ivanov");
        client.setCity ("Kiev");
        client.setEmail("Aleksander@gmail.com");
        // Add some fields from Client
        // Marked as @ NoDB, with different values
        // For client and client2
        client.addAccount (new CheckingAccount());
        try {
            bank1.addClient (bank1,client);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        bank2 = new Bank();
        bank2.setId (2);
        bank2.setName ("My Bank");
        Client client2 = new Client ();
        client2.setName ("Ivan Ivanov");
        client2.setCity ("Kiev");
        client2.setEmail("someMail@gmail.com");
        client2.addAccount (new CheckingAccount ());
        try {
            bank2.addClient (bank2, client2);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEquals () {
        assertTrue(TestService.isEquals(bank1, bank2));
    }
}
