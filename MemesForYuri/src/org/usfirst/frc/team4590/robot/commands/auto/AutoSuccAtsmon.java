package org.usfirst.frc.team4590.robot.commands.auto;

import org.usfirst.frc.team4590.robot.commands.chassis.DriveStraightByDistance;
import org.usfirst.frc.team4590.robot.commands.chassis.TurnDegrees;
import org.usfirst.frc.team4590.robot.commands.feeder.FeedToShooter;
import org.usfirst.frc.team4590.robot.commands.shifter.ValveSetState;
import org.usfirst.frc.team4590.robot.subsystems.Shifts;
import org.usfirst.frc.team4590.robot.subsystems.Shifts.ShifterState;
import org.usfirst.frc.team4590.utils.AllianceCommandGroup;
import org.usfirst.frc.team4590.utils.ntcore.robot.NTField;

public class AutoSuccAtsmon extends AllianceCommandGroup {
	
	@NTField(name = "Blue/ShooterRPM")
	private double blue_ShooterRPM = 2035; //prev:[2045 - overshoot]
	
	@NTField(name = "Blue/StraightDistance")
	private double blue_StraightDistance = 1480;
	
	@NTField(name = "Blue/TotalAngle")
	private double blue_TotalAngle = -149.4; //Values: [=> -149.4, -147]
	
	@NTField(name = "Blue/HopperAngle")
	private double blue_HopperAngle = -90;
	
	//____________________RED CONSTANTS____________________//
	@NTField(name = "Red/ShooterRPM")
	private double red_ShooterRPM = 2035; 
	
	@NTField(name = "Red/StraightDistance")
	private double red_StraightDistance = 1500;
	
	@NTField(name = "Red/TotalAngle")
	private double red_TotalAngle = 162; // Values: [one we were stuck on: 166.8 on right rim!]
	
	@NTField(name = "Red/HopperAngle")
	private double red_HopperAngle = 90;
	
	private int RIBUA_SHLITA = -4600;
	private int ROBOT_LENGTH = -910;
	private int AUTO_LINE = -5000;
	private int EXTRA_AUTO_LINE = -1390;
	@Override
	public void onBlue() {
		//addParallel(new ValveSetState(ShifterState.SPEED)); bad distance
		addParallel(new BeNiceToShooter(blue_ShooterRPM), 15); // 0 - 15
		addSequential(new DriveStraightByDistance(AUTO_LINE + EXTRA_AUTO_LINE - ROBOT_LENGTH), 6.5);
		addSequential(new TurnDegrees(-10), 1.2);
		addSequential(new FeedToShooter(), 3);
		addSequential(new TurnDegrees(10), 1.2);
		//TODO: might need to change to a little less
		addSequential(new DriveStraightByDistance(RIBUA_SHLITA - (AUTO_LINE + EXTRA_AUTO_LINE)), 3.5);

	}

	@Override
	public void onRed() {
		//addParallel(new ValveSetState(ShifterState.SPEED)); Bad distance
		addParallel(new BeNiceToShooter(red_ShooterRPM), 15); // 0 - 15
		addSequential(new DriveStraightByDistance(AUTO_LINE + EXTRA_AUTO_LINE - ROBOT_LENGTH), 5);
		addSequential(new TurnDegrees(10), 1.2);
		addSequential(new FeedToShooter(), 3);
		addSequential(new TurnDegrees(-10), 1.2);
		//TODO: might need to change to a little less
		addSequential(new DriveStraightByDistance(RIBUA_SHLITA - (AUTO_LINE + EXTRA_AUTO_LINE)), 3.5);

	}

}
