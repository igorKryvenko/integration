package com.kyivstar.services.message;

import com.kyivstar.common.protocol.RequestData;

/**
 * Created by igor on 24.09.17.
 */
public class SellMelodyRequest implements RequestData {
    private String userId;
    private String melodyId;

    public String getUserId() {
        return userId;
    }

    public SellMelodyRequest setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getMelodyId() {
        return melodyId;
    }

    public SellMelodyRequest setMelodyId(String melodyId) {
        this.melodyId = melodyId;
        return this;
    }
}
