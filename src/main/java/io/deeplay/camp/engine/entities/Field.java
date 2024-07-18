package io.deeplay.camp.engine.entities;

import java.util.*;

/**
 * Класс игрового поля
 */
public class Field {


    private final int size;
    private Cell[][] board;
    public final List<Cell> planetsOnField = new ArrayList<>();

    public void updateField() {
    }

    public Field(final int size) {
        this.size = size;
        generateField(size);
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getBoard() {
        return board;
    }

    /**
     * Простая генерация зеркальной карты
     *
     * @param size размер поля
     */
    private void generateField(final int size) {
        Random random = new Random();
        board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= i; j++) {
                if (random.nextInt(2) == 1) {
                    int temp = random.nextInt(10);
                    board[i][j] = new Cell(i, j, new Planet(temp));
                    if (i != j) {
                        board[j][i] = new Cell(j, i, new Planet(temp));

                    }
                } else {
                    board[i][j] = new Cell(i, j);
                    if (i != j) {
                        board[j][i] = new Cell(j, i);
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field field)) return false;
        return size == field.size && Arrays.deepEquals(board, field.board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.deepHashCode(board);
        return result;
    }
}
