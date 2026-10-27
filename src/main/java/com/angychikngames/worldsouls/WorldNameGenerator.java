package com.angychikngames.worldsouls;

import java.util.random.RandomGenerator;

public final class WorldNameGenerator {
    private static final String[] STARTS = {
        "a", "e", "i", "o", "u", "ae", "es", "il", "nu", "ar", "nei", "or",
        "aur", "el", "isa", "quel", "za", "ze"
    };
    private static final String[] ACCENT_STARTS = {
        "az", "ith", "vyl", "q", "qu", "zh"
    };
    private static final String[] CONSONANTS = {
        "v", "l", "r", "n", "m", "z", "qu"
    };
    private static final String[] ACCENT_CONSONANTS = {
        "th", "sk", "k", "s", "h", "t", "q", "zh", "c"
    };
    private static final String[] SYLLABLES = {
        "mea", "elia", "sera", "thea", "oria", "ara", "ielle", "ira",
        "elle", "wyn", "eth", "is", "os", "une", "quel", "za", "zen"
    };
    private static final String[] REFERENCE_STARTS = {
        "tatoo", "nab", "alder", "corus", "dan", "korri", "ty",
        "il", "musta", "kash", "datho", "manda", "mala", "mor",
        "onde", "ry", "bes", "dago", "kami", "geo", "sca",
        "lo", "jed", "ho", "en", "core", "os", "felu",
        "uta", "sere", "ro", "mon", "vul", "rom", "re",
        "qo", "an", "ba", "beta", "ri", "feren", "card",
        "tri", "deno", "tel", "ta", "pro", "arra", "cala",
        "gie", "kai", "salu", "ze", "tal", "ae", "phaa",
        "rea", "sang", "doi", "jan", "thes", "pala", "tu",
        "sur", "ran", "fe", "no", "vir", "eden", "i",
        "aze", "drae", "ar", "ka", "mar", "xo", "nath",
        "elu", "ni", "mas", "se", "obli", "hydae", "zo",
        "sp", "vali", "vana", "eo", "ne", "eu", "io",
        "fun", "sava", "terra", "fen", "cad", "mac", "noc",
        "sol", "mag", "betel", "tra", "hel", "hyper", "cap",
        "ko", "gal", "sk", "ave", "ast", "pan", "ath",
        "art", "apo", "he", "perse", "de", "her", "posei",
        "dio", "hepha", "gai", "ny", "e", "oura", "chro",
        "rh", "the", "oce", "teth", "ja", "ves", "ju",
        "ce", "min", "nep", "sa", "ve", "mer", "plu",
        "o", "fri", "fre", "frey", "tho", "id", "ska",
        "njo", "bal", "ei", "si", "anu", "hat", "sekh",
        "bas", "a", "neph", "in", "ish", "nin", "eresh",
        "tia", "ama", "tsuku", "susa", "iza", "quetzal", "huitzilo",
        "neme", "har", "pho", "dei", "hes", "hyp", "thana",
        "aphro", "dia", "bello", "for", "au", "po", "flo",
        "fau", "sil", "quiri", "ter", "bri", "cern", "rhi",
        "cerr", "arian", "da", "lu", "man", "nua", "og",
        "morri", "aen", "bele", "tara", "su", "per", "mo",
        "sva", "dazh", "stri", "la", "mora", "zor", "uk",
        "ilma", "mie", "ah", "nyy", "lai", "sau", "me",
        "ga", "aus",
        "aby", "chul", "daka", "toll", "ori", "cel", "atlan",
        "helio", "kh", "pracla", "tao", "visu", "lan", "sat",
        "ge", "asu", "otha", "ere", "icar", "des", "so",
        "nir", "olo", "camu", "qe", "kal", "te", "na",
        "tag", "han"
    };
    private static final String[] REFERENCE_SYLLABLES = {
        "ine", "oo", "aan", "cant", "tooine", "ban", "thon",
        "um", "far", "yyyk", "mir", "lore", "chor", "tis",
        "ron", "loth", "pin", "bah", "no", "nosis", "rif",
        "thal", "ha", "th", "dor", "llia", "sus", "cia",
        "pau", "nno", "dia", "cala", "can", "ulus", "mus",
        "nos", "doria", "jor", "zed", "sa", "ginar", "assia",
        "ll", "bula", "lar", "los", "gel", "cyon", "kis",
        "dan", "di", "tain", "chese", "bes", "lon", "ther",
        "ze", "ch", "helios", "laho", "sac", "jur", "sia",
        "ven", "chanka", "kesh", "noch", "lium", "ros", "veria",
        "mire", "prime", "roth", "nor", "gus", "resh", "dum",
        "reza", "ne", "rn", "ser", "cunda", "therius", "vion",
        "lyn", "diark", "ira", "sthea", "diel", "valice", "rzea",
        "ssus", "ropa", "na", "dament", "thun", "nova", "ris",
        "ia", "ragge", "turne", "aris", "rathea", "geuse", "ntor",
        "ion", "rica", "bol", "lifrey", "aro", "tera", "lorn",
        "raea", "dora", "ena", "emis", "llo", "cate", "phone",
        "meter", "ra", "mes", "don", "nysus", "estus", "a",
        "x", "lene", "os", "lios", "eria", "ea", "anus",
        "ys", "nus", "ta", "res", "erva", "piter", "tune",
        "turn", "cury", "to", "din", "gg", "ya", "r",
        "ki", "unn", "rd", "dr", "f", "sis", "siris",
        "bis", "hor", "met", "tet", "mun", "rus", "thys",
        "anna", "tar", "lil", "hursag", "kigal", "mat", "duk",
        "terasu", "yomi", "noo", "nagi", "nami", "coatl", "pochtli",
        "ke", "monia", "che", "bos", "mos", "tia", "be",
        "tos", "rebos", "dite", "tuna", "rora", "mona", "vanus",
        "minus", "gid", "unnos", "annon", "idwen", "rhod", "nu",
        "gh", "annan", "da", "ma", "gan", "pona", "nis",
        "cellus", "un", "les", "kosh", "rog", "bog", "glav",
        "ko", "pio", "likki", "ti", "rikki", "kunas", "le",
        "ness", "myna", "bija", "teja",
        "dos", "ak", "ana", "lla", "estis", "polis", "eb",
        "rush", "nas", "tea", "eda", "ff", "nii", "ras",
        "la", "bus", "us", "tiny", "phis", "al", "kar",
        "rti", "kun", "lus", "tesh", "tu", "lana", "galus",
        "gara", "ronda", "gar", "sya", "rea", "ka"
    };
    private static final String[] VOWELS = {
        "a", "e", "i", "o", "u", "ae", "ia", "ai", "ei", "y"
    };
    private static final String[] ENDINGS = {
        "n", "th", "oth", "us", "ar", "or"
    };
    private static final String[] DESIGNATIONS = {
        "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII",
        "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX"
    };
    private static final int[] LENGTHS = {2, 2, 2, 3, 3, 4};

    private WorldNameGenerator() {
    }

    public static String generate(RandomGenerator random) {
        if (random.nextInt(100) == 0) {
            StringBuilder candidate = new StringBuilder(REFERENCE_STARTS[random.nextInt(REFERENCE_STARTS.length)]);
            candidate.append(REFERENCE_SYLLABLES[random.nextInt(REFERENCE_SYLLABLES.length)]);
            if (candidate.length() >= 3 && candidate.length() <= 16 && !NameFilter.isBlocked(candidate.toString())) {
                candidate.setCharAt(0, Character.toUpperCase(candidate.charAt(0)));
                return addDesignation(candidate.toString(), random);
            }
        }
        boolean guided = random.nextInt(100) >= 15;
        StringBuilder name;
        do {
            String[] starts;
            if (random.nextInt(5) == 0) {
                starts = REFERENCE_STARTS;
            } else {
                starts = random.nextInt(4) == 0 ? ACCENT_STARTS : STARTS;
            }
            name = new StringBuilder(starts[random.nextInt(starts.length)]);
            int length = LENGTHS[random.nextInt(LENGTHS.length)];
            int apostrophe = random.nextInt(10) == 0 ? 1 + random.nextInt(length - 1) : -1;
            for (int i = 1; i < length; i++) {
                if (i == apostrophe) {
                    name.append('\'');
                }
                for (int attempt = 0; attempt < 4; attempt++) {
                    String syllable;
                    if (random.nextInt(3) == 0) {
                        String[] syllables = random.nextInt(5) == 0 ? REFERENCE_SYLLABLES : SYLLABLES;
                        syllable = syllables[random.nextInt(syllables.length)];
                    } else {
                        String[] consonants = random.nextInt(8) == 0 ? ACCENT_CONSONANTS : CONSONANTS;
                        syllable = consonants[random.nextInt(consonants.length)]
                                + VOWELS[random.nextInt(VOWELS.length)];
                    }
                    if (!guided || !awkwardJoin(name.toString(), syllable) || attempt == 3) {
                        name.append(syllable);
                        break;
                    }
                }
            }
            if (random.nextInt(6) == 0) {
                name.append(ENDINGS[random.nextInt(ENDINGS.length)]);
            }
        } while (name.length() < 3 || name.length() > 16 || NameFilter.isBlocked(name.toString()));
        name.setCharAt(0, Character.toUpperCase(name.charAt(0)));
        return addDesignation(name.toString(), random);
    }
    private static String addDesignation(String name, RandomGenerator random) {
        if (random.nextInt(20) == 0) {
            int index = random.nextInt(10) == 0
                    ? 12 + random.nextInt(8)
                    : random.nextInt(12);
            String designation = DESIGNATIONS[index];
            if (name.length() + 1 + designation.length() <= 16) {
                return name + " " + designation;
            }
        }
        return name;
    }
    private static boolean awkwardJoin(String left, String right) {
        for (String group : new String[] {"th", "kh", "zh", "qu"}) {
            left = left.replace(group, group.substring(0, 1));
            right = right.replace(group, group.substring(0, 1));
        }
        char last = left.charAt(left.length() - 1);
        if (last == '\'') {
            return false;
        }
        boolean vowel = "aeiouy".indexOf(last) >= 0;
        int count = 0;
        for (int i = left.length() - 1; i >= 0; i--) {
            char c = left.charAt(i);
            if (c == '\'' || ("aeiouy".indexOf(c) >= 0) != vowel) {
                break;
            }
            count++;
        }
        int before = count;
        for (int i = 0; i < right.length(); i++) {
            if (("aeiouy".indexOf(right.charAt(i)) >= 0) != vowel) {
                break;
            }
            count++;
        }
        return count > before && count > 2;
    }
}


