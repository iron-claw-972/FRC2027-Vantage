package frc.robot.subsystems.intake;

import org.wpilib.math.system.DCMotor;
import org.wpilib.math.util.Units;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.constants.Constants;

public class IntakeIOReal implements IntakeIO {
    public final TalonFX rollerMotor = new TalonFX(0, Constants.CANIVORE_CAN); // 0 is a temp id
    public final TalonFX pivotMotor = new TalonFX(1, Constants.CANIVORE_CAN); // 0 is a temp id
    private DCMotor dcMotor = DCMotor.getKrakenX60(1);

    public IntakeIOReal() {
        rollerMotor.getConfigurator().apply(new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Brake).withInverted(InvertedValue.CounterClockwise_Positive));
        pivotMotor.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.CounterClockwise_Positive));
        // pivotMotor.setNeutralMOde i cant find equal
        
        rollerMotor.getConfigurator().apply(new CurrentLimitsConfigs().withStatorCurrentLimit(40).withStatorCurrentLimitEnable(true));
    }

    @Override
    public void updateInputs(IntakeIOInputsAutoLogged inputs) {
        inputs.measuredPivotPosition = Units.rotationsToDegrees(pivotMotor.getPosition().getValueAsDouble()) / IntakeConstants.PIVOT_GEAR_RATIO;
        inputs.motorR0hms = dcMotor.R; // Guessed was equal to rohms
        inputs.motorKtNMPerAmp = dcMotor.Kt; // Guessed was equal to ktnperamp
    }

    @Override
    public void setRollerMotorThrottle(double throttle) {
        rollerMotor.setThrottle(throttle);
    }

    @Override
    public void setPivotMotorPosition(double position) {
        pivotMotor.setPosition(position);
    }

    @Override
    public void setPivotMotorThrottle(double throttle) {
        pivotMotor.setThrottle(throttle);
    }
}