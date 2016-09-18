package kr.pe.kwonnam.hibernate4localdatetime.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Type(type = "kr.pe.kwonnam.hibernate4localdatetime.LocalDateTimeUserType")
    @Column(name = "createdAt", nullable = false, columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @Type(type = "kr.pe.kwonnam.hibernate4localdatetime.LocalDateTimeUserType")
    @Column(name = "updatedAt", nullable = true, columnDefinition = "datetime")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

