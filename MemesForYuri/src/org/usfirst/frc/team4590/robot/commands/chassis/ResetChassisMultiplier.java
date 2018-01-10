package org.usfirst.frc.team4590.robot.commands.chassis;

import org.usfirst.frc.team4590.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.command.Command;

public class ResetChassisMultiplier extends Command {

	protected void execute(){
		
		Chassis.getInstance().setDriveMultiplier(1);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
