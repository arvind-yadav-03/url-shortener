package com.learn.urlshortner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tblURL")
public class ShortURL {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private long id;

  @Column(nullable = false)
  private String longURL;

  @JsonIgnore
  private String shortURL;

  @Column(nullable = false)
  private Date created;

  public long getHitCount() {
    return hitCount;
  }

  public void setHitCount(long hitCount) {
    this.hitCount = hitCount;
  }

  private long hitCount;

  private Date expirationDate;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLongURL() {
    return longURL;
  }
    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }


    public void setLongURL(String longURL) {
    this.longURL = longURL;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }
}
