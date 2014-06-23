%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Play note                                                               %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function playnote(b)
    b.outputClrCount(0,Device.MotorA)

    anglePlayForward = 90;
    anglePlayBackward = 90;
    speedPlayForward = 90;
    speedPlayBackward = -90;

    b.outputClrCount(0,Device.MotorA)
    b.outputStepSpeed(0,Device.MotorA,speedPlayForward,0,anglePlayForward,0,Device.Brake)
    while(b.outputTest(0,Device.MotorA))
        pause(0.1)
    end
    %b.outputStop(0,Device.MotorA,0)
    %b.outputClrCount(0,Device.MotorA)
    b.outputStepSpeed(0,Device.MotorA,speedPlayBackward,0,anglePlayBackward,0,Device.Brake)
    while(b.outputTest(0,Device.MotorA))
        pause(0.1)
    end
    b.outputStop(0,Device.MotorA,0)
    b.outputClrCount(0,Device.MotorA)
end
