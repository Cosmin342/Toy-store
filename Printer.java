import java.util.List;

public class Printer {
    //Metoda showProducts arata informatii despre produsele cu un anumit producator
    public static void showProducts(String command, Store store) {
        //Se extrage numele producatorului
        String manufacturer = CommandProcessor.manufacturer(command);
        //Se creaza un nou producator cu numele extras anterior
        Manufacturer manuf = new Manufacturer(manufacturer);
        //Se obtine lista produselor care au producatorul manuf
        List<Product> products = store.getProductsByManufacturer(manuf);
        for (Product p:products){
            //Pentru fiecare produs din lista se afiseaza date despre acesta
            System.out.println(p.information(store.getCurrencySymbol()));
        }
    }

    //Metoda care afiseaza detaliile despre un produs cautat dupa id
    public static void showProduct(String command, Store store){
        //Se extrage id-ul din comanda
        String id = CommandProcessor.firstParameter(command);
        //Se cauta produsul dupa id
        Product product = store.findProduct(id);
        if (product == null){
            //Daca produsul nu a putut fi gasit, se afiseaza un mesaj corespunzator
            System.out.println("Produsul cu ID-ul " + id + " nu a putut fi gasit");
            return;
        }
        //Altfel, se afiseaza detaliile despre produs
        System.out.println(product.information(store.getCurrencySymbol()));
    }
}
