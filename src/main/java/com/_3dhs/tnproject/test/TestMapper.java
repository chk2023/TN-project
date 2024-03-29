package com._3dhs.tnproject.test;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    void makeLikeList(int postCode, int memberCode);
}
