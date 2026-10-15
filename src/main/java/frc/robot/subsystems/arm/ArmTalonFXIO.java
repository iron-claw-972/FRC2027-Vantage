package frc.robot.subsystems.arm;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.constants.Constants;
import frc.robot.constants.IdConstants;

public class ArmTalonFXIO implements ArmIO{
    private TalonFX ArmMotor;

    private final double GEAR_RATIO = 1.0;

    private final MotionMagicVoltage m_request = new MotionMagicVoltage(0);

    public ArmTalonFXIO(){
        ArmMotor = new TalonFX(IdConstants.ARM_MOTOR_ID, Constants.CANIVORE_CAN);

        // in init function
        TalonFXConfiguration talonFXConfigs = new TalonFXConfiguration();

        // set slot 0 gains
        var slot0Configs = talonFXConfigs.Slot0;
        slot0Configs.kS = 0.0; // Add 0.25 V output to overcome static friction
        slot0Configs.kV = 0.0; // A velocity target of 1 rps results in 0.12 V output
        slot0Configs.kA = 0.0; // An acceleration of 1 rps/s requires 0.01 V output
        slot0Configs.kP = 4.8; // A position error of 2.5 rotations results in 12 V output
        slot0Configs.kI = 0; // no output for integrated error
        slot0Configs.kD = 0.1; // A velocity error of 1 rps results in 0.1 V output

        // set Motion Magic settings
        var motionMagicConfigs = talonFXConfigs.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = 80 * GEAR_RATIO; // Target cruise velocity of 80 rps
        motionMagicConfigs.MotionMagicAcceleration = 160 * GEAR_RATIO; // Target acceleration of 160 rps/s (0.5 seconds)
        motionMagicConfigs.MotionMagicJerk = 1600 * GEAR_RATIO; // Target jerk of 1600 rps/s/s (0.1 seconds)

        ArmMotor.getConfigurator().apply(talonFXConfigs);
    }
    

    @Override
    public void updateInputs(ArmIOInputsAutoLogged inputs) {
        inputs.inputVoltage = ArmMotor.getMotorVoltage().refresh().getValueAsDouble();
        inputs.setpointRotations = m_request.Position;
        inputs.armPosition = ArmMotor.getPosition().getValueAsDouble()/GEAR_RATIO;
    }

    @Override
    public void setSetpointRotation(double position) {
        ArmMotor.setControl(m_request.withPosition(position));
    }


    @Override
    public void setCurrentLimits(double supplyCurrentLimit, double statorCurrentLimit) {
        CurrentLimitsConfigs configs = new CurrentLimitsConfigs();
        configs.SupplyCurrentLimitEnable = true;
        configs.StatorCurrentLimitEnable = true;

        configs.SupplyCurrentLimit = supplyCurrentLimit;
        configs.StatorCurrentLimit = statorCurrentLimit;

        ArmMotor.getConfigurator().apply(configs);
    }

}
