
package frc.robot;

// IMPORTS
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.Autonomo;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;

// CODE
public class RobotContainer {

  //#region CRIANDO OS OBJETOS

  // CONTROLES (PILOTO E CO_PILOTO)
  XboxController pilot;
  XboxController co_pilot;

  // SUBSISTEMA COM PADRAO M_ NA FRENTE
  Drivetrain m_drive;
  Collector m_coll;
  Storage m_stor;
  // Climber m_climb;
  Shooter m_shot;

  // COMANDO COM PADRAO C_ NA FRENTE
  Autonomo c_auto;
  Timer _d;
  // SENSORES, ETC PADRAO _ NA FRENTE
  Timer _t;

  // Camera _servo;
  AnalogPotentiometer _poten;

  //PID de controle do yaw
  PIDController yawControl;

  //#endregion
 
  public RobotContainer() {

  //#region DEFINIÇAO
  
    // DEFININDO CONTROLES (PILOTO E CO_PILOTO)
    pilot    = new XboxController(Constants.Control_map._pilot);
    co_pilot = new XboxController(Constants.Control_map._copilot);

    // DEFININDO SUBSISTEMAS NO CONTAINER
    m_drive = new Drivetrain();
    //m_coll  = new Collector();
    m_shot   = new Shooter();
    //m_stor   = new Storage();

    // DEFININDO SENSORES, ETC
    _t = new Timer();

    configureButtonBindings();
 
    //#endregion
  
  }

  private void configureButtonBindings() {

    //#region DRIVETRAIN
    /*
    m_drive.setDefaultCommand(new RunCommand(() -> {
      m_drive.direction(pilot.getRightX(), -pilot.getLeftY());
    }, m_drive));
    //*/

    //#endregion

    //#region COLLECTOR
  /*
   m_coll.setDefaultCommand(new RunCommand(() -> {

    // CONTROLE SOLENOID
    if(co_pilot.getAButton()) {
      m_coll.collectorSolenoid(true);
    } else {
      m_coll.collectorSolenoid(false);
    }

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
  //*/
    

    //#endregion
 
    //#region SHOOTER
    
    m_shot.setDefaultCommand(new RunCommand(() -> {

      SmartDashboard.putBoolean("boo", co_pilot.getAButtonPressed());
      // SHOOTER
      if (co_pilot.getAButton()) {
        m_shot.shootActv();
        System.out.println("jdsjdsjdo");
      }
      else m_shot.shootDisab();

      if (!m_shot.isLimelightDetected()) {
     
        // CONTROLE MANUAL PITCH/YAW
        if (co_pilot.getRightX() > 0) m_shot.rotation(0.5);
        else if (co_pilot.getRightX() < 0) m_shot.rotation(-0.5);
        else m_shot.rotation(0.0);

        m_shot.servoMov(co_pilot.getLeftY() / 2 + 0.5);
        
      } else {
      
        // CONTROLE AUTOMATICO PITCH/YAW LIMELIGHT
        //m_shot.limelightPitchControl();
        //m_shot.limelightYawControl();
      
      }
      
      

    }, m_shot));

    //*/
    //#endregion
    
    //#region STORAGE
    /*
    m_stor.setDefaultCommand(new RunCommand(() -> {
      
      // STORAGE
      if (pilot.getAButton()) {
        m_stor.stor(0.5);
      } else if (pilot.getBButton()) { // igual
        m_stor.stor(-0.5);
      } else {
        m_stor.stor(0);
      }

    }, m_stor));
    //*/
    //#endregion

    //#region TESTES

    
     //*/

      //#endregion
  
  }

  // COMANDO AUTONOMO
  public Command getAutonomousCommand() {
    return null; //c_auto;
  }
}