% MotorA = arm1
% MotorB = rijden
% MotorC = draai2
% MotorD = arm2

stapgrootte = 110;

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Make connection with brick                                              %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');

 %function playNote = outputGetCount(brick,layer,nos)

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Play note                                                               %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');

%b.outputClrCount(0,Device.MotorA)

anglePlayForward = 90;
anglePlayBackward = -90;
speedPlayForward = 90;
speedPlayBackward = 90;

%b.outputClrCount(0,Device.MotorA)
b.outputStepSpeed(0,Device.MotorA,speedPlayForward,0,anglePlayForward,0,Device.Brake)
while(b.outputTest(0,Device.MotorA))
    pause(0.1)
end
b.outputStop(0,Device.MotorA,0)
%b.outputClrCount(0,Device.MotorA)
b.outputStepSpeed(0,Device.MotorA,speedPlayBackward,0,anglePlayBackward,0,Device.Brake)
while(b.outputTest(0,Device.MotorA))
    pause(0.1)
end
b.outputStop(0,Device.MotorA,0)
b.outputClrCount(0,Device.MotorA)
%delete(b)


%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Play note with same algorithm as driving                                %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb') ;

angleDriveForward = 110;
speedDriveForward = 40;

b.outputClrCount(0,Device.MotorA)
b.outputStepSpeed(0,Device.MotorA,speedDriveForward,0,angleDriveForward,0,Device.Brake)
while(b.outputTest(0,Device.MotorA))
    pause(0.1)
end
b.outputStop(0,Device.MotorA,0)


%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Drive one key to the right                    WORKS!                    %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb') ;

angleDriveForward = 110;
speedDriveForward = 40;

b.outputClrCount(0,Device.MotorB)
b.outputStepSpeed(0,Device.MotorB,speedDriveForward,0,angleDriveForward,0,Device.Brake)
while(b.outputTest(0,Device.MotorB))
    pause(0.1)
end
b.outputStop(0,Device.MotorB,0)

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Drive one key to the left             WORKS!                            %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');

angleDriveBackward = 110;
speedDriveBackward = -40;

b.outputClrCount(0,Device.MotorB)
b.outputStepSpeed(0,Device.MotorB,speedDriveBackward,0,angleDriveBackward,0,Device.Brake)
while(b.outputTest(0,Device.MotorB))
    pause(0.1)
end
b.outputStop(0,Device.MotorB,0)

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


b.outputStop(0,Device.MotorA,Device.Brake)


while (~b.outputGetCount(0,Device.MotorA)==-50)
    b.outputPower(0,Device.MotorA,30)
    b.outputStart(0,Device.MotorA)
end
b.outputStop(0,Device.MotorA,0)


%b.outputStop(0,Device.MotorA,0)
pause(3);
b.outputStepSpeed(0,Device.MotorA,-80,0,40,0,Device.Coast)


% motor power
motorPowerForward = -60;
motorPowerBackward = 60;

RUNTIMEforward = 0.4;
RUNTIMEbackward = 0.05;





if(~b.outputGetCount(0,Device.MotorA)==73)
timerID = tic;  %# Start a clock and return the timer ID
while (toc(timerID) < RUNTIMEforward)
    b.outputPower(0,Device.MotorA,motorPowerForward)
    b.outputStart(0,Device.MotorA)
end
b.outputStop(0,Device.MotorA,0)
end
timerID = tic;  %# Start a clock and return the timer ID
while (toc(timerID) < RUNTIMEbackward)
    b.outputPower(0,Device.MotorA,motorPowerBackward)
    b.outputStart(0,Device.MotorA)
end
b.outputStop(0,Device.MotorA,0)


%% moving with tachometer
clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');

b.outputClrCount(0,Device.MotorA)
bla = true;
while (bla)
    if(b.outputGetCount(0,Device.MotorA)< 90)
        b.outputPower(0,Device.MotorA,60)
        b.outputStart(0,Device.MotorA)
    end
    bla = false;
    b.outputStop(0,Device.MotorA,0);
end

bla = true;
while (bla)
    if(b.outputGetCount(0,Device.MotorA)> 0)
        b.outputPower(0,Device.MotorA,-60)
        b.outputStart(0,Device.MotorA)
    end
    bla = false;
    b.outputStop(0,Device.MotorA,0);
end

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

%% Try motors C and D
% LEFT
clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');

% MotorC = draai2
% MotorD = arm2

SpeedMotorCForward = 20;
SpeedMotorCBackward = -20;
AngleMotorC = 40;

draaigrootte = 110;
% Left
b.outputStepSpeed(0,Device.MotorC,SpeedMotorCForward,0,AngleMotorC,0,Device.Brake) 
b.outputStop(0,Device.MotorC,0);

%% RIGHT
clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');

% MotorC = draai2
% MotorD = arm2

SpeedMotorCForward = 20;
SpeedMotorCBackward = -20;
AngleMotorC = 40;

% Right
b.outputStepSpeed(0,Device.MotorC,SpeedMotorCBackward,0,AngleMotorC,0,Device.Brake) 
b.outputStop(0,Device.MotorC,0);

%% Move motor D
clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');

% MotorC = draai2
% MotorD = arm2

SpeedMotorDForward = 25;
SpeedMotorDBackward = -25;
AngleMotorD = 70;

% Right
b.outputStepSpeed(0,Device.MotorD,SpeedMotorDBackward,0,AngleMotorD,0,Device.Brake) 
b.outputStop(0,Device.MotorD,0);


%% Move motor D and C together
clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');
stapgrootte = 35;
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

