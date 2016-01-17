import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.server.BankServerThreaded;
import com.luxoft.bankapp.service.BankApplication;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.Gender;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by LChlebda on 2016-01-14.
 */
public class BankServerThreadedTest {

    List<Thread> t = new ArrayList<Thread>();
    @Test
    public void testMockClient() {
        float amount = 10000;

        Client client = null;

        for(int i=0; i<5; i++) {
            t.add(new Thread(new BankClientMock("Lukasz", 1f)));
        }

        for (int i = 0; i <5; i ++) {
            t.get(i).start();
        }

        for(int i =0; i < 5; i++) {
            try {
                t.get(i).join ();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double amount2 = 9000;//client.getAccounts().get(0).getBalance();
        assertEquals(amount-1000, amount2,0.1);
        System.out.println("Test");
    }
}

