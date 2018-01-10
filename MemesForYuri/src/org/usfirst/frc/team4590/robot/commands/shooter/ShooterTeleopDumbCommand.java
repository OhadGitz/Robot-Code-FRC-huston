package org.usfirst.frc.team4590.robot.commands.shooter;

import org.usfirst.frc.team4590.robot.subsystems.Shooter;
import org.usfirst.frc.team4590.utils.ntcore.robot.NTConstLoad;
import org.usfirst.frc.team4590.utils.ntcore.robot.NTField;
import org.usfirst.frc.team4590.utils.ntcore.robot.NetworkTablesSyncer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
@NTConstLoad(path = "Shooter/Teleop")
public class ShooterTeleopDumbCommand extends Command {

	@NTField(name = "Power")
	private double speed = 0.5;

	public ShooterTeleopDumbCommand(double speed) {
		// Use requires() here to declare subsystem dependencies
		requires(Shooter.getInstance());
		this.speed = -speed;
		SmartDashboard.putNumber("Shooter::SetSpeed",0);
		NetworkTablesSyncer.addHook(this, ShooterTeleopDumbCommand.class);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		Shooter.getInstance().setPower(speed);
		Shooter.getInstance().status();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Shooter.getInstance().getPIDController().disable();
		Shooter.getInstance().getPIDController().reset();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
