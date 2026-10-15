package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command2.SubsystemBase;

public class Arm extends SubsystemBase {
    private ArmIO io;
    private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();

    public Arm(ArmIO io) {
        this.io = io;

    }

    public void extendArm(){
        io.rotateArm(2);
    }

    public void retractArm(){
        io.rotateArm(0);
    }

    public double getArmPosition(){
        return inputs.armPositionRots;
    }

    @Override
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Arm", inputs);
    }

}
