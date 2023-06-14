package hla13.projektMSK.client;

import java.util.Random;

public class Client {
    int timeToNext;
    private Random random;

    public Client() {
        random = new Random();
        timeToNext = generateTimeToNext();
    }

    public int produce()
    {
        timeToNext=generateTimeToNext();
        int count = random.nextInt(4)+1;
        System.out.println("Do kas przybyło " + count + " klientów. Kolejni klienci przybędą za: " + timeToNext);
        return count;
    }

    public int getTimeToNext() {
        return timeToNext;
    }

    private int generateTimeToNext()
    {
        return random.nextInt(8)+3;
    }


}
