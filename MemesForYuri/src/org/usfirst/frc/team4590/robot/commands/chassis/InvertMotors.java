package org.usfirst.frc.team4590.robot.commands.chassis;

import org.usfirst.frc.team4590.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class InvertMotors extends Command {

    public InvertMotors() {
       requires(Chassis.getInstance());
    }

    protected void initialize() {}

    protected void execute() {
    	Chassis.getInstance().invertMotors();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {
    	end();
    }
}
