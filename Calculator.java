import java.util.*;

public class Calculator {
    public static void calculate(String command, Store store) {
        List<Product> products = new ArrayList<Product>();
        //Se extrage lista cu id-urile produselor
        List<String> productsNames = CommandProcessor.allParameters(command);
        for (String param:productsNames){
            //Se cauta fiecare produs in magazin si se adauga in lista de produse
            Product product = store.findProduct(param);
            if (product != null) {
                products.add(product);
            }
        }
        //Se apeleaza metoda calculateTotal din store pentru a determina totalul
        double total = store.calculateTotal(products);
        //Este afisat totalul obtinut + simbolul monedei curente
        System.out.println(store.getCurrencySymbol() + total);
    }
}
