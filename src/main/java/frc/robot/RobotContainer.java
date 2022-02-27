
package frc.robot;

// IMPORTS
import com.kauailabs.navx.frc.AHRS;
// import com.kauailabs.vmx.AHRSJNI;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.SerialPort.Port;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.Autonomo;
import frc.robot.subsystems.Camera;
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
  Climber m_climb;
  Shooter m_sho;

  // COMANDO COM PADRAO C_ NA FRENTE
  Autonomo c_auto;
  
  // SENSORES, ETC PADRAO _ NA FRENTE
  Timer _t;
  Camera _servo;
  AnalogPotentiometer _poten;
  AHRS _navx;

  public RobotContainer() {

    // DEFININDO CONTROLES (PILOTO E CO_PILOTO)
    pilot = new XboxController(Constants.Control_map._pilot);
    co_pilot = new XboxController(Constants.Control_map._copilot);
    
    // DEFININDO SUBSISTEMAS NO CONTAINER
    m_drive = new Drivetrain();
    m_coll = new Collector();
    m_sho = new Shooter();
    m_sto = new Storage();
    m_climb = new Climber();

    // DEFININDO SENSORES, ETC
    _t = new Timer();
    _servo = new Camera();
    _poten = new AnalogPotentiometer(0, 90, 0);
    
    //TESTE
    /* 
    navx = new AHRS(Port.kUSB); 
    */

    configureButtonBindings();
  }

  private void configureButtonBindings() {

    // DRIVETRAIN
    m_drive.setDefaultCommand(new RunCommand(() -> {
      m_drive.direction(-pilot.getLeftY(), pilot.getRightX());
    }, m_drive));

    // COLLECTOR
    m_coll.setDefaultCommand(new RunCommand(() -> {
      
      // COLLECTOR
      if (pilot.getRightTriggerAxis() > 0) {
        _t.start();
        m_coll.collect(_t.get() * 0.5);
      } else if (pilot.getBButton()) {
        m_coll.collect(-0.5);
      } else {
        m_coll.collect(0.0);
        _t.stop();
        _t.reset();
      }

      // MOVE COLLECTOR
      if (m_coll._limitSHORT.get() && pilot.getLeftTriggerAxis() > 0) {
        m_coll.collect(0.0);
      } else if (m_coll._limitUP.get() && pilot.getLeftBumper()) {
        m_coll.collect(0.0);
      } else {
        if (pilot.getLeftTriggerAxis() > 0) {
          m_coll.move_c(0.5);
        } else if (co_pilot.getLeftBumper()) {
          m_coll.move_c(-0.5);
        } else
          m_coll.move_c(0.0);
        }

    }, m_coll));

    // SHOOTER
    m_sho.setDefaultCommand(new RunCommand(() -> {

      // SHOOTER
      if (co_pilot.getRightTriggerAxis() > 0) {
        _t.start();
        m_sho.shoot(_t.get() * 0.5);
      } else {
        m_sho.shoot(0.0);
        _t.stop();
        _t.reset();
      }

      // PITCH
      if (pilot.getLeftY() > 0 && m_sho.EP() <= 18) {
        m_sho.angle(-0.5);
      } else if (pilot.getLeftY() < 0 && m_sho.EP() >= 0) {
        m_sho.angle(0.5);
      } else {
        m_sho.angle(0.0);
      }

      // YAW
      if (co_pilot.getRightX() > 0) {
        m_sho.rotation(0.5);
      } else if (co_pilot.getRightX() < 0) {
        m_sho.rotation(-0.5);
      }
      
      // SENSORS YAW
      if (m_sho._limit_right.get() && co_pilot.getRightX() > 0) {
        m_sho.rotation(0.0);
      } else if (m_sho._limit_left.get() && co_pilot.getRightX() < 0) {
        m_sho.rotation(0.0);
      } else if (m_sho._limit_center.get()){
        SmartDashboard.getBoolean("PONTO 0", true);
      } else {
        if (co_pilot.getRightX() > 0) {
          m_sho.rotation(0.5);
        } else if (co_pilot.getRightX() < 0) {
          m_sho.rotation(-0.5);
        } else
          m_sho.rotation(0.0);
        }

    }, m_sho));

    // STORAGE
    m_sto.setDefaultCommand(new RunCommand(() -> {
      SmartDashboard.putBoolean("estado", m_sto.estado);
      
      // STORAGE
      if (co_pilot.getLeftTriggerAxis() > 0) {
        m_sto.stor(0.5);
      } else if (co_pilot.getBButton()) { // igual
        m_sto.stor(-0.5);
      }
      
      // SENSOR STORAGE
      if (m_sto.sensorS1()) {
        m_sto.stor(0.8);
      }

    }, m_sto));

    // CLIMB
    m_climb.setDefaultCommand(new RunCommand(() -> {
      
      // CLIMB
      if (co_pilot.getRightBumper()) {
        m_climb.climbing(0.5);
      } else if (co_pilot.getLeftBumper()) {
        m_climb.climbing(-0.5);
      } else {
        m_climb.climbing(0.0);
      }

      // RISE CLIMB
      if (co_pilot.getYButton()) {
        m_climb.rise_climber(0.5);
      } else {
        m_climb.rise_climber(0.0);
      }

    }, m_climb));

    // SERVO
    _servo.setDefaultCommand(new RunCommand(() -> {
      if (co_pilot.getLeftY() > 0) {
        m_sho.servomov(m_sho.EP());
      } else if (co_pilot.getBButton()) {
        m_sho.servomov(m_sho.EP());
      } else {
        m_sho.servomov(0.0);
      }
    }, m_sho));

    // TESTES
    /*
     * // TESTE POTENCIOMETRO FUNCIONAL
     * if (potenciometro.get() > 65) {
     * cole.collect(0.4);
     * }
     */

  }

  // COMANDO AUTONOMO
  public Command getAutonomousCommand() {
    return c_auto;
  }
}
