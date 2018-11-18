package com.demo.camera;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class PhotoCameraTest {

    @Test
    public void sensorShouldTurnOnAfterCameraIsOn() {
        ImageSensor sensor = mock(ImageSensor.class);
        PhotoCamera camera = new PhotoCamera(sensor);
        camera.turnOn();
        Mockito.verify(sensor).turnOn();
        Mockito.verifyZeroInteractions(sensor);
    }
    @Test
    public void sensorShouldTurnOffAfterCameraIsOff() {
        ImageSensor sensor = mock(ImageSensor.class);
        PhotoCamera camera = new PhotoCamera(sensor);
        camera.turnOff();
        Mockito.verify(sensor).turnOff();
        Mockito.verifyZeroInteractions(sensor);
    }

    @Test
    public void PressingTheShutterIfThePowerIsCutOffDoesNothing() {
        ImageSensor sensor = mock(ImageSensor.class);
        PhotoCamera camera = new PhotoCamera(sensor);
        camera.turnOff();
        Mockito.clearInvocations(sensor);
        camera.pressButton();
        Mockito.verifyZeroInteractions(sensor);

    }

    @Test
    public void PressingTheShutterWithThePowerOnCopiesTheDataFromTheSensorToTheMemorycard() {
        ImageSensor sensor = mock(ImageSensor.class);
        Card card = mock(Card.class);
        PhotoCamera camera = new PhotoCamera(sensor,card);
        byte [] data = "data" .getBytes();
        camera.turnOn();
        camera.pressButton();
        Mockito.when(sensor.read()).thenReturn(data);
        Mockito.verify(card).write(data);

    }
    @Test
    public void IfTheDataIsCurrentlySavingTurningTheCameraOffDoesNotCutOffThePowerSupplyToTheSensor() {
        ImageSensor sensor = mock(ImageSensor.class);
        PhotoCamera camera = new PhotoCamera(sensor);
        byte [] data = "data" .getBytes();
        camera.turnOn();
        camera.pressButton();
        Mockito.when(sensor.read()).thenReturn(data);
        Mockito.clearInvocations(sensor);
        camera.turnOff();
        Mockito.verifyZeroInteractions(sensor);
        camera.writeCompleted();
    }


}

