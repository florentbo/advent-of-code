package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static be.bonamis.advent.year2021.Day16.hexToBin;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.year2021.Day16.Packet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day16Test {

    private Day16 day;

    @BeforeEach
    void setUp() {
        List<String> data = getLines("2021_day16_test.txt");
        day = new Day16(data);
    }

	/*
	00111000000000000110111101000101001010010001001000000000
VVVTTTILLLLLLLLLLLLLLLAAAAAAAAAAABBBBBBBBBBBBBBBB
The three bits labeled V (001) are the packet version, 1.
The three bits labeled T (110) are the packet type ID, 6, which means the packet is an operator.
The bit labeled I (0) is the length type ID, which indicates that the length is a 15-bit number representing the number of bits in the sub-packets.
The 15 bits labeled L (000000000011011) contain the length of the sub-packets in bits, 27.
The 11 bits labeled A contain the first sub-packet, a literal value representing the number 10.
The 16 bits labeled B contain the second sub-packet, a literal value representing the number 20.

38006F45291200
00111000000000000110111101000101001010010001001000000000
00111000000000000110111101000101001010010001001000000000


	 */

    @Test
    void solvePart01() {
        assertEquals(18, day.solvePart01());
    }

    @Test
    void solvePart02() {
        assertEquals(19, day.solvePart02());
    }

    @Test
    void test_hexToBin() {
        assertEquals("110100101111111000101000", day.getDecimal("D2FE28"));
        assertEquals("110100101111111000101000", hexToBin("D2FE28"));
        assertEquals("110100101111111000101000", Integer.toBinaryString(Integer.parseInt("D2FE28", 16)));
    }
    @Test
    void test_binToHex() {
        assertEquals("D2FE28", Day16.binToHex("110100101111111000101000").toUpperCase());
        assertEquals("1", Day16.binToHex("0001"));
        assertEquals("6", Day16.binToHex("0110"));
    }

    @Test
    void packet_parser() {
        Packet packet = new Packet("00111000000000000110111101000101001010010001001000000000");
        assertEquals(1, packet.getVersion());
        assertEquals(6, packet.getTypeID().getId());
        assertEquals(Packet.TypeId.OPERATOR, packet.getTypeID());
        assertEquals(0, packet.getLenghTypeID());
        assertEquals(27, packet.getSubPacketsLength());
    }

    @Test
    void name() {
        System.out.println(hexToBin("D2FE28"));
        String binAddr = Integer.toBinaryString(Integer.parseInt("D2FE28", 16));

        System.out.println(binAddr);
    }
}
