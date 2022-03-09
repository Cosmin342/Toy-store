import java.util.*;

public class Main {
    //Functie ce permite deschiderea magazinului prin citirea produselor acestuia
    public static void openStore(String filename, Store store){
        //Se citesc produsele din fisierul csv dat ca parametru
        List<Product> products = store.readCSV(filename);
        //Pentru fiecare produs se incearca adaugarea acestuia in lista de produse
        //a magazinului
        for (Product product:products){
            try {
                store.addProduct(product);
            }
            //Daca exista un produs duplicat, se afiseaza detalii despre acesta
            catch (DuplicateProductException e){
                System.out.println(e.getMessage());
            }
        }
        //Se adauga producatorii produselor in magazin
        store.addManufacturers();
    }

    public static void main(String[] args) {
        //Pentru citirea de la input se utilizeaza clasa Scanner
        Scanner scanner = new Scanner(System.in);
        Store store = Store.getINSTANCE();
        //Moneda initiala a magazinului va fi Euro
        Currency currency = new Currency("EUR", "\u20ac", 1.00);
        //Moneda este adaugata si este setata ca activa
        store.addCurrency(currency);
        store.setCurrency(currency);
        while (true) {
            String command = scanner.nextLine();
            //Se obtine numarul comenzii de executat
            int commandNumber = CommandProcessor.commandNumber(command);
            switch (commandNumber){
                case 1:
                    //Magazinul este initializat
                    String filename = CommandProcessor.firstParameter(command);
                    openStore(filename, store);
                    break;
                case 2:
                    //Sunt salvate detalii despre produsele din magazin
                    String out = CommandProcessor.firstParameter(command);
                    store.saveCSV(out);
                    break;
                case 3:
                case 4:
                    //In cazul 3 si 4, va fi oprita executia programului
                    return;
                case 5:
                    //Se afiseaza toti producatorii din magazin sortati
                    store.listManufacturers();
                    break;
                case 6:
                    //Se afizeaza produsele cu un anumit producator
                    Printer.showProducts(command, store);
                    break;
                case 7:
                    //Se afizeaza detalii despre toate produsele din magazin
                    store.listProducts();
                    break;
                case 8:
                    //Se afiseaza detalii despre un anumit produs
                    Printer.showProduct(command, store);
                    break;
                case 9:
                    //Sunt afisate toate monedele disponibile in magazin
                    store.listCurrencies();
                    break;
                case 10:
                    //Este afisata moneda curenta
                    store.getCurrency();
                    break;
                case 11:
                    //Se adauga o noua moneda pe store
                    CurrencyUpdater.addCurrency(command, store);
                    break;
                case 12:
                    //Se actualizeaza paritatea catre euro a unei monede
                    CurrencyUpdater.updateParity(command, store);
                    break;
                case 13:
                    //Se seteaza o anumita moneda pentru utilizarea in momentul actual
                    //pe store
                    CurrencyUpdater.setStoreCurrency(command, store);
                    break;
                case 14:
                    //Se afiseaza discounturile disponibile
                    store.listDiscounts();
                    break;
                case 15:
                    //Se adauga un nou discount
                    DiscountUpdater.addDiscount(command, store);
                    break;
                case 16:
                    //Se aplica (daca este posibil) un discount tutoror produselor din
                    //magazin
                    DiscountUpdater.applyDiscount(command, store);
                    break;
                case 17:
                    //Se calculeaza valoarea totala a unor produse date ca parametru
                    Calculator.calculate(command, store);
                    break;
                case 18:
                    //Se salveaza in fisierul state.dat starea magazinului
                    store.saveStore();
                    break;
                case 19:
                    //Se incarca din fisierul state.dat o stare a magazinului
                    store.loadStore();
                    break;
            }
        }
    }
}
