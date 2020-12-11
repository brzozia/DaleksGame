package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Vector2DTest {
    @Test
    public void getCloseToDiagonallyUp() {
        //given
        Vector2D positionToCatch = new Vector2D(5,5);
        Vector2D myPosition = new Vector2D(1,1);
        Vector2D positionToFinishOn = new Vector2D(2,2);

        //when
        Vector2D myNewPosition = myPosition.getCloseTo(positionToCatch);

        //then
        assertEquals(positionToFinishOn, myNewPosition);
    }

    @Test
    public void getCloseToNearlyDiagonallyUp() {
        //given
        Vector2D positionToCatch = new Vector2D(2,3);
        Vector2D myPosition = new Vector2D(1,1);
        Vector2D positionToFinishOn = new Vector2D(2,2);

        //when
        Vector2D myNewPosition = myPosition.getCloseTo(positionToCatch);

        //then
        assertEquals(positionToFinishOn, myNewPosition);
    }

    @Test
    public void getCloseToStraightUp() {
        //given
        Vector2D positionToCatch = new Vector2D(1,4);
        Vector2D myPosition = new Vector2D(1,1);
        Vector2D positionToFinishOn = new Vector2D(1,2);

        //when
        Vector2D myNewPosition = myPosition.getCloseTo(positionToCatch);

        //then
        assertEquals(positionToFinishOn, myNewPosition);
    }

    @Test
    public void getCloseToDiagonallyDown() {
        //given
        Vector2D positionToCatch = new Vector2D(1,1);
        Vector2D myPosition = new Vector2D(5,5);
        Vector2D positionToFinishOn = new Vector2D(4,4);

        //when
        Vector2D myNewPosition = myPosition.getCloseTo(positionToCatch);

        //then
        assertEquals(positionToFinishOn, myNewPosition);
    }

    @Test
    public void getCloseToNearlyDiagonallyDown() {
        //given
        Vector2D positionToCatch = new Vector2D(1,3);
        Vector2D myPosition = new Vector2D(4,5);
        Vector2D positionToFinishOn = new Vector2D(3,4);

        //when
        Vector2D myNewPosition = myPosition.getCloseTo(positionToCatch);

        //then
        assertEquals(positionToFinishOn, myNewPosition);
    }

    @Test
    public void getCloseToStraightDown() {
        //given
        Vector2D positionToCatch = new Vector2D(1,2);
        Vector2D myPosition = new Vector2D(1,5);
        Vector2D positionToFinishOn = new Vector2D(1,4);

        //when
        Vector2D myNewPosition = myPosition.getCloseTo(positionToCatch);

        //then
        assertEquals(positionToFinishOn, myNewPosition);
    }

    @Test
    public void toStringTest() {
        //given
        Vector2D position = new Vector2D(2,3);
        String expectedStringPosition = "x: 2, y: 3";

        //when
        String toStringPosition = position.toString();

        // then
        assertEquals(expectedStringPosition,toStringPosition);
    }

}