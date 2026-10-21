package frc.robot.subsystems.arm;


import org.littletonrobotics.junction.Logger;
import org.wpilib.command2.SubsystemBase;

public class Arm extends SubsystemBase{
    private ArmIO io;
    private ArmIOInputsAutoLogged inputs;

    private final double tolerance = 0.01;

    public Arm(ArmIO io){
        this.io=io;
    }

    @Override
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Arm", inputs);
    }

    public void setSetpointRotations(double position){
        io.setSetpointRotation(position);
    }

    public boolean atSetpoint(){
        return  Math.abs(inputs.setpointRotations - inputs.armPosition) < tolerance;
    }
}

