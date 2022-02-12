
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  //Criando o controlador do nosso sistema de escalada
  private VictorSPX _mc;
  
  public Climber() {
    //Definindo o controlador do nosso sistema de escalada
    _mc = new VictorSPX(Constants.Motors.Climber._Climber);
  }

  //Dando a funcao de liberar o nosso sistema de escalada
  public void climbing(double cl) {
    _mc.set(ControlMode.PercentOutput, cl);
  }

  @Override
  public void periodic() {
    
  }
}
