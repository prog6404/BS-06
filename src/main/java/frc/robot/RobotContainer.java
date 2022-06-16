
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.controller.PIDController;

// IMPORTS
// import com.kauailabs.vmx.AHRSJNI;

//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.SerialPort.Port;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LimelightDetector;
import frc.robot.commands.Autonomo;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
// import pabeles.concurrency.ConcurrencyOps.Reset;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

// CODE
public class RobotContainer {

  // CRIANDO OS OBJETOS

  // CONTROLES (PILOTO E CO_PILOTO)
  XboxController pilot;
  XboxController co_pilot;

  // SUBSISTEMA COM PADRAO M_ NA FRENTE
  Drivetrain m_drive;
  Collector m_coll;
  Storage m_sto;
  // Climber m_climb;
  Shooter m_sho;

  LimelightDetector m_limelight;

  // COMANDO COM PADRAO C_ NA FRENTE
  Autonomo c_auto;
  Timer _d;
  // SENSORES, ETC PADRAO _ NA FRENTE
  Timer _t;

  // Camera _servo;
  AnalogPotentiometer _poten;

  //PID de controle do yaw
  PIDController yawControl;

  private TalonSRX taloncontrl;

 
  public RobotContainer() {

    yawControl = new PIDController(Constants.YawControl._kp, Constants.YawControl._ki, Constants.YawControl._kd);
  
    // DEFININDO CONTROLES (PILOTO E CO_PILOTO)
    pilot    = new XboxController(Constants.Control_map._pilot);
    co_pilot = new XboxController(Constants.Control_map._copilot);

    // DEFININDO SUBSISTEMAS NO CONTAINER
    m_drive     = new Drivetrain();
    m_coll      = new Collector();
    m_sho       = new Shooter();
    m_sto       = new Storage();
    m_limelight = new LimelightDetector();

    // m_climb = new Climber();

    // DEFININDO SENSORES, ETC
    _t = new Timer();

    // _servo = new Camera();
    _poten = new AnalogPotentiometer(0, 90, 0);

    // TESTE
    /*
     * navx = new AHRS(Port.kUSB);
     */

    configureButtonBindings();
  }

  private void configureButtonBindings() {

    // DRIVETRAIN
    m_drive.setDefaultCommand(new RunCommand(() -> {
      m_drive.direction(pilot.getRightX(), -pilot.getLeftY());
    }, m_drive));

    // COLLECTOR
   /* m_coll.setDefaultCommand(new RunCommand(() -> {

      /*
      // MOVE COLLECTOR
      if (co_pilot.getLeftTriggerAxis() > 0) {
        m_coll.move_c(0.9);
      } else if (co_pilot.getLeftBumper()) {
        m_coll.move_c(-0.3);
      } else {
        m_coll.move_c(0.0);
      }
      

      // COLLECTOR
      if (pilot.getRightTriggerAxis() > 0) {
        m_coll.collect(0.5);
      } else if (pilot.getLeftTriggerAxis() > 0) {
        m_coll.collect(-0.5);
      } else {
        m_coll.collect(0.0);
      }
    }, m_coll));
*/

    m_limelight.setDefaultCommand(new RunCommand(() -> {

    }, m_limelight));

    taloncontrl = new TalonSRX(1);

    taloncontrl.getSensorCollection();

    SmartDashboard.putNumber("Posicao", taloncontrl.getSelectedSensorPosition());

    // SHOOTER
    m_sho.setDefaultCommand(new RunCommand(() -> {

      // SHOOTER

      if (co_pilot.getRightTriggerAxis() > 0) {
        _t.start();
        if (_t.get() >= 2) {
          //m_sho.fpid();
        } else {
          double d = Math.min(_t.get() * .5, 0.8556);
          m_sho.shoot(0.3);
          SmartDashboard.putNumber("d", d);
        }
      } else {
        m_sho.shoot(0.0);
        _t.reset();
      }

      /*
      // ENCODER SHOOTER
      if (m_sho.encodershooterget() > 1) {
        SmartDashboard.putNumber("Encoder Shooter", m_sho.encodershooterrate() * 60);
      }
      */

      
      // YAW (PID)

      yawControl.setSetpoint(0.0);
      yawControl.setTolerance(7.0);

      if (m_limelight.limeligtUpdate('v') == 0) {
        
        if (co_pilot.getRightX() > 0) m_sho.rotation(0.5);
        else if (co_pilot.getRightX() < 0) m_sho.rotation(-0.5);
        else m_sho.rotation(0.0);
        
      }
      else {

        m_sho.rotation(yawControl.calculate(m_limelight.limeligtUpdate('x')));

      }
      SmartDashboard.putNumber("PidYaw", yawControl.calculate(m_limelight.limeligtUpdate('x')));
      
      /*
      // YAW (SEM PID)
      if (m_limelight.limeligtUpdate('v') == 0) {
        
        if (co_pilot.getRightX() > 0) m_sho.rotation(0.1);
        else if (co_pilot.getRightX() < 0) m_sho.rotation(-0.1);
        else m_sho.rotation(0.0);
        
      }
      else if (m_limelight.limeligtUpdate('x') < -7.0) {
        m_sho.rotation(0.1);
      }
      else if (m_limelight.limeligtUpdate('x') > 7.0) {
        m_sho.rotation(-0.1);
      }
      else {
        m_sho.rotation(0.0);
      }
      */

      // PITCH

      if (co_pilot.getLeftY() > 0) {
        m_sho.angle(-0.5);
      } else if (co_pilot.getLeftY() < 0) {
        m_sho.angle(0.5);
      } else {
        m_sho.angle(0.0);
      }

    }, m_sho));

    // STORAGE
    m_sto.setDefaultCommand(new RunCommand(() -> {

      // STORAGE
      if (pilot.getAButton()) {
        m_sto.stor(0.5);
      } else if (pilot.getLeftTriggerAxis() > 0) { // igual
        m_sto.stor(-0.5);
      } else {
        m_sto.stor(0);
      }

    }, m_sto));

    // CLIMB

    // m_climb.setDefaultCommand(new RunCommand(() -> {

    // CLIMB
    /*
     * if (co_pilot.getRightBumper()) {
     * m_climb.climbing(0.5);
     * } else if (co_pilot.getLeftBumper()) {
     * m_climb.climbing(-0.5);
     * } else {
     * m_climb.climbing(0.0);
     * }
     * 
     * // RISE CLIMB
     * if (co_pilot.getYButton()) {
     * m_climb.rise_climber(0.5);
     * } else {
     * m_climb.rise_climber(0.0);
     * }
     * 
     * }, m_climb));
     * 
     */
    // TESTES
    /*
     * // TESTE POTENCIOMETRO FUNCIONAL
     * if (potenciometro.get() > 65) {
     * cole.collect(0.4);
     * }
     * 
     * 
     * // SERVO
     * _servo.setDefaultCommand(new RunCommand(() -> {
     * if (co_pilot.getLeftY() > 0) {
     * m_sho.servomov(m_sho.EP());
     * } else if (co_pilot.getBButton()) {
     * m_sho.servomov(m_sho.EP());
     * } else {
     * m_sho.servomov(0.0);
     * }
     * }, m_sho));
     */

  }

  // COMANDO AUTONOMO
  public Command getAutonomousCommand() {
    return c_auto;
  }
}