package io.deeplay.camp.game.bots;

import io.deeplay.camp.game.entites.Cell;
import io.deeplay.camp.game.entites.Field;
import io.deeplay.camp.game.entites.Move;
import io.deeplay.camp.game.entites.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomBot extends Bot {
    private final Player player;
    private final Field field;
    private Random random;

    protected RandomBot(Field field, Player player) {
        super(field);
        this.player = player;
        this.field = field;
        this.random = new Random();
    }

    @Override
    protected Move getMove() {
        Cell[][] board = field.getBoard();
        Cell startCell = Arrays.stream(field.getBoard())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getFleet() != null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No cell with a fleet found"));
        Cell endCell = getRandomCell(board); //Тут должна быть валидная клетка
        Move.MoveType moveType = Move.MoveType.ORDINARY;

        Move move = new Move(startCell, endCell, moveType);
        move.makeMove(player);
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
        private final Player player;

        public Factory(Player player) {
            this.player = player;
        }

        @Override
        public RandomBot createBot(final Field field) {
            return new RandomBot(field, player);
        }
    }
}
