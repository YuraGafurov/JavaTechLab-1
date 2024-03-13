package Transactions;

import java.util.UUID;

/**
 * Интерфейс банковской транзакции
 *
 * @author Юра Гафуров
 */
public interface Transaction {
    /**
     * Метод исполнения транзакции
     */
    void execute();

    /**
     * Метод отмены транзакции
     */
    void rollback();

    UUID getId();
}
