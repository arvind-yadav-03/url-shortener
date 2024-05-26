package com.learn.urlshortner.service;

import org.springframework.stereotype.Service;

@Service
public class Base62Conversion {
  private static final String allowedString =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private final char[] allowedCharacters = allowedString.toCharArray();
  private final int base = allowedCharacters.length;

  public String encode(long input) {
    var encodedString = new StringBuilder();

    if (input == 0) {
      return String.valueOf(allowedCharacters[0]);
    }

    while (input > 0) {
      encodedString.append(allowedCharacters[(int) (input % base)]);
      input = input / base;
    }

    return encodedString.reverse().toString();
  }

  public long decode(String input) {
    var characters = input.toCharArray();
    var length = characters.length;

    var decoded = 0;

    // counter is used to avoid reversing input string
    var counter = 1;
    for (char ch:characters) {
      decoded += allowedString.indexOf(ch) * Math.pow(base, length - counter);
      counter++;
    }
    return decoded;
  }
}
