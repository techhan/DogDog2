package com.dogworld.dogdog.member.domain;

import com.dogworld.dogdog.member.interfaces.dto.request.MemberRequest;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class TestMemberFactory {

  public static Member createMember() {
    String unique = UUID.randomUUID().toString().substring(0, 8);
    MemberRequest request = MemberRequest.builder()
        .username("id" + unique)
        .password("password")
        .email("email" + unique)
        .name("name")
        .phoneNumber("010-" + unique)
        .build();

    return Member.create(request, new BCryptPasswordEncoder());
  }

}
