
package frc.robot.subsystems;

// IMPORTS
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// CODE
public class Shooter extends SubsystemBase {

  // CRIANDO OS CONTROLADORES DO SISTEMA DE SHOOTER, PITCH E YAW
  private VictorSPX _left, _right, _yaw, _pitch;

  // CRIANDO O SERVO
  private Servo _servo;

  // CRIANDO OS SENSORES DO SISTEMA DE PITCH E YAW
  private Encoder _encoderpitch;
  private DigitalInput _limit_p_up;
  private DigitalInput _limit_p_short;
  private Encoder _encodershooter;
  private DigitalInput _limit_left;
  private DigitalInput _limit_right;
  private DigitalInput _limit_center;
  private PIDController _pid;

  public Shooter() {

    // DEFININDO OS CONTROLADORES DO SISTEMA DE SHOOTER, PITCH E YAW
    _pitch = new VictorSPX(Constants.Motors.Shooter._pitch);
    _yaw = new VictorSPX(Constants.Motors.Shooter._yaw);

    _left = new VictorSPX(Constants.Motors.Shooter._left);
    _right = new VictorSPX(Constants.Motors.Shooter._right);

    // DEFININDO O SERVO
    _servo = new Servo(9);

    // DEFININDO OS SENSORES DO SISTEMA DE PITCH E YAW
    _encoderpitch = new Encoder(Constants.Encoders._enc_pitch1, Constants.Encoders._enc_pitch2);
    _encoderpitch.setDistancePerPulse(1 / 44.4);
    _encoderpitch.setReverseDirection(true);

    _limit_p_up = new DigitalInput(Constants.Sensors._limit_p_up);
    _limit_p_short = new DigitalInput(Constants.Sensors._limit_p_short);

    _limit_left = new DigitalInput(Constants.Sensors._limit_left);
    _limit_right = new DigitalInput(Constants.Sensors._limit_right);
    _limit_center = new DigitalInput(Constants.Sensors._limit_center);

    _encodershooter = new Encoder(Constants.Encoders._enc_yaw1, Constants.Encoders._enc_yaw2);
    _encodershooter.setDistancePerPulse(1 / 48.0);
    _pid = new PIDController(0.005, 0.0, 0.005);
    _pid.setSetpoint(4000);
  }

  // CRIANDO FUNCAO DO SHOOTER
  public void shoot(double s) {
    _left.set(ControlMode.PercentOutput, s);
    _right.follow(_left);
  }

  // CRIANDO FUNCAO DO PID
  public void fpid() {
    _left.set(ControlMode.PercentOutput, _pid.calculate(_encodershooter.getRate() * 60));
  }

  // CRIANDO FUNCAO DO PITCH
  public void angle(double a) {
    if(encoderpitch() >= 18 && _limit_p_short.get()) {
      _pitch.set(VictorSPXControlMode.PercentOutput, 0.0);
    } if (encoderpitch() >= 0 && _limit_p_up.get()) {
    _pitch.set(VictorSPXControlMode.PercentOutput, 0.0);
    } else {
      _pitch.set(VictorSPXControlMode.PercentOutput, a);
    }
  }

  // CRIANDO FUNCAO DO YAW
  public void rotation(double y) {
    if  (_limit_right.get() && y > 0.0){
    _yaw.set(VictorSPXControlMode.PercentOutput, 0.0);
    } else if (_limit_left.get() && y < 0.0) {
      _yaw.set(VictorSPXControlMode.PercentOutput, 0.0);
    } else {
      _yaw.set(VictorSPXControlMode.PercentOutput, y);
    }
  }

  public boolean limitcenteryaw() {
    return _limit_center.get();
  }

  // CRIANDO FUNCAO DE ANGULACAO EM GRAUS DO PITCH
  public double encoderpitch() {
    // RETORNA QUANTIDADE DE GRAU ATUAL
    return (_encoderpitch.get() * 0.2895);
  }

  // CRIANDO FUNCAO DE ANGULACAO DO SERVO
  public void servomov(double servoangle) {
    _servo.setAngle(servoangle);
  }


  // FUNCAO GET ENCODER
  public double encodershooterget() {
    return _encodershooter.get();
  }

  // FUNCAO RATE ENCODER
  public double encodershooterrate() {
    return _encodershooter.getRate();
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Limit", limitcenteryaw());
  }
}
