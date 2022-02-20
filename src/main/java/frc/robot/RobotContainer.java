
package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.vmx.AHRSJNI;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.CameraServo;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class RobotContainer {

  // Criando os objetos dos nossos subsistemas
  XboxController control;
  XboxController control_2;
  Drivetrain drive;
  Collector cole;
  Storage sto;
  Climber climb;
  Shooter sho;
  Timer t;
  CameraServo _servo;
  AnalogPotentiometer potenciometro;
  // AHRS navx;

  public RobotContainer() {

    // Definindo os objetos dos subsistemas no robotcontainer
    control = new XboxController(Constants.Control_map._pilot);
    control_2 = new XboxController(Constants.Control_map._copilot);
    drive = new Drivetrain();
    cole = new Collector();
    sho = new Shooter();
    t = new Timer();
    sto = new Storage();
    climb = new Climber();
    _servo = new CameraServo();
    // navx = new AHRS(Port.kUSB);

    // POTENCIOMETRO CODIGO INTEIRO DELE FUNCIONAL potenciometro = new
    // AnalogPotentiometer(0, 90, 0);

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    /*
     * // Drivetrain
     * drive.setDefaultCommand(new RunCommand(() -> {
     * drive.direction(-control.getLeftY(), control.getRightX());
     * }, drive));
     */
    // Collector
    cole.setDefaultCommand(new RunCommand(() -> {

      // Collector
      if (control.getRightBumper()) {
        cole.collect(0.2);
      } else if (control.getLeftBumper()) { // igual
        cole.collect(-0.5);
      } else {
              // Limits do coletor
      if (!cole.limitswitchBAIXO.get() && control.getAButton()) {
        cole.collect(0.0);
      } else if (!cole.limitswitchCIMA.get()) {
        cole.collect(0.0);
      } else {
        cole.collect(0.0);
      }
      }

      // TESTE POTENCIOMETRO FUNCIONAL
      /*
       * if (potenciometro.get() > 65) {
       * cole.collect(0.4);
       * }
       */

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
    sho.setDefaultCommand(new RunCommand(() -> {

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

      if (control.getRightY() > 0) {
        sho.angle(0.6);
      } else if (control.getRightY() < 0) {
        sho.angle(-0.6);
      } else {
        if (control.getXButton() && sho.EP() <= 9) {
          sho.angle(-0.6);
        } else {
          sho.angle(0.0);
        }
      }

      // Yaw
      if (control_2.getLeftX() > 0) {
        sho.rotation(0.5);
      } else if (control_2.getLeftX() < 0) {
        sho.rotation(-0.5);
      }

    }, sho));

    // Storage
    sto.setDefaultCommand(new RunCommand(() -> {
      SmartDashboard.putBoolean("estado", sto.estado);
      // comando controle
      if (control_2.getLeftTriggerAxis() > 0) {
        sto.stor(0.5);
      } else if (control_2.getLeftBumper()) { // igual
        sto.stor(-0.5);
      }
      // Sensor Storage
      if (sto.sensorS1()) {
        sto.stor(0.8);
      }

    }, sto));

    // Climb
    /*
     * climb.setDefaultCommand(new RunCommand(() -> {
     * if (control.getBButton()) {
     * climb.climbing(0.5);
     * } else {
     * climb.climbing(0.0);
     * }
     * }, climb));
     */
    // Servo
    _servo.setDefaultCommand(new RunCommand(() -> {
      if (control.getXButton()) {
        _servo.servomov(sho.EP());
      } 
    }, _servo));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
