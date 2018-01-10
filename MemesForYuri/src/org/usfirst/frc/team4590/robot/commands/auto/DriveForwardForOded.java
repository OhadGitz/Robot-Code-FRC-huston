package org.usfirst.frc.team4590.robot.commands.auto;

import org.usfirst.frc.team4590.robot.commands.chassis.ArcadeDriveByValues;
import org.usfirst.frc.team4590.utils.AllianceCommandGroup;

/**
 *
 */
public class DriveForwardForOded extends AllianceCommandGroup {

    public DriveForwardForOded() {super();}
	@Override
	public void onBlue() {
		// TODO Auto-generated method stub
		addSequential(new ArcadeDriveByValues(0.5, 0), 2.5);
	}

	@Override
	public void onRed() {
		// TODO Auto-generated method stub
		addSequential(new ArcadeDriveByValues(0.5, 0), 2.5);
	}

	@Override
	public void onAny() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initAlliance() {
		// TODO Auto-generated method stub
		addSequential(new ArcadeDriveByValues(0.5, 0), 2.5);
	}
}
