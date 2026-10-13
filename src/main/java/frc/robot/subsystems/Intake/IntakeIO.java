package frc.robot.subsystems.Intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
    @AutoLog
    public static class IntakeIOInputs {
        public double rollerPositionRot = 0.0;
        public double rollerVelocityRps = 0.0;
        public double rollerStatorCurrentAmps = 0.0;
        public double rollerSupplyCurrentAmps = 0.0;
        public double rollerAppliedVolts = 0.0;
        public double stowPositionRot= 0.0;
        public double stowVelocityRps = 0.0;
        public double stowStatorCurrentAmps = 0.0;
        public double stowSupplyCurrentAmps = 0.0;
        public double stowAppliedVolts = 0.0;
    }

    public void updateInputs(IntakeIOInputs inputs);

    public void stowIntake(double speed);

    public void extendIntake(double speed);

    public void startRollers(double speed);

    public default void stopRollers() {
        startRollers(0.0);
    }

    public default void close() {}
}