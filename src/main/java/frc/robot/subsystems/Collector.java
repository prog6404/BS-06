
package frc.robot.subsystems;

// IMPORTS
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// CODE
public class Collector extends SubsystemBase {
 
  // CRIANDO OS CONTROLADORES DO SISTEMA DE (MOVIMENTACAO DO) COLETOR
  private VictorSPX _coll;
  private VictorSPX _coll_move;

  // CRIANDO OS SENSORES DO SISTEMA DE MOVIMENTACAO DO COLETOR
  public DigitalInput _limitSHORT;
  public DigitalInput _limitUP;
 
  public Collector() {

    // DEFININDO OS CONTROLADORES DO SISTEMA DE (MOVIMENTACAO DO) COLETOR
    _coll = new VictorSPX(Constants.Motors.Collector._collector);
    _coll_move = new VictorSPX(Constants.Motors.Collector._move_coll);

    // DEFININDO OS SENSORES DO SISTEMA DE MOVIMENTACAO DO COLETOR
    _limitSHORT = new DigitalInput(Constants.Sensors._limit_short);
    _limitUP = new DigitalInput(Constants.Sensors._limit_up);

  }

  // FUNCAO DO SISTEMA DE COLETOR
  public void collect(double c) {
    _coll.set(ControlMode.PercentOutput, c);
  }

  // FUNCAO DO SISTEMA DE MOVINTACAO DO COLETOR
  public void move_c(double move) {
    _coll_move.set(ControlMode.PercentOutput, move);
  }

  @Override
  public void periodic() {
  }
}
