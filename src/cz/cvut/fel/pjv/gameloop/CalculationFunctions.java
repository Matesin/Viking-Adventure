package cz.cvut.fel.pjv.gameloop;

/**
 * Class containing static methods for various calculations.
 * Most of the methods are used for calculating distances and angles, but not yet implemented in the game.

 */
public class CalculationFunctions {
        public static double distance(double x1, double y1, double x2, double y2){
            return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        }
        public static double angle(double x1, double y1, double x2, double y2){
            return Math.atan2(y2 - y1, x2 - x1);
        }
        public static double angleDifference(double a1, double a2){
            double diff = a2 - a1;
            while(diff < -Math.PI){
                diff += 2 * Math.PI;
            }
            while(diff > Math.PI){
                diff -= 2 * Math.PI;
            }
            return diff;
        }
        public static double angleLerp(double a1, double a2, double t){
            return a1 + angleDifference(a1, a2) * t;
        }
        public static double lerp(double a, double b, double t){
            return a + (b - a) * t;
        }
        public static double clamp(double a, double min, double max){
            return Math.min(Math.max(a, min), max);
        }
        public static double wrap(double a, double min, double max){
            double range = max - min;
            while(a < min){
                a += range;
            }
            while(a >= max){
                a -= range;
            }
            return a;
        }
        public static double wrap(double a, double max){
            return wrap(a, 0, max);
        }
        public static double wrapAngle(double a){
            return wrap(a, -Math.PI, Math.PI);
        }
        public static double wrapAngleMax(double a, double max){
            return wrap(a, -max, max);
        }
        public static double wrapAngleMax(double a, double min, double max){
            return wrap(a, min, max);
        }
        public static double wrapAngleOffset(double a, double max, double offset){
            return wrap(a, -max + offset, max + offset);
        }
        public static double wrapAngle(double a, double min, double max, double offset){
            return wrap(a, min + offset, max + offset);
        }
        public static double wrapAngleOffset(double a, double offset){
            return wrap(a, -Math.PI, Math.PI, offset);
        }

    private static double wrap(double a, double v, double pi, double offset) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
