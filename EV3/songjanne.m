
clear all
% init the connection
disp('Connecting ... ')
% brick usb init
b = Brick('ioType','usb');

playnote(b);
driveforward(b);
driveforward(b);
playnote(b);