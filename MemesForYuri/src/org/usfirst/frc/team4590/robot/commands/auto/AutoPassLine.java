package org.usfirst.frc.team4590.robot.commands.auto;

import org.usfirst.frc.team4590.robot.commands.chassis.DriveStraightByDistance;
import org.usfirst.frc.team4590.robot.commands.shifter.ValveSetState;
import org.usfirst.frc.team4590.robot.subsystems.Shifts.ShifterState;
import org.usfirst.frc.team4590.utils.AllianceCommandGroup;

public class AutoPassLine extends AllianceCommandGroup {

	private int RIBUA_SHLITA = -4600;
	private int ROBOT_LENGTH = -910;
	private int AUTO_LINE = -5000;
	private int EXTRA_AUTO_LINE = -1390;
	@Override
	public void onBlue() {
		//addParallel(new ValveSetState(ShifterState.SPEED)); bad distance
		addSequential(new DriveStraightByDistance(AUTO_LINE + EXTRA_AUTO_LINE - ROBOT_LENGTH), 9);
		//TODO: might need to change to a little less
		addSequential(new DriveStraightByDistance(RIBUA_SHLITA - (AUTO_LINE + EXTRA_AUTO_LINE)), 6);
		
	}

	@Override
	public void onRed() {
		//addParallel(new ValveSetState(ShifterState.SPEED)); bad distance
		addSequential(new DriveStraightByDistance(AUTO_LINE + EXTRA_AUTO_LINE - ROBOT_LENGTH), 9);
		//TODO: might need to change to a little less
		addSequential(new DriveStraightByDistance(RIBUA_SHLITA - (AUTO_LINE + EXTRA_AUTO_LINE)), 6);
		
	}

}
