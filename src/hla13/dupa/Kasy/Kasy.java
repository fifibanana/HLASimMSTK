package hla13.dupa.Kasy;

import java.util.Random;

public class Kasy {
    int timeToNext;
    private Random random;

    public Kasy() {
        random = new Random();
        timeToNext = generateTimeToNextSlow();
    }


    public int consume()
    {
        timeToNext= generateTimeToNextSlow();
        int count = 1;
        System.out.println("Chcę zabrać po jednym kliencie z każdej kolejki. Czas do kolejnej próby pobrania klientów: " + timeToNext);
        return count;
    }

    public int getTimeToNext() {
        return timeToNext;
    }

    private int generateTimeToNextSlow()
    {
        return (random.nextInt(5)+1);
    }


    private int generateTimeToNextFast()
    {
        return (random.nextInt(2)+1);
    }


}
