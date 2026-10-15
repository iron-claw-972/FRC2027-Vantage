package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.AutoLog;

public interface ArmIO {
    @AutoLog
    public static class ArmIOInputs{
        public double setpointRotations = 0.0;
        public double armPosition = 0.0;
        public double inputVoltage = 0.0;
    }

    public void updateInputs(ArmIOInputsAutoLogged inputs);

    public void setSetpointRotation(double position);

    public void setCurrentLimits(double supplyCurrentLimit, double statorCurrentLimit);


    //public void setCurrentLimits(doub);
}
