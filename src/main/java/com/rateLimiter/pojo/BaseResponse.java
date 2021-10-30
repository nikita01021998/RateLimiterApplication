package com.swiggy.alchemist.pojo;

/**
 * Created by RAVISINGH on 28/09/16.
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {

    private int statusCode;
    private String statusMessage;
    private Object data;

    public BaseResponse(int statusCode, String statusMessage, Object data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public BaseResponse(int statusCode, String statusMessage) {
        this(statusCode, statusMessage, null);
    }

}