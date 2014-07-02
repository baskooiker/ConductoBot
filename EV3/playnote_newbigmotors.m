
clear all

% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');
% beep to indicate connection
b.beep();

% motor power
motorPowerForward = 50;
motorPowerBackward = -80;

RUNTIMEforward = 0.4;
RUNTIMEbackward = 0.0048;
timerID = tic;  %# Start a clock and return the timer ID
while (toc(timerID) < RUNTIMEforward)
    b.outputClrCount(0,Device.MotorA)
    b.outputPower(0,Device.MotorA,motorPowerForward)
    b.outputStart(0,Device.MotorA)
end
%b.outputStop(0,Device.MotorA,0)
timerID = tic;  %# Start a clock and return the timer ID
while (toc(timerID) < RUNTIMEbackward)
    b.outputClrCount(0,Device.MotorA)
    b.outputPower(0,Device.MotorA,motorPowerBackward)
    b.outputStart(0,Device.MotorA)
end
b.outputStop(0,Device.MotorA,0)