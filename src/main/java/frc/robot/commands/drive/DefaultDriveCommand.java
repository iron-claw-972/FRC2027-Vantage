package frc.robot.commands.drive;

import org.wpilib.math.controller.PIDController;
import org.wpilib.math.kinematics.ChassisVelocities;
import org.wpilib.driverstation.Alliance;
import org.wpilib.telemetry.Telemetry;
import org.wpilib.telemetry.TelemetryTable;
import org.wpilib.command2.Command;
import frc.robot.Robot;
import frc.robot.constants.swerve.DriveConstants;
import frc.robot.controls.BaseDriverConfig;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.vision.DriverAssist;

/** Default drive command. Drives robot using driver controls. */
public class DefaultDriveCommand extends Command {
  protected final Drivetrain swerve;
  private final BaseDriverConfig driver;
  private PIDController trenchAssistPid = new PIDController(9, 0.0, 3);
  private final TelemetryTable telemetry = Telemetry.getTable("Drivetrain");

  public DefaultDriveCommand(Drivetrain swerve, BaseDriverConfig driver) {
    this.swerve = swerve;
    this.driver = driver;

    addRequirements(swerve);
  }

  @Override
  public void initialize() {
    swerve.setStateDeadband(true);

    trenchAssistPid.setIZone(2);
    trenchAssistPid.setIntegratorRange(-1, 1);

    telemetry.log("0 degrees snap location", 0);
  }

  @Override
  public void execute() {
    double forwardTranslation = driver.getForwardTranslation();
    double sideTranslation = driver.getSideTranslation();
    double rotation = -driver.getRotation();

    double slowFactor = driver.getIsSlowMode() ? DriveConstants.SLOW_DRIVE_FACTOR : 1;

    forwardTranslation *= slowFactor;
    sideTranslation *= slowFactor;
    rotation *= driver.getIsSlowMode() ? DriveConstants.SLOW_ROT_FACTOR : 1;

    int allianceReversal = Robot.getAlliance() == Alliance.RED ? 1 : -1;
    forwardTranslation *= allianceReversal;
    sideTranslation *= allianceReversal;

    ChassisVelocities driverInput =
        new ChassisVelocities(forwardTranslation, sideTranslation, rotation);
    @SuppressWarnings("unused")
    ChassisVelocities corrected =
        DriverAssist.calculate(swerve, driverInput, swerve.getDesiredPose(), true);

    drive(driverInput);
  }

  /**
   * Drives the robot
   *
   * @param speeds The ChassisVelocities to drive at
   */
  protected void drive(ChassisVelocities speeds) {
    // If the driver is pressing the align button or a command set the drivetrain to
    // align, then align to speaker
    if (driver.getIsAlign() || swerve.getIsAlign()) {
      swerve.driveHeading(speeds.vx, speeds.vy, swerve.getAlignAngle(), true);
    } else {
      swerve.drive(speeds.vx, speeds.vy, speeds.omega, true, false);
    }
  }
}
