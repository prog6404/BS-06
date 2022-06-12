
package frc.robot.subsystems;

// IMPORTS
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.drive.KilloughDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// CODE
public class Storage extends SubsystemBase {

  // CRIANDO OS CONTROLADORES DO SISTEMA DE ARAMAZENADOR
  private VictorSPX _storage;

  // CRIANDO OS SENSORES DO SISTEMA DE ARMAZENADOR
  private DigitalInput _sensor_sto;

  public Storage() {

    // DEFININDO OS CONTROLADORES DO SISTEMA DE ARMAZENADOR
    _storage = new VictorSPX(Constants.Motors.Storage._storage);

    // DEFININDO OS SENSORES DO SISTEMA DE ARMAZENADOR
    _sensor_sto = new DigitalInput(Constants.Sensors._sensor_sto);
  }

  // CRIANDO FUNCAO DO ARMAZENADOR
  public void stor(double vel) {
    _storage.set(VictorSPXControlMode.PercentOutput, vel);
  }

  // CRIANDO FUNCAO DOS SENSORES
  public boolean sensorS1() {
    return _sensor_sto.get();
  }

  @Override
  // FAZENDO O ARMAZENADOR PUXAR QUANDO A BOLA PASSAR NO SENSOR
  public void periodic() {
    SmartDashboard.putBoolean("lalala", _sensor_sto.get());
    /*if (sensorS1()) {
      stor(0.7);
    } else {
      stor(0.0);
    }
    //*/
  }
  }
