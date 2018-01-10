package org.usfirst.frc.team4590.robot.commands.chassis;

import org.usfirst.frc.team4590.robot.subsystems.Chassis;
import org.usfirst.frc.team4590.utils.ntcore.common.NTFieldType;
import org.usfirst.frc.team4590.utils.ntcore.robot.NTConstLoad;
import org.usfirst.frc.team4590.utils.ntcore.robot.NTField;
import org.usfirst.frc.team4590.utils.ntcore.robot.NetworkTablesSyncer;

import edu.wpi.first.wpilibj.command.Command;

@NTConstLoad(path = "TurnCiruclarCommand")
public class TurnByCircularSpeed extends Command{

	private double m_startAngle;
	
	@NTField(name = "SetpointAngle")
	private double m_angle;
	
	private boolean m_reset;
	
	private boolean m_stopping = false;
	
	@NTField(name = "AngleTolerance")
	private double m_slowdown;
	
	@NTField(name = "EndingTolerance")
	private double m_tolerance;

	@NTField(name = "BaseValue")
	private double m_baseValue;
	
	@NTField(name = "kP")
	private double m_kP;
	
	@NTField(name = "AngularSpeed")
	private double m_angularSpeed;
	
	private double m_lastAngle;
	
	public TurnByCircularSpeed(boolean reset){
		requires(Chassis.getInstance());
		m_reset = reset;
		NetworkTablesSyncer.addHook(this, TurnByCircularSpeed.class);
	}
	
	public void initialize(){
		m_stopping = false;
		if (m_reset){
			Chassis.getInstance().resetAHRS();
			m_startAngle = 0;
		} else {
			m_startAngle = Chassis.getInstance().getAngle();
		}
		
		m_lastAngle = m_startAngle;
		
	}
	
	@Override
	protected boolean isFinished() {
		return Math.abs(Chassis.getInstance().getAngle() - m_startAngle - m_angle) <= m_tolerance;
	}
	
	
	
	@Override
	public void execute(){
		double error = Chassis.getInstance().getAngle() - m_startAngle - m_angle;
		double angle = Chassis.getInstance().getAngle();
		double angularSpeed = 20 * (angle - m_lastAngle);
		double angularSpeedError = angularSpeed - m_angularSpeed;
		m_lastAngle = angle;
		if (m_stopping || Math.abs(error) < m_slowdown){
			m_stopping = true;
			Chassis.getInstance().arcadeAccDrive(0, 0);
		} else {
			double output = Math.signum(error) * (m_baseValue + angularSpeedError * m_kP);
			Chassis.getInstance().arcadeAccDrive(0, output);
		}
		
	}

}
