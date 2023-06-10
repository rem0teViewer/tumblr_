package com.tmblr.blog.models.dto;

import com.tmblr.blog.models.Message;
import com.tmblr.blog.models.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MessageDto {
    private Long id;
    private String text;
    private String tag;
    private User author;
    private String filename;
    private Long likes;
    private Boolean meLiked;
    private LocalDateTime createdDate;

    public MessageDto(Message message, Long likes, Boolean meLiked) {
        this.id = message.getId();
        this.text = message.getText();
        this.tag = message.getTag();
        this.author = message.getAuthor();
        this.filename = message.getFilename();
        this.likes = likes;
        this.meLiked = meLiked;
        this.createdDate = message.getCreatedDate();
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public String getCreatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("dd MMM HH:mm:ss"));
    }
}