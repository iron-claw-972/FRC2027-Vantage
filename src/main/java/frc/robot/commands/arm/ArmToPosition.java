package frc.robot.commands.arm;

import org.wpilib.command2.Command;

import frc.robot.subsystems.arm.Arm;

public class ArmToPosition extends Command{
    private Arm arm;
    private double setpointRotations;

    public ArmToPosition(Arm arm, double setpointRotations){
        this.arm = arm;
        this.setpointRotations = setpointRotations;
    }

    @Override
    public void initialize(){
        addRequirements(arm);
        arm.setSetpointRotations(setpointRotations);
    }

    @Override
    public void execute(){

    }

    @Override
    public boolean isFinished() {
        return arm.atSetpoint();
    }

    @Override
    public void end(boolean interrupted){
        
    }
}