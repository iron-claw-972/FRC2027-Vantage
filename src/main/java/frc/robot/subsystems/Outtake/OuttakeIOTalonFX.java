package frc.robot.subsystems.Outtake;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.constants.Constants;
import frc.robot.constants.IdConstants;

public class OuttakeIOTalonFX implements OuttakeIO{
    private final TalonFX outtakeMotor = new TalonFX(IdConstants.OUTTAKE_MOTOR_ID, Constants.CANIVORE_CAN);

    public OuttakeIOTalonFX() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        outtakeMotor.getConfigurator().apply(config);
        outtakeMotor.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.CounterClockwise_Positive).withNeutralMode(NeutralModeValue.Brake));
    }

    @Override
    public void updateInputs(OuttakeIOInputs inputs) {
        inputs.outtakePosition = outtakeMotor.getPosition().getValueAsDouble();
        inputs.outtakeVelocity = outtakeMotor.getVelocity().getValueAsDouble();
        inputs.outtakeStatorCurrentAmps = outtakeMotor.getStatorCurrent().getValueAsDouble();
        inputs.outtakeSupplyCurrentAmps = outtakeMotor.getSupplyCurrent().getValueAsDouble();
        inputs.outtakeAppliedVolts = outtakeMotor.getMotorVoltage().getValueAsDouble();
    }

    @Override
    public void startOuttake(double speed) {
        outtakeMotor.setThrottle(speed);
    }
}
