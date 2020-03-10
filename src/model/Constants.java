package model;

import java.util.Map;

import static model.ShipType.*;

public interface Constants {

	Map<ShipType, Integer> SIZES = Map.of(CARRIER, 5, BATTLESHIP, 4, CRUISER, 3, SUBMARINE, 3, DESTROYER, 2);
}
