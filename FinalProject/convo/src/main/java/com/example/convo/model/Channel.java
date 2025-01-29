package com.example.convo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "channels")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer ownerId;

    @ManyToMany
    @JoinTable(
            name = "channel_users",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore // Изключва се, за да се избегне цикъл при сериализация
    private Set<User> members = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "channel_roles", joinColumns = @JoinColumn(name = "channel_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "role")
    @JsonIgnore // Изключва се, ако не е необходимо за сериализация
    private Map<User, String> roles = new HashMap<>();

    // Конструктори
    public Channel() {
    }

    public Channel(String name, Integer ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }

    // Getters и Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public Map<User, String> getRoles() {
        return roles;
    }

    public void setRoles(Map<User, String> roles) {
        this.roles = roles;
    }
}
