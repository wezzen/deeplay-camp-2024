package io.deeplay.camp.game.utils;

import io.deeplay.camp.game.entites.Cell;
import io.deeplay.camp.game.entites.Field;
import io.deeplay.camp.game.entites.Move;
import io.deeplay.camp.game.entites.Player;

public final class ValidationMove {
    public static boolean isValidOrdinaryMove(final Move move, final Field field, final Player currentPlayer) {
        return isPositionValid(move.endPosition(), field.getSize()) &&
                isOrdinaryMove(move) &&
                isPositionChanged(move) &&
                isFleetPresent(move) &&
                isOwnerFleet(move, currentPlayer) &&
                isEnoughPoints(currentPlayer, move);
    }
    // проверка - тип хода соответствует типу перемещения
    private static boolean isOrdinaryMove(final Move move) {
        return move.moveType() == Move.MoveType.ORDINARY;
    }
    private static boolean isSkipMove(final Move move) {
        return move.moveType() == Move.MoveType.SKIP;
    }

    // проверка - флот не выйдет за границы карты
    public static boolean isPositionValid(final Cell newPosition, final int fieldSize) {
        return newPosition.x >= 0 && newPosition.x < fieldSize && newPosition.y >= 0 && newPosition.y < fieldSize;
    }

    // проверка - начальная и конечная позиция отличается
    private static boolean isPositionChanged(final Move move) {
        return !move.startPosition().equals(move.endPosition()) || move.startPosition().planet != null;
    }

    // проверка - на начальной позиции стоит флот
    public static boolean isFleetPresent(final Move move) {
        return move.startPosition().getFleet() != null;
    }

    // проверка - флот принадлежит игроку, который ходит
    private static boolean isOwnerFleet(final Move move, final Player currentPlayer) {
        return move.startPosition().getFleet().getOwner().getName().equals(currentPlayer.getName());
    }

    // проверка - игроку хватает очков, чтобы совершить ход
    public static boolean isEnoughPoints(Player player, Move move) {
        return player.getTotalGamePoints() - move.cost() >= 0;
    }

    // проверка - флоту хватает очков для захвата планеты
    public static boolean isCapturePlanet(final int powerFleet, final int pointPlanet) {
        return powerFleet >= pointPlanet;
    }
}
