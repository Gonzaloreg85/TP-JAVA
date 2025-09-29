package com.bbva.tpIntegrador.exception;

public class ProductoNotFoundException extends RuntimeException {

        public ProductoNotFoundException(String message) {
            super(message);
        }

        public ProductoNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }

}
