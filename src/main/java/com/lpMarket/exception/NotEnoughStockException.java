package com.lpMarket.exception;

public class NotEnoughStockException extends RuntimeException {
    private final int remainingStock;

    public NotEnoughStockException(String message, int remainingStock) {
        super(message);
        this.remainingStock = remainingStock;
    }
    public int getRemainingStock() {
        return remainingStock;
    }
    public NotEnoughStockException(String message, Throwable cause, int remainingStock) {
        super(message, cause);
        this.remainingStock = remainingStock;
    }

    public NotEnoughStockException(Throwable cause, int remainingStock) {
        super(cause);
        this.remainingStock = remainingStock;
    }

    public NotEnoughStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int remainingStock) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.remainingStock = remainingStock;
    }

    public NotEnoughStockException(int remainingStock) {
        this.remainingStock = remainingStock;
    }
}
