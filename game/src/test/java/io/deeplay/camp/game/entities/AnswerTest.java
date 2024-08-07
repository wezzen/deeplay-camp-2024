package io.deeplay.camp.game.entities;

import io.deeplay.camp.game.entites.Answer;
import io.deeplay.camp.game.entites.Cell;
import io.deeplay.camp.game.entites.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerTest {
    private Answer answer;
    private Move move;

    @BeforeEach
    public void setUp() {
        move = new Move(new Cell(0, 0), new Cell(1, 1), Move.MoveType.ORDINARY, 5);
        answer = new Answer(move);
    }

    @Test
    public void testToStringWithAutoGeneratedResponseTime() {
        Answer answer = new Answer(move);

        String expectedStrPrefix = "Answer{response='" + move + " : ";
        String actualToString = answer.toString();

        // Проверяем, что actualToString начинается с expectedStrPrefix и что оставшаяся часть это корректное ISO_LOCAL_DATE_TIME время
        boolean isValid = actualToString.startsWith(expectedStrPrefix);

        // Извлекаем и проверяем дату
        if (isValid) {
            String dateTimePart = actualToString.substring(expectedStrPrefix.length(), actualToString.length() - 2);
            // Пробуем разобрать дату
            try {
                LocalDateTime.parse(dateTimePart, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (Exception e) {
                isValid = false;
            }
        }

        assertTrue(isValid);
    }

    @Test
    public void testToStringWithCustomResponseTime() {
        String customTime = "2024-07-22T11:03:39";
        Answer answer = new Answer(move, customTime);

        String expectedStr = "Answer{response='" + move + " : " + customTime + "'}";
        assertEquals(expectedStr, answer.toString());
    }

    @Test
    public void testThrowExceptionForNullMove() {
        assertThrows(IllegalArgumentException.class, () -> new Answer(null));
        assertThrows(IllegalArgumentException.class, () -> new Answer(null, "2024-07-22T11:03:39"));
    }

    @Test
    public void testThrowExceptionForNullResponseTime() {
        assertThrows(IllegalArgumentException.class, () -> new Answer(move, null));
    }
}
