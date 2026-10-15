package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
    @AutoLog
    public static class IntakeIOInputs {
        public double measuredPivotPosition = 0.0;
    }

    public void updateInputs(IntakeIOInputsAutoLogged inputs);

    public void setPivotMotorPosition(double position);
    public void setRollerMotorThrottle(double throttle);
}
