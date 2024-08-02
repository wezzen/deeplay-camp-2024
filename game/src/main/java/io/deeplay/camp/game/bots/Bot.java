package io.deeplay.camp.game.bots;

import io.deeplay.camp.game.domain.GameTypes;
import io.deeplay.camp.game.entites.*;
import io.deeplay.camp.game.interfaces.PlayerInterface;
import io.deeplay.camp.game.utils.PointsCalculator;
import io.deeplay.camp.game.utils.ValidationMove;

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

    /**
     * Обрабатывает действие игрока в игре.
     *
     * <p>Метод выполняет следующие действия:
     * <ul>
     *     <li>Проверяет, существует ли игрок с данным именем.</li>
     *     <li>Проверяет, что текущий ход принадлежит правильному игроку.</li>
     *     <li>Получает объект игрока по его имени.</li>
     *     <li>Подсчитывает очки, затрачиваемые на данный ход.</li>
     *     <li>Проверяет валидность хода в зависимости от его типа.</li>
     *     <li>Если ход валиден, выполняет его.</li>
     *     <li>Добавляет ход в список всех ходов игры.</li>
     *     <li>Обновляет очки игрока.</li>
     *     <li>Передает ход следующему игроку.</li>
     * </ul>
     *
     * @param move       Объект хода {@link Move}, содержащий информацию о типе хода и его параметрах.
     * @param playerName Имя игрока, выполняющего ход.
     * @throws IllegalArgumentException если игрок с данным именем не найден или тип хода не существует.
     * @throws IllegalStateException    если ход выполняется не в очереди игрока или если ход недопустим.
     */
    @Override
    public void getPlayerAction(final Move move, final String playerName) {
        // Проверка наличия игрока
        if (!game.getPlayerNames().containsKey(playerName)) {
            throw new IllegalArgumentException("Отсутствует игрок: " + playerName);
        }

        // Проверка, что текущий ход принадлежит правильному игроку
        /*if (!playerName.equals(game.getNextPlayerToAct())) {
            throw new IllegalStateException("Сейчас не ход игрока: " + playerName);
        }*/

        // Получаем игрока
        Player player = game.getPlayerNames().get(playerName);

        // Подсчет очков для хода
        int cost = PointsCalculator.costMovement(move);

        //Проверка принятого хода на валидность
        /*boolean isValidMove = false;
        if (move.moveType() == Move.MoveType.ORDINARY) {
            isValidMove = ValidationMove.isValidOrdinaryMove(move, game.getField(), player);
        } else if (move.moveType() == Move.MoveType.CAPTURE) {
            isValidMove = ValidationMove.isValidCaptureMove(move, player);
        } else if (move.moveType() == Move.MoveType.SKIP) {
            isValidMove = true;
        } else {
            throw new IllegalArgumentException("Нет такого типа хода!");
        }

        if (!isValidMove) {
            throw new IllegalStateException("Недопустимый ход: " + move);
        }*/

        // Исполнение хода
       /* if (move.moveType() == Move.MoveType.ORDINARY) {
            game.getPlayerAction(move, playerName);
            //move.makeMove(player);
        } else if (move.moveType() == Move.MoveType.CAPTURE) {
            game.getPlayerAction(move, playerName);
            //move.makeAttack(player);
        }*/

        game.getPlayerAction(move, playerName);

        // Добавляем ход в список всех ходов
        game.getAllGameMoves().add(move);

        // Обновляем очки игрока
        player.decreaseTotalGamePoints(cost);

        // Переход хода к следующему игроку
        game.switchPlayerToAct();
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
