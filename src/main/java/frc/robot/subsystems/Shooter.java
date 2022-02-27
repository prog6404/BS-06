
package frc.robot.subsystems;

// IMPORTS
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// CODE
public class Shooter extends SubsystemBase {

  // CRIANDO OS CONTROLADORES DO SISTEMA DE SHOOTER, PITCH E YAW
  private VictorSPX _left, _right, _yaw, _pitch;

  // CRIANDO O SERVO
  private Servo _servo;

  // CRIANDO OS SENSORES DO SISTEMA DE PITCH E YAW
  public Encoder _encoderpitch;
  public DigitalInput _limit_left;
  public DigitalInput _limit_right;
  public DigitalInput _limit_center;

  public Shooter() {
    
    // DEFININDO OS CONTROLADORES DO SISTEMA DE SHOOTER, PITCH E YAW
    _pitch = new VictorSPX(Constants.Motors.Shooter._pitch);
    _yaw = new VictorSPX(Constants.Motors.Shooter._yaw);

    _left = new VictorSPX(Constants.Motors.Shooter._left);
    _right = new VictorSPX(Constants.Motors.Shooter._right);

    // DEFININDO O SERVO
    _servo = new Servo(9);

    // DEFININDO OS SENSORES DO SISTEMA DE PITCH E YAW
    _encoderpitch = new Encoder(Constants.Encoders._enc_pitch1,Constants.Encoders._enc_pitch2);
    _encoderpitch.setDistancePerPulse(1/44.4);
    _encoderpitch.setReverseDirection(true);

    _limit_left = new DigitalInput(Constants.Sensors._limit_left);
    _limit_right = new DigitalInput(Constants.Sensors._limit_right);
    _limit_center = new DigitalInput(Constants.Sensors._limit_center);

  }

  // CRIANDO FUNCAO DO SHOOTER
  public void shoot(double s) {
    _left.set(ControlMode.PercentOutput, s);
    _right.follow(_left);
  }

  // CRIANDO FUNCAO DO PITCH
  public void angle(double a) {
    _pitch.set(VictorSPXControlMode.PercentOutput, a);
  }

  // CRIANDO FUNCAO DO YAW
  public void rotation(double y) {
    _yaw.set(VictorSPXControlMode.PercentOutput, y);
  }

  // CRIANDO FUNCAO DE ANGULACAO EM GRAUS DO PITCH
  public double EP() {
    // RETORNA QUANTIDADE DE GRAU ATUAL
    return (_encoderpitch.get() * 0.2895);
  }

  // CRIANDO FUNCAO DE ANGULACAO DO SERVO
  public void servomov(double servoangle) {
    _servo.setAngle(servoangle);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("key", EP());
  }
}
