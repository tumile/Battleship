package util;

import java.util.List;
import java.util.Map;

public interface Constants {

    boolean HORIZONTAL = true, VERTICAL = false;

    int CARRIER = 0, BATTLESHIP = 1, CRUISER = 2, SUBMARINE = 3, DESTROYER = 4;

    List<Integer> SHIPS = List.of(CARRIER, BATTLESHIP, CRUISER, SUBMARINE, DESTROYER);

    Map<Integer, Integer> SIZES = Map.of(CARRIER, 5, BATTLESHIP, 4, CRUISER, 3, SUBMARINE, 3, DESTROYER, 2);

    boolean P1 = true;

    boolean P2 = false;
}
