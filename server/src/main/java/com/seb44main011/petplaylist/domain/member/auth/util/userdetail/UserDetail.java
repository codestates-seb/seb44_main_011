package com.seb44main011.petplaylist.domain.member.auth.util.userdetail;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetail extends Member implements UserDetails {
    public UserDetail(Member member) {
        this.updateMemberId(member.getMemberId());
        this.updateEmail(member.getEmail());
        this.updatePassword(member.getPassword());
        this.updateName(member.getName());
        this.updateProfile(member.getProfile());
        this.updatePlayLists(member.getPlayLists());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
