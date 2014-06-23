



motorPowe

tempo = 4;


% play song
cmd = Command();
            cmd.addHeaderDirect(42,0,0);
            cmd.opSOUND_TONE(5,440,500);
            cmd.opSOUND_READY();
            cmd.opSOUND_TONE(10,880,500);
            cmd.opSOUND_READY();
            cmd.opSOUND_TONE(15,1320,500);
            cmd.opSOUND_READY();
            cmd.addLength();

