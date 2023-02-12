package ru.edu.Game;

record Position(
    int x,
    int y
) {
    public Position add(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    @Override
    public String toString() {
        return "(%d, %d)".formatted(x, y);
    }
}
