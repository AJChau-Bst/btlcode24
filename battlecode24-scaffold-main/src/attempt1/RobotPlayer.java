package attempt1;

import battlecode.common.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
public strictfp class RobotPlayer {

    /**
     * We will use this variable to count the number of turns this robot has been alive.
     * You can use static variables like this to save any information you want. Keep in mind that even though
     * these variables are static, in Battlecode they aren't actually shared between your robots.
     */
    static int turnCount = 0;

    /**
     * A random number generator.
     * We will use this RNG to make some random moves. The Random class is provided by the java.util.Random
     * import at the top of this file. Here, we *seed* the RNG with a constant number (6147); this makes sure
     * we get the same sequence of numbers every time this code is run. This is very useful for debugging!
     */
    static final Random rng = new Random(6147);
    static final Random num = new Random(8);

    /** Array containing all the possible movement directions. */
    static final Direction[] directions = {
        Direction.NORTH,
        Direction.NORTHEAST,
        Direction.EAST,
        Direction.SOUTHEAST,
        Direction.SOUTH,
        Direction.SOUTHWEST,
        Direction.WEST,
        Direction.NORTHWEST,
    };

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * It is like the main function for your robot. If this method returns, the robot dies!
     *
     * @param rc  The RobotController object. You use it to perform actions from this robot, and to get
     *            information on its current status. Essentially your portal to interacting with the world.
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // Hello world! Standard output is very useful for debugging.
        // Everything you say here will be directly viewable in your terminal when you run a match!
        System.out.println("I'm alive");

        // You can also use indicators to save debug notes in replays.
        rc.setIndicatorString("Hello world!");

        while (true) {
            // This code runs during the entire lifespan of the robot, which is why it is in an infinite
            // loop. If we ever leave this loop and return from run(), the robot dies! At the end of the
            // loop, we call Clock.yield(), signifying that we've done everything we want to do.

            turnCount += 1;  // We have now been alive for one more turn!

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode.
            try {
                // Make sure you spawn your robot in before you attempt to take any actions!
                // Robots not spawned in do not have vision of any tiles and cannot perform any actions.
                if (!rc.isSpawned()){
                    MapLocation[] spawnLocs = rc.getAllySpawnLocations();
                    // Pick a random spawn location to attempt spawning in.
                    MapLocation randomLoc = spawnLocs[rng.nextInt(spawnLocs.length)];
                    if (rc.canSpawn(randomLoc)) rc.spawn(randomLoc);
                }
                else{
                    if(turnCount < 200){
                        duckPrep(rc);
                    } else {
                        ducksDo(rc);
                    }

                }

            } catch (GameActionException e) {
                // Oh no! It looks like we did something illegal in the Battlecode world. You should
                // handle GameActionExceptions judiciously, in case unexpected events occur in the game
                // world. Remember, uncaught exceptions cause your robot to explode!
                System.out.println("GameActionException");
                e.printStackTrace();

            } catch (Exception e) {
                // Oh no! It looks like our code tried to do something bad. This isn't a
                // GameActionException, so it's more likely to be a bug in our code.
                System.out.println("Exception");
                e.printStackTrace();

            } finally {
                // Signify we've done everything we want to do, thereby ending our turn.
                // This will make our code wait until the next turn, and then perform this loop again.
                Clock.yield();
            }
            // End of loop: go back to the top. Clock.yield() has ended, so it's time for another turn!
        }

        // Your code should never reach here (unless it's intentional)! Self-destruction imminent...
    }

    /**
     * Code Goes Here!! Yaaaaa. 
     */

    //Get Our Spawn Zones
    static void ducksDo(RobotController rc) throws GameActionException {
    //     MapLocation[] nearestCrumbs = rc.senseNearbyCrumbs(-1);
    //     if (nearestCrumbs.length > 1){
    //         MapLocation nearestCrumbDirection = nearestCrumbs[0];
    //         Direction moveThisWay = rc.getLocation().directionTo(nearestCrumbDirection);
    //         if(rc.canMove(moveThisWay)){
    //             rc.move(moveThisWay);
    //         }
    //     } else{
    //         Direction randomDir = directions[rng.nextInt(directions.length)];
    //         if (rc.canMove(randomDir)){
    //             rc.move(randomDir);
    //         }
    //     }
        
    }


    static void duckPrep(RobotController rc) throws GameActionException{
        int mapHeight = rc.getMapHeight();
        int mapWidth = rc.getMapWidth();
        int flagPlaceHeight = (mapHeight/10);
        int flagPlaceWidth = (mapWidth - mapWidth/10);
        //Inefficient Corner Code
        //Identify the Corner, write code to find margin
        //Send Ducks to Margin
        //DON'T PICK UP FLAGS IF IN THE MARGIN
        //First, Find All Crumbs and Relocate Flags to the same corner (Top Left Corner for now)
        //Dumb Ducks need exploration code
        //Get Flag to Corner
        FlagInfo[] ourFlagLoc = rc.senseNearbyFlags(-1);
        MapLocation leftCorner =  new MapLocation(0, mapHeight);
        rc.setIndicatorString(leftCorner.toString());
        if(rc.canPickupFlag(ourFlagLoc[0].getLocation())){
            rc.pickupFlag(ourFlagLoc[0].getLocation());
        }
        FlagInfo[] sixawayCovid = rc.senseNearbyFlags(5);
            if(rc.hasFlag()){
                mooTwo(rc, leftCorner);
            if(sixawayCovid.length > 0){
                flee(rc, sixawayCovid[0].getLocation());
                if(rc.canDropFlag(rc.getLocation()) && rc.getLocation().y > 28){
                    rc.dropFlag(rc.getLocation());
                    rc.setIndicatorString("Dropped Flag!");
            }
        } else if (ourFlagLoc.length > 0){
            mooTwo(rc, ourFlagLoc[0].getLocation()); //<aybe no clustering... but later issue :)
        }
        MapLocation[] crumbArray = rc.senseNearbyCrumbs(-1);
        mooTwo(rc, crumbArray[0]);
              
        //If flag in corner, build first layer of explosive traps

        //If See explosive trap, place water trap

        //If see water trap, then place stun traps

    }
    }


    
    public static void moveTo(RobotController rc, MapLocation loc) throws GameActionException {
    	if(rc.getLocation().distanceSquaredTo(loc) > 2) {
    		if(!lookTwoMove(rc, loc)) {
    			mooTwo(rc, loc);
    		}
    	} else {
    		mooTwo(rc, loc);
    	}
    }
    
    public static boolean lookTwoMove(RobotController rc, MapLocation loc) throws GameActionException {
    	int leastDistanceSquared = 65537;
    	int xOffset = 0;
    	int yOffset = 0;
    	
    	MapLocation locOffset = new MapLocation(rc.getLocation().x + 0, rc.getLocation().y + 2);
    	int distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = 0;
    		yOffset = 2;
    	}
    	locOffset = new MapLocation(rc.getLocation().x + 1, rc.getLocation().y + 2);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = 1;
    		yOffset = 2;
    	}
    	locOffset = new MapLocation(rc.getLocation().x + 2, rc.getLocation().y + 2);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = 2;
    		yOffset = 2;
    	}
    	locOffset = new MapLocation(rc.getLocation().x + 2, rc.getLocation().y + 1);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = 2;
    		yOffset = 1;
    	}
    	locOffset = new MapLocation(rc.getLocation().x + 2, rc.getLocation().y + 0);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = 2;
    		yOffset = 0;
    	}
    	locOffset = new MapLocation(rc.getLocation().x + 2, rc.getLocation().y - 1);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = 2;
    		yOffset = -1;
    	}
    	locOffset = new MapLocation(rc.getLocation().x + 2, rc.getLocation().y -2);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = 2;
    		yOffset = -2;
    	}
    	locOffset = new MapLocation(rc.getLocation().x + 1, rc.getLocation().y -2);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = 1;
    		yOffset = -2;
    	}
    	locOffset = new MapLocation(rc.getLocation().x + 0, rc.getLocation().y -2);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = 0;
    		yOffset = -2;
    	}
    	locOffset = new MapLocation(rc.getLocation().x -1, rc.getLocation().y -2);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = -1;
    		yOffset = -2;
    	}
    	locOffset = new MapLocation(rc.getLocation().x - 2, rc.getLocation().y -2);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = -2;
    		yOffset = -2;
    	}
    	locOffset = new MapLocation(rc.getLocation().x - 2, rc.getLocation().y -1);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = -2;
    		yOffset = -1;
    	}
    	locOffset = new MapLocation(rc.getLocation().x - 2, rc.getLocation().y + 0);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = -2;
    		yOffset = 0;
    	}
    	locOffset = new MapLocation(rc.getLocation().x - 2, rc.getLocation().y + 1);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = -2;
    		yOffset = 1;
    	}
    	locOffset = new MapLocation(rc.getLocation().x - 2, rc.getLocation().y + 2);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = -2;
    		yOffset = 2;
    	}
    	locOffset = new MapLocation(rc.getLocation().x - 1, rc.getLocation().y + 2);
    	distanceSquared = loc.distanceSquaredTo(locOffset);
    	if(rc.onTheMap(locOffset) && !rc.senseMapInfo(locOffset).isWall() && distanceSquared < leastDistanceSquared) {
    		leastDistanceSquared = distanceSquared;
    		xOffset = -1;
    		yOffset = 2;
    	}
    	
    	if (xOffset == 0 && yOffset == 2) {
    		if (moveN(rc)) {return true;} else if
    		(moveNNE(rc)) {return true;} else if
    		(moveNNW(rc)) {return true;} else if
    		(moveNE(rc)) {return true;} else if
    		(moveNW(rc)) {return true;} else if
    		(moveENE(rc)) {return true;} else if
    		(moveWNW(rc)) {return true;} else if
    		(moveE(rc)) {return true;} else if
    		(moveW(rc)) {return true;} else if
    		(moveESE(rc)) {return true;} else if
    		(moveWSW(rc)) {return true;} else if
    		(moveSE(rc)) {return true;} else if
    		(moveSW(rc)) {return true;} else if
    		(moveSSE(rc)) {return true;} else if
    		(moveSSW(rc)) {return true;} else if
    		(moveS(rc)) {return true;} else return false;
    	}
    	
    	return false;
    }
    
    public static boolean moveN(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.NORTH)) {
    		rc.move(Direction.NORTH); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTH))) {rc.fill(rc.getLocation().add(Direction.NORTH));};
    		return true;
    	} else if (rc.canMove(Direction.NORTHEAST)) {
    		rc.move(Direction.NORTHEAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTHWEST))) {rc.fill(rc.getLocation().add(Direction.NORTHWEST));};
    		return true;
    	} else if (rc.canMove(Direction.NORTHWEST)) {
    		rc.move(Direction.NORTHWEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTHEAST))) {rc.fill(rc.getLocation().add(Direction.NORTHEAST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.NORTH))) {rc.fill(rc.getLocation().add(Direction.NORTH));}
    	else if (rc.canFill(rc.getLocation().add(Direction.NORTHEAST))) {rc.fill(rc.getLocation().add(Direction.NORTHEAST));}
    	else if (rc.canFill(rc.getLocation().add(Direction.NORTHWEST))) {rc.fill(rc.getLocation().add(Direction.NORTHWEST));}
    	return false;
    }
    
    public static boolean moveNNE(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.NORTH)) {
    		rc.move(Direction.NORTH); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTHEAST))) {rc.fill(rc.getLocation().add(Direction.NORTHEAST));};
    		return true;
    	} else if (rc.canMove(Direction.NORTHEAST)) {
    		rc.move(Direction.NORTHEAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTH))) {rc.fill(rc.getLocation().add(Direction.NORTH));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.NORTH))) {rc.fill(rc.getLocation().add(Direction.NORTH));}
    	else if (rc.canFill(rc.getLocation().add(Direction.NORTHEAST))) {rc.fill(rc.getLocation().add(Direction.NORTHEAST));}
    	return false;
    }
    
    public static boolean moveNE(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.NORTHEAST)) {
    		rc.move(Direction.NORTHEAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTHEAST))) {rc.fill(rc.getLocation().add(Direction.NORTHEAST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.NORTHEAST))) {rc.fill(rc.getLocation().add(Direction.NORTHEAST));}
    	return false;
    }
    
    public static boolean moveENE(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.EAST)) {
    		rc.move(Direction.EAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTHEAST))) {rc.fill(rc.getLocation().add(Direction.NORTHEAST));};
    		return true;
    	} else if (rc.canMove(Direction.NORTHEAST)) {
    		rc.move(Direction.NORTHEAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.EAST))) {rc.fill(rc.getLocation().add(Direction.EAST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.EAST))) {rc.fill(rc.getLocation().add(Direction.EAST));}
    	else if (rc.canFill(rc.getLocation().add(Direction.NORTHEAST))) {rc.fill(rc.getLocation().add(Direction.NORTHEAST));}
    	return false;
    }
    
    public static boolean moveE(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.EAST)) {
    		rc.move(Direction.EAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.EAST))) {rc.fill(rc.getLocation().add(Direction.EAST));};
    		return true;
    	} else if (rc.canMove(Direction.NORTHEAST)) {
    		rc.move(Direction.NORTHEAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTHEAST))) {rc.fill(rc.getLocation().add(Direction.SOUTHEAST));};
    		return true;
    	} else if (rc.canMove(Direction.SOUTHEAST)) {
    		rc.move(Direction.SOUTHEAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTHEAST))) {rc.fill(rc.getLocation().add(Direction.NORTHEAST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.EAST))) {rc.fill(rc.getLocation().add(Direction.EAST));}
    	else if (rc.canFill(rc.getLocation().add(Direction.NORTHEAST))) {rc.fill(rc.getLocation().add(Direction.NORTHEAST));}
    	else if (rc.canFill(rc.getLocation().add(Direction.SOUTHEAST))) {rc.fill(rc.getLocation().add(Direction.SOUTHEAST));}
    	return false;
    }
    
    public static boolean moveESE(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.EAST)) {
    		rc.move(Direction.EAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTHEAST))) {rc.fill(rc.getLocation().add(Direction.SOUTHEAST));};
    		return true;
    	} else if (rc.canMove(Direction.SOUTHEAST)) {
    		rc.move(Direction.SOUTHEAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.EAST))) {rc.fill(rc.getLocation().add(Direction.EAST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.EAST))) {rc.fill(rc.getLocation().add(Direction.EAST));}
    	else if (rc.canFill(rc.getLocation().add(Direction.SOUTHEAST))) {rc.fill(rc.getLocation().add(Direction.SOUTHEAST));}
    	return false;
    }
    
    public static boolean moveSE(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.SOUTHEAST)) {
    		rc.move(Direction.SOUTHEAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTHEAST))) {rc.fill(rc.getLocation().add(Direction.SOUTHEAST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.SOUTHEAST))) {rc.fill(rc.getLocation().add(Direction.SOUTHEAST));}
    	return false;
    }
    
    public static boolean moveSSE(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.SOUTH)) {
    		rc.move(Direction.SOUTH); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTHEAST))) {rc.fill(rc.getLocation().add(Direction.SOUTHEAST));};
    		return true;
    	} else if (rc.canMove(Direction.SOUTHEAST)) {
    		rc.move(Direction.SOUTHEAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTH))) {rc.fill(rc.getLocation().add(Direction.SOUTH));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.SOUTH))) {rc.fill(rc.getLocation().add(Direction.SOUTH));}
    	else if (rc.canFill(rc.getLocation().add(Direction.SOUTHEAST))) {rc.fill(rc.getLocation().add(Direction.SOUTHEAST));}
    	return false;
    }
    
    public static boolean moveS(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.SOUTH)) {
    		rc.move(Direction.SOUTH); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTH))) {rc.fill(rc.getLocation().add(Direction.SOUTH));};
    		return true;
    	} else if (rc.canMove(Direction.SOUTHWEST)) {
    		rc.move(Direction.SOUTHWEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTHEAST))) {rc.fill(rc.getLocation().add(Direction.SOUTHEAST));};
    		return true;
    	} else if (rc.canMove(Direction.SOUTHEAST)) {
    		rc.move(Direction.SOUTHEAST); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTHWEST))) {rc.fill(rc.getLocation().add(Direction.SOUTHWEST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.SOUTH))) {rc.fill(rc.getLocation().add(Direction.SOUTH));}
    	else if (rc.canFill(rc.getLocation().add(Direction.SOUTHWEST))) {rc.fill(rc.getLocation().add(Direction.SOUTHWEST));}
    	else if (rc.canFill(rc.getLocation().add(Direction.SOUTHEAST))) {rc.fill(rc.getLocation().add(Direction.SOUTHEAST));}
    	return false;
    }
    
    public static boolean moveSSW(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.SOUTH)) {
    		rc.move(Direction.SOUTH); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTHWEST))) {rc.fill(rc.getLocation().add(Direction.SOUTHWEST));};
    		return true;
    	} else if (rc.canMove(Direction.SOUTHWEST)) {
    		rc.move(Direction.SOUTHWEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTH))) {rc.fill(rc.getLocation().add(Direction.SOUTH));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.SOUTH))) {rc.fill(rc.getLocation().add(Direction.SOUTH));}
    	else if (rc.canFill(rc.getLocation().add(Direction.SOUTHWEST))) {rc.fill(rc.getLocation().add(Direction.SOUTHWEST));}
    	return false;
    }
    
    public static boolean moveSW(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.SOUTHWEST)) {
    		rc.move(Direction.SOUTHWEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTHWEST))) {rc.fill(rc.getLocation().add(Direction.SOUTHWEST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.SOUTHWEST))) {rc.fill(rc.getLocation().add(Direction.SOUTHWEST));}
    	return false;
    }
    
    public static boolean moveWSW(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.WEST)) {
    		rc.move(Direction.WEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTHWEST))) {rc.fill(rc.getLocation().add(Direction.SOUTHWEST));};
    		return true;
    	} else if (rc.canMove(Direction.SOUTHWEST)) {
    		rc.move(Direction.SOUTHWEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.WEST))) {rc.fill(rc.getLocation().add(Direction.WEST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.WEST))) {rc.fill(rc.getLocation().add(Direction.WEST));}
    	else if (rc.canFill(rc.getLocation().add(Direction.SOUTHWEST))) {rc.fill(rc.getLocation().add(Direction.SOUTHWEST));}
    	return false;
    }
    
    public static boolean moveW(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.WEST)) {
    		rc.move(Direction.WEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.WEST))) {rc.fill(rc.getLocation().add(Direction.WEST));};
    		return true;
    	} else if (rc.canMove(Direction.SOUTHWEST)) {
    		rc.move(Direction.SOUTHWEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTHWEST))) {rc.fill(rc.getLocation().add(Direction.NORTHWEST));};
    		return true;
    	} else if (rc.canMove(Direction.NORTHWEST)) {
    		rc.move(Direction.NORTHWEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.SOUTHWEST))) {rc.fill(rc.getLocation().add(Direction.SOUTHWEST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.WEST))) {rc.fill(rc.getLocation().add(Direction.WEST));}
    	else if (rc.canFill(rc.getLocation().add(Direction.SOUTHWEST))) {rc.fill(rc.getLocation().add(Direction.SOUTHWEST));}
    	else if (rc.canFill(rc.getLocation().add(Direction.NORTHWEST))) {rc.fill(rc.getLocation().add(Direction.NORTHWEST));}
    	return false;
    }
    
    public static boolean moveWNW(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.WEST)) {
    		rc.move(Direction.WEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTHWEST))) {rc.fill(rc.getLocation().add(Direction.NORTHWEST));};
    		return true;
    	} else if (rc.canMove(Direction.NORTHWEST)) {
    		rc.move(Direction.NORTHWEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.WEST))) {rc.fill(rc.getLocation().add(Direction.WEST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.WEST))) {rc.fill(rc.getLocation().add(Direction.WEST));}
    	else if (rc.canFill(rc.getLocation().add(Direction.NORTHWEST))) {rc.fill(rc.getLocation().add(Direction.NORTHWEST));}
    	return false;
    }
    
    public static boolean moveNW(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.NORTHWEST)) {
    		rc.move(Direction.NORTHWEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTHWEST))) {rc.fill(rc.getLocation().add(Direction.NORTHWEST));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.NORTHWEST))) {rc.fill(rc.getLocation().add(Direction.NORTHWEST));}
    	return false;
    }
    
    public static boolean moveNNW(RobotController rc) throws GameActionException {
    	if (rc.canMove(Direction.NORTH)) {
    		rc.move(Direction.NORTH); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTHWEST))) {rc.fill(rc.getLocation().add(Direction.NORTHWEST));};
    		return true;
    	} else if (rc.canMove(Direction.NORTHWEST)) {
    		rc.move(Direction.NORTHWEST); 
    		if (rc.canFill(rc.getLocation().add(Direction.NORTH))) {rc.fill(rc.getLocation().add(Direction.NORTH));};
    		return true;
    	} else if (rc.canFill(rc.getLocation().add(Direction.NORTH))) {rc.fill(rc.getLocation().add(Direction.NORTH));}
    	else if (rc.canFill(rc.getLocation().add(Direction.NORTHWEST))) {rc.fill(rc.getLocation().add(Direction.NORTHWEST));}
    	return false;
    }

    public static Direction dirSecDir(MapLocation fromLoc, MapLocation toLoc) {
        if (fromLoc == null) {
            return null;
        }

        if (toLoc == null) {
            return null;
        }

        double dx = toLoc.x - fromLoc.x;
        double dy = toLoc.y - fromLoc.y;

        if (Math.abs(dx) >= 2.414 * Math.abs(dy)) {
            if (dx > 0) {
                if (dy > 0) {
                    return Direction.NORTHEAST;
                } else {
                    return Direction.SOUTHEAST;
                }
            } else if (dx < 0) {
                 if (dy > 0) {
                    return Direction.NORTHWEST;
                } else {
                    return Direction.SOUTHWEST;
                }
            } else {
                return Direction.CENTER;
            }
        } else if (Math.abs(dy) >= 2.414 * Math.abs(dx)) {
            if (dy > 0) {
                 if (dx > 0) {
                    return Direction.NORTHEAST;
                } else {
                    return Direction.NORTHWEST;
                }
            } else {
                if (dx > 0) {
                    return Direction.SOUTHEAST;
                } else {
                    return Direction.SOUTHWEST;
                }
            }
        } else {
            if (dy > 0) {
                if (dx > 0) {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        return Direction.EAST;
                    } else {
                        return Direction.NORTH;
                    }
                } else {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        return Direction.WEST;
                    } else {
                        return Direction.NORTH;
                    }
                }
            } else {
                if (dx > 0) {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        return Direction.EAST;
                    } else {
                        return Direction.SOUTH;
                    }
                } else {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        return Direction.WEST;
                    } else {
                        return Direction.SOUTH;
                    }
                }
            }
        }
    }

    public static void mooTwo(RobotController rc, MapLocation loc) throws GameActionException {
        Direction dir = rc.getLocation().directionTo(loc);
        if (dir == Direction.CENTER) {
        	int width = rc.getMapWidth();
            int height = rc.getMapHeight();
        	int centerWidth = Math.round(width/2);
            int centerHeight = Math.round(height/2);
            MapLocation centerOfMap = new MapLocation(centerWidth, centerHeight);
        	dir = rc.getLocation().directionTo(centerOfMap);
        }
        Direction secDir = dirSecDir(rc.getLocation(), loc);
        scoot(rc, dir, secDir);
    }
    
    public static void flee(RobotController rc, MapLocation loc) throws GameActionException {
        Direction dir = rc.getLocation().directionTo(loc).opposite();
        if (dir == Direction.CENTER) {
        	int width = rc.getMapWidth();
            int height = rc.getMapHeight();
        	int centerWidth = Math.round(width/2);
            int centerHeight = Math.round(height/2);
            MapLocation centerOfMap = new MapLocation(centerWidth, centerHeight);
        	dir = rc.getLocation().directionTo(centerOfMap);
        }
        Direction secDir = dirSecDir(rc.getLocation(), loc).opposite();
        scoot(rc, dir, secDir);
    }
    
    public static void scoot(RobotController rc, Direction dir, Direction secDir) throws GameActionException {
    	if (rc.canMove(dir) || rc.canFill(rc.getLocation().add(dir))) {
    		if(!rc.canMove(dir)) {
    			rc.fill(rc.getLocation().add(dir));
    		} else {
                rc.move(dir);
    		}
        } else if (rc.canMove(secDir) || rc.canFill(rc.getLocation().add(secDir))) {
        	if(!rc.canMove(secDir)) {
    			rc.fill(rc.getLocation().add(secDir));
    		} else {
                rc.move(secDir);
    		}
        } else if (dir.rotateLeft() == secDir) {
        	if (rc.canMove(dir.rotateRight()) || rc.canFill(rc.getLocation().add(dir.rotateRight()))) {
        		if(!rc.canMove(dir.rotateRight())) {
        			rc.fill(rc.getLocation().add(dir.rotateRight()));
        		} else {
                    rc.move(dir.rotateRight());
        		}
        	} else if (rc.canMove(dir.rotateLeft().rotateLeft()) || rc.canFill(rc.getLocation().add(dir.rotateLeft().rotateLeft()))) {
        		if(!rc.canMove(dir.rotateLeft().rotateLeft())) {
        			rc.fill(rc.getLocation().add(dir.rotateLeft().rotateLeft()));
        		} else {
            		rc.move(dir.rotateLeft().rotateLeft());
        		}
        	} else if (rc.canMove(dir.rotateRight().rotateRight()) || rc.canFill(rc.getLocation().add(dir.rotateRight().rotateRight()))) {
        		if(!rc.canMove(dir.rotateRight().rotateRight())) {
        			rc.fill(rc.getLocation().add(dir.rotateRight().rotateRight()));
        		} else {
            		rc.move(dir.rotateRight().rotateRight());
        		}
        	} else if (rc.canMove(dir.rotateLeft().rotateLeft().rotateLeft()) || rc.canFill(rc.getLocation().add(dir.rotateLeft().rotateLeft().rotateLeft()))) {
        		if(!rc.canMove(dir.rotateLeft().rotateLeft().rotateLeft())) {
        			rc.fill(rc.getLocation().add(dir.rotateLeft().rotateLeft().rotateLeft()));
        		} else {
            		rc.move(dir.rotateLeft().rotateLeft().rotateLeft());
        		}
        	} else if (rc.canMove(dir.rotateRight().rotateRight().rotateRight()) || rc.canFill(rc.getLocation().add(dir.rotateRight().rotateRight().rotateRight()))) {
        		if(!rc.canMove(dir.rotateRight().rotateRight().rotateRight())) {
        			rc.fill(rc.getLocation().add(dir.rotateRight().rotateRight().rotateRight()));
        		} else {
            		rc.move(dir.rotateRight().rotateRight().rotateRight());
        		}
        	}
        } else if (rc.canMove(dir.rotateLeft()) || rc.canFill(rc.getLocation().add(dir.rotateLeft()))) {
        	if(!rc.canMove(dir.rotateLeft())) {
    			rc.fill(rc.getLocation().add(dir.rotateLeft()));
    		} else {
            	rc.move(dir.rotateLeft());
    		}
    	} else if (rc.canMove(dir.rotateRight().rotateRight()) || rc.canFill(rc.getLocation().add(dir.rotateRight().rotateRight()))) {
    		if(!rc.canMove(dir.rotateRight().rotateRight())) {
    			rc.fill(rc.getLocation().add(dir.rotateRight().rotateRight()));
    		} else {
        		rc.move(dir.rotateRight().rotateRight());
    		}
    	} else if (rc.canMove(dir.rotateLeft().rotateLeft()) || rc.canFill(rc.getLocation().add(dir.rotateLeft().rotateLeft()))) {
    		if(!rc.canMove(dir.rotateLeft().rotateLeft())) {
    			rc.fill(rc.getLocation().add(dir.rotateLeft().rotateLeft()));
    		} else {
        		rc.move(dir.rotateLeft().rotateLeft());
    		}
    	} else if (rc.canMove(dir.rotateRight().rotateRight().rotateRight()) || rc.canFill(rc.getLocation().add(dir.rotateRight().rotateRight().rotateRight()))) {
    		if(!rc.canMove(dir.rotateRight().rotateRight().rotateRight())) {
    			rc.fill(rc.getLocation().add(dir.rotateRight().rotateRight().rotateRight()));
    		} else {
        		rc.move(dir.rotateRight().rotateRight().rotateRight());
    		}
    	} else if (rc.canMove(dir.rotateLeft().rotateLeft().rotateLeft()) || rc.canFill(rc.getLocation().add(dir.rotateLeft().rotateLeft().rotateLeft()))) {
    		if(!rc.canMove(dir.rotateLeft().rotateLeft().rotateLeft())) {
    			rc.fill(rc.getLocation().add(dir.rotateLeft().rotateLeft().rotateLeft()));
    		} else {
        		rc.move(dir.rotateLeft().rotateLeft().rotateLeft());
    		}
    	}
    }
}
