package io.deeplay.camp.game.bots;

import io.deeplay.camp.game.entites.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomBot extends Bot {
    private Random random;
    private List<Move> availableMoves;

    protected RandomBot(final String name, final Field field) {
        super(name, field);
        this.random = new Random();
    }

    @Override
    public Move getMove() {
        final Field field = game.getField();
        final Cell[][] board = field.getBoard();
        final Player player = game.getPlayerByName(name);

        Cell startCell = Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getFleet() != null && cell.getFleet().getOwner() == player)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Нет клеток с флотом"));
        startCell.getFleet().addFleetMoves(field);

        availableMoves = startCell.getFleet().getFleetMoves();
        Move move = availableMoves.get(random.nextInt(availableMoves.size()));

        if (availableMoves.isEmpty()) {
            throw new RuntimeException("Нет клеток совершения для хода");
        }

        if (move.moveType() == Move.MoveType.ORDINARY) {
            move.makeMove(player);
        } else if (move.moveType() == Move.MoveType.CAPTURE) {
            move.makeAttack(player);
        } else {
            throw new IllegalArgumentException("Нет такого типа хода!");
        }
        return move;
    }

    /**
     * Метод для получения случайной клетки из игрового поля.
     *
     * @param board двумерный массив клеток игрового поля.
     * @return случайная клетка.
     */
    protected Cell getRandomCell(Cell[][] board) {
        int row = random.nextInt(board.length);
        int col = random.nextInt(board[row].length);
        return board[row][col];
    }

    public static class Factory extends BotFactory {

        public Factory() {
        }

        @Override
        public RandomBot createBot(final String name, final Field field) {
            return new RandomBot(name, field);
        }
    }
}
