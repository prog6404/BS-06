
package frc.robot.subsystems;

//Imports
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

//Code
public class Storage extends SubsystemBase {
  // Criando os controladores do nosso armazenador
  private VictorSPX _storage;

  //criando os sensores do armazenador
  private DigitalInput S1;

  public boolean estado = false;

  public Storage() {
    // Definindo o controlador do nosso armazenador
    _storage = new VictorSPX(Constants.Motors.Storage._storage);
    //Definindo os sensores do armazenador
    S1 = new DigitalInput(Constants.Sensors._S1);
  }

  // Criando a funcao da velocidade do nosso armazenador armazenar a bola
  public void stor(double vel) {
    _storage.set(VictorSPXControlMode.PercentOutput, vel);
  } 

  public boolean sensorS1() {
    return S1.get();
  }

  @Override
  public void periodic() {

  }
}
