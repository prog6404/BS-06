
package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
// IMPORTS
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.Motors.Collector;



// CODE
public class Robot extends TimedRobot {
   RelativeEncoder m_encoder;

    /** Hardware */
    

    WPI_TalonSRX _talon = new WPI_TalonSRX(1);

    CANSparkMax m_motor = new CANSparkMax(2, MotorType.kBrushed);

    //m_encoder = m_motor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, 4096);
    XboxController _joy = new XboxController(Constants.Control_map._pilot);
  
      /* Nonzero to block the config until success, zero to skip checking */
      final int kTimeoutMs = 30;
    
      /**
     * If the measured travel has a discontinuity, Note the extremities or
     * "book ends" of the travel.
     */
    final boolean kDiscontinuityPresent = true;
    final int kBookEnd_0 = 910;		/* 80 deg */
    final int kBookEnd_1 = 1137;	/* 100 deg */
  
    /**
     * This function is called once on roboRIO bootup
     * Select the quadrature/mag encoder relative sensor
     */
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  private Collector n_Collector;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

      /* Factory Default Hardware to prevent unexpected behaviour */
      _talon.configFactoryDefault();
  
      /* Seed quadrature to be absolute and continuous */
      initQuadrature();
      
      /* Configure Selected Sensor for Talon */
      _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,	// Feedback
                        0, 											// PID ID
                        kTimeoutMs);								// Timeout
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
	}

	/**
	 * Seed the quadrature position to become absolute. This routine also
	 * ensures the travel is continuous.
	 */


  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {

  }

  public void initQuadrature() {
		/* get the absolute pulse width position */
		int pulseWidth = _talon.getSensorCollection().getPulseWidthPosition();

		/**
		 * If there is a discontinuity in our measured range, subtract one half
		 * rotation to remove it
		 */
		if (kDiscontinuityPresent) {

			/* Calculate the center */
			int newCenter;
			newCenter = (kBookEnd_0 + kBookEnd_1) / 2;
			newCenter &= 0xFFF;

			/**
			 * Apply the offset so the discontinuity is in the unused portion of
			 * the sensor
			 */
			pulseWidth -= newCenter;
		}

		/**
		 * Mask out the bottom 12 bits to normalize to [0,4095],
		 * or in other words, to stay within [0,360) degrees 
		 */
		pulseWidth = pulseWidth & 0xFFF;

		/* Update Quadrature position */
		_talon.getSensorCollection().setQuadraturePosition(pulseWidth, kTimeoutMs);
	}

	/**
	 * @param units CTRE mag encoder sensor units 
	 * @return degrees rounded to tenths.
	 */
	String ToDeg(double units) {
		double deg = units * 360.0 / 4096.0;

		/* truncate to 0.1 res */
		deg *= 10;
		deg = (int) deg;
		deg /= 10;

		return "" + deg;
	}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    //n_Collector.fall();
  }

  @Override
  public void teleopPeriodic() {

    
/**
		 * When button is pressed, seed the quadrature register. You can do this
		 * once on boot or during teleop/auton init. If you power cycle the 
		 * Talon, press the button to confirm it's position is restored.
		 */
		if (_joy.getYButton()) {
			initQuadrature();
		}

		/**
		 * Quadrature is selected for soft-lim/closed-loop/etc. initQuadrature()
		 * will initialize quad to become absolute by using PWD
		 */
		double selSenPos = _talon.getSelectedSensorPosition(0);
		int pulseWidthWithoutOverflows = 
				_talon.getSensorCollection().getPulseWidthPosition() & 0xFFF;

		/**
		 * Display how we've adjusted PWM to produce a QUAD signal that is
		 * absolute and continuous. Show in sensor units and in rotation
		 * degrees.
		 */
		SmartDashboard.putNumber("pulseWidPos:", pulseWidthWithoutOverflows);
    SmartDashboard.putNumber("SelSenPos", selSenPos);
		SmartDashboard.putString("pulseWidDeg:", ToDeg(pulseWidthWithoutOverflows));
    SmartDashboard.putString("SelSenDeg", ToDeg(selSenPos));

  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}
}
