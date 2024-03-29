Dungeons of Doom Instructions

You are Zack, a brave fortune-hunter that has been sent on a mission to loot Lord Michael's
dungeon. Your mission involves looting all the gold and then escaping from the dungeon. But be
aware, Lord Michael will be roaming his dungeon. Should you get caught, it will be game over.
Good Luck!


SETUP:

To install the game, compile all the java files into Java byte code using the command line. Make
sure to keep all of these files together in one folder.

To run the game, run the Java byte code file you have just created called GameLogic.class .

The game will launch on the command line and ask you for a map file. You can type in a file
location for your map or if your map file is in the maps folder then you can just type the name of
your map file to play it. If you do not have a map file to use, you can just click Enter and the
default map, 'Very small Labyrinth of Doom', will be loaded. In the case that the map file location
you specified cannot be found or accessed, this default map will be loaded.

(NOTE: maps must have at least one exit tile, they must have enough gold tiles so that the player
can reach the win condition and they must only contain gold, exit, wall and blank tiles. The map
must also have at least 12 tiles that are not gold.)

(SIDE NOTE FOR EXAMINER: Although you are welcome to use absolute paths to access map files when
running the game, I have included another folder called maps within the Project folder to increase
ease of use. If you put your map files in here you simply need to type the name of the file,
excluding the .txt extension. Also some pre-made maps have been included that you may use, if you
would like to take a look at them I encourage you to do so as they demonstrate some of the odd
shaped maps and exceptions that my game can handle which makes it more robust.
For example:
    - Maps can have spaces ' ' in them and these will be converted to walls '#' by the game, this is
      demonstrated by the map "holes".
    - Maps do not need walls surrounding them, the walls are added by the game, this is
      demonstrated by the map "walls_demo"
    - Maps do not have to be square, this is demonstrated by the map "jagged" and "diamond")

Depending on the map size, up to three bots can play. If the map is large enough that bots can play
then the game will ask you how many bots you'd like to play against. This can be a number between
zero and the maximum number of bots for the map size. Entering a number too large will assume the
maximum number of bots, entering a number less than zero will assume zero bots.

The maximum number of bots for a map will not exceed 3, the bots are programmed to use their first
action to look and then they will move randomly until they reach the edge of the grid that they
saved in memory the last time they looked. If a bot spots a player then it will remember the
position of the player and then move to where the player was. When the bot reaches the players
location it will look again and if it sees the player it will repeat this process, if it does not
it will go back to moving randomly.


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

After you have finished your turn, Lord Michael will take his turn (if you are playing with bots).
Lord Michael takes turns just like you however his only goal is to catch you. He will not need to
stop to pickup gold or exit the dungeon, therefore he will only use the MOVE and LOOK commands.

Good Luck!