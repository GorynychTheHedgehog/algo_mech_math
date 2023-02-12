package ru.edu.Game;

import lombok.AllArgsConstructor;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PuzzleSolver {
    private static final int SIZE = 4;

    public static void main(String[] args) {
        var puzzleSolver = new PuzzleSolver();

        Puzzle puzzle = new Puzzle(SIZE);

        System.out.println(puzzle);
        System.out.println(puzzleSolver.countManhattanDistance(puzzle));

        puzzleSolver.shuffle(puzzle, 1000);
        System.out.println(puzzle);
        System.out.println(puzzleSolver.countManhattanDistance(puzzle));

        List<Puzzle> puzzles = puzzleSolver.solvePuzzle(puzzle);
        Collections.reverse(puzzles);
        puzzles.forEach(System.out::println);
    }

    public List<Puzzle> solvePuzzle(Puzzle puzzle) {
        var previousPuzzles = new HashMap<Puzzle, Puzzle>();
        var states = new HashMap<Puzzle, State>();
        var queueOfPuzzles = new PriorityQueue<Puzzle>(1000, Comparator.comparingInt(key -> states.get(key).getScore()));

        var initialState = new State(puzzle, 0, countManhattanDistance(puzzle));

        previousPuzzles.put(puzzle, null);
        states.put(puzzle, initialState);
        queueOfPuzzles.add(puzzle);

        while (!queueOfPuzzles.isEmpty()) {
            var currentPuzzle = queueOfPuzzles.poll();

            if (currentPuzzle.isSolved()) {
                var linkedToHead = new ArrayList<Puzzle>();
                while (currentPuzzle != null) {
                    linkedToHead.add(currentPuzzle);
                    currentPuzzle = previousPuzzles.get(currentPuzzle);
                }
                return linkedToHead;
            }

            for (var move : currentPuzzle.getAllPossibleMoves()) {
                var nextPuzzle = currentPuzzle.clone().makeMove(move);
                if (previousPuzzles.containsKey(nextPuzzle)) {
                    continue;
                }
                var nextState = new State(
                        nextPuzzle,
                        states.get(currentPuzzle).numberOfIterations + 1,
                        countManhattanDistance(currentPuzzle)
                );
                previousPuzzles.put(nextPuzzle, currentPuzzle);
                states.put(nextPuzzle, nextState);
                queueOfPuzzles.add(nextPuzzle);
            }
        }

        return null;
    }


    public void shuffle(Puzzle puzzle, int N) {
        for (int i = 0; i < N; i++) {
            var possibleMoves = puzzle.getAllPossibleMoves();
            var move = possibleMoves.get(ThreadLocalRandom.current().nextInt(possibleMoves.size()));
            puzzle.makeMove(move);
        }
    }

    private int countManhattanDistance(Puzzle puzzle) {
        var distance = 0;
        var position = 1;
        for (var value : puzzle.getTiles()) {
            var delta = Math.abs((value != 0 ? value : SIZE * SIZE) - position);
            distance += delta % SIZE + delta / SIZE;
            position++;
        }
        return distance;
    }

    @AllArgsConstructor
    static class State {
        private Puzzle puzzle;
        private int numberOfIterations;
        private int manhattanDistance;

        private int getScore() {
            return 5 * manhattanDistance + numberOfIterations;
        }
    }

}
