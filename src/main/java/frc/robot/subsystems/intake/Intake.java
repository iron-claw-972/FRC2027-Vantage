package frc.robot.subsystems.intake;

import org.wpilib.command2.SubsystemBase;
import org.wpilib.math.controller.ArmFeedforward;
import org.wpilib.math.controller.PIDController;
import org.wpilib.math.util.Units;

import frc.robot.constants.Constants;

public class Intake extends SubsystemBase {
    private final IntakeIO io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
    private final double positionTolerance = 5;
    private boolean hasCoral = false;
    // private boolean isMoving = false; -> Unused in original code aswell
    private PIDController stowPID = new PIDController(0.015, 0, 0);
    private double startPosition = 90;
    private double power;
    private ArmFeedforward feedforward = new ArmFeedforward(0,
            Constants.GRAVITY_ACCELERATION * IntakeConstants.CENTER_OF_MASS_DIST * IntakeConstants.MASS
                    / IntakeConstants.PIVOT_GEAR_RATIO * inputs.motorR0hms / inputs.motorKtNMPerAmp / Constants.ROBOT_VOLTAGE,
            0);
    // private LaserCan laserCan;

    public Intake(IntakeIO io) {
        this.io = io;

        io.setPivotMotorPosition(Units.degreesToRotations(startPosition) * IntakeConstants.PIVOT_GEAR_RATIO);
        stowPID.setTolerance(positionTolerance);
        setAngle(startPosition);
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        double position = getPivotAngle();
        power = stowPID.calculate(position) + feedforward.calculate(Units.degreesToRadians(position), 0);
        power = Math.clamp(power, -1, 1);
        io.setPivotMotorThrottle(power);
        // if (laserCan != null) {
        //     Measurement measurement = laserCan.getMeasurement();
        //     hasCoral = measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT
        //             && measurement.distance_mm <= 1000 * IntakeConstants.DETECT_CORAL_DIST;
        // }
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
        // isMoving = Math.abs(power) < 0.01; -> isMoving unused in original code
    }

    public void stow() {
        stowPID.setSetpoint(IntakeConstants.STOW_SETPOINT);
    }

    public void unstow() {
        stowPID.setSetpoint(IntakeConstants.INTAKE_SETPOINT);
    }

    public void deactivate() {
        io.setRollerMotorThrottle(0);
    }

    public void activate() {
        io.setRollerMotorThrottle(IntakeConstants.INTAKE_MOTOR_POWER);
    }
}
