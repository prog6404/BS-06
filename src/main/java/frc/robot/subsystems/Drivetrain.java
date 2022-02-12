
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Drivetrain extends SubsystemBase {
  //Criando os controladores do nosso sistema de tracao
  private WPI_TalonSRX _lf, _lb, _rf, _rb;
  private MotorControllerGroup _l, _r;
  private Encoder e;
  private DifferentialDrive _drive;

  public Drivetrain() {
    //Definindo os controladores para cada motor da tracao
    e = new Encoder(0, 1);
    e.setDistancePerPulse(1/48.0);

    _lf = new WPI_TalonSRX(Constants.Motors.Drivetrain._leftfront);
    _lb = new WPI_TalonSRX(Constants.Motors.Drivetrain._leftback);
    _rf = new WPI_TalonSRX(Constants.Motors.Drivetrain._rightfront);
    _rb = new WPI_TalonSRX(Constants.Motors.Drivetrain._rightback);

    _l = new MotorControllerGroup(_lf, _lb);
    _r = new MotorControllerGroup(_rf, _rb);

    _drive = new DifferentialDrive(_l, _r);
  }
  //Definindo os valores de Y e X para a tracao, fazendo ela andar
  public void direction (double Y, double X) {
    _drive.arcadeDrive(Y, X);
  }

  @Override
  public void periodic() {
    
  }
}
