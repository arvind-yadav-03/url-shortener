package com.learn.urlshortner.controller;

import com.learn.urlshortner.dto.LongURLRequest;
import com.learn.urlshortner.entity.ShortURL;
import com.learn.urlshortner.service.URLShortenerService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/url-shortener/")
public class URLShortenerController {

  private final URLShortenerService shortenerService;

  public URLShortenerController(URLShortenerService urlShortenerService) {
    this.shortenerService = urlShortenerService;
  }

  @PostMapping()
  public String convertToShortUrl(@RequestBody LongURLRequest request) {
    return shortenerService.convertToShortURL(request);
  }

  @GetMapping(value = "{shortUrl}")
  @Cacheable(value = "urls", key = "#shortUrl", sync = true)
  public void getAndRedirect(HttpServletResponse response, @PathVariable String shortUrl)
      throws EntityNotFoundException {
    try {

      var url = shortenerService.getOriginalUrl(shortUrl);
      response.sendRedirect(url);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    } catch (IOException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Unable to redirect to URL.Please try again");
    }
  }

  @GetMapping(value = "{shortUrl}/hit-count")
  public String getHitCount(@PathVariable String shortUrl)throws EntityNotFoundException  {
      long hitCount;
      try{
        hitCount = shortenerService.getHitCount(shortUrl);
    }
   catch (EntityNotFoundException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
      return String.format("Total Hits:%d", hitCount);
  }

  @GetMapping()
  public List<ShortURL> getAllUrls() {
    return shortenerService.getAllUrls();
  }
}
