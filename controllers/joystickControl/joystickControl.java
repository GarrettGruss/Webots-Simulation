import com.cyberbotics.webots.controller.Joystick;
import com.cyberbotics.webots.controller.Keyboard;
import com.cyberbotics.webots.controller.Motor;
import com.cyberbotics.webots.controller.Robot;

public class joystickControl {

    private static final int TIME_STEP = 32;
    private static final double MAX_SPEED = 6.28;

    public static void main(String[] args) {
        Robot robot = new Robot();
        //Joystick joystick = robot.getJoystick();
        // joystick.enable(TIME_STEP);
        
        Keyboard keyboard = new Keyboard();
        keyboard.enable(TIME_STEP);

        // Initialize motors
        String[] wheelNames = {
        "front_left_motor", "front_right_motor", 
        "rear_left_motor", "rear_right_motor", 
        "middle_left_motor", "middle_right_motor"};
        Motor[] motors = new Motor[6];

        for (int i = 0; i < wheelNames.length; i++) {
            motors[i] = robot.getMotor(wheelNames[i]);
            motors[i].setPosition(Double.POSITIVE_INFINITY); // Set motor to velocity control mode
            motors[i].setVelocity(0.0); // Initialize motor velocity to 0
        }

        while (robot.step(TIME_STEP) != -1) {
            // Read joystick axes
            // double leftStickY = -joystick.getAxisValue(2); // Inverted Y-axis for forward/backward
            // double rightStickX = joystick.getAxisValue(1); // X-axis for turning

            double forward = 0;
            double backward = 0;
            double left = 0;
            double right = 0;
            
            int key;
            while ((key = keyboard.getKey()) != -1) {  // Process all pressed keys
                if (key == Keyboard.UP) forward = 1;
                if (key == Keyboard.DOWN) backward = 1;
                if (key == Keyboard.LEFT) left = 1;
                if (key == Keyboard.RIGHT) right = 1;
            }
            
            double leftStickY = forward - backward;
            double rightStickX = right- left;
            // System.out.println("Joystick Values: Left Stick Y = " + leftStickY + ", Right Stick X = " + rightStickX);
 
            // Compute left and right motor speeds for skid-steer control
            double leftSpeed = (leftStickY + rightStickX) * MAX_SPEED;
            double rightSpeed = (leftStickY - rightStickX) * MAX_SPEED;
            
            // Limit wheel speeds to maximum
            leftSpeed = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, leftSpeed));
            rightSpeed = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, rightSpeed));
            
            // Set the same speed for both left-side and right-side motors
            motors[0].setVelocity(leftSpeed);  // front_left_motor
            motors[1].setVelocity(rightSpeed); // front_right_motor
            motors[2].setVelocity(leftSpeed);  // rear_left_motor
            motors[3].setVelocity(rightSpeed); // rear_right_motor
            motors[4].setVelocity(leftSpeed); // middle_left_motor
            motors[5].setVelocity(rightSpeed); // middle_right_motor
        }
    }
}
