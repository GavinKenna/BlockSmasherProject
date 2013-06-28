Block Smasher Project
===================
![The super duper BS LOGO](https://github.com/GavinKenna/BlockSmasherProject/raw/master/BlockSmasher/src/main/res/drawable-xxhdpi/blocksmasherlogo.png)

Block Smasher is a clone of the popular block breaker games. The object of the game is to prevent the ball from falling 
under your paddle, while also trying to destroy all of the bricks on screen. Along the way there will be gems and
powerups that affect the game, such as making your paddle larger, smaller, slowing the speed of the ball down (or up), 
among others.

The game is in early development, and as such only has basic functionality, such as basic collision, level generation
and some more.

Feel free to fork this repo, or even better yet take it and try add some features, I'll be happy to merge it in :)

To jump straight into the main code, here's a link : https://github.com/GavinKenna/BlockSmasherProject/tree/master/BlockSmasher/src/main/java/com/thegavinkenna/blocksmasher

 
<h2>Important source files</h2>
MainGamePanel.java - This is where all of the init, update and draw code goes.<br />
Entity.java - Any object that will be on the playing screen is/will be a subset of the Entity class. This class contains
the Bitmap image of the object, the XY cord. and the update and drawing methods. <br />
Breakable.java - This is a subset of Entity, and this class is used for objects in the game that break, such as bricks
and gems. <br />
Paddle.java - Contains the movement of the paddle. This is of course a subset of Entity.<br />

<br />

Feel free to send me any emails if you would like to contact me for any reasons : Gavin.Kenna@ittd.ie


Screenshots
===
![The super duper BS LOGO](http://i.imgur.com/V9ua7fW.png)
![The super duper BS LOGO](http://i.imgur.com/xA9erhk.png)
