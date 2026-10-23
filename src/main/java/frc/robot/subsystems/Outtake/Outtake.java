package frc.robot.subsystems.Outtake;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command2.SubsystemBase;

public class Outtake extends SubsystemBase {
    private final OuttakeIO io;
    private final OuttakeIOInputsAutoLogged inputs = new OuttakeIOInputsAutoLogged();

    public Outtake(OuttakeIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Outtake", inputs);
    }

    public void startOuttake(double speed) {
        io.startOuttake(speed);
    }

    public void stopOuttake() {
        io.stopOuttake();
    }
}
