
package frc.robot;

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
            public static final int _move_c = 5;
        }

        public static class Storage {
            public static final int _storage = 6;
        }

        public static class Shooter {
            public static final int _left = 8;
            public static final int _right = 15;
            
            public static final int _pitch = 9;
            public static final int _yaw = 10;
        }

        public static class Climber {
            public static final int _Climber = 11;
        }
    }

    public static class Control_map {
        public static final int _pilot = 0;
        public static final int _copilot = 1;
    }
}