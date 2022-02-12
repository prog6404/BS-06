
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    
    //Criando os controladores do nosso shooter, do yaw e do pitch
    private VictorSPX yaw;
    private VictorSPX pitch, l, r;
    private CANSparkMax _l, _r;
    private MotorControllerGroup _m; 

  public Shooter() {
    //Definindo os controladores do shooter, yaw e do pitch
    pitch = new VictorSPX(Constants.Motors.Shooter._pitch); 
    yaw = new VictorSPX(Constants.Motors.Shooter._yaw); 

    l = new VictorSPX(Constants.Motors.Shooter._left);
    r = new VictorSPX(Constants.Motors.Shooter._right);
    //_l = new CANSparkMax(Constants.Motors.Shooter._left, MotorType.kBrushed);
    //_r = new CANSparkMax(Constants.Motors.Shooter._right, MotorType.kBrushed);
    //_m = new MotorControllerGroup(_l, _r);
  }
  
  //Criando a funcao para nosso shooter atirar
  public void shoot (double s) {
    //_m.set(s);
    l.set(ControlMode.PercentOutput, s);
    r.set(ControlMode.PercentOutput, s);
  }
  //Criando a funcao para nosso sistema de angulacao angular
  public void angle (double a) {
    pitch.set(VictorSPXControlMode.PercentOutput, a);
  }
  //Criando a funcao para a rotacao do nosso shooter
  public void rotation (double y) {
    yaw.set(VictorSPXControlMode.PercentOutput, y);
  }

  @Override
  public void periodic() {
  
  }
}
