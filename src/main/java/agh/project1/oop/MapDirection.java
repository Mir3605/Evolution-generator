package agh.project1.oop;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    NORTHWEST,
    WEST,
    EAST,
    SOUTHWEST,
    SOUTHEAST,
    SOUTH;

    private int thisToNumber() {
        switch (this) {
            case NORTH -> {
                return 0;
            }
            case NORTHEAST -> {
                return 1;
            }
            case EAST -> {
                return 2;
            }
            case SOUTHEAST -> {
                return 3;
            }
            case SOUTH -> {
                return 4;
            }
            case SOUTHWEST -> {
                return 5;
            }
            case WEST -> {
                return 6;
            }
            case NORTHWEST -> {
                return 7;
            }
        }
        return 1;
    }

    public MapDirection numberToMapDirection(int n) throws IllegalArgumentException {
        switch (n) {
            case 0 -> {
                return MapDirection.NORTH;
            }
            case 1 -> {
                return MapDirection.NORTHEAST;
            }
            case 2 -> {
                return MapDirection.EAST;
            }
            case 3 -> {
                return MapDirection.SOUTHEAST;
            }
            case 4 -> {
                return MapDirection.SOUTH;
            }
            case 5 -> {
                return MapDirection.SOUTHWEST;
            }
            case 6 -> {
                return MapDirection.WEST;
            }
            case 7 -> {
                return MapDirection.NORTHWEST;
            }
            default -> throw new IllegalArgumentException("Cannot resolve given number into map direction");
        }
    }

    public MapDirection rotate(int x) {
        while (x < 0)
            x += 8;
        return this.numberToMapDirection((x + thisToNumber()) % 8);
    }

    public MapDirection opposite() {
        return this.rotate(4);
    }

    public Vector2d toUnitVector() {
        switch (this) {
            case NORTH -> {
                return new Vector2d(0, 1);
            }
            case NORTHEAST -> {
                return new Vector2d(1, 1);
            }
            case EAST -> {
                return new Vector2d(1, 0);
            }
            case SOUTHEAST -> {
                return new Vector2d(1, -1);
            }
            case SOUTH -> {
                return new Vector2d(0, -1);
            }
            case SOUTHWEST -> {
                return new Vector2d(-1, -1);
            }
            case WEST -> {
                return new Vector2d(-1, 0);
            }
            case NORTHWEST -> {
                return new Vector2d(-1, 1);
            }
        }
        return new Vector2d(0, 0);
    }
}
