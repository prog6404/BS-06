
package frc.robot.subsystems;

// IMPORTS
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
/*
// CODE
public class Climber extends SubsystemBase {
  
  // CRIANDO OS CONTROLADORES DO SISTEMA DE ESCALADA
  private VictorSPX _c_tube;

  // CRIANDO OS CONTROLADORES DO SISTEMA DE SUBIDA DO ROBO NA ESCALADA
  private VictorSPX _c_rise1, _c_rise2;

  public Climber() {

    // DEFININDO OS CONTROLADORS DO SISTEMA DE ESCALADA
    _c_tube = new VictorSPX(Constants.Motors.Climber._climber_tube);
    
    // DEFININDO OS CONTROLADORES DO SISTEMA DE SUBIDA DO ROBO NA ESCALADA
    _c_rise1 = new VictorSPX(Constants.Motors.Climber._climb_rise1);
    _c_rise2 = new VictorSPX(Constants.Motors.Climber._climb_rise2);

  }

  // FUNCAO DO SISTEMA DE ESCALADA
  public void climbing(double ct) {
    _c_tube.set(ControlMode.PercentOutput, ct);
  }

  // FUNCAO DE SUBIDA DO ROBO NA ESCALADA
  public void rise_climber(double cr) {
    _c_rise1.set(ControlMode.PercentOutput, cr);
    _c_rise2.follow(_c_rise1);
  }

  @Override
  public void periodic() {

  }
}
*/