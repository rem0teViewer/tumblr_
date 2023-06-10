package com.tmblr.blog.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "Too long")
    private String text;
    @Length(max = 255, message = "Too long")
    private String tag;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String filename;
    @ManyToMany
    @JoinTable(
            name = "message_likes",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public Message(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

}
