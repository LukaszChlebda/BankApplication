import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.server.*;
import com.luxoft.bankapp.service.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by LChlebda on 2016-01-14.
 */
public class BankServerThreadedTest {

    List<Thread> t = new ArrayList<>();
    int loopIteration = 1000;
    float amount, amount1;
    static Bank bank;



    @Before
    public void prepereTest() {
        BankService bService = new BankServiceImpl();
        bank = initialize(bService);
        try {
            amount1 = bank.getClient("Lukasz").getAccounts().get(0).getBalance();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
        BankServerThreaded bankServerThreaded = new BankServerThreaded(bank);
        Thread server = new Thread(bankServerThreaded);
        server.start();
    }


    @Test
    public void testMockClient() {
        for (int i = 0; i < loopIteration; i++) {
            t.add(new Thread(new BankClientMock()));

        }

        for (int i = 0; i < loopIteration; i++) {
            t.get(i).start();
            try {
                t.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        try {
            amount = bank.getClient("Lukasz").getAccounts().get(0).getBalance();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(amount1-1000, amount);
    }

    public static Bank initialize(BankService bService) {

        Client client1 = new Client("Lukasz","Krakow","lukasz@gmail.com","123456789", Gender.MALE,1000);
        Client client2 = new Client("Jarek","Krakow","Jarek@gmail.com","123456789", Gender.MALE,500);
        Client client3 = new Client("Przemek","Warszawa","przemo@gmail.com","123456789", Gender.MALE,200);

        SavingAccount savingAccount = new SavingAccount(5000);
        CheckingAccount checkingAccount = new CheckingAccount(1000);

        Bank bank = new Bank(2, "My Bank");

        try {
            bService.addClient(bank, client1);
        } catch (ClientExistsException e) {
            System.out.println(e.getMessage());
        }
        try {
            bService.addClient(bank, client2);
        } catch (ClientExistsException e) {
            System.out.println(e.getMessage());
        }
        try {
            bService.addClient(bank, client3);
        } catch (ClientExistsException e) {
            System.out.println(e.getMessage());
        }

        Account savingAccount1 = new SavingAccount(52000);
        Account checkingAccount1 = new CheckingAccount(1000,1000);
        bService.addAccount(client1,savingAccount1);
        bService.addAccount(client1, checkingAccount1);
        bService.setActiveAccoutnt(client1, checkingAccount1);
        //-----------------------------------------------------
        Account savingAccount2 = new SavingAccount(1000);
        Account checkingAccount2 = new CheckingAccount(1000);
        bService.addAccount(client2,savingAccount2);
        bService.addAccount(client2, checkingAccount2);
        bService.setActiveAccoutnt(client2, savingAccount2);
        //-----------------------------------------------------
        Account savingAccount3 = new SavingAccount(1000);
        Account checkingAccount3 = new CheckingAccount(1000,1000);
        bService.addAccount(client3,savingAccount3);
        bService.addAccount(client3, checkingAccount3);
        bService.setActiveAccoutnt(client3, checkingAccount3);

        return bank;
    }

}