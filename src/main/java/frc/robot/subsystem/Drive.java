package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Drive {
    // Talon Definition: an instruction reader that gives instructions to the stupid
    // motors after reading the instructions from the big instruction reader that
    // reads instructions from the instruction manual written by the instruction man
    private TalonSRX rightMaster;
    private TalonSRX rightSlave;
    private TalonSRX leftMaster;
    private TalonSRX leftSlave;


    // Assigns IDs talons to hand instructions to dum kopf motors
    public Drive(int backRightID, int frontRightID, int backLeftID, int frontLeftID) {
        rightMaster = new TalonSRX(backRightID);
        rightSlave = new TalonSRX(frontRightID);

        leftMaster = new TalonSRX(frontLeftID);
        leftSlave = new TalonSRX(backLeftID);

        rightMaster.setInverted(false);
        leftMaster.setInverted(true);

        rightSlave.follow(rightMaster);
        rightSlave.setInverted(InvertType.FollowMaster);
        // Slaves loyally mimic master's movements, including inversion
        leftSlave.follow(leftMaster);
        leftSlave.setInverted(InvertType.FollowMaster);
    }

    // Better tankDrive used exclussively with joystick(s)
    public void arcadeDrive(double y, double x) {
        // y is the y axis of the joystick
        // x is the x axis of the SAME joystick

        if (Math.abs(x) + Math.abs(y) < 1) {
            tankDrive(y + x, y - x);
        } else {
            double betterX = x / (Math.abs(x) + Math.abs(y));
            double betterY = y / (Math.abs(x) + Math.abs(y));
            tankDrive(betterY + betterX, betterY - betterX);
        }
    }

    // Runs speed methods base upon two values
    public void tankDrive(double leftSpeed, double rightSpeed) {
        runLeftPower(leftSpeed);
        runRightPower(rightSpeed);
    }

    // Commands leftMaster's speed with a percentage
    public void runLeftPower(double speed) {
        leftMaster.set(ControlMode.PercentOutput, speed);
    }

    // Commands rightMaster's speed with a percentage
    public void runRightPower(double speed) {
        rightMaster.set(ControlMode.PercentOutput, speed);
    }

}