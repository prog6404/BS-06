// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class contador extends SubsystemBase {

  
  private AnalogInput di = new AnalogInput(2);
  private int _epr = 0;
  private double _cont = 0;
  
  @Override
  public void periodic() {

    int a = di.getValue();
    int b = di.getValue();
    
    if (a != b) {
      _epr++;
    }

    if (_epr == 2) {
      _cont++;
      _epr = 0;
    }
    //System.out.println("SOCORRO " + b);
    //System.out.println("xd " + a);
  }

  // FUNÇÃO COM O VALOR DO CONTADOR    
  public double valuecont() {
      return _cont;
    }
  
}
