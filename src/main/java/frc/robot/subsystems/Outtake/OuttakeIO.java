package frc.robot.subsystems.Outtake;

import org.littletonrobotics.junction.AutoLog;

public interface OuttakeIO {
    @AutoLog
    public static class OuttakeIOInputs {
        public double outtakePosition = 0.0;
        public double outtakeVelocity = 0.0;
        public double outtakeStatorCurrentAmps = 0.0;
        public double outtakeSupplyCurrentAmps = 0.0;
        public double outtakeAppliedVolts = 0.0;
    }

    public void updateInputs(OuttakeIOInputs inputs);

    public void startOuttake(double speed);

    public default void stopOuttake() {
        startOuttake(0.0);
    }
} 
