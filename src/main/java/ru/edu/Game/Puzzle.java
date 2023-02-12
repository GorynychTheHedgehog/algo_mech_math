package ru.edu.Game;

import com.google.common.primitives.Ints;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Puzzle {
    @Getter private final int size;
    private final int[] board;
    @Getter private Position blankTile;

    public Puzzle(int size) {
        this.size = size;
        this.board = new int[size * size];
        init();
    }

    private void init() {
        for (int i = 0; i < size * size - 1; i++) {
            board[i] = 1 + i;
        }
        board[(size-1) + (size-1) * size] = 0;
        blankTile = new Position(size-1, size-1);
    }

    public Iterable<Integer> getTiles() {
        return Ints.asList(board);
    }

    public int getTile(Position position) {
        return board[position.x() + position.y() * size];
    }

    private void setTile(int tile, Position position) {
        board[position.x() + position.y() * size] = tile;
    }

    public boolean isSolved() {
        for (int i = 0; i < size * size - 1; i++) {
            if (board[i] - 1 - i != 0) {
                return false;
            }
        }
        return true;
    }

    public Puzzle makeMove(Position newBlankPosition) {
        if (!moveIsValid(newBlankPosition)) {
            throw new IllegalArgumentException("New position is illegal!");
        }
        setTile(getTile(newBlankPosition), blankTile);
        setTile(0, newBlankPosition);
        blankTile = newBlankPosition;
        return this;
    }

    public List<Position> getAllPossibleMoves() {
        var validBlankPositions = new ArrayList<Position>();
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                var newPosition = blankTile.add(dx, dy);
                if (moveIsValid(newPosition)) {
                    validBlankPositions.add(newPosition);
                }
            }
        }
        return validBlankPositions;
    }

    private boolean moveIsValid(Position newBlankPosition) {
        return Math.abs(blankTile.x() - newBlankPosition.x()) != Math.abs(blankTile.y() - newBlankPosition.y())
            && newBlankPosition.x() >= 0 && newBlankPosition.x() <= size - 1
            && newBlankPosition.y() >= 0 && newBlankPosition.y() <= size - 1;
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (var tile : board) {
            hash += (hash * size * size) + tile;
        }
        return hash;
    }

    @Override
    public Puzzle clone() {
        var clone = new Puzzle(size);
        System.arraycopy(board, 0, clone.board, 0, board.length);
        clone.blankTile = blankTile;
        return clone;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder("##############");
        var tilesCounter = 0;
        for (var tile : board) {
            if (tilesCounter % size == 0) {
                stringBuilder.append('\n');
            }
            stringBuilder.append(tile != 0 ? String.valueOf(tile) : " ").append('\t');
            tilesCounter++;
        }
        return stringBuilder.toString();
    }
}
