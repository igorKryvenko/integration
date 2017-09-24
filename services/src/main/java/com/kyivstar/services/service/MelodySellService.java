package com.kyivstar.services.service;

import com.kyivstar.common.dao.entity.Melody;
import com.kyivstar.common.dao.entity.User;
import com.kyivstar.common.dao.repository.MelodyRepository;
import com.kyivstar.common.dao.repository.UserRepository;
import com.kyivstar.common.protocol.Request;
import com.kyivstar.common.protocol.Response;
import com.kyivstar.services.message.SellMelodyRequest;
import com.kyivstar.services.message.SellMelodyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//suppose we have a routing system that route request for melody sell for user to this class
@Component
public class MelodySellService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MelodyRepository melodyRepository;


    public Response<SellMelodyResponse> sellMelodyForUser(Request<SellMelodyRequest> request) {
        final String melodyId = request.getData().getMelodyId();
        final String userId = request.getData().getUserId();
        User user = userRepository.findOne(userId);
        if(null == user) {
            //send bad request -> user not found
        }
        Melody melody = melodyRepository.findOne(melodyId);
        if(null == melody) {
            //send bad request -> melody not found

        }
        if(Double.compare(user.getBalance(),melody.getPrice()) < 0) {
            //send bad request -> not enough money
        }

        user.setBalance(user.getBalance() - melody.getPrice());
        user.setMelodyId(melodyId);

        return new Response<>();
    }
}
