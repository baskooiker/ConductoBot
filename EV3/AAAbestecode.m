%% Beste code

%% Try new WORKSSSSSSSSSSSSSSSS MOTOR A AND B
clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');
stapgrootte = 110;
angleA = [25;25;25;25;25;25];
speedA = 70;
angleAB = [-25;-25;-25;-25;-25;-25];
speedAB = 70;
angleB = [stapgrootte;-stapgrootte;stapgrootte;-stapgrootte;stapgrootte;-stapgrootte];
speedB = 40;
for ii=1:length(angleA)
    % move motor A forwards
    b.outputStepSpeed(0,Device.MotorA,sign(angleA(ii))*speedA,0,angleA(ii),0,Device.Brake)
    % wait until motor A has moved
    while(b.outputTest(0,Device.MotorA))
        pause(0.1)
    end
    % mvoe motor A backwards
    b.outputStepSpeed(0,Device.MotorA,sign(angleAB(ii))*speedAB,0,angleAB(ii),0,Device.Brake)
    % wait until motor A has moved
    while(b.outputTest(0,Device.MotorA))
        pause(0.1)
    end
    % move motor B
    b.outputStepSpeed(0,Device.MotorB,sign(angleB(ii))*speedB,0,angleB(ii),0,Device.Brake) 
    % wait until motor B has moved
    while(b.outputTest(0,Device.MotorB))
        pause(0.1)
    end
end
b.outputStop(0,Device.MotorA,0);
b.outputStop(0,Device.MotorB,0);

%% Move motor D and C together
clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');
stapgrootte = 30;
stapgrootte2 = 35;
angleD = [25;25;25;25;25;25];
speedD = 60;
angleDB = [-25;-25;-25;-25;-25;-25];
speedDB = 70;
angleC = [stapgrootte;-stapgrootte2;stapgrootte;-stapgrootte2;stapgrootte;-stapgrootte2];
speedC = 5;
for ii=1:length(angleD)
    % move motor D forwards
    b.outputStepSpeed(0,Device.MotorD,sign(angleD(ii))*speedD,0,angleD(ii),0,Device.Brake)
    % wait until motor D has moved
    while(b.outputTest(0,Device.MotorD))
        pause(0.1)
    end
    % move motor D backwards
    b.outputStepSpeed(0,Device.MotorD,sign(angleDB(ii))*speedDB,0,angleDB(ii),0,Device.Brake)
    % wait until motor D has moved
    while(b.outputTest(0,Device.MotorD))
        pause(0.1)
    end
    % move motor C
    b.outputStepSpeed(0,Device.MotorC,sign(angleC(ii))*speedC,0,angleC(ii),0,Device.Brake) 
    % wait until motor C has moved
    while(b.outputTest(0,Device.MotorC))
        pause(0.1)
    end
end
b.outputStop(0,Device.MotorD,0);
b.outputStop(0,Device.MotorC,0);
