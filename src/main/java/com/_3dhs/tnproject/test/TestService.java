package com._3dhs.tnproject.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestMapper mapper;

    public void makeLikeList(int postCode, int memberCode) {
        mapper.makeLikeList(postCode, memberCode);
    }
}
