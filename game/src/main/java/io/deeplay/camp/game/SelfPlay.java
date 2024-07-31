package io.deeplay.camp.game;

import io.deeplay.camp.game.bots.Bot;
import io.deeplay.camp.game.domain.GalaxyListener;
import io.deeplay.camp.game.domain.GameTypes;
import io.deeplay.camp.game.entites.*;
import io.deeplay.camp.game.interfaces.PlayerInterface;
import io.deeplay.camp.game.utils.GameLogger;

import java.util.*;

public class SelfPlay implements GalaxyListener {

    private final int sizeField;
    private final String[] playerNames;
    private final Bot.BotFactory[] factories;
    private final PlayerInterface[] players = new PlayerInterface[2];
    private final Map<String, PlayerInterface> playerNamesMap;
    private final List<GalaxyListener> listeners;

    public SelfPlay(final int sizeField, final String[] playerNames, final Bot.BotFactory[] factories) {
        this.factories = factories;
        this.sizeField = sizeField;
        this.playerNames = playerNames;
        listeners = new ArrayList<>();
        playerNamesMap = new HashMap<>();
    }

    public void playGame() {
        final Field field = new Field(sizeField);
        final Game game = new Game(field);
        final GameLogger logger = new GameLogger();
        players[0] = factories[0].createBot(playerNames[0], game.getField());
        players[1] = factories[1].createBot(playerNames[1], game.getField());
        playerNamesMap.put(playerNames[0], players[0]);
        playerNamesMap.put(playerNames[1], players[1]);

        listeners.add(game);
        listeners.add(logger);
        listeners.add(players[0]);
        listeners.add(players[1]);

        startGameSession("0000", GameTypes.BotVsBot);
        connectingPlayer(playerNames[0]);
        connectingPlayer(playerNames[1]);
        gameStarted(field);
        while (!game.isGameOver()) {
            final String nextPlayerToAct = game.getNextPlayerToAct();
            final PlayerInterface player = playerNamesMap.computeIfAbsent(nextPlayerToAct, (key) -> {
                throw new IllegalStateException("There is no player with name " + key);
            });
            final Answer answer = player.getAnswer(game.getField());
            getPlayerAction(answer.getMove(), nextPlayerToAct);
        }
        String winner = game.isWinner().getName();
        gameEnded(winner);
        endGameSession();
    }

    @Override
    public void startGameSession(final String gameId, final GameTypes gameType) {
        for (final GalaxyListener listener : listeners) {
            listener.startGameSession(gameId, gameType);
        }
    }

    @Override
    public void connectingPlayer(final String waitingPlayerName) {
        for (final GalaxyListener listener : listeners) {
            listener.connectingPlayer(waitingPlayerName);
        }
    }

    @Override
    public void gameStarted(final Field field) {
        for (final GalaxyListener listener : listeners) {
            listener.gameStarted(field);
        }
    }

    @Override
    public void getPlayerAction(final Move move, final String playerName) {
        for (final GalaxyListener listener : listeners) {
            listener.getPlayerAction(move, playerName);
        }
    }

    @Override
    public void gameEnded(final String winner) {
        for (final GalaxyListener listener : listeners) {
            listener.gameEnded(winner);
        }
    }

    @Override
    public void endGameSession() {
        for (final GalaxyListener listener : listeners) {
            listener.endGameSession();
        }
    }
}
