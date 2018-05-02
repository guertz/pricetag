package com.guerzonica.app.mocks;

import java.util.Vector;
import com.guerzonica.app.models.data.*;

public class SampleProducts {

    // all a reference. So it is not cloned. If the method die, the data die?
    public static Vector<ProductDetails> make() {
        Vector<ProductDetails> x = new Vector<ProductDetails>();

            x.add(new ProductDetails(
                new Product(1, "Motorola Moto G4", "Telefono di ultima generazione con supporto rete 4G/LTE e 4GB Ram"),
                new Vector<History>() {{
                    add(new History("23/04", 100));
                    add(new History("24/04", 110));
                    add(new History("25/04",  98));
                    add(new History("22/04", 120));
                }}
            ));

            x.add(new ProductDetails(
                new Product(2, "LG G6", "Top di gamma di marca LG"),
                new Vector<History>() {{
                    add(new History("18/04", 300));
                    add(new History("19/04", 350));
                    add(new History("23/04", 320));
                    add(new History("24/04", 320));
                }}
            ));

            x.add(new ProductDetails(
                new Product(3, "HP Notebook", "Laptop HP con supporto esteso"),
                new Vector<History>() {{
                    add(new History("29/04", 689));
                    add(new History("30/04", 700));
                    add(new History("01/05", 680));
                    add(new History("02/05", 700));
                }}
            ));

        return x;
    }
}