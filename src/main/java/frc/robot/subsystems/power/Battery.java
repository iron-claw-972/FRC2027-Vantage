package frc.robot.subsystems.power;

import org.wpilib.command2.SubsystemBase;

import frc.robot.constants.power.BatteryConstants;

public class Battery extends SubsystemBase {
  private double voltage;

  public Battery() {
    updateVoltageFromBattery();
  }

  private void updateVoltageFromBattery() {
    voltage = BatteryManager.getVoltage();
  }

  public double getVoltage() {
    return voltage;
  }

  public double voltsTillBrownOut() {
    return voltage - BatteryManager.getBrownoutVoltage();
  }

  public double toBrownOut() {
    // percent of volts we've got left over what we had to start with
    return voltsTillBrownOut()
        / (BatteryConstants.MAX_STARTING_VOLTS - BatteryManager.getBrownoutVoltage());
  }

  @Override
  public void periodic() {
    updateVoltageFromBattery();
  }
}
