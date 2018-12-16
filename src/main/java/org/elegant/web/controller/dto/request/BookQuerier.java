package org.elegant.web.controller.dto.request;

import java.time.LocalDateTime;

public class BookQuerier {
    private Integer size = 20;
    private Seek seek;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Seek getSeek() {
        return seek;
    }

    public void setSeek(Seek seek) {
        this.seek = seek;
    }

    public static class Seek {
        private LocalDateTime updateTime;
        private Integer bookId;

        public LocalDateTime getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
        }

        public Integer getBookId() {
            return bookId;
        }

        public void setBookId(Integer bookId) {
            this.bookId = bookId;
        }
    }
}
