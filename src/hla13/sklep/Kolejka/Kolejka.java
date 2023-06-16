package hla13.sklep.Kolejka;


public class Kolejka {

    // define MAX LENGHT OF CLIENTS  = L
    private int maxQLength = 20;

    private int numberOfClietnsInQueueFast;
    private int numberOfClietnsInQueueSlow;
    private int numberOfProductsForClientFast;
    private int numberOfProductsForClientSlow;
    private int maxNumberOfClients;
    private int takeFromFast = 1;
    private int takeFromSlow = 1;

    private static Kolejka instance = null;

    private Kolejka() {
        numberOfClietnsInQueueFast =0;
        numberOfClietnsInQueueSlow =0;
        numberOfProductsForClientFast =0;
        numberOfProductsForClientSlow =0;
        maxNumberOfClients=maxQLength;
    }
    static public Kolejka getInstance()
    {
        if(instance==null) instance = new Kolejka();
        return instance;
    }

    public int getNumberOfClietnsInQueueFast() {
        return numberOfClietnsInQueueFast;
    }

    public int getNumberOfClietnsInQueueSlow() {
        return numberOfClietnsInQueueSlow;
    }

    public int getNumberOfProductsForClientFast() {
        return numberOfProductsForClientFast;
    }

    public int getNumberOfProductsForClientSlow() {
        return numberOfProductsForClientSlow;
    }

    public int getMaxNumberOfClients() {
        return maxNumberOfClients;
    }

    public void setNumberOfClietnsInQueueFast(int numberOfClietnsInQueueFast) {
        this.numberOfClietnsInQueueFast = numberOfClietnsInQueueFast;
    }

    public void setNumberOfClietnsInQueueSlow(int numberOfClietnsInQueueSlow) {
        this.numberOfClietnsInQueueSlow = numberOfClietnsInQueueSlow;
    }

    public void setNumberOfProductsForClientFast(int numberOfProductsForClientFast) {
        this.numberOfProductsForClientFast = numberOfProductsForClientFast;
    }

    public void setNumberOfProductsForClientSlow(int numberOfProductsForClientSlow) {
        this.numberOfProductsForClientSlow = numberOfProductsForClientSlow;
    }

    public void setMaxNumberOfClients(int maxNumberOfClients) {
        this.maxNumberOfClients = maxNumberOfClients;
    }

    public boolean addTo(int count, int noOfProducts)
    {
        if(this.numberOfClietnsInQueueFast + numberOfClietnsInQueueSlow +count<=this.maxNumberOfClients) {
            if(count >= 5){
                //this.numberOfClietnsInQueueB += count;
                this.numberOfClietnsInQueueSlow += 1;
                System.out.println("Kolejka: Dodaję klienta do kolejki Slow");
            }
            else{
                this.numberOfClietnsInQueueFast += 1;
                System.out.println("Kolejka: Dodaję klienta do kolejki Fast");
            }
            System.out.println("Kolejka: Przybył klient. Aktualna liczba klientów: ");
            System.out.println("W kolejce Fast: " + this.numberOfClietnsInQueueFast);
            System.out.println("W kolejce Slow: " + this.numberOfClietnsInQueueSlow);

            return true;
        }
        else
        {
            System.out.println("Kolejka: Nie ma miejsca w kolejce dla " + count + " klientów");
            return false;
        }
    }

    public boolean addProductsTo(int count)
    {
        if(numberOfProductsForClientFast == 0 && takeFromFast == 1){
            numberOfProductsForClientFast = count%5 + 1;
            takeFromFast = 0;
        }
        if(numberOfProductsForClientSlow == 0 && takeFromSlow == 1){
            numberOfProductsForClientSlow = count%5 + 6;
            takeFromSlow = 0;
        }

        return true;
    }

    public boolean getFrom(int count)
    {
        boolean isTrue = false;
        if(numberOfClietnsInQueueFast -count>=0) {
            if(numberOfProductsForClientFast == 0){
                this.numberOfClietnsInQueueFast -=count;
                System.out.println("Kolejka: Właśnie obsłużono klienta w kolejce Fast. Aktualnie w kolejce Fast jest "+ this.numberOfClietnsInQueueFast + " klientów");
                isTrue = true;
                takeFromFast = 1;
            }
            else if(numberOfProductsForClientFast >0){
                numberOfProductsForClientFast--;
                System.out.println("Kolejka: Właśnie skasowano produkt klienta w kolejce Fast. Aktualnie liczba produktów klienta w kolejce Fast wynosi: "+ this.numberOfProductsForClientFast + " produktów");
                takeFromFast = 0;
            }

        }
        else if(numberOfClietnsInQueueFast -count<0)
        {
            System.out.println("Kolejka: W kolejce Fast nie ma klientów do obsłużenia.");
            isTrue = false;
        }
        if(numberOfClietnsInQueueSlow -count>=0) {
            if(numberOfProductsForClientSlow == 0){
                this.numberOfClietnsInQueueSlow -=count;
                System.out.println("Kolejka: Właśnie obsłużono klienta w kolejce Slow. Aktualnie w kolejce Slow jest "+ this.numberOfClietnsInQueueSlow + " klientów");
                isTrue = true;
                takeFromSlow = 1;
            }
            else if(numberOfProductsForClientSlow >0){
                numberOfProductsForClientSlow--;
                System.out.println("Kolejka: Właśnie skasowano produkt klienta w kolejce Slow. Aktualnie liczba produktów klienta w kolejce Slow wynosi: "+ this.numberOfProductsForClientSlow + " produktów");
                takeFromSlow = 0;
            }
        }
        else if(numberOfClietnsInQueueSlow -count<0)
        {
            System.out.println("Kolejka: W kolejce Slow nie ma klientów do obsłużenia.");
            isTrue = false;
        }
        return isTrue;

    }
}
