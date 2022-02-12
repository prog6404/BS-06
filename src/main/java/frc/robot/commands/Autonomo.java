
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Autonomo extends CommandBase {

  Drivetrain dt;
  Timer t;
  
  public Autonomo(Drivetrain d) {
    addRequirements(d);
    dt = d;
    t = new Timer();
  }

  @Override
  public void initialize() {
    t.start();
  }

  @Override
  public void execute() {
    if (t.get() < 1) {
      dt.direction(0.1, 0);
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
