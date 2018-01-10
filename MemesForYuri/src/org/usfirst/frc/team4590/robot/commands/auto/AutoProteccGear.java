package org.usfirst.frc.team4590.robot.commands.auto;

import org.usfirst.frc.team4590.robot.commands.chassis.ArcadeDriveByValues;
import org.usfirst.frc.team4590.robot.commands.chassis.DriveStraightByDistance;
import org.usfirst.frc.team4590.robot.commands.chassis.TurnDegrees;
import org.usfirst.frc.team4590.robot.commands.gearsPlacer.ClosePlacer;
import org.usfirst.frc.team4590.robot.commands.gearsPlacer.OpenPlacer2;
import org.usfirst.frc.team4590.robot.commands.gearsPlacer.PlaceGear;
import org.usfirst.frc.team4590.robot.commands.shifter.ValveSetState;
import org.usfirst.frc.team4590.robot.subsystems.Shifts.ShifterState;
import org.usfirst.frc.team4590.utils.AllianceCommandGroup;

public class AutoProteccGear extends AllianceCommandGroup {

	private int PEG_STRAIGHT /**NOHOMO**/ = 4500;
	private int ROBOT_LENGTH = 910;

	private int EXTRA_DIS = 5000;
	@Override
	public void onBlue() {
		//addParallel(new ValveSetState(ShifterState.SPEED)); bad distance
		addSequential(new DriveStraightByDistance(PEG_STRAIGHT - ROBOT_LENGTH / 2), 3.5);
		//TODO: might need to change to a little less
		addSequential(new TurnDegrees(90), 2.5);
		addSequential(new DriveStraightByDistance(EXTRA_DIS), 4);
		addSequential(new OpenPlacer2());
		addSequential(new ArcadeDriveByValues(-0.5, -0.5), 1);// TODO change commad
		addSequential(new ClosePlacer());
		
	}

	@Override
	public void onRed() {
		//addParallel(new ValveSetState(ShifterState.SPEED)); bad distance 
		addSequential(new DriveStraightByDistance(PEG_STRAIGHT - ROBOT_LENGTH / 2), 3.5);
		//TODO: might need to change to a little less
		addSequential(new TurnDegrees(90), 2.5);
		addSequential(new DriveStraightByDistance(EXTRA_DIS), 4);
		addSequential(new OpenPlacer2());
		addSequential(new ArcadeDriveByValues(-0.5, -0.5), 1);// TODO change commad
		addSequential(new ClosePlacer());
	}

}