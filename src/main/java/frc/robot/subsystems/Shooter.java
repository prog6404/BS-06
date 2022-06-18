
package frc.robot.subsystems;

// IMPORTS
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

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
  private CANSparkMax _left, _right;
  private WPI_TalonSRX _yaw;

  // CRIANDO O SERVO
  private Servo _pitch;

  // CRIANDO OS SENSORES DO SISTEMA DE PITCH E YAW
  private DigitalInput _limit_left;
  private DigitalInput _limit_right;
  private DigitalInput _limit_center;

  //PID ENCODER
  public SparkMaxPIDController _pidController;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, rotations;
  public RelativeEncoder _shoEnc;

  

  public Shooter() {

    // DEFININDO OS CONTROLADORES DO SISTEMA DE SHOOTER, PITCH E YAW
    _yaw = new WPI_TalonSRX(Constants.Motors.Shooter._yaw);

    _left  = new CANSparkMax(Constants.Motors.Shooter._left, MotorType.kBrushed);
    _right = new CANSparkMax(Constants.Motors.Shooter._right, MotorType.kBrushed);

    _pitch = new Servo(Constants.Motors.Shooter._pitch);

    // DEFININDO OS SENSORES DO SISTEMA DE PITCH E YAW

    _limit_left   = new DigitalInput(Constants.Sensors._limit_left);
    _limit_right  = new DigitalInput(Constants.Sensors._limit_right);
    _limit_center = new DigitalInput(Constants.Sensors._limit_center);

    _left.setInverted(true);
    _left.follow(_right);

    _shoEnc = _right.getEncoder(Type.kQuadrature, 4096);
    
    _right.restoreFactoryDefaults();

    /**
     * In order to use PID functionality for a controller, a SparkMaxPIDController object
     * is constructed by calling the getPIDController() method on an existing
     * CANSparkMax object
     */
    _pidController = _right.getPIDController();
  
    /**
     * The PID Controller can be configured to use the analog sensor as its feedback
     * device with the method SetFeedbackDevice() and passing the PID Controller
     * the CANAnalog object. 
     */
    _pidController.setFeedbackDevice(_shoEnc);

    // PID coefficients
    kP         = 0.1; 
    kI         = 1e-4;
    kD         = 1; 
    kIz        = 0; 
    kFF        = 0; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    rotations  = 0.0;

    // set PID coefficients
    _pidController.setP(kP);
    _pidController.setI(kI);
    _pidController.setD(kD);
    _pidController.setIZone(kIz);
    _pidController.setFF(kFF);
    _pidController.setOutputRange(kMinOutput, kMaxOutput);

    _shoEnc.setPosition(0.0);

  }

  /*
  public void shoot(double s) {
    _right.set(s);
  }
  */

// CRIANDO FUNCAO DO SHOOTER
public void shootActv(int rpm){

  
  
  PIDController shoPid = new PIDController(kP, kI, kD);

  _right.set(shoPid.calculate(_shoEnc.getPosition(), rpm / 60));

}

  // CRIANDO FUNCAO DO YAW
  public void rotation(double y) {
    if  (_limit_right.get() && y > 0.0){
      _yaw.set(0.0);
    } else if (_limit_left.get() && y < 0.0) {
      _yaw.set(0.0);
    } else {
      _yaw.set(y);
  }
}

  public boolean limitcenteryaw() {
    return _limit_center.get();
  }

  // CRIANDO FUNCAO DE ANGULACAO DO SERVO
  public void servomov(double servoangle) {
    _pitch.setAngle(servoangle);
  }

  @Override
  public void periodic() {
    //Pegando a função do contador com o valor
    SmartDashboard.putBoolean("Limit", limitcenteryaw());

    double p   = kP;
    double i   = kI;
    double d   = kD;
    double iz  = kIz;
    double ff  = kFF;
    double max = kMaxOutput;
    double min = kMinOutput;
    double rot = rotations;

    if((p != kP)) { _pidController.setP(p); kP = p; }
    if((i != kI)) { _pidController.setI(i); kI = i; }
    if((d != kD)) { _pidController.setD(d); kD = d; }
    if((iz != kIz)) { _pidController.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { _pidController.setFF(ff); kFF = ff; }
    if((max != kMaxOutput) || (min != kMinOutput)) { 
      _pidController.setOutputRange(min, max); 
      kMinOutput = min; kMaxOutput = max; 
    }

    _pidController.setReference(rot, CANSparkMax.ControlType.kPosition);

  }
}
