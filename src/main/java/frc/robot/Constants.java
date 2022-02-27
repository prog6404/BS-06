
package frc.robot;

// CODE
public final class Constants {
    
    public static class Motors {

        public static class Drivetrain {
            public static final int _leftfront = 0;
            public static final int _leftback = 1;
            public static final int _rightfront = 2;
            public static final int _rightback = 3;
        }

        public static class Collector {
            public static final int _collector = 4;
            public static final int _move_coll = 5;
        }

        public static class Storage {
            public static final int _storage = 6;
        }

        public static class Shooter {
            public static final int _left = 7;
            public static final int _right = 8;
            
            public static final int _pitch = 9;
            public static final int _yaw = 10;
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
        public static final int _sensor_sto = 0;
        public static final int _limit_short = 1;
        public static final int _limit_up = 2;
        public static final int _limit_right = 3;
        public static final int _limit_left = 4;
        public static final int _limit_center = 5;
    }

    public static class Encoders {
        public static final int _enc_pitch1 = 0;
        public static final int _enc_pitch2 = 1; 
    }
}