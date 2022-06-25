
package frc.robot.subsystems;

// IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// CODE
public class Drivetrain extends SubsystemBase {

  //#region CRIACAO DAS VARIAVEIS

  // CRIANDO OS CONTROLADORES DO SISTEMA DE TRACAO  
  private WPI_TalonSRX _lfront, _lback, _rfront, _rback;
  
  // CRIANDO O AGRUPAMENTO DOS CONTROLADORES
  private MotorControllerGroup _left, _right;
  
  // CRIANDO O DIFERENCIAL DO SISTEMA DE TRACAO
  private DifferentialDrive _drive;

  //#endregion

    public Drivetrain() {

    //#region INICIALIZACAO DO SISTEMA

    // DEFININDO OS CONTROLADORES DO SISTEMA DE TRACAO 
    _lfront = new WPI_TalonSRX (Constants.Motors.Drivetrain._leftfront);
    _lback  = new WPI_TalonSRX (Constants.Motors.Drivetrain._leftback);
    _rfront = new WPI_TalonSRX (Constants.Motors.Drivetrain._rightfront);
    _rback  = new WPI_TalonSRX (Constants.Motors.Drivetrain._rightback);
     
    
    // DEFININDO OS AGRUPAMENTO DOS CONTROLADORES
    
    _left  = new MotorControllerGroup(_lfront, _lback);
    _right = new MotorControllerGroup(_rfront, _rback);

    // DEFININDO O DIFERENCIAL DO SISTEMA DE TRACAO
    _drive = new DifferentialDrive(_left, _right);

    //#endregion

  }

  // FUNCAO DO SISTEMA DE TRACAO (EIXO Y, EIXO X)
  public void direction(double Y, double X) {
    _drive.arcadeDrive(Y, X);
  }

  // PERIODICA
  @Override
  public void periodic() {

  }
}
