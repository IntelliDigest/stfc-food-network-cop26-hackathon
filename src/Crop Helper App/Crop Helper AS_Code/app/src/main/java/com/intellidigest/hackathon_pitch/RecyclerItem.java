package com.intellidigest.hackathon_pitch;

public class RecyclerItem {

    private String crop;
    private String seedName;
    private String price;
    private int image;

    public RecyclerItem(String crop, String seedName, String price, int image){
        this.crop = crop;
        this.seedName = seedName;
        this.price = price;
        this.image = image;
    }

    public String getCrop() { return crop; }

    public String getSeedName() { return seedName; }

    public String getPrice() { return price; }

    public int getImage() {return image;}
}
