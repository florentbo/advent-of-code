package be.bonamis.advent.year2015;

import java.security.MessageDigest;

public class MD5HashExample {
  public static void main(String[] args) throws Exception {
    String input = "abcdef609043";

    MessageDigest md = MessageDigest.getInstance("MD5");

    md.update(input.getBytes());

    byte[] hashBytes = md.digest();

    StringBuilder sb = new StringBuilder();
    for (byte b : hashBytes) {
      sb.append(String.format("%02x", b));
    }

    System.out.println("MD5 Hash: " + sb);
  }
}
