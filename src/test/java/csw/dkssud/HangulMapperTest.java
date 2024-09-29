package csw.dkssud;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HangulMapperTest {

    @Test
    public void testQwertyToHangul() {
        // Simple cases
        assertEquals("안녕", HangulMapper.qwertyToHangul("dkssud"));
        assertEquals("가나다라", HangulMapper.qwertyToHangul("rkskekfk"));

        // Complex cases
        assertEquals("하 ㅋㅋㅋㅋ 메롱", HangulMapper.qwertyToHangul("gk zzzz apfhd"));
        assertEquals("거끼까지야ㅑ", HangulMapper.qwertyToHangul("rjRlRkwldii"));
        assertEquals("계룡", HangulMapper.qwertyToHangul("rPfyd"));
        assertEquals("가깐ㄹㄱ닭미췬뷁야야얄꼐류1", HangulMapper.qwertyToHangul("rkRksfrekfralcnlsqnpfrdididifRPfB1"));

        // Edge cases
        assertEquals("", HangulMapper.qwertyToHangul(""));
        assertEquals("123", HangulMapper.qwertyToHangul("123"));
        assertEquals("안녕123", HangulMapper.qwertyToHangul("dkssud123"));

        // Special characters
        assertEquals("안녕!", HangulMapper.qwertyToHangul("dkssud!"));
    }

    @Test
    public void testHangulToQwerty() {
        // Simple cases
        assertEquals("dkssudgktpdy", HangulMapper.hangulToQwerty("안녕하세요"));
        assertEquals("anlfrRkfrRkR", HangulMapper.hangulToQwerty("뮑깕깎"));
        assertEquals("abcd", HangulMapper.hangulToQwerty("뮻ㅇ"));

        // Edge cases
        assertEquals("", HangulMapper.hangulToQwerty(""));
        assertEquals("123", HangulMapper.hangulToQwerty("123"));
        assertEquals("dkssud123", HangulMapper.hangulToQwerty("안녕123"));

        // Complex cases
        String hangulInput = "가깐ㄹㄱ닭미췬뷁야야얄꼐류1";
        String expectedQwerty = "rkRksfrekfralcnlsqnpfrdididifRPfb1";
        assertEquals(expectedQwerty, HangulMapper.hangulToQwerty(hangulInput));

        // Special characters
        assertEquals("dkssud!", HangulMapper.hangulToQwerty("안녕!"));
    }

    @Test
    public void testIsQwertyHangul() {
        assertTrue(HangulMapper.isQwertyHangul("dkssud"));
        assertFalse(HangulMapper.isQwertyHangul("안녕하세요"));
        assertFalse(HangulMapper.isQwertyHangul("123"));
        assertTrue(HangulMapper.isQwertyHangul("dkssud123"));
        assertFalse(HangulMapper.isQwertyHangul("경기도"));
        assertFalse(HangulMapper.isQwertyHangul("가낟ㄱㄴㅁㄱ닥ㄴㄷ"));
        assertTrue(HangulMapper.isQwertyHangul("hello there"));
        assertFalse(HangulMapper.isQwertyHangul("안녕123"));
        assertFalse(HangulMapper.isQwertyHangul("뮻ㅇ"));
        assertTrue(HangulMapper.isQwertyHangul("abcd"));
    }

    @Test
    public void testIsAttachAvailable() {
        assertEquals(2, HangulMapper.isAttachAvailable('r', 'k')); // 자 + 모
        assertEquals(0, HangulMapper.isAttachAvailable('k', 'o')); // 모 + 모, 결합 불가
        assertEquals(4, HangulMapper.isAttachAvailable('k', 'r')); // 모 + 자
        assertEquals(3, HangulMapper.isAttachAvailable('h', 'k')); // 모 + 모, composite vowel
        assertEquals(5, HangulMapper.isAttachAvailable('r', 't')); // 자 + 자, forming double consonant ("rt")
        assertEquals(0, HangulMapper.isAttachAvailable('a', 'v')); // Not attachable
        assertEquals(4, HangulMapper.isAttachAvailable('k', 'z')); // 모 + other
        assertEquals(0, HangulMapper.isAttachAvailable('R', 'R')); // 자 + 자
    }

    @Test
    public void testInvalidInputs() {
        assertEquals("!@#$%^&*()", HangulMapper.qwertyToHangul("!@#$%^&*()"));
        assertEquals("!@#$%^&*()", HangulMapper.hangulToQwerty("!@#$%^&*()"));
        assertEquals("", HangulMapper.qwertyToHangul(""));
        assertEquals("", HangulMapper.hangulToQwerty(""));
    }

}
