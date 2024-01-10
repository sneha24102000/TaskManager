package com.nikozka.app.model;

import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

    public class ErrorResponse {
        private String errorMessage;
        private HttpStatusCode status;
        private LocalDateTime datetime;

        public ErrorResponse(String errorMessage, HttpStatusCode status, LocalDateTime datetime) {
            this.errorMessage = errorMessage;
            this.status = status;
            this.datetime = datetime;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public HttpStatusCode getStatus() {
            return status;
        }

        public void setStatus(HttpStatusCode status) {
            this.status = status;
        }

        public LocalDateTime getDatetime() {
            return datetime;
        }

        public void setDatetime(LocalDateTime datetime) {
            this.datetime = datetime;
        }
    }
