package com.dogworld.dogdog.member.domain;

import com.dogworld.dogdog.member.interfaces.dto.request.MemberRequest;
import com.dogworld.dogdog.common.domain.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50, nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(length = 20, nullable = false, unique = true)
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MemberRole role;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MemberStatus status;

  @Column(nullable = false)
  private boolean agreedTerms;

  @Column(nullable = false)
  private boolean agreedPrivacy;

  @Column(nullable = false)
  private boolean agreedMarketing;

  private LocalDateTime marketingAgreedAt;

  private LocalDateTime deletedAt;

  private Member(String username, String password, String name, String email, String phoneNumber,
      MemberRole role, MemberStatus status, boolean agreedTerms, boolean agreedPrivacy,
      boolean agreedMarketing, LocalDateTime marketingAgreedAt) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.role = (role != null) ? role : MemberRole.MEMBER;
    this.status = (status != null) ? status : MemberStatus.ACTIVE;
    this.agreedTerms = agreedTerms;
    this.agreedPrivacy = agreedPrivacy;
    this.agreedMarketing = agreedMarketing;
    this.marketingAgreedAt = marketingAgreedAt;
  }

  public static Member create(MemberRequest request, BCryptPasswordEncoder passwordEncoder) {
    return new Member(
        request.getUsername(),
        passwordEncoder.encode(request.getPassword()),
        request.getName(),
        request.getEmail(),
        request.getPhoneNumber(),
        request.getRole(),
        null,
        request.isAgreedTerms(),
        request.isAgreedPrivacy(),
        request.isAgreedMarketing(),
        request.isAgreedMarketing() ? LocalDateTime.now() : null
        );
  }

  @Override
  public String toString() {
    return "Member{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", role=" + role +
            ", status=" + status +
            ", agreedTerms=" + agreedTerms +
            ", agreedPrivacy=" + agreedPrivacy +
            ", agreedMarketing=" + agreedMarketing +
            ", marketingAgreedAt=" + marketingAgreedAt +
            ", deletedAt=" + deletedAt +
            '}';
  }
}
