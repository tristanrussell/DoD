Dungeons of Doom Instructions

You are Zack, a brave fortune-hunter that has been sent on a mission to loot Lord Michael's cave of
treasures. Your mission involves looting all the gold from Lord Michael's cave and then escaping.
But be aware, Lord Michael will be roaming his cave. Should you get caught, it will be game over.
Good Luck!


SETUP:

To install the game, compile all the java files into Java byte code using the command line. Make
sure to keep all of these files together in one folder.

To run the game, run the Java byte code file you have just created called GameLogic.class.

The game will launch on the command line and ask you for a map file. You can type in a file
location for your map in the form C:\.....\yourMapFile.txt (your map does not need to be stored on
the C drive, any drive will do). If you do not have a map file to use, you can just click Enter and
the default map, 'Very small Labyrinth of Doom', will be loaded. In the case that the map file
location you specified cannot be found, this default map will be loaded.

(NOTE: map files must have both width and height greater than or equal to 5, or else the map would
be almost impossible, they must have at least one exit tile, they must have enough gold tiles so
that the player can reach the win condition and they must only contain gold, exit, wall and blank
tiles. The map must also have at least 12 tiles that are not gold.)


HOW TO PLAY:

The map is a grid containing gold tiles, shown by a 'G', exit tiles, shown by an 'E', wall tiles,
shown by a '#' and blank tiles, shown by a '.' There will also be Lord Michael (a bot), shown by a
'B' and you, Zack, shown by a 'P'.

You will move around the board trying to collect enough gold to meet the win conditions for the
map while avoiding Lord Michael. You can move around on all tiles that aren't walls.

If you get enough gold and get to an exit tile before Lord Michael catches you, then you will win.
However if you try to leave before you have gathered enough gold, or Lord Michael catches you
before you escape then you will loose.

You and Lord Michael will take it in turns to perform an action, you will go first. You
have the option of 6 different actions on your turn, these should be typed into the command line
(actions are not case sensitive):

    HELLO: This will tell you the required gold to beat the map.

    GOLD: This will tell you the amount of gold you have.

    MOVE X: This will move you in the specified direction, X should be replaced by:
         N: To move north
         E: To move east
         S: To move south
         W: To move west
    (NOTE: If you try moving into a wall tile you will FAIL and your turn will end)

    PICKUP: If you are currently stood on a tile containing gold, then the gold will be picked up
            and added to your gold, this will also remove it from the map.

    LOOK: This will show you a 5 x 5 grid of the map centred around you, be aware that if you are
          stood on a gold or exit tile your player tag will cover it.

    QUIT: If you have the required gold AND you are stood on an exit tile, typing this command will
          win the game, if this command is typed and you do not meet both of the win conditions
          then you will lose the game.

(NOTE: ALL inputs will count as actions, whether valid or not, and will end your turn, input is not
case sensitive but you WILL miss your go for misspelling a command or entering an invalid command.
Also, valid inputs will use a turn whether they succeed or not.)

After you have finished your turn, Lord Michael will take his turn. Lord Michael takes turns just
like you however his only goal is to catch you. He will not need to stop to pickup gold or exit
the cave, therefore he will only use the MOVE and LOOK commands.

Good Luck!