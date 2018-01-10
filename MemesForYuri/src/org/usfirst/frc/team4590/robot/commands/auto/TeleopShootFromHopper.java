package org.usfirst.frc.team4590.robot.commands.auto;

import org.usfirst.frc.team4590.robot.commands.chassis.ArcadeDriveByValues;
import org.usfirst.frc.team4590.robot.commands.chassis.DriveStraightByDistance;
import org.usfirst.frc.team4590.robot.commands.chassis.ResetChassisMultiplier;
import org.usfirst.frc.team4590.robot.commands.chassis.SetDriveMultiplier;
import org.usfirst.frc.team4590.robot.commands.chassis.TurnDegrees;
import org.usfirst.frc.team4590.robot.commands.shifter.ValveSetState;
import org.usfirst.frc.team4590.robot.subsystems.Chassis;
import org.usfirst.frc.team4590.robot.subsystems.Shifts;
import org.usfirst.frc.team4590.robot.subsystems.Shifts.ShifterState;
import org.usfirst.frc.team4590.utils.AllianceCommandGroup;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class TeleopShootFromHopper extends AllianceCommandGroup {
	private double blue_TotalAngle = -59.4; //Values: [=> -149.4, -147]
	private double red_TotalAngle = 72; // Values: [one we were stuck on: 166.8 on right rim!]
	private double error_engle = 1.79;  // this angle should be in abs (error becuase of hopper plate)
  
	{
		requires(Chassis.getInstance());
	}

	@Override
	public void onBlue() {
		addSequential(new ResetChassisMultiplier());
		addParallel(new ValveSetState(ShifterState.POWER));
		addSequential(new DriveStraightByDistance(500,0,true), 0.8); // 5.05 - 5.85
		addParallel(new TurnDegrees(blue_TotalAngle - error_engle, false), 1.5); // 5.85 - 6.9
		addSequential(new WaitCommand(0.6)); // 5.85 - 6.45
	}

	@Override
	public void onRed() {
		addSequential(new ResetChassisMultiplier());
		addParallel(new ValveSetState(ShifterState.POWER));
    	addSequential(new DriveStraightByDistance(500,0,true), 0.8); // 5.05 - 5.85
		addParallel(new TurnDegrees(red_TotalAngle + error_engle, false), 1.5); // 5.85 - 6.9
		addSequential(new WaitCommand(0.6)); // 5.85 - 6.45
		}
	@Override
	public void end(){
		super.end();

		
	}
	
}
