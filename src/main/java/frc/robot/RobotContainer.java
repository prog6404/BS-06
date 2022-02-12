
package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.CameraServo;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class RobotContainer {

  //Criando os objetos dos nossos subsistemas
  XboxController control;
  XboxController control_2;
  Drivetrain drive;
  Collector cole;
  Storage sto;
  Climber climb;
  Shooter sho;
  Timer t;
  CameraServo servoaaa;

  public RobotContainer() {
    //Definindo os objetos dos subsistemas no robotcontainer
    control = new XboxController(Constants.Control_map._pilot);
    control_2 = new XboxController(Constants.Control_map._copilot);
    drive = new Drivetrain();
    cole = new Collector();
    sho = new Shooter();
    t = new Timer();
    sto = new Storage();
    climb = new Climber();
    servoaaa = new CameraServo();

    configureButtonBindings();
  }

  private void configureButtonBindings() {

    // Drivetrain
    drive.setDefaultCommand( new RunCommand(() -> {
      drive.direction(-control.getLeftY(), control.getRightX()); 
    }, drive));

    //Collector
    cole.setDefaultCommand(new RunCommand(() -> {
     
      // Collector
      if (control.getRightBumper()) {
      cole.collect(0.5);
      }else if (control.getLeftBumper()){ // igual
      cole.collect(-0.5);
      }else {
      cole.collect(0.0);
      }

      // Move Collector
      if (control_2.getAButton()) {
      cole.move_c(0.5);
      } else if (control_2.getBButton()) {
      cole.move_c(-0.5);
      } else {
      cole.move_c(0.0);
      }
                         
    }, cole));

    // Shooter
    sho.setDefaultCommand( new RunCommand(() -> {
      
      // Shooter
      if (control.getRightTriggerAxis() > 0) {
      t.start();
      sho.shoot(t.get() * 0.5);
      } else {
      sho.shoot(0.0);
      t.stop();
      t.reset();
      }

      // Pitch
      if (control_2.getRightY() > 0) {
      sho.angle(0.5);
      } else if (control_2.getRightY() < 0) {
      sho.angle(-0.5);
      } else {
      sho.angle(0.0);
      }

      // Yaw
      if (control_2.getLeftX() > 0) {
      sho.rotation(0.5);
      } else if (control_2.getLeftX() < 0) {
      sho.rotation(-0.5);
      }

    }, sho));

    // Storage
    sto.setDefaultCommand( new RunCommand(() -> {
      if (control_2.getLeftTriggerAxis() > 0) {
      sto.stor(0.5);
      } else if (control_2.getLeftBumper()) { //igual
      sto.stor(-0.5);
      } else {
      sto.stor(0.0);
      }
    }, sto));

    // Climb
    climb.setDefaultCommand(new RunCommand(()-> {
      if (control_2.getYButton()){
      climb.climbing(0.5);
      } else {
      climb.climbing(0.0);
      }
    }, climb)); 
    
    //Servo
    servoaaa.setDefaultCommand(new RunCommand(() -> {
      if (control.getAButton() == true) {
        servoaaa.servomov(180);
      } else {
        servoaaa.servomov(0);

      }
     }, servoaaa));
      
    }
  
  public Command getAutonomousCommand() {
    return null;
  }
}
