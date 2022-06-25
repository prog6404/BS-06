
package frc.robot.subsystems;

// IMPORTS
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// CODE
public class Climber extends SubsystemBase {
  
  // CRIANDO OS CONTROLADORES DO SISTEMA DE ESCALADA
  private VictorSPX _c_tube;

  public Climber() {

    // DEFININDO OS CONTROLADORS DO SISTEMA DE ESCALADA
    _c_tube = new VictorSPX(Constants.Motors.Climber._climber_tube);

  }

  // FUNCAO DO SISTEMA DE ESCALADA
  public void climbing(double ct) {
    _c_tube.set(ControlMode.PercentOutput, ct);
  }

  @Override
  public void periodic() {

  }
}
//*/