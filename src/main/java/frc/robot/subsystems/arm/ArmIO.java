package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.AutoLog;

public interface ArmIO {
    @AutoLog
    public static class ArmIOInputs {
        public double armPositionRots = 0.0;
    }
    
    public void rotateArm(double rotation);

    public void updateInputs(ArmIOInputsAutoLogged inputs);
    
}
