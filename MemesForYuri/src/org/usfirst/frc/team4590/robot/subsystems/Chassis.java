
package org.usfirst.frc.team4590.robot.subsystems;

import static org.usfirst.frc.team4590.robot.RobotMap.*;

import org.usfirst.frc.team4590.robot.commands.chassis.ArcadeDriveByJoystick;
import org.usfirst.frc.team4590.utils.PIDGetter;
import org.usfirst.frc.team4590.utils.PIDSlave;
import org.usfirst.frc.team4590.utils.ThreeCIMShifter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The main robot frame. Its in charge of the wheels, driving, AHRS and
 * encoders.
 *
 */
public class Chassis extends PIDSubsystem {
	
	private int invertedMotors = 1;
//	private double multiplater = 1;
	private static double kP = 0, kI = 0, kD = 0;
	private Encoder m_encLeft, m_encRight;
	private AHRS m_gyro;
	private ThreeCIMShifter m_drive;
	private boolean m_driveSpeed;
	private PIDSlave m_leftSlave;
	private PIDSlave m_rightSlave;
	private double m_multi = 1;
	private static double MAX_FREE_RPM = 5330;
	private static double SPEED_GEARS_RATIO = 4.17;
	private static double POWER_GEARS_RATIO = 11.03;
	private PowerDistributionPanel m_pdp;
	//	private boolean arcadeMode = true;
	private static Chassis instance;
	private static final double WHEEL_DIAMETER = 10.16;


	
	private Chassis() {
		super("Chassis", kP, kI, kD);
		m_encLeft = new Encoder(CHASSIS_ENCODER_LEFT_A, CHASSIS_ENCODER_LEFT_B);
		m_encRight = new Encoder(CHASSIS_ENCODER_RIGHT_A, CHASSIS_ENCODER_RIGHT_B);
		m_encLeft.setDistancePerPulse(1.0 / 220.5/*83.22485*/);
		m_encRight.setDistancePerPulse(1.0 / 220.5);
		m_gyro = new AHRS(SPI.Port.kMXP);
		PIDGetter leftGetter = (pid) -> m_encLeft.getRate();
		PIDGetter rightGetter = (pid) -> m_encRight.getRate();
		m_drive = new ThreeCIMShifter(leftGetter, rightGetter);
		//m_drive.setControlMode(true);
		SmartDashboard.putNumber("Chassis__PID(P)", kP);
		SmartDashboard.putNumber("Chassis__PID(I)", kI);
		SmartDashboard.putNumber("Chassis__PID(D)", kD);
		SmartDashboard.putNumber("ON SCHOOL : Speed Multiplier", m_multi);
		LiveWindow.addSensor("Chassis", "Encoder left", m_encLeft);
		LiveWindow.addSensor("Chassis", "Encoder right", m_encRight);
		m_pdp = new PowerDistributionPanel();
		//LiveWindow.addSensor("Chassis", "AHRS", ahrs);
	}

	public static final void init() {
		instance = new Chassis();
	}

	public static final Chassis getInstance() {
		return instance;
	}

	// getters for sensors
	public double getDistance() {
		return (m_encLeft.getDistance() - m_encRight.getDistance()) / 2;
	}

	public double getSpeed() {
		return (m_encLeft.getRate() - m_encRight.getRate()) / 2;
	}
	
	public double getSpeedL() {
		return - m_encRight.getRate();
	}
	
	public double getSpeedR() {
		return m_encLeft.getRate();
	}

	public double getAngle() {
		return m_gyro.getYaw();
	}
	

	public double getAccel(char axis) {
		switch (axis) {
		case 'x':
			return m_gyro.getWorldLinearAccelX();
		case 'y':
			return m_gyro.getWorldLinearAccelY();
		case 'z':
			return m_gyro.getWorldLinearAccelZ();
		}
		System.out.println("Chassis::getAccel - RIP");
		return -1;
	}

	// reset methods for sensors
	public void resetEncoders() {
		m_encLeft.reset();
		m_encRight.reset();
	}

	public void resetAHRS() {
		m_gyro.reset();
	}

	// drives
	public void tankDrive(double left, double right) {
			//m_drive.tankDrive(m_leftSlave.getValue(), m_rightSlave.getValue());
		m_drive.tankDrive(invertedMotors * left * m_multi, invertedMotors * right * m_multi);
	}

	public void arcadeDrive(double forward, double side) {
		m_drive.arcadeDrive(invertedMotors * forward * m_multi, side * m_multi);
	}

	public void arcadeAccDrive(double forward, double side) {
		double pdp_multi = Math.max(12 / m_pdp.getVoltage(), 1);
		m_drive.arcadeDrive(forward * m_multi * pdp_multi, side * m_multi * pdp_multi);
	}
	
	// displays status
	public void status() {
		SmartDashboard.putNumber("Left Encoder", m_encLeft.getDistance());
		SmartDashboard.putNumber("Right Encoder", m_encRight.getDistance());
		SmartDashboard.putNumber("CHASSIS::Angle", getAngle());
		SmartDashboard.putNumber("CHASSIS::Distance", getDistance());
		kP = SmartDashboard.getNumber("Chassis__PID(P)", kP);
		kI = SmartDashboard.getNumber("Chassis__PID(I)", kI);
		kD = SmartDashboard.getNumber("Chassis__PID(D)", kD);
		m_multi = SmartDashboard.getNumber("ON SCHOOL : Speed Multiplier", m_multi);
		//SmartDashboard.putNumber("ON SCHOOL: multiplier", m_multi);
	 	SmartDashboard.putNumber("pitch angle", m_gyro.getPitch());
    	SmartDashboard.putNumber("yaw angle", m_gyro.getYaw());
    	SmartDashboard.putNumber("roll angle", m_gyro.getRoll());
    	SmartDashboard.putNumber("Speed oded", Chassis.getInstance().getSpeed());
		getPIDController().setPID(kP, kI, kD);
		/*
		 * for debugging purposes
		 * 
		 * SmartDashboard.putNumber("CHASSIS::Left encoder",
		 * encLeft.getDistance()); SmartDashboard.putNumber(
		 * "CHASSIS::Right encoder", encRight.getDistance());
		 * SmartDashboard.putNumber("CHASSIS::Left CIM Power",
		 * drive.getPowerLeft()); SmartDashboard.putNumber(
		 * "CHASSIS::Right CIM Power", drive.getPowerRight());
		 */
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveByJoystick());
	}

	@Override
	protected double returnPIDInput() {
		return getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		arcadeDrive(0, output);
	}
	
	
	public void setDriveMode(boolean isSpeed){
		m_driveSpeed = isSpeed;
	}

	public void setDriveMultiplier(double multi) {
		m_multi = multi;
	}

	public double getDriveMultiplier(){
		return m_multi;
	}

	public void toggleSpeedControl() {
		m_drive.setControlMode(!m_drive.getControlMode());
	}
	
	public void invertMotors() {
		invertedMotors = -invertedMotors;
	}
	
	public int getInvertedMotors() {
		return invertedMotors;
	}
}
