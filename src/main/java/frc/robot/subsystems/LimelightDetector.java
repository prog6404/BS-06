// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightDetector extends SubsystemBase {

  //read values periodically
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry tv = table.getEntry("tv");


  public LimelightDetector (){

  }

  public Double limeligtUpdate(char value) {


    switch (value) {

      case 'x':
        return tx.getDouble(0.0);

      case 'y':
        return ty.getDouble(0.0);

      case 'v':
        return tv.getDouble(0.0);

      case 'a':
        return ta.getDouble(0.0);

      default:
        return 0.0;

    }

    //post to smart dashboard periodically
    /*SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
    SmartDashboard.putNumber("LimelightV", v);*/

  }

  @Override
  public void periodic() {

    Double x = limeligtUpdate('x');
    Double tv = limeligtUpdate('v');
    
    if (tv == 0) {
      SmartDashboard.putString("Position", "Nada");
    }
    else if (x < -7.0) {
      SmartDashboard.putString("Position", "Esquerda");
    }
    else if (x > 7.0) {
      SmartDashboard.putString("Position", "Direita");
    }
    else {
      SmartDashboard.putString("Position", "Centro");
    }
    
  }

}