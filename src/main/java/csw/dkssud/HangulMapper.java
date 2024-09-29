package csw.dkssud;

import java.util.*;

public class HangulMapper {
    // Constants for bitmasks
    private static final int T = 0b00010000;
    private static final int M = 0b00000100;
    private static final int B = 0b00000001;
    private static final int TM = T + M;
    private static final int TMM = T + M + M;
    private static final int TMB = T + M + B;
    private static final int TMMB = T + M + M + B;
    private static final int TMBB = T + M + B + B;
    private static final int TMMBB = T + M + M + B + B;

    // combLen maps combinations to their lengths in terms of characters
    private static final Map<Integer, Integer> COMB_LEN = new HashMap<>();

    static {
        COMB_LEN.put(T, 1);
        COMB_LEN.put(TM, 2);
        COMB_LEN.put(TMM, 3);
        COMB_LEN.put(TMB, 3);
        COMB_LEN.put(TMMB, 4);
        COMB_LEN.put(TMBB, 4);
        COMB_LEN.put(TMMBB, 5);
    }

    // Mapping from QWERTY consonants to Hangul initial consonants (초성)
    private static final Map<String, Integer> KO_TOP_EN_MAP = new HashMap<>();

    // Mapping from QWERTY vowels to Hangul vowels (중성)
    private static final Map<String, Integer> KO_MID_EN_MAP = new HashMap<>();

    // Mapping from QWERTY consonants to Hangul final consonants (종성)
    private static final Map<String, Integer> KO_BOT_EN_MAP = new HashMap<>();

    // Reverse mappings for Hangul to QWERTY conversion
    private static final String[] KO_TOP_EN = {
            "r", "R", "s", "e", "E", "f", "a", "q", "Q",
            "t", "T", "d", "w", "W", "c", "z", "x", "v", "g"
    };

    private static final String[] KO_MID_EN = {
            "k", "o", "i", "O", "j", "p", "u", "P", "h",
            "hk", "ho", "hl", "y", "n", "nj", "np", "nl",
            "b", "m", "ml", "l"
    };

    private static final String[] KO_BOT_EN = {
            "", "r", "R", "rt", "s", "sw", "sg", "e", "f",
            "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a",
            "q", "qt", "t", "T", "d", "w", "c", "z", "x",
            "v", "g"
    };

    // Sets for character classification
    private static final Set<Character> KO_TOP_EN_SET = new HashSet<>();
    private static final Set<Character> KO_MID_EN_SET_SINGLE_CHAR = new HashSet<>();
    private static final Set<String> KO_MID_EN_SET_MULTI_CHAR = new HashSet<>();
    private static final Set<Character> KO_BOT_EN_SET_SINGLE_CHAR = new HashSet<>();
    private static final Set<String> KO_BOT_EN_SET_MULTI_CHAR = new HashSet<>();

    // Uppercase letters to keep as uppercase
    private static final Set<Character> UPPERCASE_LETTERS_TO_KEEP = new HashSet<>();

    private static final Map<String, Integer> RAW_MAPPER_MAP = new HashMap<>();

    // Arrays for reverse mappings
    private static final String[] RAW_MAPPER = {
            "r", "R", "rt", "s", "sw", "sg", "e", "E", "f", "fr", "fa",
            "fq", "ft", "fx", "fv", "fg", "a", "q", "Q", "qt", "t", "T",
            "d", "w", "W", "c", "z", "x", "v", "g", "k", "o", "i", "O",
            "j", "p", "u", "P", "h", "hk", "ho", "hl", "y", "n", "nj",
            "np", "nl", "b", "m", "ml", "l"
    };

    static {
        UPPERCASE_LETTERS_TO_KEEP.add('T');
        UPPERCASE_LETTERS_TO_KEEP.add('R');
        UPPERCASE_LETTERS_TO_KEEP.add('E');
        UPPERCASE_LETTERS_TO_KEEP.add('Q');
        UPPERCASE_LETTERS_TO_KEEP.add('P');
        UPPERCASE_LETTERS_TO_KEEP.add('O');
        UPPERCASE_LETTERS_TO_KEEP.add('W');
    }

    static {
        // Initialize KO_TOP_EN_SET (consonants)
        char[] koTopEnChars = {'r', 'R', 's', 'e', 'E', 'f', 'a', 'q', 'Q',
                't', 'T', 'd', 'w', 'W', 'c', 'z', 'x', 'v', 'g'};
        for (char c : koTopEnChars) {
            KO_TOP_EN_SET.add(c);
        }

        // Initialize KO_MID_EN_SET_SINGLE_CHAR (single vowels)
        char[] koMidEnSingleChars = {'k', 'o', 'i', 'O', 'j', 'p', 'u', 'P',
                'h', 'y', 'n', 'b', 'm', 'l'};
        for (char c : koMidEnSingleChars) {
            KO_MID_EN_SET_SINGLE_CHAR.add(c);
        }

        // Initialize KO_MID_EN_SET_MULTI_CHAR (double vowels)
        String[] koMidEnMultiChars = {"hk", "ho", "hl", "nj", "np", "nl", "ml"};
        KO_MID_EN_SET_MULTI_CHAR.addAll(Arrays.asList(koMidEnMultiChars));

        // Initialize KO_BOT_EN_SET_SINGLE_CHAR (single final consonants)
        KO_BOT_EN_SET_SINGLE_CHAR.addAll(KO_TOP_EN_SET);

        // Initialize KO_BOT_EN_SET_MULTI_CHAR (double final consonants)
        String[] koBotEnMultiChars = {"rt", "sw", "sg", "fr", "fa", "fq", "ft",
                "fx", "fv", "fg", "qt", "df", "cz", "xg"};
        KO_BOT_EN_SET_MULTI_CHAR.addAll(Arrays.asList(koBotEnMultiChars));
    }

    static {
        // Initialize KO_TOP_EN_MAP
        String[] koTopEnKeys = {
                "r", "R", "s", "e", "E", "f", "a", "q", "Q",
                "t", "T", "d", "w", "W", "c", "z", "x", "v", "g"
        };
        for (int i = 0; i < koTopEnKeys.length; i++) {
            KO_TOP_EN_MAP.put(koTopEnKeys[i], i);
        }

        // Initialize KO_MID_EN_MAP
        String[] koMidEnKeys = {
                "k", "o", "i", "O", "j", "p", "u", "P", "h",
                "hk", "ho", "hl", "y", "n", "nj", "np", "nl",
                "b", "m", "ml", "l"
        };
        for (int i = 0; i < koMidEnKeys.length; i++) {
            KO_MID_EN_MAP.put(koMidEnKeys[i], i);
        }

        // Initialize KO_BOT_EN_MAP
        String[] koBotEnKeys = {
                "", "r", "R", "rt", "s", "sw", "sg", "e", "f",
                "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a",
                "q", "qt", "t", "T", "d", "w", "c", "z", "x",
                "v", "g"
        };
        for (int i = 0; i < koBotEnKeys.length; i++) {
            KO_BOT_EN_MAP.put(koBotEnKeys[i], i);
        }

        // Initialize RAW_MAPPER_MAP
        for (int i = 0; i < RAW_MAPPER.length; i++) {
            RAW_MAPPER_MAP.put(RAW_MAPPER[i], i);
        }
    }

    public static String qwertyToHangul(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        List<String[]> charGroups = splitEn(input);
        StringBuilder convertedString = new StringBuilder(input.length());

        for (String[] charGroup : charGroups) {
            if (charGroup.length == 1) {
                String ch = charGroup[0];
                Integer idx = RAW_MAPPER_MAP.get(ch);
                if (idx != null) {
                    convertedString.append((char) (idx + 0x3131)); // 12593 in decimal
                } else {
                    convertedString.append(ch);
                }
                continue;
            }

            int topIdx = -1, midIdx = -1, botIdx = 0;

            for (int j = 0; j < charGroup.length; j++) {
                String ch = charGroup[j];

                if (ch.isEmpty()) continue;

                char r = ch.charAt(0);
                if (!Character.isLetter(r) || Character.isDigit(r) || ch.equals(" ")) {
                    convertedString.append(ch);
                    break;
                }

                switch (j) {
                    case 0:
                        topIdx = KO_TOP_EN_MAP.getOrDefault(ch, -1);
                        break;
                    case 1:
                        midIdx = KO_MID_EN_MAP.getOrDefault(ch, -1);
                        break;
                    case 2:
                        botIdx = KO_BOT_EN_MAP.getOrDefault(ch, 0);
                        break;
                    default:
                        break;
                }
            }

            if (topIdx != -1 && midIdx != -1) {
                char combinedChar = (char) ((topIdx * 21 * 28) + (midIdx * 28) + botIdx + 0xAC00); // 44032 in decimal
                convertedString.append(combinedChar);
            } else {
                for (String ch : charGroup) {
                    convertedString.append(ch);
                }
            }
        }

        return convertedString.toString();
    }

    public static String hangulToQwerty(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder convertedString = new StringBuilder(input.length());

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == ' ') {
                convertedString.append(' ');
            } else if (c >= 0xAC00 && c <= 0xD7A3) { // Hangul Syllables
                int syllableIndex = c - 0xAC00;
                int topIdx = syllableIndex / (21 * 28);
                int midIdx = (syllableIndex % (21 * 28)) / 28;
                int botIdx = syllableIndex % 28;

                convertedString.append(KO_TOP_EN[topIdx]);
                convertedString.append(KO_MID_EN[midIdx]);
                if (botIdx != 0) {
                    convertedString.append(KO_BOT_EN[botIdx]);
                }
            } else if (c >= 0x3131 && c <= 0x3163) { // Hangul Compatibility Jamo
                int jamoIndex = c - 0x3131;
                if (jamoIndex >= 0 && jamoIndex < RAW_MAPPER.length) {
                    convertedString.append(RAW_MAPPER[jamoIndex]);
                } else {
                    convertedString.append(c);
                }
            } else {
                convertedString.append(c);
            }
        }

        return convertedString.toString();
    }

    public static boolean isQwertyHangul(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        boolean hasLetters = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_SYLLABLES ||
                    Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO ||
                    Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_JAMO) {
                return false; // Contains Hangul characters
            }

            if (c > 0x7F || (!Character.isLetterOrDigit(c) && c != ' ')) {
                return false; // Contains non-ASCII or special characters
            }

            if (Character.isLetter(c)) {
                hasLetters = true;
            }
        }

        return hasLetters;
    }


    private static List<String[]> splitEn(String input) {
        if (input == null || input.isEmpty()) {
            return Collections.emptyList();
        }

        // Convert uppercase letters to lowercase, except for letters in UPPERCASE_LETTERS_TO_KEEP
        StringBuilder sb = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                if (!UPPERCASE_LETTERS_TO_KEEP.contains(c)) {
                    c = Character.toLowerCase(c);
                }
            }
            sb.append(c);
        }
        String processedInput = sb.toString();

        List<String[]> separated = new ArrayList<>();
        int inputLen = processedInput.length();
        int i = 0;

        while (i < inputLen) {
            int jump = 0;

            char c = processedInput.charAt(i);
            if (!isLetter(c) || isDigit(c) || c == ' ') {
                separated.add(new String[]{String.valueOf(c)});
                i++;
                continue;
            }

            int shift = 0;
            int combination = T;
            int currentIdx = i;

            int idx = currentIdx + shift;
            int idxNext = idx + 1;

            if (idxNext < inputLen) {
                int attachType = isAttachAvailable(processedInput.charAt(idx), processedInput.charAt(idxNext));
                if (attachType == 2) {
                    shift++;
                    combination += M;

                    idx = currentIdx + shift;
                    idxNext = idx + 1;

                    if (idxNext < inputLen) {
                        attachType = isAttachAvailable(processedInput.charAt(idx), processedInput.charAt(idxNext));
                        if (attachType == 3) {
                            shift++;
                            combination += M;
                            idx = currentIdx + shift;
                            idxNext = idx + 1;
                        }
                    }

                    if (idxNext < inputLen) {
                        attachType = isAttachAvailable(processedInput.charAt(idx), processedInput.charAt(idxNext));
                        if (attachType == 4) {
                            shift++;
                            combination += B;
                            idx = currentIdx + shift;
                            idxNext = idx + 1;

                            if (idxNext < inputLen) {
                                int attachment3 = isAttachAvailable(processedInput.charAt(idx), processedInput.charAt(idxNext));
                                if (attachment3 == 5) {
                                    if (idxNext + 1 == inputLen) {
                                        combination += B;
                                    } else if (idxNext + 1 < inputLen) {
                                        shift++;
                                        idx = currentIdx + shift;
                                        idxNext = idx + 1;
                                        int attachment4 = isAttachAvailable(processedInput.charAt(idx), processedInput.charAt(idxNext));
                                        if (attachment4 != 2) {
                                            combination += B;
                                        }
                                    }
                                } else if (attachment3 == 2) {
                                    combination -= B;
                                }
                            }
                        }
                    }
                }
            }

            // Append slices based on combination
            separated.add(createSliceByCombination(processedInput, currentIdx, combination));

            jump = COMB_LEN.getOrDefault(combination, 1) - 1;
            i += jump + 1;
        }

        return separated;
    }

    private static String[] createSliceByCombination(String input, int startIdx, int combination) {
        return switch (combination) {
            case T -> createSlice(input, startIdx, 1);
            case TM -> createSlice(input, startIdx, 1, 1);
            case TMM -> createSlice(input, startIdx, 1, 2);
            case TMB -> createSlice(input, startIdx, 1, 1, 1);
            case TMMB -> createSlice(input, startIdx, 1, 2, 1);
            case TMBB -> createSlice(input, startIdx, 1, 1, 2);
            case TMMBB -> createSlice(input, startIdx, 1, 2, 2);
            default ->
                // Handle unexpected combination
                    new String[]{String.valueOf(input.charAt(startIdx))};
        };
    }

    private static String[] createSlice(String input, int startIdx, int... lengths) {
        String[] result = new String[lengths.length];
        int idx = startIdx;
        for (int i = 0; i < lengths.length; i++) {
            int endIdx = idx + lengths[i];
            if (endIdx > input.length()) {
                endIdx = input.length();
            }
            result[i] = input.substring(idx, endIdx);
            idx = endIdx;
        }
        return result;
    }

    private static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    private static boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    // Determines if two characters can be attached according to Korean composition rules
    public static int isAttachAvailable(char i, char l) {
        // Check for Consonant + Vowel (자 + 모)
        if (KO_TOP_EN_SET.contains(i)) {
            if (KO_MID_EN_SET_SINGLE_CHAR.contains(l)) {
                return 2;
            }
            // For multi-character vowels, we need to handle this in the splitEn function
        }

        // Check for Vowel + Vowel (Composite Vowel) (모 + 모)
        if (KO_MID_EN_SET_SINGLE_CHAR.contains(i)) {
            if (KO_MID_EN_SET_SINGLE_CHAR.contains(l)) {
                String combined = "" + i + l;
                if (KO_MID_EN_SET_MULTI_CHAR.contains(combined)) {
                    return 3;
                }
            }
        }

        // Check for Vowel + Consonant (모 + 자)
        if (KO_MID_EN_SET_SINGLE_CHAR.contains(i) || KO_MID_EN_SET_MULTI_CHAR.contains(String.valueOf(i))) {
            if (KO_BOT_EN_SET_SINGLE_CHAR.contains(l)) {
                return 4;
            }
        }

        // Check for Double Consonant (자 + 자) in the final position
        if (KO_BOT_EN_SET_SINGLE_CHAR.contains(i) || KO_BOT_EN_SET_MULTI_CHAR.contains(String.valueOf(i))) {
            String combined = "" + i + l;
            if (KO_BOT_EN_SET_MULTI_CHAR.contains(combined)) {
                return 5;
            }
        }

        // If none of the above, they are not attachable
        return 0;
    }
}