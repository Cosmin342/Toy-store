import java.util.*;

public class CurrencyUpdater {
    //Metoda ce schimba paritatea la euro a unei monede din store
    public static void updateParity(String command, Store store){
        List<String> parameters = CommandProcessor.allParameters(command);
        String name = null, parity = null;
        int i = 0;
        //Se extrag numele monedei si noua paritate din lista de parametri
        for (String parameter:parameters){
            if (i == 0){
                name = parameter;
                i++;
                continue;
            }
            parity = parameter;
        }
        //Se converteste in double paritatea
        double par = Helper.convertPrice(parity);
        //Se seteaza noua paritate monedei cautate
        try {
            store.setParityToCurrency(name, par);
        }
        //Daca moneda nu este gasita, se afiseaza un mesaj corespunzator
        catch (CurrencyNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    //Metoda ce adauga un nou currency in magazin
    public static void addCurrency(String command, Store store) {
        List<String> parameters = CommandProcessor.allParameters(command);
        String name = null, symbol = null;
        double price = 0;
        int i = 0;
        //Se extrag numele, simbolul si paritatea catre euro
        for (String parameter:parameters){
            if (i == 0){
                name = parameter;
            }
            if (i == 1){
                symbol = parameter;
            }
            if (i == 2){
                price = Helper.convertPrice(parameter);
            }
            i++;
        }
        //Se creeaza un nou currency, pornind de la datele extrase anterior si se adauga in store
        Currency currency = new Currency(name, symbol, price);
        store.addCurrency(currency);
    }

    //Metoda cauta un currency in store si il seteaza ca moneda actuala pentru afisarea preturilor
    public static void setStoreCurrency(String command, Store store) {
        String name = CommandProcessor.firstParameter(command);
        store.findAndSetCurrency(name);
    }
}
