package be.bonamis.advent.year2015;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

@Slf4j
class Day04Test {

  @Test
  void solvePart01() {
    assertThat(solve("abcdef")).isEqualTo(609043);
    assertThat(solve("pqrstuv")).isEqualTo(1048970);
  }

  Integer solve(String input) {
    return IntStream.range(0, 100000000)
        .mapToObj(i -> Pair.of(md5Hash(input + i), i))
        .filter(s -> s.getLeft().startsWith("00000"))
        .findFirst()
        .map(Pair::getRight)
        .orElseThrow();
  }

  String md5Hash(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] hashBytes = md.digest(input.getBytes());

      StringBuilder sb = new StringBuilder();
      for (byte b : hashBytes) {
        sb.append(String.format("%02x", b));
      }

      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
