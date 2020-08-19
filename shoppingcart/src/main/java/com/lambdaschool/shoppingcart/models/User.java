package com.lambdaschool.shoppingcart.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User
        extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @Column(nullable = false,
        unique = true)
    private String username;

    private String comments;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private Set<UserRoles> roles = new HashSet<>();

    public User()
    {

    }

    public User(
        String username,
        String password)
    {
        this.username = username;
        this.password = password;
    }

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public List<Cart> getCarts()
    {
        return carts;
    }

    public void setCarts(List<Cart> carts)
    {
        this.carts = carts;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoCrypt(String password)
    {
        this.password = password;
    }

    public Set<UserRoles> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles)
    {
        this.roles = roles;
    }

    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (UserRoles r : this.roles)
        {
            String string = "ROLE_ " + r.getRole().getName().toUpperCase();
            list.add(new SimpleGrantedAuthority(string));
        }
        return list;
    }
}
