package com.learn.urlshortner.repository;

import com.learn.urlshortner.entity.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLShortenerRepository extends JpaRepository<ShortURL,Long>{}
