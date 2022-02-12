
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Collector extends SubsystemBase {
  //Criando o controlador do coletor
  private VictorSPX m_collector;
  private VictorSPX collector_move;
    
  public Collector() {
    //Definindo o controlador do coletor
    m_collector = new VictorSPX(Constants.Motors.Collector._collector);
    collector_move = new VictorSPX(Constants.Motors.Collector._move_c);
  }
    
  //Funcao para setar a velocidade do coletor
  public void collect(Double c) {
    m_collector.set(ControlMode.PercentOutput, c); 
  }
  
  public void move_c(double move) {
    collector_move.set(ControlMode.PercentOutput, move);
  }
  
  @Override
  public void periodic() {
  }
}
