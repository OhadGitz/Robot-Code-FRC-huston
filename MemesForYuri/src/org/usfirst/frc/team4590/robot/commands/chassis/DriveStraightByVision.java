package org.usfirst.frc.team4590.robot.commands.chassis;

import org.usfirst.frc.team4590.robot.subsystems.Chassis;
import org.usfirst.frc.team4590.utils.CameraIndex;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraightByVision extends Command implements PIDOutput,PIDSource {
	private static final double Kp = 0.625, Ki = 0 , Kd = 0;
	
	private PowerDistributionPanel pdp;
	
	private PIDController turnPID = new PIDController(Kp, Ki, Kd, this, this);

    public DriveStraightByVision() {
       
        
    	requires(Chassis.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putNumber("VisionDrive PID P", turnPID.getP());
    	SmartDashboard.putNumber("VisionDrive PID I", turnPID.getI());
    	SmartDashboard.putNumber("VisionDrive PID D", turnPID.getD());
    	turnPID.setAbsoluteTolerance(0.051);
    	turnPID.setSetpoint(0);
    	turnPID.enable();
    	CameraIndex.LIFT.set();
    	pdp = new PowerDistributionPanel();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("NETWORK TABLES", NetworkTable.getTable("vision").getNumber("elevatorX", -2.0));
    	
    	

    	
    	turnPID.setPID(	SmartDashboard.getNumber("VisionDrive PID P", 0.0),
    					SmartDashboard.getNumber("VisionDrive PID I", 0.0),
    					SmartDashboard.getNumber("VisionDrive PID D", 0.0));
    	
    	SmartDashboard.putNumber("VisionDrive PID P", turnPID.getP());
    	SmartDashboard.putNumber("VisionDrive PID I", turnPID.getI());
    	SmartDashboard.putNumber("VisionDrive PID D", turnPID.getD());
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
   
    	return Chassis.getInstance().getAccel('x') < -0.3;// (times_on_target += turnPID.onTarget() ? 1 : - times_on_target) >= 20;
    	//return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	turnPID.reset();
    	turnPID.disable();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

	@Override
	public void pidWrite(double output) {
		if(output > 0.3) output = 0.3;
		if(output < -0.3) output = -0.3;
		//if(output > 0 && output < 0.2) output = 0.2;
		//if(output < 0 && output > -0.2) output = -0.2;
		//System.out.println("I am writing a new PID value");
		SmartDashboard.putNumber("DRIVE STRAIGHT BY VISION: output", output);
		
		Chassis.getInstance().arcadeDrive(Chassis.getInstance().getInvertedMotors() * 0.46 * 12.0 / Math.min(12.0, pdp.getVoltage()), -output * 12.0 / Math.min(12.0, pdp.getVoltage()));
		
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}
	
	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		double ret = pidGet_();
		System.out.println(ret);
		return ret;
	}
	
	private double pidGet_() {
		if (NetworkTable.getTable("vision").getNumber("elevatorX", -2.0) == -2) return 0;
		return NetworkTable.getTable("vision").getNumber("elevatorX", -2.0);
	}
	
	

}
