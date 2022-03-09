public class ProductBuilder {
    //product reprezinta produsul ce va fi construit
    private Product product = new Product();

    //Metodele urmatoare apeleaza setter-ul corespunzator pentru fiecare
    //variabila membru din product
    public ProductBuilder withUniqueId(String uniqueId){
        product.setUniqueId(uniqueId);
        return this;
    }

    public ProductBuilder withName(String name){
        product.setName(name);
        return this;
    }

    public ProductBuilder withManufacturer(Manufacturer manufacturer){
        product.setManufacturer(manufacturer);
        return this;
    }

    public ProductBuilder withPrice(double price){
        product.setPrice(price);
        return this;
    }

    public ProductBuilder withQuantity(int quantity){
        product.setQuantity(quantity);
        return this;
    }

    //Metoda build intoarce produsul cu atributele setate utilizand metodele
    //anterioare
    public Product build(){
        return product;
    }
}
