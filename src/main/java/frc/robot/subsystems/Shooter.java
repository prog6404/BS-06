
package frc.robot.subsystems;
//Imports
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

//Code
public class Shooter extends SubsystemBase {

  // Criando os controladores do nosso shooter, do yaw e do pitch
  private VictorSPX yaw;
  private VictorSPX pitch, l, r;
  public Encoder encoderpitch;

  public Shooter() {
    // Definindo os controladores do shooter, yaw e do pitch
    pitch = new VictorSPX(Constants.Motors.Shooter._pitch);
    yaw = new VictorSPX(Constants.Motors.Shooter._yaw);

    l = new VictorSPX(Constants.Motors.Shooter._left);
    r = new VictorSPX(Constants.Motors.Shooter._right);

    encoderpitch = new Encoder(Constants.Encoders.p1,Constants.Encoders.p2);
    encoderpitch.setDistancePerPulse(1/44.4);
    encoderpitch.setReverseDirection(true);
  }

  // Criando a funcao para nosso shooter atirar
  public void shoot(double s) {
    // _m.set(s);
    l.set(ControlMode.PercentOutput, s);
    r.follow(l);
  }

  // Criando a funcao para nosso sistema de angulacao angular
  public void angle(double a) {
    pitch.set(VictorSPXControlMode.PercentOutput, a);
  }

  // Criando a funcao para a rotacao do nosso shooter
  public void rotation(double y) {
    yaw.set(VictorSPXControlMode.PercentOutput, y);
  }

  public double EP() {
    // Da a quantidade de graus atual
    return (encoderpitch.get() * 0.2895);
 }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("key", EP());
  }
}
