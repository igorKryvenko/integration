package com.kyivstar.common.protocol;

import org.springframework.web.bind.annotation.ResponseStatus;
import sun.security.provider.certpath.OCSPResponse;

import java.io.Serializable;

/**
 * Created by igor on 24.09.17.
 */
public class Response<T extends Serializable> extends Action<T> {
    private static final long serialVersionUID = 8979170551734666755L;

    private ResponseStatus status;

    public Response() {
        super();
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                '}';
    }

    public enum Status implements Response.StatusType {
        OK("200", "OK"),
        OK_DEPRECATED("200","WARNING: DEPRECATED"),
        BAD_REQUEST("400", "Bad Request"),
        UNAUTHORIZED("401", "Unauthorized"),
        FORBIDDEN("403", "Forbidden"),
        NOT_FOUND("404", "Not Found"),
        METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
        NOT_ACCEPTABLE("406", "Not Acceptable"),
        UNPROCESSABLE_ENTITY("422", "Unprocessable Entity"),
        UPGRADE_REQUIRED("426","Upgrade Required"),
        TOO_MANY_REQUESTS("429", "Too Many Requests"),
        INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
        NOT_IMPLEMENTED("501", "Not Implemented")

        ;

        private final String statusCode;
        private final String reasonPhrase;

        Status(String statusCode, String reasonPhrase) {
            this.statusCode = statusCode;
            this.reasonPhrase = reasonPhrase;
        }

        @Override
        public String getStatusCode() {
            return this.statusCode;
        }

        @Override
        public String getReasonPhrase() {
            return this.toString();
        }

        @Override
        public String toString() {
            return this.reasonPhrase;
        }

    }

    public interface StatusType extends Serializable {
        String getStatusCode();

        String getReasonPhrase();
    }
}
