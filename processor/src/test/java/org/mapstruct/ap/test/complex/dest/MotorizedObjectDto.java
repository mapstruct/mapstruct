package org.mapstruct.ap.test.complex.dest;

public class MotorizedObjectDto {

    private int maxSpeed;

    private MotorDto motor;

    public MotorizedObjectDto(int maxSpeed) {
        super();
        this.maxSpeed = maxSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public MotorDto getMotor() {
        return motor;
    }

    public void setMotor(MotorDto motor) {
        this.motor = motor;
    }

}
