package io.deeplay.camp.game.bots;

import io.deeplay.camp.game.domain.GameTypes;
import io.deeplay.camp.game.entites.*;
import io.deeplay.camp.game.interfaces.PlayerInterface;


import java.util.List;

/**
 * Абстрактный класс Бот. В дальнейшем будет родителем
 * для разных ботов в игре.
 * <p>
 * Реализует интерфейс PlayerInterface, чтобы дать возможность
 * совершать боту ходы.
 */
public abstract class Bot implements PlayerInterface {
    private static final int NUM_PLAYERS = 2;

    protected final String name;
    /**
     * В классе есть только экземпляр класса Game
     * aka контроллер
     */
    protected final Game game;

    protected Bot(final String name, final Field field) {
        this.game = new Game(field);
        this.name = name;
    }

    @Override
    public Answer getAnswer(final Field field) {
        return new Answer(getMove());
    }

    protected abstract Move getMove();

    @Override
    public void startGameSession(final String gameId, final GameTypes gameType) {
        game.startGameSession(gameId, gameType);
    }

    @Override
    public void connectingPlayer(final String waitingPlayerName) {
        game.connectingPlayer(waitingPlayerName);
    }

    @Override
    public void gameStarted(final Field field) {
        game.gameStarted(field);
    }


    @Override
    public void createShips(List<Ship.ShipType> ships, String playerName) {
        game.createShips(ships, playerName);
    }

    @Override
    public void getPlayerAction(final Move move, final String playerName) {
        game.getPlayerAction(move, playerName);
        // Проверка наличия игрока
        if (!game.getPlayerNames().containsKey(playerName)) {
            throw new IllegalArgumentException("Отсутствует игрок:" + playerName);
        }

        // Получаем игрока
        Player player = game.getPlayerNames().get(playerName);

        //todo разобраться с порядком хода

        // Проверка, что текущий ход принадлежит правильному игроку
//        if (!playerName.equals(game.getNextPlayerToAct())) {
//        String name =game.getNextPlayerToAct();
//        if (!playerName.equals(name)){
//            throw new IllegalStateException("Сейчас не ход игрока: " + playerName);
//        }

        // Подсчет очков для хода
        //int cost = PointsCalculator.costMovement(move);

        //todo проверитьвалидность(эта пытается проверять уже совершенный код)
        // Проверка валидности хода
//        if (move.moveType() == Move.MoveType.ORDINARY) {
//            if (ValidationMove.isValidOrdinaryMove(move, game.getField(), player)) {
//                game.getAllGameMoves().add(move);
//                move.makeMove(player);
//            } else {
//                throw new IllegalStateException("Недопустимый 'ORDINARY' ход: " + move);
//            }
//        } else if (move.moveType() == Move.MoveType.CAPTURE) {
//            if (ValidationMove.isValidCaptureMove(move, player)) {
//                game.getAllGameMoves().add(move);
//                move.makeMove(player);
//            } else {
//                throw new IllegalStateException("Недопустимый 'CAPTURE' ход: " + move);
//            }
//        } else throw new IllegalStateException("Нет такого типа хода!");

        // Обновляем очки игрока
        player.decreaseTotalGamePoints(move.cost());

        // Переход хода к следующему игроку
//        game.setNextPlayerToAct((game.getNextPlayerToActIndex() + 1) % NUM_PLAYERS);
    }

    @Override
    public void addCredits() {
        game.addCredits();
    }

    @Override
    public void gameEnded(final String winner) {
        game.gameEnded(winner);
    }

    @Override
    public void endGameSession() {
        game.endGameSession();
    }

    /**
     * Паттерн абстрактная фабрика
     * Статический абстрактный класс
     */
    public static abstract class BotFactory {
        /**
         * Метод создания бота
         *
         * @param field поле, по которому ходит созданный бот
         * @return бота, который будет играть в игру
         */
        public abstract Bot createBot(final String name, final Field field);
    }
}
