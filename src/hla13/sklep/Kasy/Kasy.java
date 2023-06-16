package hla13.sklep.Kasy;

import java.util.Random;

public class Kasy {
    int timeToNext;
    private Random random;

    //liczba klientow do obsluzenia w kasie, zawsze rowna 1 - bo ciezko obsluzyc 2 klientów w jednej kasie na raz :)
    private int clientsToServe=1;

    public Kasy() {
        random = new Random();
        timeToNext = generateTimeToNext();
    }


    public int consume()
    {
        timeToNext= generateTimeToNext();
        int count = clientsToServe;
        System.out.println("Chcę zabrać po jednym kliencie z każdej kolejki. Czas do kolejnej próby pobrania klientów: " + timeToNext);
        return count;
    }

    public int getTimeToNext() {
        return timeToNext;
    }

    //not used
//    private int generateTimeToNextSlow()
//    {
//        return (random.nextInt(5)+1);
//    }


    private int generateTimeToNext()
    {
        return (random.nextInt(2)+1);
    }


}
