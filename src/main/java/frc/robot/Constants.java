
package frc.robot;

// CODE
public final class Constants {
    
    public static class Motors {

        public static class Drivetrain {
            public static final int _leftfront = 14;
            public static final int _leftback = 15;
            public static final int _rightfront = 11;
            public static final int _rightback = 16;
        }

        public static class Collector {
            public static final int _collector = 9;
            public static final int _move_coll = 4;
        }

        public static class Storage {
            public static final int _storage = 1;
        }  

        public static class Shooter {
            public static final int _left  = 3;
            public static final int _right = 2;
            
            public static final int _pitch = 0; //Servo PWM
            public static final int _yaw = 17;
        }
        public static class Climber {
            public static final int _climber_tube = 11;
            public static final int _climb_rise1 = 12;
            public static final int _climb_rise2 = 13;
        }
    }

    public static class Control_map {
        public static final int _pilot = 0;
        public static final int _copilot = 1;
    }

    public static class Sensors {
        public static final int _sensor_sto = 8;
        public static final int _limit_short = 7;
        public static final int _limit_up = 15;
        public static final int _limit_right = 3;
        public static final int _limit_left = 4;
        public static final int _limit_center = 5;
        public static final int _limit_p_short = 6;
        public static final int _limit_p_up = 9;
    }

    public static class Encoders {
        public static final int _enc_pitch = 0;
        public static final int _enc_pitch1 = 1;
        public static final int _enc_pitch2 = 2; 
        public static final int _enc_yaw1 = 10;
        public static final int _enc_yaw2 = 18; 
    }
    
    public static class Pneumatic {
        public static final int _solenoide_col1 = 7;
        public static final int _solenoide_col2 = 6;
    }
    
    public static class YawControl {
        
        public static final double _kp = 0.01;
        public static final double _ki = 0.0;
        public static final double _kd = 0.0;
    }

}
