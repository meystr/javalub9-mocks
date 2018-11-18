package com.demo.camera;

public class PhotoCamera implements WriteListener {

    private boolean status;
    private ImageSensor imageSensor;
    private Card card;
    byte[] data = new byte[1];

    public PhotoCamera(ImageSensor imageSensor) {
        this.imageSensor = imageSensor;
    }

    public PhotoCamera(ImageSensor imageSensor,Card card) {
        this.imageSensor = imageSensor;
        this.card = card;
    }

    public void turnOn() {

        imageSensor.turnOn();
        status = true;
    }

    public void turnOff() {
        imageSensor.turnOff();
        status = false;
    }

    public void pressButton() {
        if (status) {
            imageSensor.read();
            card.write(data);
        } else {
            return;
        }
    }


    @Override
    public void writeCompleted() {

    }

}

