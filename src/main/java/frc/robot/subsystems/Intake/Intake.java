package frc.robot.subsystems.Intake;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command2.SubsystemBase;

public class Intake extends SubsystemBase {
    private final IntakeIO io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

    public Intake(IntakeIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);
    }

    public void startRollers(double speed) {
        io.startRollers(speed);
    }

    public void stow(double speed) {
        io.extendIntake(speed);
    }

    public void stopRollers(double speed) {
        io.stopRollers();
    }
    
    public void stowIntake(double speed) {
        io.stowIntake(speed);
    }

    public void close() {
        io.close();
    }
}
