package org.teamtuna.yaguroute.common;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMessage {
    private int httpStatus;
    private String message;
    private Map<String, Object> result;

    public ResponseMessage(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.result = null;
    }
}
