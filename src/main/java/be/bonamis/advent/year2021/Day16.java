package be.bonamis.advent.year2021;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.bonamis.advent.DaySolver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day16 extends DaySolver<String> {
    public Day16(List<String> puzzle) {
        super(puzzle);
    }

    static String binToHex(String s) {
        int decimal = Integer.parseInt(s,2);
        return Integer.toString(decimal,16);
    }

    @Override
    public long solvePart01() {
        return puzzle.get(0).length();
    }

    @Override
    public long solvePart02() {
        return puzzle.get(0).length() + 1;
    }

    public static void main(String[] args) {


    }

    static String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    @Getter
    static class Packet {
        private final int version;
        private final TypeId typeID;
        private final Integer lenghTypeID;
        private final int subPacketsLength;

        public Packet(String input) {
            String versionText = "0" + input.substring(0, 3);
            String typeText = "0" + input.substring(3, 6);

            String string = binToHex(versionText);
            this.version = Integer.parseInt(string);
            String string1 = binToHex(typeText);
            this.typeID = TypeId.valueOf(Integer.parseInt(string1));
            //this.lenghTypeID = Integer.parseInt(input.substring(6, 8), 16);
            this.lenghTypeID = Integer.parseInt(input.substring(6, 7));
            this.subPacketsLength = Integer.parseInt(input.substring(7, 22), 2);
        }

        enum TypeId {
            LITERAL(4),
            OPERATOR(6);

            private final int id;

            TypeId(int id) {
                this.id = id;
            }

            static TypeId valueOf(int id) {
                return Arrays.stream(TypeId.values()).filter(typeId -> typeId.getId() == id).findFirst().orElse(null);
            }

            public int getId() {
                return id;
            }
        }

    }

    /*private static Character findHexaValue(String versionText) {
        return hexadecimalToBinaryMap().
                entrySet().stream()
                .filter(entry -> entry.getValue().equals(versionText)).findFirst().map(Map.Entry::getKey).orElseThrow();
    }*/


    static String list = "0 = 0000\n" +
            "1 = 0001\n" +
            "2 = 0010\n" +
            "3 = 0011\n" +
            "4 = 0100\n" +
            "5 = 0101\n" +
            "6 = 0110\n" +
            "7 = 0111\n" +
            "8 = 1000\n" +
            "9 = 1001\n" +
            "A = 1010\n" +
            "B = 1011\n" +
            "C = 1100\n" +
            "D = 1101\n" +
            "E = 1110\n" +
            "F = 1111";


    public String getDecimal(String hex) {


        var hexa = hexadecimalToBinaryMap();

        return hex.chars().mapToObj(c -> (char) c).map(hexa::get).collect(Collectors.joining());
    }

    static Map<Character, String> hexadecimalToBinaryMap() {
        return getHexa(list);
    }

    static int binToDec(String s) {
        return Integer.parseInt(new BigInteger(s, 2).toString(10));
    }


    private static Map<Character, String> getHexa(String list) {
        return Stream.of(list.split("\n"))
                .map(s -> s.split(" = "))
                .map(s -> new String[]{s[0], s[1]})
                .collect(Collectors.toMap(s -> s[0].charAt(0), s -> s[1]));
    }
}

