package com.learn.urlshortner.service;


import com.learn.urlshortner.dto.LongURLRequest;
import com.learn.urlshortner.entity.ShortURL;
import com.learn.urlshortner.repository.URLShortenerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;



import java.util.Date;
import java.util.List;

@Service
public class URLShortenerService {

  private final URLShortenerRepository urlShortenerRepository;
  private final Base62Conversion convertor;

  public URLShortenerService(
      URLShortenerRepository urlShortenerRepository, Base62Conversion base62Conversion) {
    this.urlShortenerRepository = urlShortenerRepository;
    this.convertor = base62Conversion;
  }

  public String convertToShortURL(LongURLRequest request) {

    ShortURL shortURL = new ShortURL();
    shortURL.setLongURL(request.getLongUrl());
    shortURL.setExpirationDate(request.getExpiryDate());
    shortURL.setCreated(new Date());
    var entity = urlShortenerRepository.save(shortURL);
    var encodedUrl=convertor.encode(entity.getId());
    shortURL.setShortURL(encodedUrl);//Just for reference NOT RECOMMENDED
    urlShortenerRepository.save(shortURL);
    return encodedUrl;
  }

  public String getOriginalUrl(String shortUrl) {
    var entity = getEntity(shortUrl);
    entity.setHitCount(entity.getHitCount() + 1);
    urlShortenerRepository.save(entity);
    if (entity.getExpirationDate() != null && entity.getExpirationDate().before(new Date())) {
      urlShortenerRepository.delete(entity);
      throw new EntityNotFoundException("Link has been expired!!");
    }
    return entity.getLongURL();
  }

  public long getHitCount(String shortUrl) {
    var entity = getEntity(shortUrl);
    return entity.getHitCount();
  }

  private ShortURL getEntity(String shortUrl) {
    var id = convertor.decode(shortUrl);
    return
        urlShortenerRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("No result found for entity : " + shortUrl));
  }

  public List<ShortURL> getAllUrls() {
    return urlShortenerRepository.findAll();
  }
}
