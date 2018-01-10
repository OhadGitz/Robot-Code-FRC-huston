package org.usfirst.frc.team4590.robot.commands.shooter;

import org.usfirst.frc.team4590.robot.commands.feeder.FeedToShooterTeleop;
import org.usfirst.frc.team4590.utils.ntcore.robot.NTConstLoad;
import org.usfirst.frc.team4590.utils.ntcore.robot.NTField;
import org.usfirst.frc.team4590.utils.ntcore.robot.NetworkTablesSyncer;

@NTConstLoad(path = "Shooter/Teleop")
public class ShooterTeleopCommandCopy extends ShooterSetSpeedCopy{

	@NTField(name = "ShooterRPM")
	private double m_speed_ = 2035;
	
	public ShooterTeleopCommandCopy() {
		super(0);
		NetworkTablesSyncer.addHook(this, ShooterTeleopCommandCopy.class);
	}
	
	protected void execute(){
		m_speed = m_speed_;
		System.out.println("A: " + m_speed);
		super.execute();
	}

}
