package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
    @AutoLog
    public static class IntakeIOInputs {
        public double measuredPivotPosition = 0.0;
        /**
         * The units are in ohms.
         */
        public double motorROhms; 
        /**
         * Motor torque constant (units: NM / Amp)
         */
        public double motorKtNMPerAmp;
    }

    public void updateInputs(IntakeIOInputsAutoLogged inputs);

    public void setPivotMotorPosition(double position);
    public void setRollerMotorThrottle(double throttle);
    public void setPivotMotorThrottle(double throttle);
}
