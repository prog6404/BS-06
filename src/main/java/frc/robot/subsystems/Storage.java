
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Storage extends SubsystemBase {
  //Criando os controladores do nosso armazenador
  private VictorSPX _storage;
  
  public Storage() {
    //Definindo o controlador do nosso armazenador
    _storage = new VictorSPX(Constants.Motors.Storage._storage);
  }
  
  //Criando a funcao da velocidade do nosso armazenador armazenar a bola
  public void stor(double vel) {
    _storage.set(VictorSPXControlMode.PercentOutput, vel);
  }

  @Override
  public void periodic() {

  }
}
