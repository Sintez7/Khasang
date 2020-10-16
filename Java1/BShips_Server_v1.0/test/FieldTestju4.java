import app.Field;
import app.Ship;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldTestju4 {

    Field field = new Field();

    @Test
    public void placeShip() {
        //acceptable numbers as bounds [1,1][10,10]
        //all that not in bounds must not be placed
        //placeShip test
        assertTrue(field.placeShip(5,5 ,1, Ship.UP), "place ship 5.5 failed");
        //top-left point, min bounds
        assertTrue(field.placeShip(1,1,1, Ship.UP), "min bounds check failed");
        //bottom-right point, max bounds
        assertTrue(field.placeShip(10,10,1, Ship.LEFT), "max bounds check failed");

        //collision check, center is ship in 5.5
        //if collision detected - test will pass
        //place in another ship
        assertFalse(field.placeShip(5,5 ,1, Ship.UP), "override 1 sized ship with another 1 sized ship failed");
        //UP from center
        assertFalse(field.placeShip(5,4,1, Ship.UP), "UP collision failed");
        //UP-RIGHT
        assertFalse(field.placeShip(6,4,1, Ship.UP), "UP-RIGHT collision failed");
        //RIGHT
        assertFalse(field.placeShip(6,5,1, Ship.UP), "RIGHT collision failed");
        //RIGHT-DOWN
        assertFalse(field.placeShip(6,6,1, Ship.UP), "RIGHT-DOWN collision failed");
        //DOWN
        assertFalse(field.placeShip(5,6,1, Ship.UP), "DOWN collision failed");
        //DOWN-LEFT
        assertFalse(field.placeShip(4,6,1, Ship.UP), "DOWN-LEFT collision failed");
        //LEFT
        assertFalse(field.placeShip(4,5,1, Ship.UP), "LEFT collision failed");
        //LEFT-UP
        assertFalse(field.placeShip(4,4,1, Ship.UP), "LEFT-UP collision failed");

        //bounds collision check
        //down side collision check
        assertFalse(field.placeShip(3,8, 4, Ship.DOWN), "down side bounds collision failed");
        //up side
        assertFalse(field.placeShip(3,1,2, Ship.UP), "up side bounds collision failed");
        //right side
        assertFalse(field.placeShip(9,3, 3, Ship.RIGHT), "right side bounds collision failed");
        //left side
        assertFalse(field.placeShip(1,3, 2, Ship.LEFT), "left side bounds collision failed");

        //place out of bounds check
        //up
        assertFalse(field.placeShip(1,0, 1, Ship.UP), "up out of bounds check failed");
        //right
        assertFalse(field.placeShip(11,1, 1, Ship.UP), "right out of bounds check failed");
        //down
        assertFalse(field.placeShip(1,11, 1, Ship.UP), "down out of bounds check failed");
        //left
        assertFalse(field.placeShip(0,1, 1, Ship.UP), "left out of bounds check failed");
    }
}