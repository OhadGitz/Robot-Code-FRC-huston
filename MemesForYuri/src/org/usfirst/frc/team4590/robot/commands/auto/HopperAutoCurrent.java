package org.usfirst.frc.team4590.robot.commands.auto;

import org.usfirst.frc.team4590.robot.commands.chassis.ArcadeDriveByValues;
import org.usfirst.frc.team4590.robot.commands.chassis.DriveStraightAndTurn;
import org.usfirst.frc.team4590.robot.commands.chassis.DriveStraightByDistance;
import org.usfirst.frc.team4590.robot.commands.chassis.TurnDegrees;
import org.usfirst.frc.team4590.robot.commands.climber.Climb;
import org.usfirst.frc.team4590.robot.commands.feeder.FeedToShooter;
import org.usfirst.frc.team4590.robot.commands.fuelCollector.CollectFuel;
import org.usfirst.frc.team4590.robot.commands.shifter.ValveSetState;
import org.usfirst.frc.team4590.robot.subsystems.Shifts.ShifterState;
import org.usfirst.frc.team4590.utils.AllianceCommandGroup;
import org.usfirst.frc.team4590.utils.ntcore.robot.NTConstLoad;
import org.usfirst.frc.team4590.utils.ntcore.robot.NTField;

import edu.wpi.first.wpilibj.command.WaitCommand;

@NTConstLoad(path = "HopperAuto")
public class HopperAutoCurrent extends AllianceCommandGroup {
	
	//_____________________BLUE CONSTANTS_____________________//
	
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
	
	
	
	
	public void onBlue(){
		addParallel(new ValveSetState(ShifterState.POWER)); // 0 - 15
		addParallel(new BeNiceToShooter(blue_ShooterRPM), 15); // 0 - 15
		addParallel(new Climb(), 5);
		addParallel(new DriveStraightAndTurn(blue_StraightDistance, blue_HopperAngle, true), 2.8); // 0 - 2.95
		addSequential(new WaitCommand(1.4)); // 0 - 1.4
		addParallel(new CollectFuel(), 13.6); // 1.4 - 15
		addParallel(new FeedToShooter(-0.7), 3.25); // 1.4 - 4.65
		addSequential(new WaitCommand(1.4)); // 1.4 - 2.95
		addSequential(new ArcadeDriveByValues(-0.12, 0), 0.25); // 2.95 - 3.25 
		addSequential(new ArcadeDriveByValues(-0.85, 0), 0.25); // 3.25 - 3.55
		addSequential(new ArcadeDriveByValues(-0.12, 0), 0.25); // 3.55 - 3.85
		addSequential(new ArcadeDriveByValues(-0.85, 0), 0.25); // 3.85 - 4.15
		addSequential(new ArcadeDriveByValues(-0.12, 0), 0.25); // 4.15 - 4.45
		addSequential(new ArcadeDriveByValues(-0.85, 0), 0.25); // 4.45 - 4.75
		addSequential(new ArcadeDriveByValues(-0.12, 0), 0.25); // 4.75 - 5.05
		addSequential(new ArcadeDriveByValues(-0.2, 0), 0.2); // 4.75 - 5.05
		addSequential(new DriveStraightByDistance(500, blue_HopperAngle, false), 0.8); // 5.05 - 5.85
		addParallel(new TurnDegrees(blue_TotalAngle, false), 1.5); // 5.85 - 6.9
		addSequential(new WaitCommand(0.6)); // 5.85 - 6.45
		addSequential(new FeedToShooter(1), 8.55); // 6.45 - 15
	}
	
	public void onRed(){
		addParallel(new ValveSetState(ShifterState.POWER)); // 0 - 15
		addParallel(new BeNiceToShooter(red_ShooterRPM), 15); // 0 - 15
		addParallel(new Climb(), 5);
		addParallel(new DriveStraightAndTurn(red_StraightDistance, red_HopperAngle, true), 2.8); // 0 - 2.95
		addSequential(new WaitCommand(1.4)); // 0 - 1.4
		addParallel(new CollectFuel(), 13.6); // 1.4 - 15
		addParallel(new FeedToShooter(-0.7), 3.25); // 1.4 - 4.65
		addSequential(new WaitCommand(1.4)); // 1.4 - 2.95
		addSequential(new ArcadeDriveByValues(-0.12, 0), 0.25); // 2.95 - 3.25 
		addSequential(new ArcadeDriveByValues(-0.85, 0), 0.25); // 3.25 - 3.55
		addSequential(new ArcadeDriveByValues(-0.12, 0), 0.25); // 3.55 - 3.85
		addSequential(new ArcadeDriveByValues(-0.85, 0), 0.25); // 3.85 - 4.15
		addSequential(new ArcadeDriveByValues(-0.12, 0), 0.25); // 4.15 - 4.45
		addSequential(new ArcadeDriveByValues(-0.85, 0), 0.25); // 4.45 - 4.75
		addSequential(new ArcadeDriveByValues(-0.12, 0), 0.25); // 4.75 - 5.05
		addSequential(new ArcadeDriveByValues(-0.2, 0), 0.2); // 4.75 - 5.05
		addSequential(new DriveStraightByDistance(500, red_HopperAngle, false), 0.8); // 5.05 - 5.85
		addParallel(new TurnDegrees(red_TotalAngle, false), 1.5); // 5.85 - 6.9
		addSequential(new WaitCommand(0.6)); // 5.85 - 6.45
		addSequential(new FeedToShooter(1), 8.55); // 6.45 - 15
	}
}
