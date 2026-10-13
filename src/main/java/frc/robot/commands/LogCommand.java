package frc.robot.commands;

import org.wpilib.command2.Command;

/// Command for logging stuff from like util classes or smth that i don't wanna put anywhere else
public class LogCommand extends Command {
  @SuppressWarnings("unused")
  private boolean hubActive = false;

  public LogCommand() {}

  @Override
  public void execute() {
    return;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
