import com.cyberbotics.webots.controller.Joystick;
import com.cyberbotics.webots.controller.Keyboard;
import com.cyberbotics.webots.controller.Motor;
import com.cyberbotics.webots.controller.Robot;

public class joystickControl {

    private static final int TIME_STEP = 32;
    private static final double MAX_SPEED = 10;
    private static final double GRABBER_ATTITUDE_MAXSPEED = 30;
    private static final double GRABBER_ROLLER_MAXSPEED = 10;

    public static void main(String[] args) {
        Robot robot = new Robot();
        Joystick joystick = robot.getJoystick();
        joystick.enable(TIME_STEP);
        
        Keyboard keyboard = new Keyboard();
        keyboard.enable(TIME_STEP);

        // Initialize motors
        String[] motorNames = {
        "front_left_motor", "front_right_motor", 
        "rear_left_motor", "rear_right_motor", 
        "middle_left_motor", "middle_right_motor",
        "grabber_attitude", "grabber_roller_motor"};
        
        Motor[] motors = new Motor[motorNames.length];

        for (int i = 0; i < motorNames.length; i++) {
            motors[i] = robot.getMotor(motorNames[i]);
            motors[i].setPosition(Double.POSITIVE_INFINITY); // Set motor to velocity control mode
            motors[i].setVelocity(0.0); // Initialize motor velocity to 0
            //motors[i].setAvailableTorque(0.0); // Force motors to not apply braking force when idle
        }
        
        boolean grabber_roller_toggle = false;
        
        System.out.println("Use arrow keys to drive");
        System.out.println("Use Space to lift ball grabber");
        System.out.println("Press End to toggle roller");

        while (robot.step(TIME_STEP) != -1) {
            // Read joystick axes
            
            double leftStickX = joystick.getAxisValue(1) / 33000.00;
            double leftStickY = joystick.getAxisValue(0) / 33000.00; // Inverted Y-axis for forward/backward
            double rightStickX = joystick.getAxisValue(3) / 33000.00; // X-axis for turning
            double rightStickY = joystick.getAxisValue(2) / 33000.00;
            // System.out.println("left Stick Value: " + leftStickY + " Right Stick Value: " + rightStickX);
            
            int joystickButtonPressed = joystick.getPressedButton();

            double forward = 0;
            double backward = 0;
            double left = 0;
            double right = 0;
            double grabber_attitude = 0;
            double grabber_roller = 0;
            
            int key;
            while ((key = keyboard.getKey()) != -1) {  // Process all pressed keys
                if (key == Keyboard.UP) forward = 1;
                if (key == Keyboard.DOWN) backward = 1;
                if (key == Keyboard.LEFT) left = 1;
                if (key == Keyboard.RIGHT) right = 1;
                if (key == 32) grabber_attitude = -1; // if key is space
                if (key == Keyboard.END) {
                  grabber_roller_toggle = !grabber_roller_toggle;
                  if (grabber_roller_toggle) {
                    System.out.println("Roller On");
                  } else {
                    System.out.println("Roller Off");
                  } // shift key
                }
            }
            
            // double leftStickY = forward - backward;
            // double rightStickX = right- left;
            // System.out.println("Joystick Values: Left Stick Y = " + leftStickY + ", Right Stick X = " + rightStickX);
 
            // Compute left and right motor speeds for skid-steer control
            double leftSpeed = (-leftStickY + leftStickX) * MAX_SPEED;
            double rightSpeed = (-leftStickY - leftStickX) * MAX_SPEED;
            
            double grabber_roller_speed = 0;
            if (joystickButtonPressed == 5) {
              grabber_roller_speed = GRABBER_ROLLER_MAXSPEED;
            }
            double grabber_attitude_speed = grabber_attitude * GRABBER_ATTITUDE_MAXSPEED;
            // double grabber_roller_speed = 0;
            // System.out.println("grabber roller is: " + grabber_roller_toggle);
            
            // Limit wheel speeds to maximum
            leftSpeed = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, leftSpeed));
            rightSpeed = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, rightSpeed));
            // grabber_attitude_speed = Math.max(-GRABBER_ATTITUDE_SPEED, Math.max(GRABBER_ATTITUDE_SPEED, grabber_attitude));
            
            // Set the same speed for both left-side and right-side motors
            motors[0].setTorque(leftSpeed);  // front_left_motor
            motors[1].setTorque(rightSpeed); // front_right_motor
            motors[2].setTorque(leftSpeed);  // rear_left_motor
            motors[3].setTorque(rightSpeed); // rear_right_motor
            motors[4].setTorque(leftSpeed); // middle_left_motor
            motors[5].setTorque(rightSpeed); // middle_right_motor
            motors[6].setTorque(grabber_attitude_speed); // grabber_attitude
            motors[7].setTorque(grabber_roller_speed); // grabber_roller_motor
            //System.out.println("grabber speed = " + grabber_attitude_speed);
        }
    }
}
