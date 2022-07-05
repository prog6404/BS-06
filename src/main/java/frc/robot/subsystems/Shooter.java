
package frc.robot.subsystems;

// IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// CODE
public class Shooter extends SubsystemBase {

  // #region CRIAÇAO DAS VARIAVEIS
  // CRIANDO OS CONTROLADORES DO SISTEMA DE SHOOTER, PITCH E YAW
  private CANSparkMax _left, _right;
  private WPI_TalonSRX _yaw;

  // CRIANDO O SERVO
  private Servo _pitch;

  // CRIANDO OS SENSORES DO SISTEMA DE PITCH E YAW
  private DigitalInput _limit_left, _limit_right;

  // PID ENCODER
  private SparkMaxPIDController _pidController;
  private double _kP, _kI, _kD, _kIz, _kFF, _kMaxOutput, _kMinOutput, _rotations;
  private RelativeEncoder _shoEnc;

  // LIMELGHT
  private NetworkTable _table;
  private NetworkTableEntry _tx, _tv, _ta, _ty;

  // PID YAW
  private PIDController _yawPID;

  // SHOOTER CONTROLE RPM
  private boolean _shootActv = false;
  private double _RPM        = 5000.0;
  private double _shoVel     = 0.0;

  // PITCH
  private double _minAngle    = 16;
  private double _maxAngle    = 40;
  private double _maxPosition = 1.0;

  //CALCULO DE DISTANCIA
  private double _valConvr = 215.0; //Valor de converçao do teste: raizQuadrada(altura^2 + distancia^2) * (porcentagem da area da fita)
  private double _catHub   = Math.pow(238.0, 2); //Cateto da altura do hub


  //#endregion

  public Shooter() {

    //#region INICIALIZAÇAO DOS SISTEMAS

    // DEFININDO OS CONTROLADORES DO SISTEMA DE SHOOTER, PITCH E YAW
    //_yaw   = new WPI_TalonSRX(Constants.Motors.Shooter._yaw);
    //_left  = new CANSparkMax(Constants.Motors.Shooter._left, MotorType.kBrushed);
    _right = new CANSparkMax(Constants.Motors.Shooter._right, MotorType.kBrushed);
    _pitch = new Servo(Constants.Motors.Shooter._pitch);

    // DEFININDO OS SENSORES DO SISTEMA DE PITCH E YAW
    _limit_left   = new DigitalInput(Constants.Sensors._limit_left);
    _limit_right  = new DigitalInput(Constants.Sensors._limit_right);

    // TABELA DE VALORES LIMELIGHT
    _table = NetworkTableInstance.getDefault().getTable("limelight");
    _tx = _table.getEntry("tx");
    _tv = _table.getEntry("tv");
    _ta = _table.getEntry("ta");
    _ty = _table.getEntry("ty");


    // INVERSAO DA DIREÇAO DO MOTOR SHOOTER
    //_right.setInverted(true);
    //_left.setInverted(true);
    //_left.follow(_right);
    

    //#region ENCODER SHOOTER

    // DEFINE ENCODER SHOOTER
    _right.restoreFactoryDefaults();
    _shoEnc = _right.getEncoder(Type.kQuadrature, 4096);

    // PID DE CORREÇAO DO VALOR DO ENCODER
    _pidController = _right.getPIDController();
    _pidController.setFeedbackDevice(_shoEnc);

    // PID COEFICIENTES
    _kP         = 0.1; 
    _kI         = 1e-4;
    _kD         = 1; 
    _kIz        = 0; 
    _kFF        = 0; 
    _kMaxOutput = 1; 
    _kMinOutput = -1;
    _rotations  = 0.0;

    // DEFINE PID COEFICIENTES
    _pidController.setP(_kP);
    _pidController.setI(_kI);
    _pidController.setD(_kD);
    _pidController.setIZone(_kIz);
    _pidController.setFF(_kFF);
    _pidController.setOutputRange(_kMinOutput, _kMaxOutput);

    // RESET ENCODER
    _shoEnc.setPosition(0.0);

    //#endregion
    
    // PID DE CONTROLE DO YAW
    _yawPID = new PIDController(Constants.YawControl._kp, Constants.YawControl._ki, Constants.YawControl._kd);
    _yawPID.setTolerance(1.0);
    _yawPID.setSetpoint(0.0);

    //#endregion
  
  }

  // CALCULA CORRECAO ENCODER
  public void encoderCorrection (){

    double p   = _kP;
    double i   = _kI;
    double d   = _kD;
    double iz  = _kIz;
    double ff  = _kFF;
    double max = _kMaxOutput;
    double min = _kMinOutput;
    double rot = _rotations;

    if((p != _kP)) { _pidController.setP(p); _kP = p; }
    if((i != _kI)) { _pidController.setI(i); _kI = i; }
    if((d != _kD)) { _pidController.setD(d); _kD = d; }
    if((iz != _kIz)) { _pidController.setIZone(iz); _kIz = iz; }
    if((ff != _kFF)) { _pidController.setFF(ff); _kFF = ff; }
    if((max != _kMaxOutput) || (min != _kMinOutput)) { 
      _pidController.setOutputRange(min, max); 
      _kMinOutput = min; _kMaxOutput = max; 
    }

    _pidController.setReference(rot, CANSparkMax.ControlType.kPosition);

  }

  // ATIVA O SHOOTER
  public void shootActv(){

    // CORRECAO DO ENCODER
    //_shoVel += (_RPM - _shoEnc.getVelocity()) * 0.001; //Calculo proporcional

    // RPM DO SHOOTER
    _right.set(.56285);//_shoVel);
//*/
}

  // DESATIVA O SHOOTER
  public void shootDisab(){

    _shoVel = 0;
    _shoEnc.setPosition(0.0);
    _right.set(0.0);
  
}

  // YAW
  public void rotation(double y) {
/*
    if  (_limit_right.get() && y > 0.0){
      _yaw.set(0.0);
    } else if (_limit_left.get() && y < 0.0) {
      _yaw.set(0.0);
    } else {
      _yaw.set(y);
  }
  //*/
}

  // RETORNA INDENTIFICAÇAO
  public boolean isLimelightDetected() {

    return _tv.getDouble(0.0) == 1.0 ? true : false;
  
  }

  // ATIVA CONTROLE DA LIMELIGHT NO YAW
  public void limelightYawControl(){

    //_yaw.set(_yawPID.calculate(_tx.getDouble(0.0)));

  }

  // ATIVA CONTROLE DA LIMELIGHT NO PITCH
  public void limelightPitchControl() {

    _pitch.set((_ty.getDouble(0.0) + 15) + _maxAngle / ((_minAngle - _maxAngle) * _maxPosition));

  }

  // PITCH
  public void servoMov (double p) {

    _pitch.set(p);

  } 
  
  //CALCULA DISTANCIA ENTRE O ROBO E O HUB
  public double distRoboHub(){
    
    return Math.sqrt(Math.pow(_valConvr / _ta.getDouble(0.0), 2) - _catHub); //catetoDistanciaRoboHub = raizQuadrada(hipotAtual - catetoDaAlturaDoHub)
  
  }

  // PERIODICA
  @Override
  public void periodic() {
    
    // ATUALIZA CORREÇAO DO ENCODER DO SHOOTER
    encoderCorrection();

    // ATUALIZA A DISTANCIA DO ALVO EM RELACAO AO ROBO
    if (_ta.getDouble(0.0) >= 1.0) SmartDashboard.putString("Limelight Distancia", "Perto");
    else if (_ta.getDouble(0.0) <= 0.5) SmartDashboard.putString("Limelight Distancia", "Longe");
    else SmartDashboard.putString("Limelight Distancia", "Medio");
    //*/

    //SmartDashboard.putNumber("Distancia Robo ao HUB", distRoboHub());
    //SmartDashboard.putNumber("Angulo do Pitch", _pitchAngle);
    SmartDashboard.putNumber("RPM Encoder", _shoEnc.getVelocity());
    SmartDashboard.putNumber("Correçao encoder", _shoVel);
    SmartDashboard.putBoolean("ShoAct", _shootActv);

  }
}