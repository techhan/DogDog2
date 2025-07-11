package com.dogworld.dogdog.member.domain;

import com.dogworld.dogdog.member.interfaces.dto.request.MemberRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


public class TestMemberFactory {


  private PasswordEncoder encoder;

  public static Member createMember() {
    MemberRequest request = MemberRequest.builder()
        .username("id1")
        .password("password1")
        .email("email1")
        .name("name1")
        .password("010-0000-0000")
        .build();

    //Member member Member.create(request, )
    return null;
  }

}
