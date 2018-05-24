class BroadcastDetailsHandler implements MessageHandler {

    // Seek in local data (Read + Send)
    @Override
    public void handle(String blob) {

        System.out.println(blob);
        // Packet<Product> response = Packet.fromJson(blob, Product.typeToken());

        // ProductDetails pp = new ProductDetails(response.getContent());
        //     pp.history.add(new History("22/04", 100));
        //     pp.history.add(new History("23/04",  50));
        //     pp.history.add(new History("25/04", 120));

        // Packet<ProductDetails> request = new Packet<ProductDetails>(response.getUri(), response.getRid(), pp);
        //     Channel.getChannel().sendMessage(Packet.toJson(request, ProductDetails.typeToken()));

    }
}