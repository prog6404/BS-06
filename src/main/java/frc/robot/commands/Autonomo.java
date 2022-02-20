
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;

public class Autonomo extends CommandBase {

  //Timers e classes que iremos utilizar no autonomo
  Drivetrain dt;
  Timer t;
  Shooter s;
  Timer xd;
  Storage ar;
  Timer ap;

  public Autonomo(Drivetrain d, Shooter sh, Storage st) {
    addRequirements(d);
    addRequirements(sh);
    addRequirements(st);
    dt = d;
    s = sh;
    ar = st;
    t = new Timer();
    xd = new Timer();
    ap = new Timer();
  }

  @Override
  public void initialize() {
    t.start();
    xd.start();
    ap.start();
  }

  //CÓDIGO NÃO TESTADO
  //ATENÇÃO QUANDO FOR LIGAR O AUTÔNOMO (para Lucas e Enzo)
  @Override
  public void execute() {
    //Drive
    if (t.get() < 1) {
      dt.direction(0.1, 0);
    }

    //Shooting
    if (xd.get() < 6) {
      s.shoot(0.5);
    } else {
      s.shoot(0);
    }

    //Storage
    if (ap.get() < 6) {
      ar.stor(0.5);
    } else {
      ar.stor(0);
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
