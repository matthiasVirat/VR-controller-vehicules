package com.vehiculerental.controllervehicules.vehicule;

public class Vehicule {

    private String registrationNbr;
    private String make;
    private String model;
    private String colour;
    private int reservationPrice;
    private int pricePerKm;
    private int horsePower;

    public Vehicule() {
    }

    public String getRegistrationNbr() {
        return registrationNbr;
    }

    public void setRegistrationNbr(String registrationNbr) {
        this.registrationNbr = registrationNbr;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getReservationPrice() {
        return reservationPrice;
    }

    public void setReservationPrice(int reservationPrice) {
        this.reservationPrice = reservationPrice;
    }

    public int getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(int pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "registrationNbr='" + registrationNbr + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", colour='" + colour + '\'' +
                ", reservationPrice=" + reservationPrice +
                ", pricePerKm=" + pricePerKm +
                ", horsePower=" + horsePower +
                '}';
    }

}