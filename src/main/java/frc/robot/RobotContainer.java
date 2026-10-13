package frc.robot;

import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.Logger;

import com.pathplanner.lib.auto.AutoBuilderException;
import com.pathplanner.lib.commands.PathPlannerAuto;
// TODO: 2027-ALPHA-FIX - Re-enable Choreo when updated
// import choreo.auto.AutoChooser;
// import choreo.auto.AutoFactory;
// import choreo.auto.AutoRoutine;
import org.wpilib.math.geometry.Pose3d;
import org.wpilib.telemetry.Telemetry;
import org.wpilib.telemetry.TelemetryTable;
import org.wpilib.tunable.Selectable;
import org.wpilib.driverstation.Alliance;
import org.wpilib.driverstation.internal.DriverStationBackend;
import org.wpilib.command2.Command;
import org.wpilib.command2.CommandScheduler;
import frc.robot.commands.DoNothing;
import frc.robot.commands.LogCommand;
import frc.robot.commands.drive.DefaultDriveCommand;
// import frc.robot.commands.auto.ChoreoPathCommandBuilder;
import frc.robot.commands.drive.SysIDDriveCommand;
import frc.robot.constants.Constants;
import frc.robot.constants.VisionConstants;
import frc.robot.controls.BaseDriverConfig;
import frc.robot.controls.Operator;
import frc.robot.controls.PS5ControllerDriverConfig;
import frc.robot.util.Motor;
import frc.robot.subsystems.power.BatteryManager;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.drivetrain.GyroIOPigeon2;
import frc.robot.subsystems.vision.DetectedObject;
import frc.robot.subsystems.vision.Vision;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here...
  private Drivetrain drive = null;
  private Vision vision = null;
  // private LED led = null;

  // Controllers are defined here
  private BaseDriverConfig driver = null;
  private Operator operator = null;

  // private EMABreaker breaker = null;

  private final Selectable<Command> autoChooser = new Selectable<>();

  private final TelemetryTable telemetry = Telemetry.getTable("Robot");

  // auto Command selection
  // private final AutoChooser choreoAutoChooser = new AutoChooser();

  // choreo auto factory
  // AutoFactory autoFactory;
  Motor motor;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   *
   * <p>Different robots may have different subsystems.
   */
  public RobotContainer(RobotId robotId) {
    // display the current robot id on Elastic
    telemetry.log("RobotID", robotId.toString());
    telemetry.log("Match Time", 0.0);

    // Filling the Selectable on Elastic

    // dispatch on the robot
    switch (robotId) {
      case TestBed1:
        break;

      case TestBed2:
        break;

      default:

      case PrimeJr: // AKA Valence

      case WaffleHouse: // AKA Betabot

      case SwerveCompetition: // AKA "Vantage"

      case TwinBot:
      case BetaBot: // AKA "Pancake"
        vision = new Vision(VisionConstants.APRIL_TAG_CAMERAS);
      // fall-through

      case Vivace:

      case Phil: // AKA "IHOP"

      case Vertigo: // AKA "French Toast"
        drive = new Drivetrain(vision, new GyroIOPigeon2());
        driver = new PS5ControllerDriverConfig(drive);
        operator = new Operator(drive);

        initChoreo();

        // Detected objects need access to the drivetrain
        DetectedObject.setDrive(drive);

        // SignalLogger.start();
        driver.configureControls();
        operator.configureControls();

        registerCommands();
        // PathGroupLoader.loadPathGroups();

        // initializeAutoBuilder();
        autoChooserInit();

        if (drive != null && driver != null) {
          drive.setDefaultCommand(new DefaultDriveCommand(drive, driver));
          telemetry.log("SysId Characterization", new SysIDDriveCommand(drive));
        }
        break;
      case Default:
        // motor = new Motor();
    }

    // CommandScheduler.getInstance().schedule(new HardstopWarning(hood, intake, turret)); (no more
    // crt for this)
    // This is really annoying so it's disabled
    DriverStationBackend.silenceJoystickConnectionAlert(true);

    CommandScheduler.getInstance().schedule(new LogCommand());
  }

  private void initChoreo() {
    // choreo auto factory init
    /*autoFactory =
        new AutoFactory(
            drive::getPose,
            drive::resetOdometry,
            sample -> drive.setChassisVelocities(sample.getChassisVelocities(), false),
            true,
            drive,
            (trajectory, startOrFinish) -> {
              Logger.recordOutput("Autos/Trajectory", trajectory.getPoses());
              Logger.recordOutput("Autos/StartingOrFinishing", startOrFinish);
            });

    // warmup command for choreo, prevents lag on auto startup
    CommandScheduler.getInstance().schedule(autoFactory.warmupCmd().ignoringDisable(true));
    */
  }

  /** Sets whether the drivetrain uses vision toupdate odometry */
  public void setVisionEnabled(boolean enabled) {
    if (drive != null) drive.setVisionEnabled(enabled);
  }

  public void initializeAutoBuilder() {
    // AutoBuilder.configure(
    //     () -> drive.getPose(),
    //     (pose) -> {
    //       drive.resetOdometry(pose);
    //     },
    //     () -> drive.getChassisVelocities(),
    //     (chassisSpeeds) -> {
    //       Logger.recordOutput("Auto/ChassisVelocities", chassisSpeeds);
    //       drive.setChassisVelocities(chassisSpeeds, false); // problem??
    //     },
    //     AutoConstants.AUTO_CONTROLLER,
    //     AutoConstants.CONFIG,
    //     getAllianceColorBooleanSupplier(),
    //     drive);
  }

  public void registerCommands() {}

  public void addAuto(String name) {
    try {
      Command auto = new PathPlannerAuto(name);
      autoChooser.add(name, auto);
    }
    // is this the right one??
    catch (AutoBuilderException e) {
      e.printStackTrace();
      System.out.println("HELLOOOO AUTO \"" + name + "\" NOT FOUND");
    }
  }

  public void addAuto(String name, Command auto) {
    try {
      autoChooser.add(name, auto);
    } catch (AutoBuilderException e) {
      e.printStackTrace();
      System.out.println("HELLOOOO AUTO \"" + name + "\" NOT FOUND");
    }
  }

  /*public void addChoreoAuto(String name, AutoRoutine auto) {
    choreoAutoChooser.addCmd(name, auto::cmd);
  }
    */

  /**
   * Initialize the SendableChooser on Elastic. Fill the SendableChooser with available Commands.
   */
  public void autoChooserInit() {
    // add the options to the Chooser

    autoChooser.addDefault("Default", getDefaultAuto());

    // DynamicAutoBuilder dynamicAutoBuilder = new DynamicAutoBuilder();

    // names
    // String leftDynamicLiberalDoubleSwipe = "LeftDynamicDoubleLiberalSwipe";
    // String rightDynamicLiberalDoubleSwipe = "RightDynamicDoubleLiberalSwipe";
    // String leftDynamicConservativeDoubleSwipe = "LeftDynamicDoubleConservativeSwipe";
    // String rightDynamicConservativeDoubleSwipe = "RightDynamicDoubleConservativeSwipe";
    // String leftDynamicShallowDoubleSwipe = "LeftDynamicShallowDoubleSwipe";
    // String rightDynamicShallowDoubleSwipe = "RightDynamicShallowDoubleSwipe";
    // TODO: 2027-ALPHA-FIX - Re-enable Choreo vendordep when JSON is updated
    // ChoreoPathCommandBuilder choreo = new ChoreoPathCommandBuilder();

    // addAuto("testChoreo", ChoreoPathCommandBuilder.basicTrajectoryAuto("test.traj", true,
    // autoFactory));

    // put the Chooser on Elastic
    telemetry.log("Auto chooser", autoChooser);
    // telemetry.log("Choreo auto chooser", choreoAutoChooser);
  }

  public static BooleanSupplier getAllianceColorBooleanSupplier() {
    return () -> {
      // Boolean supplier that controls when the path will be mirrored for the red
      // alliance
      // This will flip the path being followed to the red side of the field.
      // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

      var alliance = DriverStationBackend.getAlliance();
      if (alliance.isPresent()) {
        return alliance.get() == Alliance.RED;
      }
      return false;
    };
  }

  public boolean brownout() {
    return BatteryManager.getVoltage() < BatteryManager.getBrownoutVoltage();
  }

  public Command getDefaultAuto() {
    return new DoNothing();
  }

  // public Command getAutoCommand() {
  //   return autoChooser.getSelected();
  //   return choreoAutoChooser.selectedCommand();
  // }

  public void logComponents() {
    if (!Constants.LOG_MECHANISMS) return;

    Logger.recordOutput(
        "ComponentPoses",
        new Pose3d[] {
          // Subsystem Pose3ds
        });
  }

  public void periodic() {}
}
