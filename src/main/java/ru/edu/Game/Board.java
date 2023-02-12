package ru.edu.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Board<T> implements Cloneable{

//    protected final int size;
//    protected final T[] board;
//    protected Position blankTile;
//
//    public Board(T[] board) {
//        assert board.length == Math.pow((int) Math.sqrt(board.length), 2);
//        this.size = (int) Math.sqrt(board.length);
//        this.board = board;
//    }
//
//    public T get(int x, int y) {
//        return board[y + x * size];
//    }
//
//    public void setTile(T tile, int x, int y) {
//        board[y + x * size] = tile;
//    }
//
//
//    public void shuffle(int N) {
//        for (int i = 0; i < N; i++) {
//            var possibleMoves = getAllPossibleMoves();
//            var move = possibleMoves.get(ThreadLocalRandom.current().nextInt(possibleMoves.size()));
//            makeMove(move);
//        }
//    }
//
//    public List<Position> getAllPossibleMoves() {
//        var validBlankPositions = new ArrayList<Position>();
//        for (int dx = -1; dx < 2; dx++) {
//            for (int dy = -1; dy < 2; dy++) {
//                var newPosition = new Position(blankTile.x() + dx, blankTile.y() + dy);
//                if (moveIsValid(newPosition)) {
//                    validBlankPositions.add(newPosition);
//                }
//            }
//        }
//        return validBlankPositions;
//    }
}
