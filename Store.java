import java.io.*;
import java.util.*;
import org.apache.commons.csv.*;

public class Store {
    //currency reprezinta moneda curenta
    private Currency currency;
    //currencies este o lista cu toate monedele disponibile in store
    private List<Currency> currencies;
    //discounts contine lista de discount-uri disponibile pentru aplicare
    private List<Discount> discounts;
    //products contine toate produsele magazinului
    private List<Product> products;
    //manufacturers contine producatorii care au in gestiune produse in magazin
    private List<Manufacturer> manufacturers;
    //Instance reprezinta instanta unica a magazinului
    private static Store INSTANCE;

    public Store() {
    }

    //Metoda care intoarce store-ul
    public static Store getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new Store();
        }
        return INSTANCE;
    }

    //Metoda ce afiseaza numele currency-ului actual
    public void getCurrency() {
        System.out.println(currency.getName());
    }

    //Getter ce intoarce lista de producatori
    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    //Metoda ce intoarce o lista cu produsele getionate de un anumit producator
    public List<Product> getProductsByManufacturer(Manufacturer manufacturer){
        List<Product> filtredProducts = new ArrayList<Product>();
        for (Product p:products){
            //Pentru fiecare produs se compara producatorii
            if (p.getManufacturer().equals(manufacturer)){
                //Daca acestia sunt egali, se va adauga produsul in lista noua
                filtredProducts.add(p);
            }
        }
        return filtredProducts;
    }

    //Metoda ce intoarce simbolul unei monede
    public String getCurrencySymbol(){
        return currency.getSymbol();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    //Metoda ce schimba paritatea unei monede la euro
    public void setParityToCurrency(String name, Double parity) throws CurrencyNotFoundException{
        for (Currency c:currencies){
            //Se verifica doar numele monezii
            if (c.getName().equals(name)){
                //Daca actuala valuta este cea setata pe store, se vor recalcula toate
                //preturile
                if (c.getName().equals(currency.getName())){
                    double oldParity = this.currency.getParityToEur();
                    if (products != null) {
                        for (Product p : products) {
                            p.setPrice((p.getPrice() / oldParity) *  parity);
                        }
                    }
                }
                //Se schimba paritatea
                c.updateParity(parity);
                return;
            }
        }
        //Daca moneda nu e gasita in store, se va arunca o exceptie
        throw new CurrencyNotFoundException("Moneda " + name + " nu a putut fi gasita in magazin");
    }

    //Metoda pentru adaugarea unui nou currency
    public void addCurrency(Currency currency){
        if (currencies == null){
            //Se initializeaza lista de currency-uri daca este nula
            currencies = new ArrayList<>();
        }
        currencies.add(currency);
    }

    //Metoda ce adauga un nou discount in store
    public void addDiscount(Discount discount) {
        //Se va seta ca fiind aplicat in momentul adaugarii
        discount.setAsAppliedNow();
        if (discounts == null){
            discounts = new ArrayList<Discount>();
        }
        discounts.add(discount);
    }

    //Metoda ce adauga un nou manufacturer in lista manufacturers
    public void addManufacturer(Manufacturer manufacturer) throws DuplicateManufacturerException{
        if (manufacturers == null){
            manufacturers = new ArrayList<Manufacturer>();
        }
        //Intai se va verifica daca producatorul curent exista in lista
        for (Manufacturer m:manufacturers){
            if (m == null){
                break;
            }
            //Daca exista, se va actualiza numarul de produse in gestiunea acestuia
            //si se va arunca o exceptie
            if (m.equals(manufacturer)){
                int count = m.getCountProducts();
                count++;
                m.setCountProducts(count);
                throw new DuplicateManufacturerException("Producatorul " + manufacturer.getName() +
                        " exista deja in magazin");
            }
        }
        //Daca nu exista deja in lista, se va adauga
        manufacturers.add(manufacturer);
    }

    //Metoda utilizata la adaugarea in manufacturers a producatorilor ce au
    //in gestiune produse
    public void addManufacturers(){
        for (Product p:products){
            try {
                //Se incearca adugarea producatorului produsului curent
                this.addManufacturer(p.getManufacturer());
            }
            //Daca se intalneste exceptie, se afiseaza mesajul acesteia
            catch (DuplicateManufacturerException e){
                System.out.println(e.getMessage());
            }
        }
    }

    //Metoda pentru adaugarea unui produs in lista de produse
    public void addProduct(Product product) throws DuplicateProductException {
        //Intai se verifica daca lista a fost initializata
        if (products == null){
            products = new ArrayList<Product>();
        }
        //Apoi se verifica daca produsul exista deja in lista
        for (Product p:products){
            if (p == null){
                break;
            }
            //Daca deja exista, se va arunca o exceptie
            if (p.equals(product)){
                throw new DuplicateProductException("Produsul " +
                        product.information(currency.getSymbol()) +" deja exista");
            }
        }
        //Se adauga produsul daca nu exista deja in lista
        products.add(product);
    }

    //Metoda ce afiseaza toti producatorii din magazin
    public void listManufacturers(){
        //Mai intai, se va sorta lista in ordine lexicografica
        for (int i = 0; i < manufacturers.size() - 1; i++) {
            for (int j = i + 1; j < manufacturers.size(); j++) {
                //Daca producatorul de pe pozitia i e mai mare decat cel de pe j,
                //acestia se vor interschimba
                if (manufacturers.get(i).getName().compareTo(manufacturers.get(j).getName()) > 0){
                    Manufacturer aux = manufacturers.remove(i);
                    manufacturers.add(i, manufacturers.remove(j - 1));
                    manufacturers.add(j, aux);
                }
            }
        }
        //Apoi se parcurge vectorul sortat si se afiseaza numele producatorilor
        for (Manufacturer m:manufacturers){
            System.out.println(m.getName());
        }
    }

    //Metoda ce permite afisarea tuturor produselor din magazin
    public void listProducts(){
        for (Product p:products){
            System.out.println(p.information(getCurrencySymbol()));
        }
    }

    //Metoda ce permite afisarea tuturor valutelor din magazin
    public void listCurrencies() {
        for (Currency c:currencies){
            System.out.println(c.getName() + " " + c.getParityToEur());
        }
    }

    //Metoda care cauta in lista de produse un produs dupa id-ul sau
    public Product findProduct(String id){
        for (Product p:products){
            //In cazul in care il gaseste, il returneaza
            if (p.getUniqueId().equals(id)){
                return p;
            }
        }
        //Altfel, intoarce null
        return null;
    }

    //Metoda ce listeaza toate discount-urile de pe store
    public void listDiscounts() {
        if (discounts == null){
            return;
        }
        for (Discount d:discounts){
            //Se va afisa intai tipul discount-ului, in functie de discountType
            if (d.getDiscountType() == DiscountType.FIXED_DISCOUNT){
                System.out.print("FIXED_DISCOUNT ");
            }
            else {
                System.out.print("PERCENTAGE_DISCOUNT ");
            }
            //Apoi se vor afisa apoi valoarea si numele discount-ului
            System.out.println(d.getValue() + " \"" + d.getName() + "\" "
                    + d.getLastDateApplied());
        }
    }

    //Metoda ce schimba valuta utilizata de magazin
    public void changeCurrency(Currency currency) throws CurrencyNotFoundException{
        //Daca valuta primita ca parametru e nula, se arunca o exceptie
        if (currency == null){
            throw new CurrencyNotFoundException("Moneda nu exista in magazin");
        }
        //Se retine vechea paritate
        double oldParity = this.currency.getParityToEur();
        //Se seteaza noua moneda
        this.currency = currency;
        if (products != null) {
            //In final, se vor recalcula preturile tuturor produselor
            for (Product p : products) {
                p.setPrice((p.getPrice() / currency.getParityToEur()) * oldParity);
            }
        }
    }

    //Metoda ce cauta in store un currency si il seteaza pentru utilizare
    public void findAndSetCurrency(String name){
        //Currency-ul va avea valoarea null daca nu se gaseste un currency
        //cu numele cautat
        Currency currency = null;
        //Se va cauta in vectorul currencies
        for (Currency c:currencies){
            if (c.getName().equals(name)){
                currency = c;
                break;
            }
        }
        //Se incearca schimbarea currency-ului default
        try {
            changeCurrency(currency);
        }
        //Daca se intoarce exceptie, se va afisa un mesaj corespunzator
        catch (CurrencyNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    //Metoda care cauta un discount si incearca sa il aplice tuturor produselor
    public void findAndApply(String type, Double value){
        DiscountType discountType;
        //Daca tipul este PERCENTAGE, se seteaza discountType la PERCENTAGE_DISCOUNT
        if (type.equals("PERCENTAGE")){
            discountType = DiscountType.PERCENTAGE_DISCOUNT;
        }
        //Altfel, se seteaza la FIXED_DISCOUNT
        else {
            discountType = DiscountType.FIXED_DISCOUNT;
        }
        Discount discount = null;
        //Se cauta in lista de discount-uri dupa tip si valoare
        for (Discount d:discounts){
            if ((d.getDiscountType().equals(discountType)) && (d.getValue() == value)){
                discount = d;
                break;
            }
        }
        //Se incearca aplicarea discount-ului la toate produsele
        try {
            this.applyDiscount(discount);
        }
        //Daca discont-ul nu exista in store, se afiseaza un mesaj
        catch (DiscountNotFoundException e){
            System.out.println(e.getMessage());
        }
        //Daca in urma aplicarii discount-ului un pret al unui produs devine negativ,
        //se va afisa un mesaj si se va sterge discount-ul de pe toate produsele
        catch (NegativePriceException e){
            System.out.println(e.getMessage());
            for (Product product:products){
                product.setDiscount(null);
            }
        }
    }

    //Metoda care aplica un discount tuturor produselor
    public void applyDiscount(Discount discount) throws DiscountNotFoundException, NegativePriceException {
        //Daca discount-ul primit ca parametru e null, se arunca o exceptie
        if (discount == null){
            throw new DiscountNotFoundException("Discount-ul nu este disponibil");
        }
        //Se va aplica discount-ul fiecarui produs
        for (Product p:products){
            //Se extrage pretul pentru a se verifica daca acesta ar deveni negativ
            double value = p.getPrice();
            if (discount.getDiscountType() == DiscountType.FIXED_DISCOUNT){
                value -= discount.getValue();
            }
            else {
                value = value - (discount.getValue() * value) / 100;
            }
            //Daca in urma discount-ului se obtine o valoare negativa, se arunca o exceptie
            if (value < 0){
                throw new NegativePriceException("Discount-ul " + discount.getName() +
                        " nu poate fi aplicat tuturor produselor");
            }
            //Altfel, se aplica discount-ul
            p.setDiscount(discount);
        }
        //Se actualizeaza data de aplicare
        discount.setAsAppliedNow();
    }

    //Metoda ce calculeaza totalul unor produse dintr-o lista
    public double calculateTotal(List<Product> product){
        double total = 0;
        for (Product p:product){
            //Pentru fiecare produs din lista se extrage pretul in value
            double value = p.getPrice();
            //Daca produsul are un discount aplicat, se va aplica discount-ul
            //asupra value
            if (p.getDiscount() != null){
                Discount discount = p.getDiscount();
                //Daca este de tip fix, se scade din value valoarea discount-ului
                if (discount.getDiscountType().equals(DiscountType.FIXED_DISCOUNT)){
                    value -= discount.getValue();
                }
                //Altfel, se elimina un procent din value
                else {
                    value = value - (value * discount.getValue()) / 100;
                }
            }
            //Indiferent daca se aplica un discount sau nu, se aduna la total value
            total += value;
        }
        return total;
    }

    //Metoda ce permite citirea listei de produse dintr-un fisier
    List<Product> readCSV(String filename){
        //In products se va retine lista produselor
        List<Product> products = null;
        //file va fi dolosit la citire
        FileReader file = null;
        try {
            //Se incearca initializarea unui nou FileReader pe fisierul dat
            //ca parametru
            file = new FileReader(filename);
        }
        //Daca fisierul nu este gasit, se va afisa o eroare
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try {
            //Se va initializa un CSVParser pentru citirea dintr-un fisier CSV
            CSVParser csvParser = null;
            if (file != null) {
                csvParser = new CSVParser(file, CSVFormat.DEFAULT);
            }
            //Se creeaza lista de produse prin citirea din csv
            products = createProducts(csvParser);
        }
        //Daca nu se initializeaza CSVParser-ul, se printeaza o eroare
        catch (IOException e){
            e.printStackTrace();
        }
        //Se intoarce lista de produse
        return products;
    }

    private List<Product> createProducts(CSVParser csvParser) {
        List<Product> products = new ArrayList<Product>();
        int ind = 0;
        for (CSVRecord csvRecord: csvParser){
            //Daca ind e 0, se ignora prima linie
            if (ind == 0){
                ind = 1;
                continue;
            }
            //Se extrag pe rand id-ul, numele, numele producatorului, pretul si nr de produse
            //aflate in stock
            String uniqId = csvRecord.get(0);
            String productName = csvRecord.get(1);
            String manufacturerName = csvRecord.get(2);
            //Daca numele producatorului nu este dat, se seteaza ca fiind indisponibil
            if (manufacturerName.equals("")){
                manufacturerName = "Not available";
            }
            Manufacturer manufacturer = new Manufacturer(manufacturerName);
            //Daca pretul nu exista, se ignora produsul
            if (csvRecord.get(3).equals("")){
                continue;
            }
            //Se converteste pretul in double si se extrage si nr de produse
            double price = Math.round(Helper.processPrice(csvRecord.get(3)) * 100.00) / 100.00;
            int numberAvailableInStock = Helper.processNumberAvailableInStock(csvRecord.get(4));
            //Se construieste un nou produs si se adauga in lista de produse
            Product product = new ProductBuilder()
                    .withManufacturer(manufacturer)
                    .withName(productName)
                    .withUniqueId(uniqId)
                    .withPrice(price)
                    .withQuantity(numberAvailableInStock)
                    .build();
            products.add(product);
        }
        return products;
    }

    //Metoda ce permite salvarea listei de produse intr-un fisier csv
    public void saveCSV(String filename){
        try {
            //Se initializeaza un nou filewriter plecand de la numele fisierului
            FileWriter file = new FileWriter(filename);
            //Se initializeaza un nou CSVPrinter pentru a scrie in csv
            CSVPrinter csvPrinter = new CSVPrinter(file, CSVFormat.DEFAULT);
            //Mai intai se va scrie prima coloana unde sunt numele campurilor
            csvPrinter.printRecord("uniq_id", "product_name", "manufacturer",
                    "price", "number_available_in_stock");
            //Apoi, pentru fiecare produs, se vor printa informatiile corespunzator
            //coloanei
            for (Product p:products){
                double price = p.getPrice();
                //Daca produsul are un discount aplicat, se va afisa pretul obtinut in urma
                //discount-ului
                if (p.getDiscount() != null){
                    if (p.getDiscount().getDiscountType() == DiscountType.FIXED_DISCOUNT){
                        price -= p.getDiscount().getValue();
                    }
                    else {
                        price -= (p.getDiscount().getValue() / 100) * price;
                    }
                }
                csvPrinter.printRecord(p.getUniqueId(), p.getName(), p.getManufacturer().getName(),
                        getCurrencySymbol() + String.format("%.2f", price),
                        p.getQuantity() + "\u00a0NEW");
                csvPrinter.flush();
            }
        }
        //Daca nu se reuseste initializarea CSVPrinter-ului sau FileWriter-ului,
        //se va afisa o eroare
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //Metoda ce permite salvarea starii actuale a magazinului in fisierul state.dat
    public void saveStore(){
        try {
            //Datele vor fi salvate folosind fileoutputstream in fisierul state.dat
            FileOutputStream out = new FileOutputStream("state.dat");
            //obj este folosit pentru scrierea unui obiect
            ObjectOutputStream obj = new ObjectOutputStream(out);
            //Prima data va fi scris currency-ul curent
            obj.writeObject(currency);
            //Apoi se va scrie intreaga lista de discount-uri
            if (discounts != null) {
                for (Discount d : discounts) {
                    obj.writeObject(d);
                }
            }
            //discDelim reprezinta un discount folosit ca delimitator in momentul
            //in care se va incarca salvarea din fisier
            Discount discDelim = new Discount("DEL", DiscountType.FIXED_DISCOUNT, 0);
            obj.writeObject(discDelim);
            //La final va fi scrisa si lista de produse
            for (Product p:products){
                obj.writeObject(p);
            }
            //Ca in cazul discount-urilor, se va crea si aici un produs delimitator
            Product prodDelim = new ProductBuilder()
                    .withName("DEL")
                    .build();
            obj.writeObject(prodDelim);
            obj.close();
            out.close();
        }
        //In cazul in care se intalneste o exceptie, se va afisa eroarea corespunzatoare
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Metoda loadstore incarca o salvare mai veche a magazinului (currency-ul default, produsele
    //si discount-urile)
    public void loadStore(){
        try{
            //Datele vor fi citite din fisierul state.dat
            FileInputStream in = new FileInputStream("state.dat");
            //Se va folosi un ObjectInputStream pentru citirea unui obiect odata
            ObjectInputStream obj = new ObjectInputStream(in);
            //Mai intai se extrage vechea valuta default
            this.currency = (Currency) obj.readObject();
            Discount discDelim = new Discount("DEL", DiscountType.FIXED_DISCOUNT, 0);
            //Apoi se vor incarca discount-uri pana este intalnit discDelim
            this.discounts = new ArrayList<Discount>();
            while (true){
                Discount d = (Discount) obj.readObject();
                if (d.equals(discDelim)){
                    break;
                }
                this.discounts.add(d);
            }
            this.products = new ArrayList<Product>();
            //In final, se citesc produse pana la intalnirea produsului cu denumirea DEL
            while (true){
                Product p = (Product) obj.readObject();
                if (p.getName().equals("DEL")){
                    break;
                }
                this.products.add(p);
            }
            obj.close();
            in.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
