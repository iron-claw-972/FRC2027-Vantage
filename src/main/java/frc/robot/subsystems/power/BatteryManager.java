package frc.robot.subsystems.power;

import frc.robot.constants.Constants;
import frc.robot.constants.power.BatteryConstants;
import org.wpilib.system.RobotController;

// Battery helpers with fallbacks for sim or dead HAL readings
public class BatteryManager {

  // Falls back to nominal if HAL gives garbage/zero
  public static double getVoltage() {
    double volts = RobotController.getBatteryVoltage();
    return (volts > 2.0) ? volts : Constants.ROBOT_VOLTAGE;
  }

  public static double getBrownoutVoltage() {
    return BatteryConstants.DEFAULT_BROWNOUT_VOLTS;
  }

  // Voltage ratio capped at 1.0 for feedforward/scaling
  public static double getVoltageScalar() {
    return Math.min(1, getVoltage() / Constants.ROBOT_VOLTAGE);
  }
}
