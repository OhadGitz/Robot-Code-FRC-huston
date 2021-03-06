package org.usfirst.frc.team4590.robot.commands.chassis;

import org.usfirst.frc.team4590.robot.OI;
import org.usfirst.frc.team4590.robot.subsystems.Chassis;
import org.usfirst.frc.team4590.robot.subsystems.Shifts;
import org.usfirst.frc.team4590.robot.subsystems.Shifts.ShifterState;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives in arcade mode: Y axis controls the power forward while the X axis
 * controls the rotation.
 */
public class ArcadeDriveByJoystick extends Command {

	private PowerDistributionPanel pdp;
	
	public ArcadeDriveByJoystick() {
		// Use requires() here to declare subsystem dependencies
		requires(Chassis.getInstance());
	}

	// Called just before this Command runs the first time
	protected void initialize() {	
		pdp = new PowerDistributionPanel();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//System.out.println(OI.getInstance().getMainRightX() + " " + OI.getInstance().getMainLeftY());
		
		SmartDashboard.putNumber("Joystick R X", OI.getInstance().getMainRightX());
		Chassis.getInstance().arcadeDrive(-OI.getInstance().getMainLeftY(), OI.getInstance().getMainRightX());
		//if (pdp.getVoltage() <= 7.75){ Shifts.getInstance().setState(ShifterState.POWER); }
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
