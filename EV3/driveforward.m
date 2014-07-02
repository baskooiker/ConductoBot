function driveforward(b)
    angleDriveForward = 110;
    speedDriveForward = 40;

    b.outputClrCount(0,Device.MotorB)
    b.outputStepSpeed(0,Device.MotorB,speedDriveForward,0,angleDriveForward,0,Device.Brake)
    while(b.outputTest(0,Device.MotorB))
        pause(0.1)
    end
    b.outputStop(0,Device.MotorB,0)
end