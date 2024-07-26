package io.deeplay.camp.game.bots;

import io.deeplay.camp.game.entites.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomBotTest {

    private Field field;
    private Player player;
    private RandomBot bot;

    @BeforeEach
    void setUp() {
        int size = 5;
        field = new Field(size);
        player = new Player(1, "TestPlayer");

        // Инициализация флота и добавление его в ячейку
        Fleet fleet = new Fleet(null, player);
        fleet.setFleetPosition(field.getBoard()[0][0]);
        Ship ship = new Ship(Ship.ShipType.BASIC, fleet);

        // Добавление флота к игроку
        player.getFleetList().add(fleet);

        bot = new RandomBot(field, player);
    }
    /*@BeforeEach
    void setUp() {
        int size = 5;
        field = new Field(size);
        player = new Player(1, "TestPlayer");

        // Заполнение поля клетками для тестов
        Cell[][] board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
        field.setBoard(board);

        Fleet fleet = new Fleet(new Cell(0, 0), player);
        Ship ship = new Ship(Ship.ShipType.BASIC, fleet);

        bot = new RandomBot(field, player);
    }*/

    @Test
    void testGetMove() {
        Move move = bot.getMove();
        assertNotNull(move, "Move should not be null");
        assertNotNull(move.startPosition(), "Start position should not be null");
        assertNotNull(move.endPosition(), "End position should not be null");

        Cell startCell = move.startPosition();
        Cell endCell = move.endPosition();
        assertTrue(startCell.x >= 0 && startCell.x < field.getBoard().length, "Start cell row should be within board range");
        assertTrue(startCell.y >= 0 && startCell.y < field.getBoard()[0].length, "Start cell column should be within board range");
        assertTrue(endCell.x >= 0 && endCell.x < field.getBoard().length, "End cell row should be within board range");
        assertTrue(endCell.y >= 0 && endCell.y < field.getBoard()[0].length, "End cell column should be within board range");
    }

    @Test
    void testMoveType() {
        Move move = bot.getMove();
        assertEquals(Move.MoveType.ORDINARY, move.moveType(), "Move type should be ORDINARY");
    }

    @Test
    void testMakeMove() {
        int initialFleetCount = player.getFleetList().size();
        Move move = bot.getMove();

        // Проверяем, что флот перемещен корректно
        if (move.startPosition().getFleet() != null) {
            assertNotNull(move.endPosition().getFleet(), "End position should have a fleet after the move");
            assertNull(move.startPosition().getFleet(), "Start position should be empty after the move");
        }

        // Проверяем изменение списка флотов игрока, если флот уничтожен или перемещен
        int finalFleetCount = player.getFleetList().size();
        assertTrue(finalFleetCount <= initialFleetCount, "Final fleet count should not exceed the initial count");
    }

    @Test
    void testGetRandomCell() {
        Cell[][] board = field.getBoard();
        Cell randomCell = bot.getRandomCell(board);
        assertNotNull(randomCell, "Random cell should not be null");
        assertTrue(randomCell.x >= 0 && randomCell.x < board.length, "Random cell row should be within board range");
        assertTrue(randomCell.y >= 0 && randomCell.y < board[0].length, "Random cell column should be within board range");
    }


    /*private Field field;
    private RandomBot randomBot;

    @BeforeEach
    public void setUp() {
        field = new Field(8); // Например, создаём поле размером 8x8
        randomBot = new RandomBot.Factory().createBot(field);
    }

    @Test
    public void testGetAction() {
        Move expectedMove = new Move(new Cell(0, 0), new Cell(1, 1), Move.MoveType.ORDINARY);
        Move move = randomBot.getAction();
        assertNotNull(move);
        assertEquals(expectedMove, move);
    }*/
}
