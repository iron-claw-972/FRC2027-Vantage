package frc.robot.subsystems.Intake;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.constants.Constants;

public class IntakeIOTalonFX implements IntakeIO{
    private final TalonFX rollerMotor = new TalonFX(1, Constants.CANIVORE_CAN);
    private final TalonFX stowMotor = new TalonFX(2, Constants.CANIVORE_CAN);

    public IntakeIOTalonFX() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        rollerMotor.getConfigurator().apply(config);
        rollerMotor.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.CounterClockwise_Positive).withNeutralMode(NeutralModeValue.Brake));
        stowMotor.getConfigurator().apply(config);
        stowMotor.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.CounterClockwise_Positive).withNeutralMode(NeutralModeValue.Brake));

    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.rollerPositionRot = rollerMotor.getPosition().getValueAsDouble();
        inputs.rollerVelocityRps = rollerMotor.getVelocity().getValueAsDouble();
        inputs.rollerStatorCurrentAmps = rollerMotor.getStatorCurrent().getValueAsDouble();
        inputs.rollerSupplyCurrentAmps = rollerMotor.getSupplyCurrent().getValueAsDouble();
        inputs.rollerAppliedVolts = rollerMotor.getMotorVoltage().getValueAsDouble();
        inputs.stowPositionRot = stowMotor.getPosition().getValueAsDouble();
        inputs.stowVelocityRps = stowMotor.getVelocity().getValueAsDouble();
        inputs.stowStatorCurrentAmps = stowMotor.getStatorCurrent().getValueAsDouble();
        inputs.stowSupplyCurrentAmps = stowMotor.getSupplyCurrent().getValueAsDouble();
        inputs.stowAppliedVolts = stowMotor.getMotorVoltage().getValueAsDouble();
    }

    @Override
    public void stowIntake(double speed) {
        stowMotor.setThrottle(speed);
    }

    @Override
    public void extendIntake(double speed) {
        stowMotor.setThrottle(speed);
    }

    @Override
    public void startRollers(double speed) {
        rollerMotor.setThrottle(speed);
    }
    
    @Override
    public void close() {
        rollerMotor.close();
        stowMotor.close();
    }
}
