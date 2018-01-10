package org.usfirst.frc.team4590.robot.commands.feeder;

import org.usfirst.frc.team4590.robot.subsystems.Feeder;
import org.usfirst.frc.team4590.utils.ntcore.robot.NTConstLoad;
import org.usfirst.frc.team4590.utils.ntcore.robot.NTField;
import org.usfirst.frc.team4590.utils.ntcore.robot.NetworkTablesSyncer;

/**
 *
 */
@NTConstLoad(path = "Feeder/Teleop")
public class FeedToShooterTeleop extends FeedToShooter {

	@NTField(name = "FeederPower")
	private double m_multi_ = 1;
	
	@NTField(name = "HelperMultiplier")
	private double m_helperMulti = -1;
	
	public FeedToShooterTeleop() {
		super(0.5);
		NetworkTablesSyncer.addHook(this, FeedToShooterTeleop.class);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//if (Shooter.getInstance().onTarget()) {
		Feeder.getInstance().m_helperMulti = m_helperMulti;
		m_multi = m_multi_;
		super.execute();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Feeder.getInstance().setPower(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
