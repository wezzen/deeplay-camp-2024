package io.deeplay.camp;

import io.deeplay.camp.engine.entities.Field;
import io.deeplay.camp.engine.entities.Fleet;
import io.deeplay.camp.engine.entities.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.deeplay.camp.ConvertorFieldToString.convertFieldToString;

public class ConvertorFieldToStringTest {

    Field field;
    Ship ship;
    List<Ship> shipList;

    @BeforeEach
    public void setUp() {
        field = new Field(5);
        ship = new Ship(Ship.ShipType.BASIC);
        shipList = new ArrayList<>();
        shipList.add(ship);
        field.getBoard()[0][0].setFleet(new Fleet(shipList, field.getBoard()[0][0]));
    }

    @Test
    void print5Cell() {
        String result = convertFieldToString(field);
        System.out.println(result);
    }
}
