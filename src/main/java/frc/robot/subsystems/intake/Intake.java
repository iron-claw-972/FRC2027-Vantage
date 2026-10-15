package frc.robot.subsystems.intake;

import org.wpilib.command2.SubsystemBase;
import org.wpilib.math.controller.PIDController;
import org.wpilib.math.util.MathUtil;
import org.wpilib.math.util.Units;

public class Intake extends SubsystemBase {
    private final IntakeIO io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
    private final double positionTolerance = 5;
    private boolean hasCoral = false;
    private boolean isMoving = false;
    private PIDController stowPID = new PIDController(0.015, 0, 0);
    private double startPosition = 90;


    public Intake(IntakeIO io) {
        this.io = io;

        io.setPivotMotorPosition(Units.degreesToRotations(startPosition) * 69); // IntakeConstants.PIVOT_GEAR_RATIO = 69
        stowPID.setTolerance(positionTolerance);
        setAngle(startPosition);
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
    }

    public double getPivotAngle() {
        return inputs.measuredPivotPosition;
    }

    public PIDController getPID() {
        return stowPID;
    }

    public boolean hasCoral() {
        return hasCoral;
    }

    public boolean isMotorAtSetpoint(double setpoint) {
        return Math.abs(getPivotAngle() - setpoint) < positionTolerance;
    }

    public boolean isIntakeAtSetpoint() {
        return stowPID.atSetpoint();
    }

    public void setAngle(double angle) {
        stowPID.setSetpoint(angle);
    }

    public void setSpeed(double power) {
        io.setRollerMotorThrottle(power);
        isMoving = Math.abs(power) < 0.01;
    }

    public void stow() {
        stowPID.setSetpoint(10); // IntakeConstants.Stowsetpoint
    }

    public void unstow() {
        stowPID.setSetpoint(0); // IntakeConstants.IntakeSetpoint
    }

    public void deactivate() {
        io.setRollerMotorThrottle(0);
    }

    public void activate() {
        io.setRollerMotorThrottle(10); // IntakeConstants.INTAKE_MOTOR_POWER
    }
}
