
package frc.robot.subsystems;

//Imports
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

//Code
public class Collector extends SubsystemBase {
  // Criando o controlador do coletor
  private VictorSPX m_collector;
  private VictorSPX collector_move;
  public DigitalInput limitswitchBAIXO;
  public DigitalInput limitswitchCIMA;
  public Collector() {
    // Definindo o controlador do coletor
    m_collector = new VictorSPX(Constants.Motors.Collector._collector);
    collector_move = new VictorSPX(Constants.Motors.Collector._move_c);
    limitswitchBAIXO = new DigitalInput(5);
    limitswitchCIMA = new DigitalInput(4);
  }

  // Funcao para setar a velocidade do coletor
  public void collect(double c) {
    m_collector.set(ControlMode.PercentOutput, c);
  }

  public void move_c(double move) {
    collector_move.set(ControlMode.PercentOutput, move);
  }

  @Override
  public void periodic() {
  }
}
